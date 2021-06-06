package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import io.micronaut.validation.Validated
import javax.transaction.Transactional
import javax.validation.constraints.NotBlank

@Validated
@Controller("/autores/{id}")
class AtualizaAutorController(val autorRepository: AutorRepository) {

    @Put
    @Transactional
    fun atualiza(@PathVariable id: Long, @NotBlank descricao: String ) : HttpResponse<Any> {

        val possivelAutor = autorRepository.findById(id)

        return when {
            possivelAutor.isEmpty -> HttpResponse.notFound()
            else -> {
                possivelAutor.get().let { autor ->
                    autor.descricao = descricao
                    HttpResponse.ok(DetalhesAutorResponse(autor))
                }
            }
        }
    }

}