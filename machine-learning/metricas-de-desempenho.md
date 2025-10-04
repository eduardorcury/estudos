---
title: "Métricas de Desempenho"
layout: single
sidebar:
  nav: main
---

> O cálculo do desempenho de um classificador é mais difícil que avaliar o desempenho de um regressor. Nesse cálculo, é preciso entender os conceitos de matriz de confusão, precisão, revocação (taxa de verdadeiros positivos), acurácia e taxa de falsos positivos.

### Matriz de Confusão

Uma matriz de confusão de um classificador é uma tabela em que cada linha representa uma classe real e cada coluna representa uma classe prevista. A primeira célula da primeira linha representa o número de **verdadeiros positivos**, ou seja, o número de instâncias que o classificador corretamente determinou pertencerem à classe positiva. A segunda célula da primeira linha representa os **falso positivos**, o número de instâncias classificadas como negativos mas que na realidade não são. Da mesma forma, a primeira célula da segunda linha representa a quantidade de instâncias classificadas como positivas e que não são (**falso positivos**), e a segunda célula da segunda linha é o número de instâncias negativas que foram corretamente classificadas como negativas (**verdadeiros negativos**).

<figure><img src="../../assets/images/metricas-desempenho.png" alt=""><figcaption></figcaption></figure>

### Métricas

1. Precisão

A precisão é a métrica que responde a seguinte pergunta: de todas as instâncias classificadas como positivas, quantas foram classificadas corretamente? Ela é dada pela seguinte fórmula:

$$
precisão = {\dfrac{TP}{TP+FP}}
$$

2. Revocação

Também chamada de taxa de verdadeiros positivos, essa métrica responde a pergunta: de todas as instâncias que pertencem à classe positiva, quantas foram detectadas corretamente como tal?



$$
revocacão = {\dfrac{TP}{TP+FN}}
$$

3. Acurácia

A acurácia é a simples taxa de classificações corretas realizadas pelo algoritmo. Ela é dada pela fórmula:

$$
acurácia = {\dfrac{TP+TN}{TN+TP+FN+FP}}
$$

4. F1 Score

F1 score é a média harmônica entre as métricas de precisão e revocação. Ela dá mais importância aos valores mais baixos, fazendo com que um algoritmo só tenha uma média alta se ambas as métricas forem altas.

$$
F1= 2*{\dfrac{precisão*revocacao}{precisão+revocacão}}
$$

5. Taxa de Falsos Positivos

É a taxa de instâncias negativas classificadas incorretamente como positivas, dada pela fórmula:

$$
TFP = {\dfrac{FP}{TN+FP}}
$$

O entendimento das métricas é importante pois cada projeto de aprendizado de máquina terá uma métrica mais valiosa, e cabe ao engenheiro definir qual é a métrica. Supondo um algoritmo de detecção de doenças, é preferível que o máximo possível de pacientes doentes sejam identificados. Dessa forma, queremos um baixo número de falsos negativos e, por consequência, uma alta revocação. Por outro lado, supondo um algoritmo de deve escolher ações para investir, é preferível que ele somente escolha as ações que realmente vão subir de preço. Ou seja, nesse caso queremos uma alta precisão.

