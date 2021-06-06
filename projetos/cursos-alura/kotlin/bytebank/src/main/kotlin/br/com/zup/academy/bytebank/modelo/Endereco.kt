package br.com.zup.academy.bytebank.modelo

data class Endereco(
    var logradouro: String = "",
    var numero: Int = 0,
    var bairro: String = "",
    var cidade: String = "",
    var estado: String = "",
    var cep: String = "",
    var complemento: String = "",
) {

    fun completo(): String {
        return """
        $logradouro, $numero, $bairro, $cidade - $estado
        $cep,
        $complemento
        """.trimIndent()
    }

}
