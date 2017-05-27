package br.com.caelum.camel;

import java.util.List;
import java.util.Scanner;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class TestePolling {

	public static void main(String[] args) throws Exception {
		CamelContext ctx = new DefaultCamelContext();
		
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
					.to("mock:livros");
			}
		});
		
		ctx.start();
		
		try(Scanner scanner = new Scanner(System.in)) {
			scanner.nextLine();
		}
		
		ctx.stop();
	}
}