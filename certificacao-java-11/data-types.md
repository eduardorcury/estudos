# Data Types

### Tipos primitivos & Referências

Existem dois tipos de dados no Java: tipos primitivos e referências.

* Tipos primitivos: não representa um objeto no Java. Todos os objetos são nada mais que uma coleção complexa de tipos primitivos. Não é possível chamar um método através de um tipo primitivo, e não é possível atribuir um valor nulo.

```java
int nulo = null; // não compila
Boolean objeto = null;
boolean primitivo = objeto; // Resulta em NullPointer
```

* Referências: apontam para o objeto e armazena o endereço de memória em que o objeto pode ser encontrado.

### Tipos primitivos

| nome      | tamanho                   | observação                                     |
| --------- | ------------------------- | ---------------------------------------------- |
| _boolean_ | `true / false`            |                                                |
| _byte_    | 8 bits                    |                                                |
| _short_   | 16 bits                   |                                                |
| _int_     | 32 bits                   | tipo numérico padrão                           |
| _long_    | 64 bits                   | precisa colocar L no final, _e.g._ **100L**    |
| _float_   | 32 bits (ponto flutuante) | precisa colocar F no final, _e.g._ **100.12F** |
| _double_  | 64 bits (ponto flutuante) |                                                |
| _char_    | 16 bits                   |                                                |

* Todos os tipos numéricos tem sinal, o que significa que um bit é reservado para indicar o sinal positivo ou negativo do número. Por isso, a range de um byte é $$[-128, ..., 0, ..., 127] (2^7=128)$$
* String **não é** um tipo primitivo.
* O java assume que estamos definindo um \_\*\*int \*\*\_quando escrevemos um número no código. Tentar atribuir um número maior que o máximo de 32 bits ($$2^{31}$$) resulta em erro de compilação.

```java
long a = 4_000_000_000;  // não compila
long b = 4_000_000_000L; // compila
```

* É possível adicionar **\_** no meio de tipos numéricos. Não é possível adicionar no começo ou final do número, nem antes ou depois de um ponto.

```java
long c   = 1_____0; // compila
double e = 1_000.0; // compila
long d   = _1000;   // não compila
```

### chars & números

Como os caracteres tem uma representação em decimal, é possível atribuir um caractere a um tipo primitivo numérico, e um número a um [_char_](https://unicode-table.com/en/).

No entanto, é preciso respeitar os valores **mínimos e máximos** dos tipos primitivos para o código compilar, _e.g_., atribuir um \_char \_de número maior que 127 a um byte causa um erro de compilação.

```java
double slash = '/';
int special = 'ç';
System.out.println(slash);           // Printa 47.0
System.out.println(special);         // Printa 231.0
System.out.println(slash + special); // Printa 278.0 (soma)
char a = 120;
System.out.println(a); // Printa x
char b = -123; // não compila -> chars não possuem sinal
byte c = '£'; // não compila -> esse char tem número 163, maior que o máximo que cabe em um byte
```

### Números em outras bases

É possível usar outras bases além da decimal.

|            Base            |  Prefixo | Exemplo |
| :------------------------: | :------: | :-----: |
|       Octal $$[0-7]$$      |     0    |   017   |
| Hexadecimal $$[0-9; a-F]$$ | 0x ou 0X |   0X1F  |
|      Binária $$[0-1]$$     | 0b ou 0B |   0b10  |

```java
int octal = -0137;
int hexadecimal = 0xabc013ed;
float binario = 0b00101;
System.out.println(octal);               // Printa -95
System.out.println(hexadecimal);         // Printa -1413475347
System.out.println(binario);             // Printa 5.0
int soma = -0137 + 0xabc013ed + 0b00101; // Printa -1413475437 (soma)
```
