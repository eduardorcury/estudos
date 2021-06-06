package br.com.zup.academy.bytebank

import br.com.zup.academy.bytebank.collections.bigDecimalArrayOf
import br.com.zup.academy.bytebank.collections.media
import br.com.zup.academy.bytebank.collections.somatoria
import java.math.BigDecimal
import java.math.RoundingMode

fun main() {

    println("Bem vindo ao bytebank")

    val idades = intArrayOf(25, 20, 40, 80, 15)

    var maiorIdade = Int.MIN_VALUE
    for (idade in idades) {
        if (idade > maiorIdade) {
            maiorIdade = idade
        }
    }

    var menorIdade = Int.MAX_VALUE
    idades.forEach { idade ->
        if (idade < menorIdade) {
            menorIdade = idade
        }
    }

    println(maiorIdade)
    println(menorIdade)

    val salarios: DoubleArray = doubleArrayOf(1500.50, 6000.0, 4000.0, 3300.0)

    val aumento = 1.1
    for (indice in salarios.indices) {
        salarios[indice] = salarios[indice] * aumento
    }

    println(salarios.contentToString())

    salarios.forEachIndexed { index, salario ->
        salarios[index] = salario * aumento
    }

    println(salarios.contentToString())

    val rangeTo = 1.rangeTo(10)
    val pares = 0..100 step 2

    val downTo = 100 downTo 0 step 3

    val intervalo = 1500.0..5000.0
    when (4000.0) {
        in intervalo -> println("está dentro do intervalo")
        else -> println("não está dentro do intervalo")
    }

    val alfabeto = 'a'..'z'

    println("Maior idade ${idades.maxOrNull()}")
    println("Menor idade ${idades.minOrNull()}")
    println("Média ${idades.average()}")

    val todosMaiores = idades.all { it >= 18 }
    val algumMaior = idades.any { it >= 18 }
    val maiores = idades.filter { it >= 18 }
    val primeiroMenor = idades.find { it < 18 }

    val salariosBigDecimal = bigDecimalArrayOf("1500.55", "5000.00", "7500.43", "500.50", "10500.60")
    println(salariosBigDecimal.contentToString())

    val salariosComAumento = salariosBigDecimal
        .map {
            calculaAumento(it)
        }.toTypedArray()

    println(salariosComAumento.contentToString())
    val gastoInicial = salariosComAumento.somatoria()
    println("Gasto inicial $gastoInicial")
    val gasto6Meses = salariosComAumento.fold(gastoInicial) { acumulador, salario ->
        (acumulador + salario * "6".toBigDecimal()).setScale(2, RoundingMode.UP)
    }
    println("Gasto após 6 meses: $gasto6Meses")

    val mediaTresMaiores = salariosComAumento
        .sorted()
        .takeLast(3)
        .toTypedArray()
        .media()

    println("Três maiores: $mediaTresMaiores")

}

private fun calculaAumento(salario: BigDecimal) =
    when {
        salario < "5000".toBigDecimal() -> salario + "500".toBigDecimal()
        else -> (salario * "1.1".toBigDecimal()).setScale(2, RoundingMode.UP)
    }