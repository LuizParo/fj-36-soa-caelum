package br.com.caelum.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TratadorDeMensagem implements MessageListener {

	@Override
	public void onMessage(Message msg) {
		TextMessage textMessage = (TextMessage) msg;
		
		try {
			System.out.println("Tratador recebendo mensage: " + textMessage.getText());
		} catch (JMSException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}