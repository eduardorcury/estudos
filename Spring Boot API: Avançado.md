# Cache, Security & Paginação

## Paginação & Ordenação

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

> Os métodos do repositório que recebem um `Pageable` devem retornar um uma `Page<T>`. 

- Para ordenar, passamos mais parâmetros (ordenação, direção) no objeto `Pageable`.
 
	```java
	@GetMapping  
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,  
								 @RequestParam int pagina,  
								 @RequestParam int quantidade,  
								 @RequestParam String ordenacao) {  
  
	    Pageable paginacao = PageRequest.of(pagina, quantidade, 
										    Sort.Direction.ASC, ordenacao);
		return topicoRepository.findAll(paginacao);
		
	}
	```

### Alternativa aos parâmetros

- É possível receber um objeto `Pageable` direto no controlador:

	```java
	@GetMapping  
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,  
								 @PageableDefault(sort = "titulo") Pageable paginacao) {
  
		return topicoRepository.findAll(paginacao);
		
	}
	```

- Para isso funcionar, anotamos a classe principal da aplicação com **`@EnableSpringDataWebSupport`** :

	```java
	@SpringBootApplication  
	@EnableSpringDataWebSupport  
	public class ForumApplication {  
  
	   public static void main(String[] args) {  
	      SpringApplication.run(ForumApplication.class, args);  
	  }  
  
	}
	```

## Cache
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE2ODIxNDY1MywtMTE2ODkwNDIzM119
-->