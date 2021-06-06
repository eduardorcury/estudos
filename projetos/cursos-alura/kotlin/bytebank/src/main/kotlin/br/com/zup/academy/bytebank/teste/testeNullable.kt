package br.com.zup.academy.bytebank.teste

import br.com.zup.academy.bytebank.modelo.Endereco
import java.lang.NumberFormatException

fun testeNullable() {

//    fun teste() {
//        return teste()
//    }
//
//    teste()

    val x = "teste"
    val a: Int? = try {
        Integer.parseInt(x)
    } catch (e: NumberFormatException) {
        null
    }

    val y = when (a) {
        null -> "opa"
        else -> 1
    }

    println(a)
    println(y)

    val endereco: Endereco? = Endereco("rua x")
    endereco?.logradouro?.length?.toBigDecimal()

    endereco?.let {
        print(it.logradouro.length.toBigDecimal())
    }

    val logradouro: String = endereco?.logradouro ?: "sem rua"


}