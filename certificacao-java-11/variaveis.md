---
title: "Variáveis"
layout: single
sidebar:
  nav: main
permalink: /certificacao-java-11/variaveis/
---

#### Identificadores

> Identificador: nome de variável, método, classe, interface ou pacote.

1. É possível inicializar várias variáveis em uma linha se elas forem do mesmo tipo.
2. Identificadores podem conter letras, números, $ ou \_.
3. Identificadores não podem começar com números.
4.  Identificadores não podem ser iguais a alguma palavra reservada (considerando letras minúsculas e maiúsculas).

    ✅

    ```java
    String $;  
    long _0;  
    boolean booLean;  
    String[] string;  
    char Char, _char, $char;
    String s1 = "s1"; String s2 = "s2";  
    String s3 = "s3", s4 = "s4";  
    String s5 = "s5", s6;
    public void $teste() {}
    ```

    ❌

    ```java
    String 1_$;  
    boolean _;  
    double class;  
    char &er;  
    long @teste;
    int i1 = 1, int i2 = 2;  
    int i3 = 3, String s7 = "s7";
    ```

> var **não é** uma palavra reservada.

1. Números podem conter \_ entre dígitos, mas não podem começar com \_.
2.  Números podem começar com 1-9, 0, 0x, 0X, 0b ou 0B.

    ✅

    ```java
    int a = 1_00000;  
    double b = 1_00_00.;  
    int c = 1_______2;  
    int d = 0x0;  
    int e = 0B100;
    ```

    ❌

    ```java
    double f = 1_0_;  
    double g = _1;  
    double h = 1000_.000;  
    int i = 1_00_00.; //compila quando é double
    ```

#### var

1. var não é uma palavra reservada.
2. var pode ser usado em um construtor, método ou initializer block.
3. var não pode ser usado em parametros de construtor ou métodos, variáveis de instancia ou variáveis de classe.
4. var deve ser inicializado na mesma linha em que é declarado.
5. var pode mudar de valor.
6. var não pode mudar de tipo.
7. var não pode ser usado em múltiplas inicializações na mesma linha.
