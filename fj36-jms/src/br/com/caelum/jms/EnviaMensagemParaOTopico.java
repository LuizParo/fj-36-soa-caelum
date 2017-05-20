package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EnviaMensagemParaOTopico {

	public static void main(String[] args) throws NamingException {
		InitialContext ic = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		Topic topico = (Topic) ic.lookup("jms/TOPICO.LIVRARIA");
		
		try(JMSContext context = factory.createContext("jms", "jms2");
			Scanner teclado = new Scanner(System.in)) {
			
			JMSProducer producer = context.createProducer();
			
			while(teclado.hasNextLine()) {
				String line = teclado.nextLine();
				producer.send(topico, line);
			}
		}
	}
}