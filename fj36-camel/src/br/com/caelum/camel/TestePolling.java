package br.com.caelum.camel;

import java.util.List;
import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import br.com.caelum.livraria.modelo.Livro;

public class TestePolling {

	public static void main(String[] args) throws Exception {
		MysqlConnectionPoolDataSource mysqlDs = new MysqlConnectionPoolDataSource();
		
		mysqlDs.setDatabaseName("fj36_camel");
		mysqlDs.setServerName("localhost");
		mysqlDs.setPort(3306);
		mysqlDs.setUser("root");
		mysqlDs.setPassword("caelum");
		
		JndiContext jndi = new JndiContext();
		jndi.rebind("mysqlDataSource",	mysqlDs);
		
		CamelContext ctx = new DefaultCamelContext(jndi);
		
		ctx.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				
				this.from("http://localhost:8088/fj36-livraria/loja/livros/mais-vendidos")
					.delay(1000)
					.unmarshal()
					.json()
					.process(exchange -> {
						List<?> livros = exchange.getIn().getBody(List.class);
						
						Message message = exchange.getIn();
						message.setBody(livros.get(0));
					})
					.log("${body}")
					.to("direct:livros");
				
				this.from("direct:livros")
					.split(body())
					.process(exchange -> {
						Message inbound = exchange.getIn();
						
						Livro livro = (Livro) inbound.getBody();
						inbound.setHeader("nomeAutor", livro.getNomeAutor());
					})
					.setBody(simple("insert	into Livros (nomeAutor) values ('${in.header[nomeAutor]}')"))
					.to("jdbc:mysqlDataSource?useHeadersAsParameters=true");
			}
		});
		
		ctx.start();
		
		try(Scanner scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}
		
		ctx.stop();
	}
}