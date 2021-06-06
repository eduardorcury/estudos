package br.com.zup.autores

class DetalhesAutorResponse(
    val id: Long?,
    val nome: String,
    val descricao: String
) {

    constructor(autor: Autor) : this(autor.id, autor.nome, autor.descricao) {

    }

    override fun toString(): String {
        return "ID: $id, Autor: $nome, Descrição: $descricao"
    }

}
