package br.com.zup.academy.bytebank.modelo

class Analista(
    nome: String,
    cpf: String,
    salario: Double,
) : Funcionario(nome, cpf, salario) {

    override val bonifica = salario

}