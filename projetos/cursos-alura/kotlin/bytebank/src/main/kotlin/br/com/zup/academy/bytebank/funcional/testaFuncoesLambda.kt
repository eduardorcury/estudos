package br.com.zup.academy.bytebank.funcional

fun lambdasAnonimas() {

    val minhaLambda: (Int, Int) -> Int = { a, b ->
        println("Executa lambda")
        println("${a + b}")
        a + b
    }

    minhaLambda(5, 5)

    val minhaFuncAnonima: (Int, Int) -> Int = fun(a, b): Int {
        println("Executa função anônima")
        println("${a + b}")
        return a + b
    }

    minhaFuncAnonima(15, 15)

    val calculaBonificacao: (salario: Double) -> Double = lambda@{ salario ->
        if (salario >= 1000.0) {
            return@lambda salario + 50
        }
        salario + 100.0
    }

    println(calculaBonificacao(1000.0))
    println(calculaBonificacao(1100.0))

}

fun testaFuncaoReferencia() {
    // dois pontos significa que estamos mandando a referência, e não a execução
    // ou seja, estamos referenciando teste, e não executando teste()
    val minhaFuncao: (Int, Int) -> Int = ::soma

    minhaFuncao(10, 10) // Executa teste
    println(minhaFuncao) // fun teste(): kotlin.Unit

    val outraFuncao = { minhaFuncao }
    outraFuncao()(10, 10)

    val printa: (Int, Int) -> Int = Printa()
    printa(5, 5)
}

fun soma(a: Int, b: Int): Int {
    return a + b
}

class Printa : (Int, Int) -> Int {

    override fun invoke(a: Int, b: Int): Int {
        println(a + b)
        return a + b
    }

}
