package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class RegistraGeradorNoTopico {

	public static void main(String[] args) throws NamingException {
		InitialContext ic = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) ic.lookup("jms/RemoteConnectionFactory");
		Topic topico = (Topic) ic.lookup("jms/TOPICO.LIVRARIA");
		
		try(JMSContext context = factory.createContext("jms", "jms2");
			Scanner teclado = new Scanner(System.in)) {
			context.setClientID("GeradorEbook");
			
			JMSConsumer consumer = context.createDurableConsumer(topico, "AssinaturaEbook", "formato='ebook'", false);
			
			consumer.setMessageListener(new TratadorDeMensagem());
			context.start();
			
			System.out.println("Gerador esperando as mensagens do tópico ...");
			System.out.println("Aperte enter para fechar a conexão");

			teclado.nextLine();
			
			consumer.close();
		}
	}
}