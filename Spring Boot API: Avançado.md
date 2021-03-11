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

### Habilitar Cache

- O cache padrão do Spring Boot não é recomendado para ambientes em produção. Nesses casos, devemos utilizar algum provedor como Redis.

- Para habilitar o Cache na aplicação:
	1. Incluir a dependência no *pom.xml*
	2. Anotar com **`@EnableCaching`** a classe principal
	3. Anotar com **`@Cacheable(value = "idDoCache")`** o método que queremos cachear

### Invalidar Cache

- Em métodos que modificam o estado do banco de dados, devemos invalidar os caches alterados:

	```java 
		@CacheEvict(value = "listaDeTopicos", allEntries = true) 
	```

### Recomendações

- Invalidar cache repetidamente pode piorar a performance.
- Utilizar Cache em tabelas que nunca, ou raramente, serão atualizadas (exemplo: tabela de países).


### Perguntar

- Na API do curso de Spring Boot, se eu inserir um tópico com um nome de curso que não existe, ele cadastra com sucesso o tópico e no banco de dados a coluna curso_id fica como null. Eu imagino que isso não seja ideal, qual a melhor forma de corrigir isso? Colocar Not Null nesse campo na tabela

- O cache pode ser 
<!--stackedit_data:
eyJoaXN0b3J5IjpbNDIzNTA1NTMzLC0xMTY4OTA0MjMzXX0=
-->