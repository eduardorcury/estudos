---


---

<h1 id="consultas-com-sql">Consultas com SQL</h1>
<h2 id="database-sucos_vendas">DATABASE sucos_vendas</h2>
<h3 id="tabelas">Tabelas</h3>
<ol>
<li>Tabela de clientes</li>
</ol>

<table>
<thead>
<tr>
<th>CPF</th>
<th>NOME</th>
<th>ENDERECO_1</th>
<th>DATA_DE_NASCIMENTO</th>
<th>IDADE</th>
<th>SEXO</th>
<th>VOLUME_DE_COMPRA</th>
</tr>
</thead>
<tbody>
<tr>
<td>1471156710</td>
<td>Érica Carvalho</td>
<td>R. Iriquitia</td>
<td>1990-09-01</td>
<td>27</td>
<td>F</td>
<td>24500</td>
</tr>
<tr>
<td>19290992743</td>
<td>Fernando Cavalcante</td>
<td>R. Dois de Fevereiro</td>
<td>2000-02-12</td>
<td>18</td>
<td>M</td>
<td>20000</td>
</tr>
<tr>
<td>2600586709</td>
<td>César Teixeira</td>
<td>Rua Conde de Bonfim</td>
<td>2000-03-12</td>
<td>18</td>
<td>M</td>
<td>22000</td>
</tr>
<tr>
<td>3623344710</td>
<td>Marcos Nougeuira</td>
<td>Av. Pastor Martin Luther</td>
<td>1995-01-13</td>
<td>23</td>
<td>M</td>
<td>22000</td>
</tr>
<tr>
<td>492472718</td>
<td>Eduardo Jorge</td>
<td>R. Volta Grande</td>
<td>1994-07-19</td>
<td>23</td>
<td>M</td>
<td>9500</td>
</tr>
</tbody>
</table><ol start="2">
<li>Tabela de vendedores</li>
</ol>

<table>
<thead>
<tr>
<th>MATRICULA</th>
<th>NOME</th>
<th>PERCENTUAL_COMISSAO</th>
<th>DATA_ADMISSAO</th>
<th>DE_FERIAS</th>
<th>BAIRRO</th>
</tr>
</thead>
<tbody>
<tr>
<td>00235</td>
<td>Márcio Almeida Silva</td>
<td>0.08</td>
<td>2014-08-15</td>
<td>0x00</td>
<td>Tijuca</td>
</tr>
<tr>
<td>00236</td>
<td>Cláudia Morais</td>
<td>0.08</td>
<td>2013-09-17</td>
<td>0x01</td>
<td>Jardins</td>
</tr>
<tr>
<td>00237</td>
<td>Roberta Martins</td>
<td>0.11</td>
<td>2017-03-18</td>
<td>0x01</td>
<td>Copacabana</td>
</tr>
<tr>
<td>00238</td>
<td>Pericles Alves</td>
<td>0.11</td>
<td>2016-08-21</td>
<td>0x00</td>
<td>Santo Amaro</td>
</tr>
</tbody>
</table><ol start="3">
<li>Tabela de produtos</li>
</ol>

<table>
<thead>
<tr>
<th>CODIGO_DO_PRODUTO</th>
<th>NOME_DO_PRODUTO</th>
<th>EMBALAGEM</th>
<th>TAMANHO</th>
<th>SABOR</th>
<th>PRECO_DE_LISTA</th>
</tr>
</thead>
<tbody>
<tr>
<td>1000889</td>
<td>Sabor da Montanha - 700 ml - Uva</td>
<td>Garrafa</td>
<td>700 ml</td>
<td>Uva</td>
<td>6.309</td>
</tr>
<tr>
<td>1002334</td>
<td>Linha Citros - 1 Litro - Lima/Limão</td>
<td>PET</td>
<td>1 Litro</td>
<td>Lima/Limão</td>
<td>7.004</td>
</tr>
<tr>
<td>1002767</td>
<td>Videira do Campo - 700 ml - Cereja/Maça</td>
<td>Garrafa</td>
<td>700 ml</td>
<td>Cereja/Maça</td>
<td>8.41</td>
</tr>
<tr>
<td>1004327</td>
<td>Videira do Campo - 1,5 Litros - Melância</td>
<td>PET</td>
<td>1,5 Litros</td>
<td>Melância</td>
<td>19.51</td>
</tr>
<tr>
<td>1013793</td>
<td>Videira do Campo - 2 Litros - Cereja/Maça</td>
<td>PET</td>
<td>2 Litros</td>
<td>Cereja/Maça</td>
<td>24.01</td>
</tr>
</tbody>
</table><ol start="4">
<li>Tabela de notas fiscais</li>
</ol>

<table>
<thead>
<tr>
<th>CPF</th>
<th>MATRICULA</th>
<th>DATA_VENDA</th>
<th>NUMERO</th>
<th>IMPOSTO</th>
</tr>
</thead>
<tbody>
<tr>
<td>7771579779</td>
<td>00235</td>
<td>2015-01-01</td>
<td>100</td>
<td>0.1</td>
</tr>
<tr>
<td>50534475787</td>
<td>00237</td>
<td>2015-01-01</td>
<td>101</td>
<td>0.12</td>
</tr>
<tr>
<td>8502682733</td>
<td>00236</td>
<td>2015-01-01</td>
<td>102</td>
<td>0.12</td>
</tr>
<tr>
<td>5840119709</td>
<td>00235</td>
<td>2015-01-01</td>
<td>103</td>
<td>0.12</td>
</tr>
<tr>
<td>1471156710</td>
<td>00235</td>
<td>2015-01-01</td>
<td>104</td>
<td>0.12</td>
</tr>
</tbody>
</table><ol start="5">
<li>Tabela itens notas fiscais</li>
</ol>

<table>
<thead>
<tr>
<th>NUMERO</th>
<th>CODIGO_DO_PRODUTO</th>
<th>QUANTIDADE</th>
<th>PRECO</th>
</tr>
</thead>
<tbody>
<tr>
<td>100</td>
<td>1013793</td>
<td>63</td>
<td>24.01</td>
</tr>
<tr>
<td>100</td>
<td>1101035</td>
<td>26</td>
<td>9.0105</td>
</tr>
<tr>
<td>100</td>
<td>520380</td>
<td>67</td>
<td>12.011</td>
</tr>
<tr>
<td>100</td>
<td>773912</td>
<td>66</td>
<td>8.008</td>
</tr>
<tr>
<td>101</td>
<td>1000889</td>
<td>35</td>
<td>6.309</td>
</tr>
</tbody>
</table><h3 id="resumindo">Resumindo:</h3>
<ul>
<li>
<p>Cada nota fiscal tem um campo cpf representando o cliente que comprou, um número associando a tabela itens_notas_fiscais e uma matricula representando o vendedor. Essa tabela contém os produtos e quantidades de cada nota venda. Ou seja: cada nota fiscal tem vários produtos vendidos, com quantidades diferentes.</p>
</li>
<li>
<p>Clientes são identificados por CPF, vendedores por MATRÍCULA e produtos por CÓDIGO.</p>
</li>
<li>
<p>Um item de venda é identificado por um NÚMERO (identificador da nota fiscal), e tem o código do produto vendido e a quantidade.</p>
</li>
</ul>
<h3 id="queries">Queries</h3>
<ol>
<li>
<p>Quantas compras foram feitas por cada cliente?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> CLI<span class="token punctuation">.</span>nome<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">AS</span> total <span class="token keyword">FROM</span> tabela_de_clientes CLI 
	<span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> CLI<span class="token punctuation">.</span>cpf <span class="token operator">=</span> FIS<span class="token punctuation">.</span>CPF 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> CLI<span class="token punctuation">.</span>cpf
	<span class="token keyword">ORDER</span> <span class="token keyword">BY</span> total<span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>nome</th>
<th>total</th>
</tr>
</thead>
<tbody>
<tr>
<td>Carlos Eduardo</td>
<td>6085</td>
</tr>
<tr>
<td>Marcelo Mattos</td>
<td>6179</td>
</tr>
<tr>
<td>César Teixeira</td>
<td>6226</td>
</tr>
<tr>
<td>Eduardo Jorge</td>
<td>6233</td>
</tr>
<tr>
<td>Fernando Cavalcante</td>
<td>6240</td>
</tr>
</tbody>
</table><ol start="2">
<li>
<p>Quantas compras foram feitas pela Érica Carvalho?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> CLI<span class="token punctuation">.</span>nome<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">AS</span> total <span class="token keyword">FROM</span> tabela_de_clientes CLI 
	<span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> CLI<span class="token punctuation">.</span>cpf <span class="token operator">=</span> FIS<span class="token punctuation">.</span>CPF 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> CLI<span class="token punctuation">.</span>cpf
	<span class="token keyword">HAVING</span> CLI<span class="token punctuation">.</span>nome<span class="token operator">=</span><span class="token string">'Érica Carvalho'</span><span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>nome</th>
<th>total</th>
</tr>
</thead>
<tbody>
<tr>
<td>Érica Carvalho</td>
<td>6310</td>
</tr>
</tbody>
</table><ol start="3">
<li>
<p>Quantas vendas foram feitas por cada vendedor?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> VEN<span class="token punctuation">.</span>matricula<span class="token punctuation">,</span> VEN<span class="token punctuation">.</span>nome<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">AS</span> total <span class="token keyword">FROM</span> tabela_de_vendedores VEN 
	<span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> VEN<span class="token punctuation">.</span>matricula <span class="token operator">=</span> FIS<span class="token punctuation">.</span>matricula 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> VEN<span class="token punctuation">.</span>matricula 
	<span class="token keyword">ORDER</span> <span class="token keyword">BY</span> total<span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>matricula</th>
<th>nome</th>
<th>total</th>
</tr>
</thead>
<tbody>
<tr>
<td>00237</td>
<td>Roberta Martins</td>
<td>29113</td>
</tr>
<tr>
<td>00236</td>
<td>Cláudia Morais</td>
<td>29375</td>
</tr>
<tr>
<td>00235</td>
<td>Márcio Almeida Silva</td>
<td>29389</td>
</tr>
</tbody>
</table><ul>
<li>
<p>Um vendedor não vendeu nada. Para mostrar ele na query, usamos <strong>LEFT JOIN</strong>:</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> VEN<span class="token punctuation">.</span>matricula<span class="token punctuation">,</span> VEN<span class="token punctuation">.</span>nome<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">AS</span> total <span class="token keyword">FROM</span> tabela_de_vendedores VEN 
	<span class="token keyword">LEFT</span> <span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> VEN<span class="token punctuation">.</span>matricula <span class="token operator">=</span> FIS<span class="token punctuation">.</span>matricula 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> VEN<span class="token punctuation">.</span>matricula 
	<span class="token keyword">ORDER</span> <span class="token keyword">BY</span> total<span class="token punctuation">;</span>
</code></pre>
</li>
</ul>

<table>
<thead>
<tr>
<th>matricula</th>
<th>nome</th>
<th>total</th>
</tr>
</thead>
<tbody>
<tr>
<td>00238</td>
<td>Pericles Alves</td>
<td>1</td>
</tr>
<tr>
<td>00237</td>
<td>Roberta Martins</td>
<td>29113</td>
</tr>
<tr>
<td>00236</td>
<td>Cláudia Morais</td>
<td>29375</td>
</tr>
<tr>
<td>00235</td>
<td>Márcio Almeida Silva</td>
<td>29389</td>
</tr>
</tbody>
</table><ul>
<li>
<p>Por alguma razão isso mostra 1, ao invés de 0. Para mostrar 0:</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> VEN<span class="token punctuation">.</span>matricula<span class="token punctuation">,</span> VEN<span class="token punctuation">.</span>nome<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span>FIS<span class="token punctuation">.</span>numero<span class="token punctuation">)</span> <span class="token keyword">AS</span> total <span class="token keyword">FROM</span> tabela_de_vendedores VEN 
	<span class="token keyword">LEFT</span> <span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> VEN<span class="token punctuation">.</span>matricula <span class="token operator">=</span> FIS<span class="token punctuation">.</span>matricula 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> VEN<span class="token punctuation">.</span>matricula 
	<span class="token keyword">ORDER</span> <span class="token keyword">BY</span> total<span class="token punctuation">;</span>
</code></pre>
</li>
</ul>

<table>
<thead>
<tr>
<th>matricula</th>
<th>nome</th>
<th>total</th>
</tr>
</thead>
<tbody>
<tr>
<td>00238</td>
<td>Pericles Alves</td>
<td>0</td>
</tr>
<tr>
<td>00237</td>
<td>Roberta Martins</td>
<td>29113</td>
</tr>
<tr>
<td>00236</td>
<td>Cláudia Morais</td>
<td>29375</td>
</tr>
<tr>
<td>00235</td>
<td>Márcio Almeida Silva</td>
<td>29389</td>
</tr>
</tbody>
</table><ol start="4">
<li>
<p>Quais vendedores venderam para Érica Carvalho?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> VEN<span class="token punctuation">.</span>matricula<span class="token punctuation">,</span> VEN<span class="token punctuation">.</span>nome <span class="token keyword">FROM</span> tabela_de_vendedores VEN 
		<span class="token keyword">JOIN</span> notas_fiscais FIS <span class="token keyword">ON</span> VEN<span class="token punctuation">.</span>matricula <span class="token operator">=</span> FIS<span class="token punctuation">.</span>matricula
        <span class="token keyword">WHERE</span> FIS<span class="token punctuation">.</span>cpf <span class="token operator">=</span> 
			<span class="token punctuation">(</span><span class="token keyword">SELECT</span> cpf <span class="token keyword">FROM</span> tabela_de_clientes <span class="token keyword">WHERE</span> nome <span class="token operator">=</span> <span class="token string">'Érica carvalho'</span><span class="token punctuation">)</span>
		<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> VEN<span class="token punctuation">.</span>matricula<span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>matricula</th>
<th>nome</th>
</tr>
</thead>
<tbody>
<tr>
<td>00235</td>
<td>Márcio Almeida Silva</td>
</tr>
<tr>
<td>00236</td>
<td>Cláudia Morais</td>
</tr>
<tr>
<td>00237</td>
<td>Roberta Martins</td>
</tr>
</tbody>
</table><ol start="5">
<li>
<p>Quantas vezes cada produto foi vendido?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> PROD<span class="token punctuation">.</span>nome_do_produto<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">as</span> TOTAL <span class="token keyword">FROM</span> tabela_de_produtos PROD 
	<span class="token keyword">JOIN</span> itens_notas_fiscais ITENS 
	<span class="token keyword">ON</span> PROD<span class="token punctuation">.</span>codigo_do_produto <span class="token operator">=</span> ITENS<span class="token punctuation">.</span>codigo_do_produto 
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> PROD<span class="token punctuation">.</span>codigo_do_produto 
	<span class="token keyword">ORDER</span> <span class="token keyword">BY</span> TOTAL<span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>nome_do_produto</th>
<th>TOTAL</th>
</tr>
</thead>
<tbody>
<tr>
<td>Festival de Sabores - 1,5 Litros - Açai</td>
<td>6893</td>
</tr>
<tr>
<td>Festival de Sabores - 700 ml - Açai</td>
<td>6909</td>
</tr>
<tr>
<td>Videira do Campo - 2 Litros - Cereja/Maça</td>
<td>6971</td>
</tr>
<tr>
<td>Videira do Campo - 700 ml - Cereja/Maça</td>
<td>7018</td>
</tr>
<tr>
<td>Linha Citros - 700 ml - Lima/Limão</td>
<td>7043</td>
</tr>
</tbody>
</table><ol start="6">
<li>
<p>Quantas vezes o produto <strong>Sabor da Montanha - 700 ml - Uva</strong> foi vendido?</p>
<pre class=" language-sql"><code class="prism  language-sql"><span class="token keyword">SELECT</span> PROD<span class="token punctuation">.</span>nome_do_produto<span class="token punctuation">,</span> <span class="token function">COUNT</span><span class="token punctuation">(</span><span class="token operator">*</span><span class="token punctuation">)</span> <span class="token keyword">as</span> TOTAL <span class="token keyword">FROM</span> tabela_de_produtos PROD 
	<span class="token keyword">JOIN</span> itens_notas_fiscais ITENS <span class="token keyword">ON</span> PROD<span class="token punctuation">.</span>codigo_do_produto <span class="token operator">=</span> ITENS<span class="token punctuation">.</span>codigo_do_produto
	<span class="token keyword">GROUP</span> <span class="token keyword">BY</span> PROD<span class="token punctuation">.</span>codigo_do_produto 
	<span class="token keyword">HAVING</span> PROD<span class="token punctuation">.</span>nome_do_produto <span class="token operator">=</span> <span class="token string">'Sabor da Montanha - 700 ml - Uva'</span><span class="token punctuation">;</span>
</code></pre>
</li>
</ol>

<table>
<thead>
<tr>
<th>nome_do_produto</th>
<th>TOTAL</th>
</tr>
</thead>
<tbody>
<tr>
<td>Sabor da Montanha - 700 ml - Uva</td>
<td>7194</td>
</tr>
</tbody>
</table>
