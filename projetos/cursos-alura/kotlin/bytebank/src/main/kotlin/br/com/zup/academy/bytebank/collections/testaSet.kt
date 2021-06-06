package br.com.zup.academy.bytebank.collections

fun testaSet() {
    val assistiramAndroid = setOf(
        "Alex",
        "Eduardo",
        "Fran",
        "Gui",
        "Leo"
    )

    val assistiramKotlin = setOf(
        "Alex",
        "Gui",
        "Leo",
        "Paulo"
    )

    println(assistiramAndroid union assistiramKotlin)
    println(assistiramAndroid intersect assistiramKotlin)
    println(assistiramAndroid subtract assistiramKotlin)
    println(assistiramKotlin subtract assistiramAndroid)

    val assistiramAmbos =
        (assistiramAndroid + assistiramKotlin)
            .apply { println(this) }
}