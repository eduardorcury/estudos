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

## Security

### Habilitar Security

1. Incluir a dependência no *pom.xml*
2. Criar classe de configuração que estende **`WebSecurityConfigurerAdapter`** e anotar com **`@EnableWebSecurity`** 

	```java
	@EnableWebSecurity  
	@Configuration  
	public class SecurityConfig extends WebSecurityConfigurerAdapter {  
	}
	```

### Métodos a serem implementados

```java
@Override  
protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
}  
 
@Override  
protected void configure(HttpSecurity http) throws Exception {     
}

@Override  
public void configure(WebSecurity web) throws Exception {  
}  
```
- Primeiro método: responsável pela **autenticação**.
- Segundo método: responsável pela **autorização**.
- Terceiro método: controla recursos estáticos (arquivos javascript, css, etc).

### Controlando o acesso

- O controle do acesso é feito através de `antMatchers` no método de autorização, informando o verbo HTTP e a URL.

	```java
	@Override  
	protected void configure(HttpSecurity http) throws Exception {  
	    http.authorizeRequests()  
	            .antMatchers(HttpMethod.GET,"/topicos").permitAll()  
	            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()  
	            .anyRequest().authenticated();
	}
	```

### Controlando ROLES de usuários

- A classe de domínio que terá as credenciais de login deve implementar a interface `UserDetails`, e sobrescrever todos os métodos dela.

- Além disso, ela deve ter uma Lista de perfis com um relacionamento de muitos para muitos com outra entidade.

	```java
	@Entity  
	public class Usuario implements UserDetails {  
	  
	  //...campos
	  
	  @ManyToMany(fetch = FetchType.EAGER)  
	  private List<Perfil> perfis = new ArrayList<>();  

	  //...getters/setters
	  
	  @Override  
	  public Collection<? extends GrantedAuthority> getAuthorities() {  
	      return this.perfis;  
	  }  
	  
	  @Override  
	  public String getPassword() {  
	      return this.senha;  
	  }  
	  
	  @Override  
	  public String getUsername() {  
	      return this.email;  
	  }  
  
	  @Override  
	  public boolean isAccountNonExpired() { return true; }  
	  
	  @Override  
	  public boolean isAccountNonLocked() { return true; }  
  
	  @Override  
	  public boolean isCredentialsNonExpired() { return true; }  
  
	  @Override  
	  public boolean isEnabled() { return true; }  
	}
	```

- A entidade de Perfil deve implementar a interface de `GrantedAuthority`, e sobrescrever o método `getAuthority` que retornará o nome da ROLE.

	```java
	@Entity  
	public class Perfil implements GrantedAuthority {  
	  
	 @Id  
	 @GeneratedValue(strategy = GenerationType.IDENTITY)  
	 private Long id;  
	  
	 private String nome;  
	  
	 //...getter/setters
	  
	  @Override  
	  public String getAuthority() {  
	        return this.nome;  
	  }  
	}
	```

### Autenticação com sessão

- No método responsável pela autenticação, usamos o objeto `AuthenticationManagerBuilder` para indicar uma instância da classe que faz a lógica de autenticação.

	```java
	@Autowired  
	private AutenticacaoService autenticacaoService;  
	  
	@Override  
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
	    auth.userDetailsService(autenticacaoService)
					    .passwordEncoder(new BCryptPasswordEncoder());  
	}
	```

- Essa instância implementa a interface **`UserDetailsService`** e sobrescreve o método `loadUserByUsername`

	```java
	@Service  
	public class AutenticacaoService implements UserDetailsService {  
	  
	    @Autowired  
		private UsuarioRepository usuarioRepository;  
	  
		@Override  
	    public UserDetails loadUserByUsername(String username)
											throws UsernameNotFoundException {  
	        
	        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);  
			if (usuario.isPresent()) {  
	            return usuario.get();  
			}  
	        throw new UsernameNotFoundException("Usuário não encontrado");  
		}  
	 }
	```

### Autenticação com JWT (stateless)

- Incluir dependência do JWT:

	```xml
		<dependency>  
			 <groupId>io.jsonwebtoken</groupId>  
			 <artifactId>jjwt</artifactId>  
			 <version>0.9.1</version>  
		</dependency>
	```

- Por padrão, o Spring Security usa autenticação com sessão, o que viola os princípios REST Stateless. Para desabilitar isso:

	```java
	@Override  
	protected void configure(HttpSecurity http) throws Exception {  
	    http.authorizeRequests()  
	            .antMatchers(HttpMethod.GET,"/topicos").permitAll()  
	            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()  
	            .antMatchers(HttpMethod.POST, "/auth").permitAll()  
	            .anyRequest().authenticated()  
	            .and().csrf().disable()  
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  
	}
	```

- Agora, é preciso criar um Controller que receberá as requisições para /auth.

### Perguntar

- Na API do curso de Spring Boot, se eu inserir um tópico com um nome de curso que não existe, ele cadastra com sucesso o tópico e no banco de dados a coluna curso_id fica como null. Eu imagino que isso não seja ideal, qual a melhor forma de corrigir isso? Colocar Not Null nesse campo na tabela ou fazer uma lógica no DTO depois de consultar o repositório de cursos?

- O cache pode ser usado em outras APIs?
<!--stackedit_data:
eyJoaXN0b3J5IjpbMTA2ODQxNzM1MCwtMTE2ODkwNDIzM119
-->