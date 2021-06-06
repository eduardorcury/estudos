package br.com.zup.academy.bytebank.collections

class Prateleira(
    val genero: String,
    val livros: List<Livro>
) {

    fun organizarPorAutor(): List<Livro> = livros.sortedBy { it.autor }

    fun organizarPorAno(): List<Livro> = livros.sortedBy { it.anoPublicacao }

}