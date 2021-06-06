import br.com.zup.academy.bytebank.exceptions.AutenticacaoException
import br.com.zup.academy.bytebank.exceptions.SaldoInsuficienteException
import br.com.zup.academy.bytebank.modelo.Cliente
import br.com.zup.academy.bytebank.modelo.ContaCorrente
import br.com.zup.academy.bytebank.modelo.ContaPoupanca
import br.com.zup.academy.bytebank.modelo.Endereco

fun testaContasDiferentes() {

    val alex = Cliente(
        nome = "Alex",
        cpf = "111",
        senha = 1,
        endereco = Endereco(
            logradouro = "rua x"
        )
    )
    val contaCorrente = ContaCorrente(
        titular = alex,
        numeroConta = 1000
    )

    val fran = Cliente(nome = "Fran", cpf = "111", senha = 1)
    val contaPoupanca = ContaPoupanca(
        titular = fran,
        numeroConta = 1001
    )
    println("Endereço do alex: ${alex.endereco.logradouro}")
    contaCorrente.deposita(valor = 1000.0)
    contaPoupanca.deposita(valor = 1000.0)

    println("saldo corrente: ${contaCorrente.saldo}")
    println("saldo poupança: ${contaPoupanca.saldo}")

    contaCorrente.saca(valor = 100.0)
    contaPoupanca.saca(valor = 100.0)

    try {
        contaCorrente.saca(10000.0)
    } catch (e: SaldoInsuficienteException) {
        println("Saldo insuficiente")
        e.printStackTrace()
    }

    println("saldo corrente após saque: ${contaCorrente.saldo}")
    println("saldo poupança após saque: ${contaPoupanca.saldo}")

    try {
        contaCorrente.transfere(100.0, contaPoupanca, 10)
    } catch (e: AutenticacaoException) {
        println("Falha na autenticacao")
        e.printStackTrace()
    }

}