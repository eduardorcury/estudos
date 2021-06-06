package br.com.zup.academy.bytebank.modelo

import br.com.zup.academy.bytebank.exceptions.SaldoInsuficienteException

class ContaCorrente(
    titular: Cliente,
    numeroConta: Int
) : Conta(
    titular = titular,
    numeroConta = numeroConta
) {

    val x: Nothing? = null

    override fun saca(valor: Double): Double {
        val valorComTaxa = valor + 0.1
        return if (this.saldo >= valorComTaxa) {
            this.saldo -= valorComTaxa
            valorComTaxa
        } else {
            throw SaldoInsuficienteException("Saldo disponível: $saldo")
        }
    }

}