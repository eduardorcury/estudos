package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.Valid

@Validated
@Controller("/autores")
class CadastroAutorController(
    val autorRepository: AutorRepository,
    val enderecoClient: EnderecoClient,
) {

    @Post
    @Transactional
    fun cadastra(@Body @Valid request: NovoAutorRequest): HttpResponse<Any> {

        println("Requisição: $request")

        request.paraAutor(
            EnderecoResponse(
                rua = "rua x",
                cidade = "RJ",
                estado = "RJ"
            ))
            .run { autorRepository.save(this) }
            .let { autor -> UriBuilder.of("/autores/{id}").expand(mutableMapOf("id" to autor.id)) }
            .let { uri -> return HttpResponse.created(uri) }

//        val endereco = enderecoClient.consulta(request.cep)
//
//        endereco.body()
//            .let { enderecoReponse -> request.paraAutor(enderecoReponse!!) }
//            .run { autorRepository.save(this) }
//            .let { autor -> UriBuilder.of("/autores/{id}").expand(mutableMapOf("id" to autor.id)) }
//            .let { uri -> return HttpResponse.created(uri) }

    }

}