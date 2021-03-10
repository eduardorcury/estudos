# Cache, Security & Paginação

## Paginação

- Anotar os parâmetros do método com `@RequestParam` indica que eles devem ser **obrigatórios**, ou seja, a propriedade *required* é true por default. Para mudar isso:

	```java
	@RequestParam(required = false)
	```

- Para paginar, passamos um objeto `Pageable` para o método `findAll` do repositório.

	```java
	@GetMapping  
	public Page<Topico> lista(@RequestParam(required = false) String nomeCurso,  
							  @RequestParam int pagina,  
							  @RequestParam int quandidade) {  
  
		Pageable paginacao = PageRequest.of(pagina, quandidade);  
		return topicoRepository.findAll(paginacao);
		
	}
	```
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTExNjg5MDQyMzNdfQ==
-->