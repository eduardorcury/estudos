# Criando código de qualidade

## 1. Informações obrigatórias entram pelo construtor

*   Nada de ficar usando _setters_ na hora de construir um objeto! Use o construtor direto, não faz sentido criar um POJO que esteja faltando atributos obrigatórios.

    :white\_check\_mark:

    ```java
      Produto produto = new Produto(request.getNome(), request.getEstoque());
    ```

    :x:

    ```java
      Produto produto = new Produto();
      produto.setNome(request.getNome());
      produto.setEstoque(request.getEstoque());
    ```

## 2. Não expor a camada de domínio

* Nada de receber um objeto model diretamente no _endpoint_, nem enviar um objeto model como resposta.
*   Para isso, criamos objetos específicos (`AutorRequest` e `AutorResponse`, por exemplo).

    :white\_check\_mark:

    ```java
      @PostMapping
      public ResponseEntity<AutorResponse> cadastrar(@RequestBody @Valid AutorRequest autor) {  
          Autor model = autor.converterParaModel();  
          repository.save(model);
          AutorResponse response = new AutorResponse(model);
          return ResponseEntity.ok().body(response);  
      }
    ```

    :x:

    ```java
      @PostMapping
      public ResponseEntity<Autor> cadastrar(@RequestBody @Valid autor) {  
          repository.save(autor);
          return ResponseEntity.ok().body(autor);  
      }
    ```

## Boas práticas para testes

### 3. Não faz sentido _mockar_ acesso ao banco de dados

* Sempre que queremos testar classes cuja tarefa é se comunicar com sistemas externos (como classes DAO ou repositórios), não faz sentido utilizar _mocks_.
* Neste caso, devemos usar testes de integração e simular o comportamento da forma mais fiel possível.
*   Testes de integração testam a classe de maneira integrada ao serviço que usam. Um teste de DAO, por exemplo, que bate em um banco de dados de verdade, é considerado um teste de integração. Testes como esses são especialmente úteis para testar classes cuja responsabilidade é se comunicar com outros serviços.

    :white\_check\_mark:

    ```java
      public class UsuarioDaoTest {  

            @Test  
            public void deveEncontrarPeloNomeEmailMockado() {  

                Usuario newUsuario = new Usuario("João", "email");  

                Session session = new CriadorDeSessao().getSession();  
                UsuarioDao usuarioDao = new UsuarioDao(session);  
                usuarioDao.salvar(newUsuario);  

                Usuario usuario = usuarioDao.porNomeEEmail("João", "email");  
                assertEquals("João", usuario.getNome());  
                assertEquals("email", usuario.getEmail());  

                session.close();  

            }  
      }
    ```

    :x:

    ```java
      public class UsuarioDaoTest {  

            @Test  
            public void deveEncontrarPeloNomeEmailMockado() {  

                //mocks
                Session session = mock(Session.class);
                Query query = mock(Query.class);  
                UsuarioDao usuarioDao = new UsuarioDao(session);  

                //...

            }  
      }
    ```

### 4. Os testes devem ser independentes

* Os testes anteriores não devem influenciar os testes seguintes. Para conseguirmos isso quando estamos testando um banco de dados, por exemplo, usamos transações.
*   Dessa forma, no método **@Before** nós iniciamos a transação, e no **@After** damos _rollback_.

    :white\_check\_mark:

    ```java
      @Before  
      public void setUp() {  
          // criação de DAOs
          session.beginTransaction(); 
      }  

      @After  
      public void depois() {  
          session.getTransaction().rollback();  
          session.close();  
      }
    ```

### Pontos sobre testes de unidade

1. Specification Based Test

> Cenário: Dado um id de usuário e um id de restaurante, retornar as formas de pagamento que tanto o usuário quanto o restaurante aceitam.

* [ ] Identificação dos parâmetros (id do usuário e id do restaurante).
* [ ] Gerar combinações possíveis:
  * [ ] Usuário aceita visa/master, restaurante aceita visa/master.
  * [ ] Usuário aceita visa/master, restaurante aceita visa.
  * [ ] Usuário aceita visa/master, restaurante aceita elo.
* [ ] Tipos de cobertura
* [ ] Por linha de código
*
