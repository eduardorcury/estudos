package br.com.zup.academy.bytebank.funcional

import br.com.zup.academy.bytebank.modelo.Cliente
import br.com.zup.academy.bytebank.modelo.ContaPoupanca
import br.com.zup.academy.bytebank.modelo.Endereco

fun main() {

    somaReceiver(1, 5) {
        println(this)
    }

}

fun withErun() {
    val enderecoCompleto = with(Endereco()) {
        logradouro = "rua x"
        numero = 10
        bairro = "vila y"
        cidade = "São Carlos"
        estado = "SP"
        cep = "11111-11"
        complemento = "Apartamento Z"
        completo()
    }.also(::println)

    val contaPoupanca = ContaPoupanca(
        Cliente(
            nome = "Eduardo",
            cpf = "1111",
            senha = 1234
        ), 1000)

    contaPoupanca.run {
        deposita(1000.0)
        saldo * 1.1
    }.also(::println)

    val rendimentoAnual = run {
        var saldo = contaPoupanca.saldo
        repeat(12) {
            saldo += saldo * 1.01
        }
        saldo
    }
    println(rendimentoAnual)
}

fun somaReceiver(a: Int, b: Int, resultado: Int.() -> Unit) {
    val total = a + b
    total.resultado()
}

