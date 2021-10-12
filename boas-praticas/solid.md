# Princípios SOLID

## Single Responsibility Principle

### Coesão: única responsabilidade

* Perguntar se a classe vai parar de crescer um dia. Se a classe tiver uma única responsabilidade, ela deve parar de crescer eventualmente.
* Eventuais mudanças no sistema devem ser feitas no mínimo de classes possível.
* Para tornar uma classe mais coesa, separamos as responsabilidades em classes diferentes.

## Acoplamento

* Classes devem depender de poucas classes. Uma classe com muitas dependências fica sujeita a um grande número de classes e, se alguma dessas tiver um erro, a classe geradora vai quebrar.
* Também há acoplamento com classes como `String` e `List`. No entanto, esses acoplamentos são menos perigosos, pois são classes que **mudam pouco**. Ou seja, são classes estáveis.
* Acoplamento eferente: uma classe depende de outras. Acoplamento aferente: uma classe é uma dependência de outras.
* Nós devemos acoplar com classes estáveis, que tendem a mudar menos. Por isso, acoplar interfaces é bom, pois ela é dependência de muitas outras implementações e mudar ela quebraria todas essas implementações.

> **O acoplamento é ruim pois mudanças nas dependências podem afetar a classe principal.**

## Open Closed Principle

* Classes devem ser abertas para extensão e fechadas para modificação.
* Para isso, podemos passar as dependências pelo construtor. Essas dependências serão instâncias de uma interface, mas pelo polimorfismo podemos passar qualquer classe que implementa ela para o construtor.
* Ou seja, mudamos o comportamento da classe sem modificá-la diretamente, uma vez que as classes passadas pelo construtor serão implementações diferentes e terão comportamentos distintos.

## Dependency Inversion Principle

* Sempre dependa de classes mais estáveis.
* **Dependa de abstrações, e não de implementações.**

## Encapsulamento

* Encapsulamento: esconder o comportamento dentro da classe.
* Caso a regra de negócio "vaze", mudanças nessa regra terão de ser feitas em todos os lugares para onde ela vazou.
* Não pergunte ao objeto sobre algo e tome uma decisão baseada na resposta, apenas diga ao objeto o que quer saber (tell, don't ask). 
* Devemos saber **o que** o método faz, mas não **como** ele faz.
* Lei de Demeter: evitar invocações em cadeia, pois essas invocações são contra o encapsulamento.

## Liskov Substitutive Principle

* Princípio Liskov:
* As **pré-condições** da classe filha não devem ser **mais** restritas. Por exemplo, se a classe mãe recebe valores de 1 a 100 e a classe filha restringe a condição (só recebe valor de 1 a 50), isso pode quebrar outras classes que enviam números maiores que 50.
* De maneira análoga, as **pós-condições** da classe filha não devem ser **menos** restritas. Se a classe mãe retorna valores de 1 a 100 e a classe filha retorna de 1 a 200, isso pode quebrar classes que esperam valores menores que 100.

[Artigo Medium sobre Liskov](https://medium.com/@tbaragao/solid-l-s-p-liskov-substitution-principle-3a31c3a7b49e)

* Composição: uma instância da classe existente é usada como componente da outra classe. É um relacionamento _**tem um**_, diferente do relacionamento de herança (_**é um**_).

[Composição x Herança](https://en.wikipedia.org/wiki/Composition_over_inheritance)
