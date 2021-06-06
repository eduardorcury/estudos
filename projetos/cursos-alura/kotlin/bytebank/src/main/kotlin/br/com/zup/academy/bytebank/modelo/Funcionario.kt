package br.com.zup.academy.bytebank.modelo

abstract class Funcionario(
    val nome: String,
    val cpf: String,
    val salario: Double
) {

    // property
    abstract val bonifica: Double

}