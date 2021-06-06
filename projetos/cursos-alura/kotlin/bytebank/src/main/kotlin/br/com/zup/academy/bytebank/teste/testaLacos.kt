fun testaLacos() {
    var i = 0
    while (i < 5) {
        val titular = "Eduardo $i" // Inferência de tipo. não pode ser modificada
        val numeroConta = 1000 + i
        var saldo = 100.0 + i

        println("Titular: $titular") // String template
        println("Número da conta: $numeroConta")
        println("Saldo: $saldo")
        println()
        i++
    }

    for (z in 5 downTo 1 step 2) {
        val titular = "Eduardo $i" // Inferência de tipo. não pode ser modificada
        val numeroConta = 1000 + i
        var saldo = 100.0 + i

        println("Titular: $titular") // String template
        println("Número da conta: $numeroConta")
        println("Saldo: $saldo")
        println()
    }
}