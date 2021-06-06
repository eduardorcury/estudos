package br.com.zup.academy.bytebank.modelo

import br.com.zup.academy.bytebank.exceptions.AutenticacaoException
import br.com.zup.academy.bytebank.exceptions.SaldoInsuficienteException

abstract class Conta(
    val titular: Cliente,
    val numeroConta: Int
) : Autenticavel {

    var saldo = 0.0
        protected set

    companion object {

        init {
            println("criou companion object")
        }

        var total = 0
            private set

    }

    init {
        println("Criando conta")
        total++
    }

    // comportamentos
    fun deposita(valor: Double) {
        if (valor > 0) {
            this.saldo += valor
        }
    }

    abstract fun saca(valor: Double): Double

    fun transfere(valor: Double, destino: Conta, senha: Int) {

        if (saldo < valor) {
            throw SaldoInsuficienteException()
        }
        saldo -= valor
        destino.deposita(valor)
        if (!autentica(senha)) {
            throw AutenticacaoException()
        }

    }

    override fun autentica(senha: Int): Boolean {
        return titular.autentica(senha)
    }
}