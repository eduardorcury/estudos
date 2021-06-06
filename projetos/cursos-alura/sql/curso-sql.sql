SELECT * FROM notas_fiscais WHERE matricula = 00238;

SELECT VEN.matricula, VEN.nome, COUNT(*) AS total FROM tabela_de_vendedores VEN INNER JOIN notas_fiscais FIS ON VEN.matricula =
 FIS.matricula GROUP BY VEN.matricula ORDER BY total;
 
SELECT VEN.matricula, VEN.nome FROM tabela_de_vendedores VEN 
		JOIN notas_fiscais FIS ON VEN.matricula = FIS.matricula
        WHERE FIS.cpf = 
			(SELECT cpf FROM tabela_de_clientes WHERE nome = 'Érica carvalho')
		GROUP BY VEN.matricula;
        
SELECT cpf FROM tabela_de_clientes WHERE nome = 'Érica carvalho';

SELECT PROD.nome_do_produto, COUNT(*) as TOTAL FROM tabela_de_produtos PROD JOIN itens_notas_fiscais ITENS ON PROD.codigo_do_produto = ITENS.codigo_do_produto GROUP BY PROD.codigo_do_produto HAVING PROD.nome_do_produto = 'Sabor da Montanha - 700 ml - Uva';
    
SELECT SUM(total) FROM (SELECT COUNT(*) as total FROM tabela_de_produtos PROD 
	JOIN itens_notas_fiscais ITENS ON PROD.codigo_do_produto = ITENS.codigo_do_produto
    GROUP BY PROD.codigo_do_produto) AS TESTE;
    
SELECT COUNT(*) FROM itens_notas_fiscais;

SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI
	JOIN notas_fiscais FIS ON CLI.cpf = FIS.cpf
    JOIN itens_notas_fiscais ITENS ON ITENS.numero = FIS.numero
    WHERE ITENS.codigo_do_produto = 
		(SELECT codigo_do_produto FROM tabela_de_produtos WHERE nome_do_produto = 'Sabor da Montanha - 700 ml - Uva')
	GROUP BY CLI.nome
    ORDER BY total;

SELECT CLI.cpf, CLI.nome, FIS.data_venda, COUNT(*) FROM tabela_de_clientes CLI 
	JOIN notas_fiscais FIS ON CLI.cpf = FIS.CPF
    GROUP BY MONTH(FIS.data_venda);
    
/*Qual foi a quantidade comprada por cada cliente por mês?*/
SELECT FIS.cpf, DATE_FORMAT(FIS.data_venda, '%Y-%m') AS 'MES_ANO', 
	SUM(ITENS.quantidade) AS 'QUANTIDADE_VENDAS' FROM notas_fiscais FIS 
	INNER JOIN itens_notas_fiscais ITENS 
    ON FIS.numero = ITENS.numero
    GROUP BY FIS.cpf, DATE_FORMAT(FIS.data_venda, '%Y-%m');

SELECT CLI.cpf, CLI.nome, CLI.volume_de_compra AS 'QUANTIDADE_LIMITE' FROM tabela_de_clientes CLI;

/*Tabela de quantidades*/
SELECT FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m') AS 'MES_ANO', 
	SUM(ITENS.quantidade) AS 'QUANTIDADE_VENDAS',
    CLI.volume_de_compra AS 'QUANTIDADE_LIMITE'
    FROM notas_fiscais FIS 
	INNER JOIN itens_notas_fiscais ITENS 
    ON FIS.numero = ITENS.numero
    INNER JOIN tabela_de_clientes CLI 
    ON FIS.cpf = CLI.cpf
    GROUP BY FIS.cpf, CLI.nome, DATE_FORMAT(FIS.data_venda, '%Y-%m');

/*Tabela de valido e invalidos*/
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
	) AS X;

SELECT VENDA_SABOR.sabor, VENDA_SABOR.ano, VENDA_SABOR.quantidade, 
ROUND((VENDA_SABOR.quantidade/VENDA_TOTAL.quantidade)*100, 2) AS Participacao FROM
(SELECT PROD.sabor, YEAR(FIS.data_venda) AS Ano, SUM(ITENS.quantidade) AS Quantidade 
	FROM tabela_de_produtos PROD 
	INNER JOIN itens_notas_fiscais ITENS 
	ON PROD.codigo_do_produto = ITENS.codigo_do_produto
    INNER JOIN notas_fiscais FIS 
    ON ITENS.numero = FIS.numero
    WHERE YEAR(FIS.data_venda) = 2016
    GROUP BY PROD.sabor, YEAR(FIS.data_venda)
    ORDER BY SUM(ITENS.quantidade) DESC) AS VENDA_SABOR
INNER JOIN
(SELECT YEAR(FIS.data_venda) AS Ano, SUM(ITENS.quantidade) AS Quantidade 
	FROM tabela_de_produtos PROD 
	INNER JOIN itens_notas_fiscais ITENS 
	ON PROD.codigo_do_produto = ITENS.codigo_do_produto
    INNER JOIN notas_fiscais FIS 
    ON ITENS.numero = FIS.numero
    WHERE YEAR(FIS.data_venda) = 2016
    GROUP BY YEAR(FIS.data_venda)) AS VENDA_TOTAL
    ON VENDA_SABOR.Ano = VENDA_TOTAL.Ano;

SELECT CLI.nome, COUNT(*) AS total FROM tabela_de_clientes CLI
		INNER JOIN notas_fiscais FIS ON CLI.cpf = FIS.cpf
	    INNER JOIN itens_notas_fiscais ITENS ON ITENS.numero = FIS.numero
	    WHERE ITENS.codigo_do_produto = 
			(SELECT codigo_do_produto FROM tabela_de_produtos
					WHERE nome_do_produto = 'Sabor da Montanha - 700 ml - Uva')
		GROUP BY CLI.nome
	    ORDER BY total;

