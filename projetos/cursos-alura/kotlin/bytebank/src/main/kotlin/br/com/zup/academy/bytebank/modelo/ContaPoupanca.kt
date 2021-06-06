package br.com.zup.academy.bytebank.modelo

import br.com.zup.academy.bytebank.exceptions.SaldoInsuficienteException

class ContaPoupanca(
    titular: Cliente,
    numeroConta: Int
) : Conta(
    titular = titular,
    numeroConta = numeroConta
) {

    override fun saca(valor: Double): Double {
        return if (this.saldo >= valor) {
            this.saldo -= valor
            valor
        } else {
            throw SaldoInsuficienteException()
        }
    }


}