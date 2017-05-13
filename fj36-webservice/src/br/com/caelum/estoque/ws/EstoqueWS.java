package br.com.caelum.estoque.ws;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@Stateless
@WebService(targetNamespace = "http://caelum.com.br/estoquews/v1")
public class EstoqueWS {
	private Map<String, ItemEstoque> repositorio = new HashMap<>();
	
	public EstoqueWS() {
		this.repositorio.put("ARQ", new ItemEstoque("ARQ", 5));
		this.repositorio.put("SOA", new ItemEstoque("SOA", 2));
		this.repositorio.put("TDD", new ItemEstoque("TDD", 3));
		this.repositorio.put("RES", new ItemEstoque("RES", 4));
		this.repositorio.put("LOG", new ItemEstoque("LOG", 3));
		this.repositorio.put("WEB", new ItemEstoque("WEB", 4));
	}
	
	@WebMethod(operationName = "ItensPeloCodigo")
	@WebResult(name = "ItemEstoque")
	public List<ItemEstoque> getQuantidade(@WebParam(name = "codigo") List<String> codigos , @WebParam(name = "tokenUsuario", header = true) String token) {
		if(token == null || !token.equals("TOKEN123")) {
			throw new AutorizacaoException("Nao autorizado");
		}
		
		if(codigos == null || codigos.isEmpty()) {
			return Collections.emptyList();
		}
		
		return codigos.stream()
			.filter(this.repositorio::containsKey)
			.map(this.repositorio::get)
			.collect(Collectors.toList());
	}
}