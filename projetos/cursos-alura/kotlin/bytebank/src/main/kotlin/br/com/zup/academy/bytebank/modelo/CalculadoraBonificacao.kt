package br.com.zup.academy.bytebank.modelo

class CalculadoraBonificacao {

    var total = 0.0
        private set

    fun registra(funcionario: Funcionario) {
        this.total += funcionario.bonifica
    }

}