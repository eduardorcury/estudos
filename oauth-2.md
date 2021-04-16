# OAuth 2

OAuth é um protocolo de autorização que permite que um serviço autorize o acesso aos dados do usuário por um software terceiro.

> Interessante: se os bancos usassem OAuth, eu não precisaria informar minha senha para o Guiabolso, por exemplo. Aplicativos como o Guiabolso são cases perfeitos para se usar OAuth.

## Fluxo do OAuth 2.0

**Contexto**: a empresa Yelp quer acesso aos seus contatos da Google para enviar uma mensagem incentivando-os a usar a Yelp.

### Terminologias importantes:

1. Client: a **aplicação que está solicitando os dados**. Nesse caso, o site da Yelp.
2. Resource Owner: os **dono dos dados** que estão sendo solicitados. Nesse caso, somos nós.
3. Authorization Server: o serviço que irá realizar a autorização. Nesse caso, uma API da Google \(accounts.google.com\).
4. Resource Server: a API que contém os dados que o Client quer. Nesse caso, também é uma API da Google \(contacts.google.com\).
5. Callback URL: a URL que o Authorization Server vai te redirecionar após você confirmar o acesso aos dados.
6. Access Token: o que a Client recebe após todo o processo. Um token que é passado ao Resource Server para obter os dados.
7. Scope: as permissões que o Client está solicitando. Nesse caso, a Yelp quer acesso de leitura dos nossos contatos.

### Fluxo:

![Exemplo de fluxo OAuth](.gitbook/assets/oauth.png)

* A Yelp te mostra um botão, algo como "Conectar com o Google".
* O botão te leva a uma página **da Google**, onde você pode colocar seu e-mail e senha. Aqui, você está enviando dados para o Authorization Server. Além disso, você está enviando uma URL de redirecionamento para a Google.
* A Google te mostra uma página dizendo que a Yelp quer acessar seus **contatos**. 
* Se você aceitar, você é redirecionado ao Callback URL informado na etapa anterior, junto com um **Authorization Code**.
* A Yelp agora envia o Authorization Code ao Authorization Server e pede o Access Token.
* De posse do Access Token, a Yelp agora consegue pedir ao Resource Server os seus contatos.

### Client ID/Client Secret

Para tudo isso funcionar, a Yelp precisa "se cadastrar" no serviço OAuth da Google. Assim, ela recebe um Client ID e um Client Secret.

* Client ID: informado pela Yelp ao Authorization Server na URL, junto com a Callback URL, scope, etc. Importante: essas informações não são sensíveis, por isso podem ser trafegadas pelo browser.
* Client Secret: informado pela Yelp ao Authorization Server **pelo backend** através de uma requisição POST. É uma informação **confidencial**, por isso não pode ser passada pelo browser.

## OpenID Connect e para que serve

O OAuth resolve os problemas de **autorização, mas não de autenticação**. Ou seja, o Access Token serve para **autorizar** a Yelp a consultar os nossos contatos, mas a Yelp não conhece dados sobre nós, como o nosso e-mail \(nós não estamos **autenticados**\). Com o OpenID Connect, o Authorization Server também retorna um **Token ID** \(um JWT\), contendo informações do usuário para que a Yelp saiba que somos nós.

