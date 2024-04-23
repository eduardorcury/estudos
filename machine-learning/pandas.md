# Pandas

## Comandos

1. Criar DataFrame (tabela):

<pre class="language-python"><code class="lang-python"><strong>pd.DataFrame({'Yes': [50, 21], 'No': [131, 2]})
</strong></code></pre>

| Index | Yes | No  |
| ----- | --- | --- |
| 0     | 50  | 131 |
| 1     | 21  | 2   |

2. Ler csv:

<pre class="language-python"><code class="lang-python"><strong>data = pd.read_csv("../path/to/data.csv")
</strong></code></pre>

3. Ver tamanho da tabela:

```python
>> data.shape
(129971, 14)
```

4. Ver 5 primeiras linhas da tabela:

```python
data.head()
```

<figure><img src="../.gitbook/assets/image (10).png" alt=""><figcaption></figcaption></figure>

5. Informações gerais da tabela (count, média, standard deviation, etc):

```python
data.describe()
```

<figure><img src="../.gitbook/assets/image (9).png" alt=""><figcaption></figcaption></figure>

