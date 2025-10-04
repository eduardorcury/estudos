---
title: "Boas práticas"
layout: single
sidebar:
  nav: main
permalink: /golang/boas-praticas/
---

## Criar slices com tamanho fixo, caso o tamanho seja conhecido

A seguir, temos um algoritmo que checa se uma string é um palíndromo. A primeira função cria um slice _letters_ sem tamanho especificado. Já a segunda, cria um slice com o tamanho exato da string informada.

* Slice com tamanho variado:
 
```go
func IsPalindrome(s string) bool {
	var letters []rune
	for _, r := range s {
		if unicode.IsLetter(r) {
			letters = append(letters, unicode.ToLower(r))
		}
	}

	n := len(letters) / 2
	for i := 0; i < n; i++ {
		if letters[i] != letters[len(letters)-1] {
			return false
		}
	}
	return true
}
```

* Slice com tamanho especificado:

```go
func IsPalindromeFaster(s string) bool {
	letters := make([]rune, 0, len(s))
	for _, r := range s {
		if unicode.IsLetter(r) {
			letters = append(letters, unicode.ToLower(r))
		}
	}

	n := len(letters) / 2
	for i := 0; i < n; i++ {
		if letters[i] != letters[len(letters)-1] {
			return false
		}
	}
	return true
}
```

Rodando um Benchmark nas 2 funções, podemos ver que a segunda função performa muito melhor que a primeira:

```bash
$ go test -bench=. -benchmem
goos: windows
goarch: amd64
pkg: goProject
cpu: AMD Ryzen 5 5600H with Radeon Graphics
BenchmarkIsPalindrome-12                 5051325               228.6 ns/op           248 B/op          5 allocs/op
BenchmarkIsPalindromeFaster-12          10803306               128.9 ns/op           128 B/op          1 allocs/op
PASS
ok      goProject       3.671s
```

Isso ocorre por causa da forma como Go trata **slices** e a função **append**. Quando o array da slice não tem capacidade suficiente pra incluir um novo elemento, um novo array é criado com uma capacidade maior. Quando criamos uma slice com tamanho pré-determinado, evitamos a alocação de memória desnecessária da primeira função, evidenciado pela diferença de allocs/op.

***

## Usar strings.Builder para concatenar um grande número de strings

Como as strings em go são imutáveis, usar a concatenação a seguir cria várias strings em memória

```go
s := ""
s += "a"
s += "b"
s += "c"
```

Para evitar isso, usar o **strings.Builder**

```go
var sb strings.Builder
sb.WriteString("x")
```

Caso seja conhecido o tamanho final da string, também é possível pré alocar o Builder para maior performance

```go
var sb strings.Builder
sb.Grow(N)
```

Comparativo de benchmarks:

```bash
$ go test -bench=. -benchmem ./strings_test.go 
goos: windows
cpu: AMD Ryzen 5 5600H with Radeon Graphics
BenchmarkConcat-12                   133           9644782 ns/op        53164097 B/op      10000 allocs/op
BenchmarkBuilder-12                64489             22232 ns/op           46584 B/op         16 allocs/op
BenchmarkBuilderGrow-12            79178             12982 ns/op           10240 B/op          1 allocs/op
PASS
ok      command-line-arguments  5.160s
```
