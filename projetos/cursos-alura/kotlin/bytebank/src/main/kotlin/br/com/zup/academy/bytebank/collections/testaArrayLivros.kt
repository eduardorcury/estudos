package br.com.zup.academy.bytebank.collections

fun testaArrayLivros() {
    val livro1 = Livro(
        titulo = "1984",
        autor = "George Orwells",
        anoPublicacao = 1960
    )

    val livro2 = Livro(
        titulo = "Segurança em aplicações web",
        autor = "Rodrigo Ferreira",
        anoPublicacao = 2015,
        editora = "Casa do código"
    )

    val livro3 = Livro(
        titulo = "A Metamorfose",
        autor = "Kafka",
        anoPublicacao = 1912,
        editora = "Companhia das Letras"
    )

    val livro4 = Livro(
        titulo = "Servidão Humana",
        autor = "Maugham",
        anoPublicacao = 1950
    )

    val livros = mutableListOf(livro1, livro2, livro3, livro4)

    livros.add(
        Livro(
            titulo = "Sagarana",
            autor = "Guimarães Rosa",
            anoPublicacao = 1946
        )
    )

    livros.imprimeComMarcadores()
    livros.sorted().imprimeComMarcadores()
    livros.sortedBy { it.titulo }.imprimeComMarcadores()

    livros
        .filter { it.autor.contains("ro", true) }
        .sortedBy { it.anoPublicacao }
        .imprimeComMarcadores()

    val listaLivrosComNulos: MutableList<Livro?> =
        mutableListOf(livro1, livro2, livro3, livro4, null, null)

    listaLivrosComNulos.imprimeComMarcadores(tamanho = listaLivrosComNulos.size)

    livros
        .groupBy { it.editora ?: "Sem editora" }
        .forEach { (editora, livros) ->
            println("$editora: ${livros.joinToString { it.titulo }}")
        }

    val prateleira = Prateleira(
        genero = "Literatura",
        livros = livros
    )

    prateleira.organizarPorAutor().imprimeComMarcadores();
    prateleira.organizarPorAno().imprimeComMarcadores()
}

fun List<Livro?>.imprimeComMarcadores(tamanho: Int = 3) {

    val joinToString = this
        .filterNotNull()
        .joinToString(separator = "\n", limit = tamanho) {
            " - ${it.titulo} de ${it.autor}"
        }
    println("##### Lista de Livros ##### \n$joinToString")

}