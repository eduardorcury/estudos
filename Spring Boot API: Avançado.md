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

## Autenticação com JWT (stateless)

### Dependência

- Incluir dependência do JWT:

	```xml
		<dependency>  
			 <groupId>io.jsonwebtoken</groupId>  
			 <artifactId>jjwt</artifactId>  
			 <version>0.9.1</version>  
		</dependency>
	```

### Desabilitando autenticação por sessão

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

### Controller de autenticação

- Agora, é preciso criar um Controller que receberá as requisições para /auth. Ele terá um método responsável pela autenticação, que receberá um objeto representando os dados de Login.

	```java
	@RestController  
	@RequestMapping("/auth")  
	public class AutenticacaoController {  
	  
	  @Autowired  
	  private AuthenticationManager authenticationManager;  
	  
	  @Autowired  
	  private TokenService tokenService;  
	  
	  @PostMapping  
	  public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {  
	  
	     UsernamePasswordAuthenticationToken dadosLogin = form.converter();  
	  
		 try {  
			  Authentication authentication = authenticationManager.authenticate(dadosLogin);  
			  String token = tokenService.gerarToken(authentication);  
			  return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		  } catch (AuthenticationException exception) {  
		      return ResponseEntity.badRequest().build();  
		  }  
	  }  
	}
	```

1. A classe **`AuthenticationManager`** é responsável por autenticar os dados, retornando um token autenticado ou uma `AuthenticationException` caso as credenciais estejam incorretas.

> Essa classe não é um Bean gerenciado pelo String, então deve ser configurada na classe SecurityConfig.

```java
	@Override  
	@Bean  
	protected AuthenticationManager authenticationManager() throws Exception {  
	    return super.authenticationManager();  
	}
```


2. O método `authenticate` requer um objeto **`UsernamePasswordAuthenticationToken`**, que pode ser criado a partir das credenciais:

	```java
	new UsernamePasswordAuthenticationToken(this.email, this.senha);
	```

3. Para gerar o token JWT, passamos o objeto `Authentication` para uma classe de serviço criada. Nessa classe, o token é gerado com a classe **`Jwts`**, passando vários parâmetros necessários

	```java
	@Service  
	public class TokenService {  
	  
	    @Value("${forum.jwt.expiration}")  
	    private String expiration;  
	  
		@Value("${forum.jwt.secret}")  
	    private String secret;  
	  
	    public String gerarToken(Authentication authentication) {  
	  
	        Usuario logado = (Usuario) authentication.getPrincipal();  
	        Date hoje = new Date();  
	        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));  
	  
	        return Jwts.builder()  
	                    .setIssuer("API do forum da Alura")  
	                    .setSubject(logado.getId().toString())  
	                    .setIssuedAt(hoje)  
	                    .setExpiration(dataExpiracao)  
	                    .signWith(SignatureAlgorithm.HS256, secret)  
	                    .compact();  
	    }  
	}
	```
	
	3.1. As propriedades *expiration* e *secret* são setadas no `application.properties`.

	```yaml
		forum:  
		jwt:  
		   secret: jwt-secret  
		   expiration: 86400000
	```

4. Após a geração do token, ele deve ser enviado no corpo da resposta para o cliente. O cliente usará então esse mesmo token para as próximas requisições. Aqui, criamos um novo DTO representando um token, com os atributos token em si e o tipo da autenticação (nesse caso, **`Bearer`**).

	```java
	return ResponseEntity.ok(new TokenDto(token, "Bearer"));
	```

### Recebendo e validando o token

- O token é recebido através de um filtro de requisições, sendo esse uma classe que estende `OncePerRequestFilter`. Primeiro, temos que indicar o uso desse filtro na configuração:

	```java
	@Override  
	protected void configure(HttpSecurity http) throws Exception {  
	    http.authorizeRequests()  
	             //... 
	            .and().addFilterBefore(new AuntenticacaoViaTokenFilter(tokenService),
	             				UsernamePasswordAuthenticationFilter.class);  
	}
	```
- O filtro será responsável por receber e validar o token.

1. Recebendo o token através do *header* de **Authorization**:

	```java
	private String recuperarToken(HttpServletRequest request) {  
	  
	    String token = request.getHeader("Authorization");  
	    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {  
	        return null;  
	    }  
	    return token.substring(7);  
	}
	```
	
2. Validando o token com um método no TokenService:

	```java
	public boolean isTokenValido(String token) {  
	  
	    try {  
	        Jwts.parser().setSigningKey(secret).parseClaimsJws(token);  
	        return true;  
	    } catch (Exception exception) {  
	        return false;  
	    }  
	}
	```

3. Forçando a autenticação

	3.1.  Pegando o ID do usuário do token
	
	```java
	public Long getIdUsuario(String token) {  
	  
	    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();  
	    return Long.parseLong(claims.getSubject());  
	  
	}
	```

	3.2. Autenticando o usuário

	```java
	private void autenticarCliente(String token) {  
	    Long idUsuario = tokenService.getIdUsuario(token);  
	    Usuario usuario = usuarioRepository.findById(idUsuario).get();  
	    UsernamePasswordAuthenticationToken authentication =  
	         new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());  
	    SecurityContextHolder.getContext().setAuthentication(authentication);  
	}
	```

### Classes completas

1. SecurityConfig **(Configuração)**

```java
@EnableWebSecurity  
@Configuration  
public class SecurityConfig extends WebSecurityConfigurerAdapter {  
  
    @Autowired  
    private AutenticacaoService autenticacaoService;  
  
    @Autowired  
    private TokenService tokenService;  
  
    @Autowired  
    private UsuarioRepository usuarioRepository;  
  
    @Override  
    @Bean  
    protected AuthenticationManager authenticationManager() throws Exception {  
        return super.authenticationManager();  
    }  
  
    @Override  
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  
        auth.userDetailsService(autenticacaoService)
           .passwordEncoder(new BCryptPasswordEncoder());  
    }  
  
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http.authorizeRequests()  
                .antMatchers(HttpMethod.GET,"/topicos").permitAll()  
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()  
                .antMatchers(HttpMethod.POST, "/auth").permitAll()  
                .anyRequest().authenticated()  
                .and().csrf().disable()  
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
                .and()
                .addFilterBefore(
                    new AuntenticacaoViaTokenFilter(tokenService, usuarioRepository), 
                    UsernamePasswordAuthenticationFilter.class
                );  
    }  
  
    @Override  
    public void configure(WebSecurity web) throws Exception { }
```

2. AuthenticationController **(Controller)**

```java
@RestController  
@RequestMapping("/auth")  
public class AutenticacaoController {  
  
    @Autowired  
    private AuthenticationManager authenticationManager;  
  
    @Autowired  
    private TokenService tokenService;  
  
    @PostMapping  
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm form) {  
  
        UsernamePasswordAuthenticationToken dadosLogin = form.converter();  
  
        try {  
            Authentication authentication = authenticationManager.authenticate(dadosLogin);  
            String token = tokenService.gerarToken(authentication);  
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));  
        } catch (AuthenticationException exception) {  
            return ResponseEntity.badRequest().build();  
        }  
    }  
}
```

3. TokenService **(Serviço)**

```java
@Service  
public class TokenService {  
  
    @Value("${forum.jwt.expiration}")  
    private String expiration;  
  
    @Value("${forum.jwt.secret}")  
    private String secret;  
  
    public String gerarToken(Authentication authentication) {  
  
        Usuario logado = (Usuario) authentication.getPrincipal();  
        Date hoje = new Date();  
	    Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));  
  
	    return Jwts.builder()  
                    .setIssuer("API do forum da Alura")  
                    .setSubject(logado.getId().toString())  
                    .setIssuedAt(hoje)  
                    .setExpiration(dataExpiracao)  
                    .signWith(SignatureAlgorithm.HS256, secret)  
                    .compact();  
	 }  
  
    public boolean isTokenValido(String token) {  
  
        try {  
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);  
            return true;  
		} catch (Exception exception) {  
            return false;  
	    }  
     }  
  
    public Long getIdUsuario(String token) {  
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();  
		return Long.parseLong(claims.getSubject()); 
    }  
}
```

4. AuntenticacaoViaTokenFilter **(Filtro)**

```java
public class AuntenticacaoViaTokenFilter extends OncePerRequestFilter {  
  
    private TokenService tokenService;  
    private UsuarioRepository usuarioRepository;  
  
    public AuntenticacaoViaTokenFilter(TokenService tokenService, 
                                       UsuarioRepository usuarioRepository) {  
        this.tokenService = tokenService;  
	    this.usuarioRepository = usuarioRepository;  
	}  
  
    @Override  
    protected void doFilterInternal(HttpServletRequest request,  
								    HttpServletResponse response,  
								    FilterChain filterChain)  
	            throws ServletException, IOException {  
  
        String token = recuperarToken(request);  
	    boolean valido = tokenService.isTokenValido(token);  
  
	    if (valido) {  
            autenticarCliente(token);  
	    }  
        filterChain.doFilter(request, response);  
    }  
  
    private void autenticarCliente(String token) {  
        Long idUsuario = tokenService.getIdUsuario(token);  
        Usuario usuario = usuarioRepository.findById(idUsuario).get();  
        UsernamePasswordAuthenticationToken authentication =  
            new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());  
        SecurityContextHolder.getContext().setAuthentication(authentication);  
    }  
  
    private String recuperarToken(HttpServletRequest request) {  
  
        String token = request.getHeader("Authorization");  
	    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {  
            return null;  
	    }  
        return token.substring(7); 
  }  
}
```

## Monitoramento com Spring Boot Actuator

- Propriedades:

	```yaml
	management:  
	  endpoint:  
	    health:  
	      show-details: always  
	    web:  
	      exposure:  
	        include: "*"
	  
	info:  
	  app:  
	    name: @project.name@  
	    version: @project.version@
	```

	```properties
	management.endpoint.health.show-details=always
	management.endpoints.web.exposure.include=*
	info.app.name=@project.name@
	info.app.description=@project.description@
	info.app.version=@project.version@
	info.app.encoding=@project.build.sourceEncoding@
	info.app.java.version=@java.version@
	```

## Documentação com Swagger

1. Dependências

	```xml
	<dependency>  
		 <groupId>io.springfox</groupId>  
		 <artifactId>springfox-swagger2</artifactId>  
		 <version>2.9.2</version>  
	</dependency>  
	<dependency>  
		 <groupId>io.springfox</groupId>  
		 <artifactId>springfox-swagger-ui</artifactId>  
		 <version>2.9.2</version>  
	</dependency>
	```

2. Anotação na classe principal

	```java
	@EnableSwagger2  
	public class ForumApplication {  
	  
		   public static void main(String[] args) {  
		      SpringApplication.run(ForumApplication.class, args);  
		  }  
	}
	```

3. Configuração do Swagger

	```java
	@Configuration  
	public class SwaggerConfig {  
		
		@Bean
	    public Docket api() {
	     
			return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
	                .paths(PathSelectors.ant("/**"))
	                .build()
	                .ignoredParameterTypes(Usuario.class)
	                .globalOperationParameters(
	                        Arrays.asList(
	                                new ParameterBuilder()
	                                    .name("Authorization")
	                                    .description("Header para Token JWT")
	                                    .modelRef(new ModelRef("string"))
	                                    .parameterType("header")
	                                    .required(false)
	                                    .build()));
	    }
	}
	```

4. Liberando endpoints

	```java
	@Override  
	public void configure(WebSecurity web) throws Exception {  
	    web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", 
								   "/configuration/**", "/swagger-resources/**");  
	}
	```

### Perguntar

- Na API do curso de Spring Boot, se eu inserir um tópico com um nome de curso que não existe, ele cadastra com sucesso o tópico e no banco de dados a coluna curso_id fica como null. Eu imagino que isso não seja ideal, qual a melhor forma de corrigir isso? Colocar Not Null nesse campo na tabela ou fazer uma lógica no DTO depois de consultar o repositório de cursos?

- O cache pode ser usado em outras APIs?
<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE2OTAzOTMwNDIsMTA2ODQxNzM1MCwtMT
E2ODkwNDIzM119
-->