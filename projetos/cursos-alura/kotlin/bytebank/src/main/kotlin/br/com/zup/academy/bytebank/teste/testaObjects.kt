package br.com.zup.academy.bytebank.teste

import br.com.zup.academy.bytebank.modelo.*

fun testaObjects() {
    // Object expression -> objeto anônimo: representar objetos sem criar uma nova classe
    val teste = object : Autenticavel {
        val nome: String = "teste"
        val cpf: String = "1111"
        override fun autentica(senha: Int): Boolean {
            TODO("Not yet implemented")
        }
        //...
    }

    val alex = Cliente(
        nome = "Alex",
        cpf = "111",
        senha = 1,
        endereco = Endereco(
            logradouro = "rua x"
        )
    )
    val contaCorrente = ContaCorrente(
        titular = alex,
        numeroConta = 1000
    )

    val fran = Cliente(
        nome = "Fran",
        cpf = "111",
        senha = 1
    )
    val contaPoupanca = ContaPoupanca(
        titular = fran,
        numeroConta = 1001
    )

    println("total contas: ${Conta.total}")
}