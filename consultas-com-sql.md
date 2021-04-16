# Consultas com SQL

## DATABASE sucos\_vendas

### Tabelas

**1. Tabela de clientes**

| CPF | NOME | ENDERECO\_1 | DATA\_DE\_NASCIMENTO | IDADE | SEXO | VOLUME\_DE\_COMPRA |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1471156710 | Érica Carvalho | R. Iriquitia | 1990-09-01 | 27 | F | 24500 |
| 19290992743 | Fernando Cavalcante | R. Dois de Fevereiro | 2000-02-12 | 18 | M | 20000 |
| 2600586709 | César Teixeira | Rua Conde de Bonfim | 2000-03-12 | 18 | M | 22000 |
| 3623344710 | Marcos Nougeuira | Av. Pastor Martin Luther | 1995-01-13 | 23 | M | 22000 |
| 492472718 | Eduardo Jorge | R. Volta Grande | 1994-07-19 | 23 | M | 9500 |

**2. Tabela de vendedores**

| MATRICULA | NOME | PERCENTUAL\_COMISSAO | DATA\_ADMISSAO | DE\_FERIAS | BAIRRO |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 00235 | Márcio Almeida Silva | 0.08 | 2014-08-15 | 0x00 | Tijuca |
| 00236 | Cláudia Morais | 0.08 | 2013-09-17 | 0x01 | Jardins |
| 00237 | Roberta Martins | 0.11 | 2017-03-18 | 0x01 | Copacabana |
| 00238 | Pericles Alves | 0.11 | 2016-08-21 | 0x00 | Santo Amaro |

**3. Tabela de produtos**

| CODIGO\_DO\_PRODUTO | NOME\_DO\_PRODUTO | EMBALAGEM | TAMANHO | SABOR | PRECO\_DE\_LISTA |
| :--- | :--- | :--- | :--- | :--- | :--- |
| 1000889 | Sabor da Montanha - 700 ml - Uva | Garrafa | 700 ml | Uva | 6.309 |
| 1002334 | Linha Citros - 1 Litro - Lima/Limão | PET | 1 Litro | Lima/Limão | 7.004 |
| 1002767 | Videira do Campo - 700 ml - Cereja/Maça | Garrafa | 700 ml | Cereja/Maça | 8.41 |
| 1004327 | Videira do Campo - 1,5 Litros - Melância | PET | 1,5 Litros | Melância | 19.51 |
| 1013793 | Videira do Campo - 2 Litros - Cereja/Maça | PET | 2 Litros | Cereja/Maça | 24.01 |

**4. Tabela de notas fiscais**

| CPF | MATRICULA | DATA\_VENDA | NUMERO | IMPOSTO |
| :--- | :--- | :--- | :--- | :--- |
| 7771579779 | 00235 | 2015-01-01 | 100 | 0.1 |
| 50534475787 | 00237 | 2015-01-01 | 101 | 0.12 |
| 8502682733 | 00236 | 2015-01-01 | 102 | 0.12 |
| 5840119709 | 00235 | 2015-01-01 | 103 | 0.12 |
| 1471156710 | 00235 | 2015-01-01 | 104 | 0.12 |

**5. Tabela itens notas fiscais**

| NUMERO | CODIGO\_DO\_PRODUTO | QUANTIDADE | PRECO |
| :--- | :--- | :--- | :--- |
| 100 | 1013793 | 63 | 24.01 |
| 100 | 1101035 | 26 | 9.0105 |
| 100 | 520380 | 67 | 12.011 |
| 100 | 773912 | 66 | 8.008 |
| 101 | 1000889 | 35 | 6.309 |

### Resumindo:

* Cada nota fiscal tem um campo cpf representando o cliente que comprou, um número associando a tabela itens\_notas\_fiscais e uma matricula representando o vendedor. Essa tabela contém os produtos e quantidades de cada nota venda. Ou seja: cada nota fiscal tem vários produtos vendidos, com quantidades diferentes.
* Clientes são identificados por CPF, vendedores por MATRÍCULA e produtos por CÓDIGO.
* Um item de venda é identificado por um NÚMERO \(identificador da nota fiscal\), e tem o código do produto vendido e a quantidade.

### Queries

**1. Quantas compras foram feitas por cada cliente?**

* Devemos contar quantas notas fiscais cada cliente tem. Ou seja, fazemos um JOIN com a tabela de cliente e a tabela de notas fiscais usando o campo comum \(CPF\) e agrupamos usando o CPF também.

  _Resposta_

  ```sql
    SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI 
        INNER JOIN notas_fiscais FIS ON CLI.cpf = FIS.CPF 
        GROUP BY CLI.cpf
        ORDER BY total;
  ```

  _Resultado_

  | nome | total |
  | :--- | :--- |
  | Carlos Eduardo | 6085 |
  | Marcelo Mattos | 6179 |
  | César Teixeira | 6226 |
  | Eduardo Jorge | 6233 |
  | Fernando Cavalcante | 6240 |

**2. Quantas compras foram feitas pela Érica Carvalho?**

* Semelhante à anterior, mas agora usamos o nome como uma condição de filtro, usando a cláusula HAVING.

  _Resposta_

  ```sql
    SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI 
        INNER JOIN notas_fiscais FIS ON CLI.cpf = FIS.CPF 
        GROUP BY CLI.cpf
        HAVING CLI.nome='Érica Carvalho';
  ```

  _Resultado_

  | nome | total |
  | :--- | :--- |
  | Érica Carvalho | 6310 |

**3. Quantas vendas foram feitas por cada vendedor?**

* Precisamos fazer um JOIN entre as tabelas de notas fiscais e de vendedores no campo matrícula, agrupando por matrícula.

  ```sql
    SELECT VEN.matricula, VEN.nome, COUNT(*) AS total FROM tabela_de_vendedores VEN 
        INNER JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
        GROUP BY VEN.matricula 
        ORDER BY total;
  ```

  | matricula | nome | total |
  | :--- | :--- | :--- |
  | 00237 | Roberta Martins | 29113 |
  | 00236 | Cláudia Morais | 29375 |
  | 00235 | Márcio Almeida Silva | 29389 |

* Um vendedor não vendeu nada. Para mostrar ele na query, usamos **LEFT JOIN**:

  ```sql
    SELECT VEN.matricula, VEN.nome, COUNT(*) AS total FROM tabela_de_vendedores VEN 
        LEFT JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
        GROUP BY VEN.matricula 
        ORDER BY total;
  ```

  | matricula | nome | total |
  | :--- | :--- | :--- |
  | 00238 | Pericles Alves | 1 |
  | 00237 | Roberta Martins | 29113 |
  | 00236 | Cláudia Morais | 29375 |
  | 00235 | Márcio Almeida Silva | 29389 |

* Por alguma razão isso mostra 1, ao invés de 0. Para mostrar 0:

  _Resposta_

  ```sql
    SELECT VEN.matricula, VEN.nome, COUNT(FIS.numero) AS total FROM tabela_de_vendedores VEN 
        LEFT JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
        GROUP BY VEN.matricula 
        ORDER BY total;
  ```

  _Resultado_

  | matricula | nome | total |
  | :--- | :--- | :--- |
  | 00238 | Pericles Alves | 0 |
  | 00237 | Roberta Martins | 29113 |
  | 00236 | Cláudia Morais | 29375 |
  | 00235 | Márcio Almeida Silva | 29389 |

**4. Quais vendedores venderam para Érica Carvalho?**

* Os vendedores e notas fiscais estão em tabelas diferentes, logo fazemos um JOIN entre essas tabelas no campo comum \(matrícula\).
* Queremos as notas fiscais associada a um nome específico. Filtramos com WHERE e fazemos um SELECT interior para encontrar o CPF desse nome.
* Por fim, agrupamos usando a matrícula do vendedor.

  _Resposta_

  ```sql
    SELECT VEN.matricula, VEN.nome FROM tabela_de_vendedores VEN 
            INNER JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula
            WHERE FIS.cpf = 
                (SELECT cpf FROM tabela_de_clientes WHERE nome = 'Érica carvalho')
            GROUP BY VEN.matricula;
  ```

  _Resultado_

  | matricula | nome |
  | :--- | :--- |
  | 00235 | Márcio Almeida Silva |
  | 00236 | Cláudia Morais |
  | 00237 | Roberta Martins |

**5. Quantas vezes cada produto foi vendido?**

* Precisamos fazer um JOIN entre a tabela de produtos e a tabela de itens notas fiscais, que tem o produto e a quantidade vendida em cada nota fiscal.
* Agrupamos por código de produto e contamos quantas vezes cada produto aparece na tabela.

  _Resposta_

  ```sql
    SELECT PROD.nome_do_produto, COUNT(*) as TOTAL FROM tabela_de_produtos PROD 
        INNER JOIN itens_notas_fiscais ITENS 
        ON PROD.codigo_do_produto = ITENS.codigo_do_produto 
        GROUP BY PROD.codigo_do_produto 
        ORDER BY TOTAL;
  ```

  _Resultado_

  | nome\_do\_produto | TOTAL |
  | :--- | :--- |
  | Festival de Sabores - 1,5 Litros - Açai | 6893 |
  | Festival de Sabores - 700 ml - Açai | 6909 |
  | Videira do Campo - 2 Litros - Cereja/Maça | 6971 |
  | Videira do Campo - 700 ml - Cereja/Maça | 7018 |
  | Linha Citros - 700 ml - Lima/Limão | 7043 |

**6. Quantas vezes o produto** Sabor da Montanha - 700 ml - Uva **foi vendido?**

* Semelhante à anterior, mas agora usamos o nome como uma condição de filtro, usando a cláusula HAVING.

  _Resposta_

  ```sql
    SELECT PROD.nome_do_produto, COUNT(*) as TOTAL FROM tabela_de_produtos PROD 
        INNER JOIN itens_notas_fiscais ITENS 
        ON PROD.codigo_do_produto = ITENS.codigo_do_produto
        GROUP BY PROD.codigo_do_produto 
        HAVING PROD.nome_do_produto = 'Sabor da Montanha - 700 ml - Uva';
  ```

  _Resultado_

  | nome\_do\_produto | TOTAL |
  | :--- | :--- |
  | Sabor da Montanha - 700 ml - Uva | 7194 |

**7. Quantas vezes cada cliente comprou o produto Sabor da Montanha - 700 ml - Uva?**

* Temos três tabelas com os dados necessários: tabela de clientes, tabela de notas fiscais \(associando o cliente a cada compra\) e tabela de itens notas fiscais \(com os dados do produto comprado\).
* Primeiro fazemos um JOIN com as tabelas de cliente e notas fiscais, no campo CPF. Com a tabela de resultado, fazemos outro JOIN com a tabela de itens notas fiscais no campo comum \(número\).
* Como queremos somente um produto específico, usamos WHERE para filtrar baseado no código do produto. Para encontrar esse código, fazemos outro SELECT na tabela de produtos filtrando pelo nome desejado, o que retorna o código do produto.

  _Resposta_

  ```sql
    SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI
        INNER JOIN notas_fiscais FIS ON CLI.cpf = FIS.cpf
        INNER JOIN itens_notas_fiscais ITENS ON ITENS.numero = FIS.numero
        WHERE ITENS.codigo_do_produto = 
            (SELECT codigo_do_produto FROM tabela_de_produtos
                    WHERE nome_do_produto = 'Sabor da Montanha - 700 ml - Uva')
        GROUP BY CLI.nome
        ORDER BY total;
  ```

  _Resultado_

  | nome | total |
  | :--- | :--- |
  | Carlos Eduardo | 476 |
  | Walber Lontra | 491 |
  | Valdeci da Silva | 497 |
  | Abel Silva | 500 |
  | Érica Carvalho | 502 |
  | Gabriel Araujo | 503 |
  | Petra Oliveira | 505 |
  | César Teixeira | 509 |
  | Marcos Nougeuira | 512 |
  | Eduardo Jorge | 516 |
  | Edson Meilelles | 527 |
  | Fernando Cavalcante | 544 |
  | Marcelo Mattos | 552 |
  | Paulo César Mattos | 560 |

**8. Qual foi a quantidade comprada por cada cliente por mês?**

* A tabela de notas fiscais tem o CPF dos clientes associados a uma nota fiscal. Os itens e quantidades em cada nota fiscal estão na tabela **itens notas fiscais**. Assim, devemos fazer um JOIN entre as tabelas de notas fiscais e itens notas fiscais no campo comum \(número\).
* Como queremos saber a soma de quantidade de itens por mês, devemos agrupar os dados com uma combinação de **CPF + MÊS**.

  _Resposta_

  ```sql
    SELECT FIS.cpf, DATE_FORMAT(FIS.data_venda, '%Y-%m') AS 'MES_ANO', 
        SUM(ITENS.quantidade) AS 'QUANTIDADE_VENDAS' FROM notas_fiscais FIS 
        INNER JOIN itens_notas_fiscais ITENS 
        ON FIS.numero = ITENS.numero
        GROUP BY FIS.cpf, DATE_FORMAT(FIS.data_venda, '%Y-%m') LIMIT 5;
  ```

  _Resultado_

  | cpf | MES\_ANO | QUANTIDADE\_VENDAS |
  | :--- | :--- | :--- |
  | 7771579779 | 2015-01 | 24879 |
  | 50534475787 | 2015-01 | 23176 |
  | 8502682733 | 2015-01 | 21986 |
  | 5840119709 | 2015-01 | 23106 |
  | 1471156710 | 2015-01 | 24316 |

1. Diferenciar, para cada cliente, as compras válidas e inválidas. Cada cliente tem uma coluna volume de compra que determina a quantidade limite de compras **por mês**.
2. Pela pergunta anterior temos a quantidade de compras de cada cliente por mês. Devemos então comparar com o volume de compra da tabela de clientes. Ou seja, com o SELECT anterior, fazemos um JOIN com a tabela de clientes usando o grupo comum CPF.
3. Como temos mais um campo comum de agrupamento \(nome\), acrescentamos no GROUP BY.

   ```sql
     SELECT FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m') AS 'MES_ANO', 
         SUM(ITENS.quantidade) AS 'QUANTIDADE_VENDAS',
         CLI.volume_de_compra AS 'QUANTIDADE_LIMITE'
         FROM notas_fiscais FIS 
         INNER JOIN itens_notas_fiscais ITENS 
         ON FIS.numero = ITENS.numero
         INNER JOIN tabela_de_clientes CLI 
         ON FIS.cpf = CLI.cpf
         GROUP BY FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m')
         LIMIT 5;
   ```

   | cpf | nome | MES\_ANO | QUANTIDADE\_VENDAS | QUANTIDADE\_LIMITE |
   | :--- | :--- | :--- | :--- | :--- |
   | 1471156710 | Érica Carvalho | 2015-01 | 24316 | 24500 |
   | 1471156710 | Érica Carvalho | 2015-02 | 22073 | 24500 |
   | 1471156710 | Érica Carvalho | 2015-03 | 22057 | 24500 |
   | 1471156710 | Érica Carvalho | 2015-04 | 19859 | 24500 |
   | 1471156710 | Érica Carvalho | 2015-05 | 26385 | 24500 |

* Para definir a compra como válida ou inválida, usamos um **CASE**. O CASE funciona como um campo com a seguinte sintaxe:

  ```sql
    CASE WHEN (condição) THEN (valor nessa condição) 
        ELSE (valor caso contrário) END AS (alias obrigatório)
  ```

* Com os campos que queremos e o campo gerado do CASE mostrado, fazemos um SELECT dentro de SELECT com a tabela anterior. O SELECT interior deve estar em parênteses e usamos um alias X para ele:

  _Resposta_

  ```sql
    SELECT X.cpf, X.nome, X.mes_ano, X.quantidade_vendas, X.quantidade_limite,
        CASE WHEN (X.quantidade_limite - X.quantidade_vendas) < 0 THEN 'INVÁLIDA'
        ELSE 'VÁLIDA' END AS STATUS_VENDA
        FROM (
            SELECT FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m') AS 'MES_ANO', 
                SUM(ITENS.quantidade) AS 'QUANTIDADE_VENDAS',
                CLI.volume_de_compra AS 'QUANTIDADE_LIMITE'
                FROM notas_fiscais FIS 
                INNER JOIN itens_notas_fiscais ITENS 
                ON FIS.numero = ITENS.numero
                INNER JOIN tabela_de_clientes CLI 
                ON FIS.cpf = CLI.cpf
                GROUP BY FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m')
        ) AS X
        LIMIT 10;
  ```

  _Resultado_

  | cpf | nome | mes\_ano | quantidade\_vendas | quantidade\_limite | STATUS\_VENDA |
  | :--- | :--- | :--- | :--- | :--- | :--- |
  | 1471156710 | Érica Carvalho | 2015-01 | 24316 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-02 | 22073 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-03 | 22057 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-04 | 19859 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-05 | 26385 | 24500 | INVÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-06 | 18712 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-07 | 19849 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-08 | 24926 | 24500 | INVÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-09 | 23323 | 24500 | VÁLIDA |
  | 1471156710 | Érica Carvalho | 2015-10 | 21002 | 24500 | VÁLIDA |

