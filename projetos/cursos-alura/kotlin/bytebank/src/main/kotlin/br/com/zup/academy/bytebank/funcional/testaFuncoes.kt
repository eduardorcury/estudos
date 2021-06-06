package br.com.zup.academy.bytebank.funcional

import br.com.zup.academy.bytebank.modelo.Autenticavel
import br.com.zup.academy.bytebank.modelo.Endereco
import br.com.zup.academy.bytebank.modelo.SistemaInterno

fun testaFuncoes() {
    Endereco(
        logradouro = "rua x",
        numero = 100
    ).run {
        "$logradouro, $numero".toUpperCase()
    }.let(::println)

    listOf(Endereco(logradouro = "rua x", numero = 100),
        Endereco(),
        Endereco(complemento = "ap 10"))
        .filter { endereco ->
            endereco.complemento.isNotEmpty()
        }.let(::println)

    // higher order function: Function que recebe ou devolve outra função

    "".let {

    }

    "".apply {

    }

    teste({ println("") }, 1)

    soma(1, 5) {
        println(it)
    }

    mutableListOf<Int>()
        .apply {
            `soma os numeros dois a dois e retorna XD`(10, 10, 20, 15, 5, 10) {
                this.addAll(it)
            }
        }.let(::println)

    val autenticavel = object : Autenticavel {
        val senha = 1234
        override fun autentica(senha: Int) = this.senha == senha
    }

    SistemaInterno().entra(autenticavel, 1234) {
        println("realiza pagamento")
    }
}

fun soma(a: Int, b: Int, resultado: (Int) -> Unit) {
    resultado(a + b)
}

fun `soma os numeros dois a dois e retorna XD`(vararg numeros: Int, resultado: (List<Int>) -> Unit) {
    numeros.filterIndexed { index, _ ->
        index % 2 != 0
    }.mapIndexed { index, numero ->
        numero + numeros[index + 1]
    }.let { resultado(it) }
}

fun teste(bloco: () -> Unit, a: Int) {

}
