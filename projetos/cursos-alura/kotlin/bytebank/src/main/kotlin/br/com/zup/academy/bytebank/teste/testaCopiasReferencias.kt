import br.com.zup.academy.bytebank.modelo.Cliente
import br.com.zup.academy.bytebank.modelo.ContaCorrente

fun testaCopiasReferencias() {

    var numeroX = 10
    var numeroY = numeroX
    numeroX++

    println("numeroX $numeroX")
    println("numeroY $numeroY")

    val joao = Cliente(nome = "João", cpf = "111", senha = 1)
    val contaJoao = ContaCorrente(numeroConta = 1000, titular = joao)

    val maria = Cliente(nome = "Maria", cpf = "111", senha = 1)
    var contaMaria = contaJoao // aponta para o mesmo objeto
    contaMaria = ContaCorrente(titular = maria, numeroConta = 1000)

    println("Joao: ${contaJoao.titular}")
    println(contaMaria.titular)
    println(contaJoao)
    println(contaMaria)
}