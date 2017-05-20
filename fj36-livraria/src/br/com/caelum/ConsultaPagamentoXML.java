package br.com.caelum;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import br.com.caelum.payfast.generated.Pagamento;

public class ConsultaPagamentoXML {
	private static final String SERVER_URI = "http://localhost:8080/fj36-webservice";

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		Pagamento resposta = client.target(SERVER_URI + "/pagamentos/1")
			.request()
			.buildGet()
			.invoke(Pagamento.class);
		
		System.out.printf("%d %f %s\n", resposta.getId(), resposta.getValor(), resposta.getStatus());
	}
}