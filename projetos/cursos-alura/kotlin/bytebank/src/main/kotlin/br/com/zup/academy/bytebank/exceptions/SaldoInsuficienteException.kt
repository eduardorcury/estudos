package br.com.zup.academy.bytebank.exceptions

class SaldoInsuficienteException(mensagem: String = "O saldo é insuficiente") :
    Exception(mensagem) {
}