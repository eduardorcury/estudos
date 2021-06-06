fun checaSaldo(saldo: Double) {
    when {
        saldo > 0 -> println("Saldo positivo")
        saldo == 0.0 -> println("Saldo igual a zero")
        else -> println("Saldo negativo")
    }
}