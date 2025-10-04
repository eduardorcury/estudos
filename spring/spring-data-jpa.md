---
title: "Spring Data JPA"
layout: single
sidebar:
  nav: main
---

Perguntar sobre repository/entityManager

## JPQL

* Linguagem que facilita a criação de consultas com o paradigma orientado a objetos. Usamos nome de classes e nome de atributos, e parâmetros são precedidos por **`:`**.
*   Exemplo de JPQL:

    ```java
      @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome 
                          AND f.salario >= :salario 
                          AND f.dataContratacao = :data")  
      List<Funcionario> findNomeSalarioMaiorData(String nome, Double salario, LocalDate data);
    ```

## Projeções

*   Com projeções, podemos retornar apenas os atributos que queremos selecionar na query. Para isso, criamos uma interface com métodos getters desses atributos:

    ```java
      public interface FuncionarioProjecao {  
          Integer getId();  
          String getNome();  
          Double getSalario();  
      }
    ```
*   No método do repositório, retornamos essa interface:

    ```java
      @Repository  
      public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {  

        @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)  
        List<FuncionarioProjecao> findFuncionarioSalario();   

      }
    ```

## Queries dinâmicas (specifications)

*   Repositório herda de `JpaSpecificationExecutor<T>`:

    ```java
      @Repository  
      public interface FuncionarioRepository extends 
                          PagingAndSortingRepository<Funcionario, Integer>,  
                          JpaSpecificationExecutor<Funcionario> {
      }
    ```
*   Classe com as specifications:

    ```java
      public class SpecificationFuncionario {  

          public static Specification<Funcionario> nome(String nome) {  
              return (root, query, criteriaBuilder) ->  
                      criteriaBuilder.like(root.get("nome"), "%" + nome + "%");  
          }  
      }
    ```
*   Agora, podemos passar a specification como parâmetro para os métodos do repositório.

    ```java
      List<Funcionario> funcionarios = repository.findAll(Specification
              .where(
                  SpecificationFuncionario.nome(nome))
                  .or(SpecificationFuncionario.cpf(cpf))
              );
    ```

### Mais sobre specifications:

[Artigo devmedia](https://www.devmedia.com.br/spring-data-e-o-padrao-specification-simplifique-a-construcao-e-o-reuso-de-consultas/38103)

> A função de specifications é flexibilizar as consultas ao banco de dados, permitindo que façamos diferentes consultas com diferentes argumentos a partir do mesmo método do repositório.

[Documentação Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.4.5/reference/html/#specifications)

1.  Com _specifications_, é possível definir um conjunto de **predicados** relacionados a uma determinada entidade para executar uma gama de consultas com diversas possibilidades, sem a necessidade de escrever a query em si:

    ```java
     public  class CustomerSpecs { 

         public static Specification<Customer> isLongTermCustomer() { 
             return (root, query, builder) -> { 
                 LocalDate date = LocalDate.now().minusYears(2); 
                 return builder.lessThan(root.get(Customer_.createdAt), date); 
             }; 
         } 

         public static Specification<Customer> hasSalesOfMoreThan(MonetaryAmount value) { 
             return (root, query, builder) -> { 
                 // build query here 
             }; 
         } 
     }
    ```
2.  O verdadeiro poder das specifications está na possibilidade de encadear e combinar diversas consultas da seguinte forma:

    ```java
     List<Customer> customers = customerRepository.findAll(                  
                     isLongTermCustomer().or(hasSalesOfMoreThan(amount)));
    ```
