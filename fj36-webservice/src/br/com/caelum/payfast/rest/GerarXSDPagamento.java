package br.com.caelum.payfast.rest;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import br.com.caelum.payfast.modelo.Pagamento;

public class GerarXSDPagamento {

	public static void main(String[] args) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Pagamento.class);
		context.generateSchema(new SchemaOutputResolver() {
			
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
				return new StreamResult(new File("pagamento.xsd"));
			}
		});
	}
}