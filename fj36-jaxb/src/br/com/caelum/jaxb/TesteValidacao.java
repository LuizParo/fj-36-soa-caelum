package br.com.caelum.jaxb;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class TesteValidacao {

	public static void main(String[] args) throws Exception {
		Livro livro = new Livro();
		livro.setCodigo("arq");
		
		JAXBContext context = JAXBContext.newInstance(Livro.class);
		JAXBSource source = new JAXBSource(context, livro);
		
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File("schema.xsd"));
		
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new ValidationHandler());
		validator.validate(source);
	}
}