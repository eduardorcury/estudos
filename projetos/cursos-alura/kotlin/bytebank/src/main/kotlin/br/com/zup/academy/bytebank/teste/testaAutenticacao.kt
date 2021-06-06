package br.com.zup.academy.bytebank.teste

import br.com.zup.academy.bytebank.modelo.Cliente
import br.com.zup.academy.bytebank.modelo.Diretor
import br.com.zup.academy.bytebank.modelo.Gerente
import br.com.zup.academy.bytebank.modelo.SistemaInterno

fun testaAutenticacao() {
    val gerente = Gerente(
        nome = "Fran",
        cpf = "111",
        salario = 30000.0,
        senha = 10
    )

    val diretora = Diretor(
        nome = "Guilherme",
        cpf = "222",
        salario = 6000.0,
        senha = 20,
        plr = 200.0
    )

    val sistema = SistemaInterno()
    sistema.entra(admin = gerente, senha = 10)
    sistema.entra(admin = diretora, senha = 10)

    val gui = Cliente(
        nome = "Gui",
        cpf = "2222",
        senha = 10
    )

    sistema.entra(admin = gui, senha = 10)
}