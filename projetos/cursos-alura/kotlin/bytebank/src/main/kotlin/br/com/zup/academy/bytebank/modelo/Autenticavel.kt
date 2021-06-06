package br.com.zup.academy.bytebank.modelo

interface Autenticavel {

    fun autentica(senha: Int): Boolean

}