package br.com.zup.academy.bytebank.collections

fun testaComportamentosMap() {
    val pedidos = mutableMapOf<Int, Double>(
        Pair(1, 20.0),
        Pair(2, 55.0),
        3 to 60.0,
        2 to 34.0,
        4 to 25.0
    ).apply { println(this) }

    val pedido = pedidos[1]
    pedido?.let { println("pedido $it") }

    pedidos[5] = 33.0
    pedidos.putIfAbsent(6, 80.0)
    pedidos.putIfAbsent(6, 100.0)

    pedidos.remove(4)

    val orElse = pedidos.getOrElse(10, {
        "não existe"
    }).apply { println(this) }

    pedidos.getOrDefault(10, 0.0).apply { println(this) }

    pedidos
        .filter { (numero, valor) ->
            numero % 2 == 0 && valor > 50.0
        }.apply { println(this) }

    println(pedidos)
    println(pedidos + Pair(7, 90.0))
    println(pedidos - 3)

    pedidos.putAll(setOf(7 to 90.0, 8 to 150.0))
    println(pedidos)

    pedidos += setOf(9 to 40.0)
    println(pedidos)

    pedidos.keys.remove(2)
    pedidos.values.remove(60.0)
    println(pedidos)

    pedidos -= listOf(1, 6)
    println(pedidos)
}