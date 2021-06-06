package br.com.zup.academy.bytebank.modelo

class Diretor(
    nome: String,
    cpf: String,
    salario: Double,
    senha: Int,
    val plr: Double
) : FuncionarioAdmin(nome, cpf, salario, senha) {

    override val bonifica = salario + plr

}