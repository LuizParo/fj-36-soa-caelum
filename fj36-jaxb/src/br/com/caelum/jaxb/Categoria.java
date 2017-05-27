package br.com.caelum.jaxb;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "CAT")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}