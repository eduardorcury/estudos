package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import javax.transaction.Transactional

@Validated
@Controller("/autores")
class BuscaAutoresController(
    val autorRepository: AutorRepository
) {

    @Get
    @Transactional
    fun lista(@QueryValue(defaultValue = "") email: String): HttpResponse<Any> {

        email.ifBlank {
            return autorRepository.findAll()
                .map { autor -> DetalhesAutorResponse(autor) }
                .let { HttpResponse.ok(it) }
        }

        val possivelAutor = autorRepository.findByEmail(email)

        return when {
            possivelAutor.isEmpty -> HttpResponse.notFound()
            else -> HttpResponse.ok(DetalhesAutorResponse(possivelAutor.get()))
        }
    }

}