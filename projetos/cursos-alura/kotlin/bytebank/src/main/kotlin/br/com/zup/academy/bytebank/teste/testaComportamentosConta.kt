import br.com.zup.academy.bytebank.modelo.Cliente
import br.com.zup.academy.bytebank.modelo.ContaCorrente
import br.com.zup.academy.bytebank.modelo.ContaPoupanca

fun testaComportamentosDaConta() {

    val alex = Cliente(nome = "Alex", cpf = "111", senha = 1)
    val contaAlex = ContaCorrente(titular = alex, numeroConta = 1000)
    println(contaAlex.titular)

    val fran = Cliente(nome = "Fran", cpf = "111", senha = 1)
    val contaFran = ContaPoupanca(titular = fran, numeroConta = 1001)
    contaFran.deposita(valor = 300.0)

    println(contaFran.titular)
    println(contaFran.numeroConta)
    println(contaFran.saldo)

    println(contaAlex.saldo)
    println("Depósito na conta do ${contaAlex.titular}")
    contaAlex.deposita(valor = 50.0)
    println(contaAlex.saldo)
}