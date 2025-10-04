---
title: "Erros Comuns Spring"
layout: single
sidebar:
  nav: main
permalink: /boas-praticas/erros-comuns-spring/
---

# Erros comuns no desenvolvimento Spring

1. Exceção `HttpMediaTypeNotAcceptableException`, mensagem "Could not find acceptable representation"

   - Falta de getters na classe retornada pelo método.

2. Exceção `HttpMessageNotReadableException` causada por erro de parseamento de JSON: 

> Cannot construct instance of `br.com.zupacademy.eduardoribeiro.casadocodigo.dto.NovoPais` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)

- Adiconar @JsonCreator no construtor e @JsonProperty(name = "nomeDoAtributo)" no parâmetro. 
- Ex:

	```java
	public class NovoPais {  
	  
		  private String nome;  
		  
		  @JsonCreator  
		  public NovoPais(@JsonProperty("nome") String nome) {  
		        this.nome = nome;  
		  }  
	}
	```
