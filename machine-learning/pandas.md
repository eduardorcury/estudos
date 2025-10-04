---
title: "Pandas"
layout: single
sidebar:
  nav: main
permalink: /machine-learning/pandas/
---

## Comandos

1. Criar DataFrame (tabela):

```python
>> pd.DataFrame({'Yes': [50, 21], 'No': [131, 2]})
```

| Index | Yes | No  |
| ----- | --- | --- |
| 0     | 50  | 131 |
| 1     | 21  | 2   |

2. Ler csv:

```python
>> pd.read_csv("../path/to/data.csv")
```

3. Ver tamanho da tabela:

```python
>> data.shape
(129971, 14)
```

4. Ver 5 primeiras linhas da tabela:

```python
data.head()
```

<figure><img src="../../assets/images/head.png" alt=""></figure>

5. Informações gerais da tabela (count, média, standard deviation, etc):

```python
data.describe()
```

<figure><img src="../../assets/images/describe.png" alt=""></figure>

6. Acessando valores

```python
data.iloc[0]

longitude              -122.23
latitude                 37.88
housing_median_age        41.0
total_rooms              880.0
total_bedrooms           129.0
population               322.0
households               126.0
median_income           8.3252
median_house_value    452600.0
ocean_proximity       NEAR BAY
---

# Primeira coluna
>> data.iloc[:, 0]
---
0       -122.23
1       -122.22
2       -122.24
3       -122.25
4       -122.25
          ...  
20635   -121.09
20636   -121.21
20637   -121.22
20638   -121.32
20639   -121.24
---

# Terceira coluna das 3 primeiras linhas
>> data.iloc[0:2, 2]
---
0    41.0
1    21.0
2    52.0
--

# Últimas 3 linhas da quarta coluna
>> data.iloc[-3:, 3]
---
20637    2254.0
20638    1860.0
20639    2785.0
---

# Primeiro valor da coluna
>> data.loc[0, "households"]
---
126.0
---

# Valores de colunas específicas
>> data.loc[:, ["latitude", "population"]]
---
	latitude	population
0	37.88	        322.0
1	37.86	        2401.0
2	37.85	        496.0
...	...	        ...
20635	39.48	        845.0
20636	39.49	        356.0
20637	39.43	        1007.0
---

# Valores filtrados
>> data.loc[(data.ocean_proximity == "NEAR BAY") &#x26; (data.population >= 3000), "population"]
---
95       3469.0
185      4367.0
283      4985.0
          ...  
18911    5008.0
18912    4163.0
18926    8276.0
---
```

