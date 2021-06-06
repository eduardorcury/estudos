package br.com.zup.academy.bytebank.teste

import br.com.zup.academy.bytebank.modelo.Analista
import br.com.zup.academy.bytebank.modelo.CalculadoraBonificacao
import br.com.zup.academy.bytebank.modelo.Diretor
import br.com.zup.academy.bytebank.modelo.Gerente

fun testaFuncionarios() {
    val alex = Analista(
        nome = "Alex",
        cpf = "111",
        salario = 3000.0
    )

    val fran = Gerente(
        nome = "Fran",
        cpf = "111",
        salario = 30000.0,
        senha = 10
    )

    val gui = Diretor(
        nome = "Guilherme",
        cpf = "222",
        salario = 6000.0,
        senha = 10,
        plr = 200.0
    )

    println("nome ${alex.nome}")
    println("cpf ${alex.cpf}")
    println("salario ${alex.salario}")
    println("bonificação ${alex.nome}: ${alex.bonifica}")

    println("bonificacao ${fran.nome}: ${fran.bonifica}")

    if (fran.autentica(senha = 10)) {
        println("autenticado")
    } else {
        println("falha na autenticação")
    }

    println("nome ${gui.nome}")
    println("cpf ${gui.cpf}")
    println("salario ${gui.salario}")
    println("bonificação ${gui.nome}: ${gui.bonifica}")

    val calculadora = CalculadoraBonificacao();
    calculadora.registra(alex)
    calculadora.registra(fran)
    calculadora.registra(gui)

    println("Total bonificação: ${calculadora.total}")
}