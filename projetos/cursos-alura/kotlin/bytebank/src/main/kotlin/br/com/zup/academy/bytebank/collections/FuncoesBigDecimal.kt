package br.com.zup.academy.bytebank.collections

import java.math.BigDecimal
import java.math.RoundingMode

fun bigDecimalArrayOf(vararg valores: String): Array<BigDecimal> =
    Array(valores.size) { i -> valores[i].toBigDecimal().setScale(2, RoundingMode.UP) }

fun Array<BigDecimal>.somatoria(): BigDecimal =
    this.reduce { acumulador, salario -> acumulador + salario }

fun Array<BigDecimal>.media(): BigDecimal {
    return if (this.isEmpty()) {
        BigDecimal.ZERO
    } else {
        (this.somatoria() / this.size.toBigDecimal()).setScale(2, RoundingMode.UP)
    }
}