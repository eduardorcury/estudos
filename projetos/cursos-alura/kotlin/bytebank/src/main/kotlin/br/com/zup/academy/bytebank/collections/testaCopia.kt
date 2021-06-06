package br.com.zup.academy.bytebank.collections

fun testaCopia() {
    val banco = BancoDeNomes()
    val nomesSalvos: Collection<String> = banco.nomes

    banco.adiciona(nome = "Alex")

    println(nomesSalvos)
    println(banco.nomes)
    println(BancoDeNomes().nomes)
}

class BancoDeNomes {

    val nomes: Collection<String> get() = dados.toList()

    companion object {
        private val dados = mutableListOf<String>()
    }

    fun adiciona(nome: String) {
        dados.add(nome)
    }

}

fun testaCollections() {
    val nomes: Collection<String> = listOf(
        "Eduardo",
        "Alex",
        "Leonardo",
        "Maria",
        "Daniela"
    )

    for (nome in nomes.iterator()) {
        println(nome)
    }

    println(nomes)
    println(nomes.size)
}