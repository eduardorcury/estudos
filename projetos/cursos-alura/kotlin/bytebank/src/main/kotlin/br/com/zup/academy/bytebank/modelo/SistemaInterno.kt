package br.com.zup.academy.bytebank.modelo

class SistemaInterno {

    fun entra(admin: Autenticavel, senha: Int, autenticado: () -> Unit = {}) {
        if (admin.autentica(senha)) {
            println("Bem vindo admin")
            autenticado()
            return
        }
        println("Falha na autenticação")
    }

}