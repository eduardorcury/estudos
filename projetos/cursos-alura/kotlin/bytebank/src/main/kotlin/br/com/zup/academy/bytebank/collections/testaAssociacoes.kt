package br.com.zup.academy.bytebank.collections

fun testaAssociacoes() {
    val pedidos = listOf(
        Pedido(numero = 1, valor = 10.0),
        Pedido(numero = 2, valor = 30.0),
        Pedido(numero = 3, valor = 25.0),
        Pedido(numero = 4, valor = 60.0),
    )

    println(pedidos)

    pedidos.associateBy { pedido ->
        pedido.numero
    }.apply { println(this) }

    pedidos.associateWith { pedido ->
        pedido.valor > 50.0
    }.apply { println(this) }

    pedidos
        .groupBy { it.valor > 50.0 }
        .apply { println(this[true]) }
}

data class Pedido(
    val numero: Int,
    val valor: Double
)