package br.com.caelum.livraria.jms;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import br.com.caelum.livraria.jaxb.SerializadorXml;
import br.com.caelum.livraria.modelo.Pedido;

@Component
@Lazy(true)
public class EnviadorMensagemJms implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ConnectionFactory factory;
	private Topic topico;
	
	@Autowired
	public EnviadorMensagemJms(ConnectionFactory factory, Topic topico) {
		this.factory = factory;
		this.topico = topico;
	}

	public void enviar(Pedido pedido) {
		System.out.println("JMS: Enviando pedido:" + pedido);
		try(JMSContext context = this.factory.createContext("jms", "jms2")) {
			JMSProducer producer = context.createProducer();
			producer.setProperty("formato",	pedido.getFormato());
			
			String xml = new SerializadorXml().toXml(pedido);
			System.out.println(xml);
			
			producer.send(this.topico, xml);
		}
	}
}