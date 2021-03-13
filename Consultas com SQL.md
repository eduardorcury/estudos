
# Consultas com SQL

## DATABASE sucos_vendas

### Tabelas

1. Tabela de clientes

| CPF | NOME| ENDERECO_1| DATA_DE_NASCIMENTO| IDADE| SEXO |VOLUME_DE_COMPRA|
|--|--|--|--|--|--|--|
| 1471156710  | Érica Carvalho| R. Iriquitia | 1990-09-01  |    27 | F    |24500 |
| 19290992743 | Fernando Cavalcante | R. Dois de Fevereiro | 2000-02-12 |  18 | M |20000 |
| 2600586709  | César Teixeira| Rua Conde de Bonfim | 2000-03-12 |18 | M|  22000 |
| 3623344710 | Marcos Nougeuira| Av. Pastor Martin Luther | 1995-01-13 | 23 | M| 22000 |
| 492472718   | Eduardo Jorge| R. Volta Grande   | 1994-07-19 |    23 | M| 9500 |

2. Tabela de vendedores

| MATRICULA | NOME  | PERCENTUAL_COMISSAO | DATA_ADMISSAO | DE_FERIAS | BAIRRO      |
|--|--|--|--|--|--|
| 00235     | Márcio Almeida Silva  |0.08 | 2014-08-15    | 0x00| Tijuca      |
| 00236     | Cláudia Morais        |0.08 | 2013-09-17    | 0x01| Jardins     |
| 00237     | Roberta Martins       |0.11 | 2017-03-18    | 0x01| Copacabana  |
| 00238     | Pericles Alves        |0.11 | 2016-08-21    | 0x00| Santo Amaro |

3. Tabela de produtos

| CODIGO_DO_PRODUTO | NOME_DO_PRODUTO | EMBALAGEM | TAMANHO| SABOR| PRECO_DE_LISTA |
|--|--|--|--|--|--|
| 1000889| Sabor da Montanha - 700 ml - Uva| Garrafa   | 700 ml     | Uva|6.309 |
| 1002334| Linha Citros - 1 Litro - Lima/Limão| PET| 1 Litro    | Lima/Limão|7.004 |
| 1002767| Videira do Campo - 700 ml - Cereja/Maça    | Garrafa   | 700 ml| Cereja/Maça|8.41 |
| 1004327| Videira do Campo - 1,5 Litros - Melância   | PET| 1,5 Litros | Melância|19.51 |
| 1013793| Videira do Campo - 2 Litros - Cereja/Maça  | PET| 2 Litros   | Cereja/Maça|24.01 |

4. Tabela de notas fiscais

| CPF         | MATRICULA | DATA_VENDA | NUMERO | IMPOSTO |
|--|--|--|--|--|
| 7771579779  | 00235     | 2015-01-01 |    100 |     0.1 |
| 50534475787 | 00237     | 2015-01-01 |    101 |    0.12 |
| 8502682733  | 00236     | 2015-01-01 |    102 |    0.12 |
| 5840119709  | 00235     | 2015-01-01 |    103 |    0.12 |
| 1471156710  | 00235     | 2015-01-01 |    104 |    0.12 |

5. Tabela itens notas fiscais

| NUMERO | CODIGO_DO_PRODUTO | QUANTIDADE | PRECO  |
|--|--|--|--|
|    100 | 1013793           |         63 |  24.01 |
|    100 | 1101035           |         26 | 9.0105 |
|    100 | 520380            |         67 | 12.011 |
|    100 | 773912            |         66 |  8.008 |
|    101 | 1000889           |         35 |  6.309 |

### Resumindo:

- Cada nota fiscal tem um campo cpf representando o cliente que comprou, um número associando a tabela itens_notas_fiscais e uma matricula representando o vendedor. Essa tabela contém os produtos e quantidades de cada nota venda. Ou seja: cada nota fiscal tem vários produtos vendidos, com quantidades diferentes.

- Clientes são identificados por CPF, vendedores por MATRÍCULA e produtos por CÓDIGO.

- Um item de venda é identificado por um NÚMERO (identificador da nota fiscal), e tem o código do produto vendido e a quantidade.

### Queries

1. Quantas compras foram feitas por cada cliente?

	```SQL
	SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI 
		JOIN notas_fiscais FIS ON CLI.cpf = FIS.CPF 
		GROUP BY CLI.cpf
		ORDER BY total;
	```

| nome                | total |
|--|--|
| Carlos Eduardo      |  6085 |
| Marcelo Mattos      |  6179 |
| César Teixeira      |  6226 |
| Eduardo Jorge       |  6233 |
| Fernando Cavalcante |  6240 |


2. Quantas compras foram feitas pela Érica Carvalho?

	```SQL
	SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI 
		JOIN notas_fiscais FIS ON CLI.cpf = FIS.CPF 
		GROUP BY CLI.cpf
		HAVING CLI.nome='Érica Carvalho';
	```

| nome            | total |
|--|--|
| Érica Carvalho  |  6310 |

3. Quantas vendas foram feitas por cada vendedor?

	```SQL
	SELECT VEN.matricula, VEN.nome, COUNT(*) AS total FROM tabela_de_vendedores VEN 
		JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
		GROUP BY VEN.matricula 
		ORDER BY total;
	```

| matricula | nome                  | total |
|--|--|--|
| 00237     | Roberta Martins       | 29113 |
| 00236     | Cláudia Morais        | 29375 |
| 00235     | Márcio Almeida Silva  | 29389 |

- Um vendedor não vendeu nada. Para mostrar ele na query, usamos **LEFT JOIN**:

	```SQL
	SELECT VEN.matricula, VEN.nome, COUNT(*) AS total FROM tabela_de_vendedores VEN 
		LEFT JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
		GROUP BY VEN.matricula 
		ORDER BY total;
	```

| matricula | nome                  | total |
|--|--|--|
| 00238     | Pericles Alves        |     1 |
| 00237     | Roberta Martins       | 29113 |
| 00236     | Cláudia Morais        | 29375 |
| 00235     | Márcio Almeida Silva  | 29389 |

- Por alguma razão isso mostra 1, ao invés de 0. Para mostrar 0:

	```SQL
	SELECT VEN.matricula, VEN.nome, COUNT(FIS.numero) AS total FROM tabela_de_vendedores VEN 
		LEFT JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula 
		GROUP BY VEN.matricula 
		ORDER BY total;
	```

| matricula | nome                  | total |
|--|--|--|
| 00238     | Pericles Alves        |     0 |
| 00237     | Roberta Martins       | 29113 |
| 00236     | Cláudia Morais        | 29375 |
| 00235     | Márcio Almeida Silva  | 29389 |


4. Quais vendedores venderam para Érica Carvalho?

	```SQL
	SELECT VEN.matricula, VEN.nome FROM tabela_de_vendedores VEN 
			JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula
	        WHERE FIS.cpf = 
				(SELECT cpf FROM tabela_de_clientes WHERE nome = 'Érica carvalho')
			GROUP BY VEN.matricula;
	```
	
| matricula | nome                  |
|--|--|
| 00235     | Márcio Almeida Silva  |
| 00236     | Cláudia Morais        |
| 00237     | Roberta Martins       |

5. Quantas vezes cada produto foi vendido?

	```SQL
	SELECT PROD.nome_do_produto, COUNT(*) as TOTAL FROM tabela_de_produtos PROD 
		JOIN itens_notas_fiscais ITENS 
		ON PROD.codigo_do_produto = ITENS.codigo_do_produto 
		GROUP BY PROD.codigo_do_produto 
		ORDER BY TOTAL;
	```

| nome_do_produto                            | TOTAL |
|--|--|
| Festival de Sabores - 1,5 Litros - Açai    |  6893 |
| Festival de Sabores - 700 ml - Açai        |  6909 |
| Videira do Campo - 2 Litros - Cereja/Maça  |  6971 |
| Videira do Campo - 700 ml - Cereja/Maça    |  7018 |
| Linha Citros - 700 ml - Lima/Limão         |  7043 |

6. Quantas vezes o produto **Sabor da Montanha - 700 ml - Uva** foi vendido?

	```SQL
	SELECT PROD.nome_do_produto, COUNT(*) as TOTAL FROM tabela_de_produtos PROD 
		JOIN itens_notas_fiscais ITENS ON PROD.codigo_do_produto = ITENS.codigo_do_produto
		GROUP BY PROD.codigo_do_produto 
		HAVING PROD.nome_do_produto = 'Sabor da Montanha - 700 ml - Uva';
	```

| nome_do_produto                  | TOTAL |
|--|--|
| Sabor da Montanha - 700 ml - Uva |  7194 |

<!--stackedit_data:
eyJoaXN0b3J5IjpbMTE0ODI2MTg3N119
-->