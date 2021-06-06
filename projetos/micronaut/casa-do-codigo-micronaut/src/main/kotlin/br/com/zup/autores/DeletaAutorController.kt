package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import javax.transaction.Transactional

@Controller("/autores/{id}")
class DeletaAutorController(val autorRepository: AutorRepository) {

    @Delete
    @Transactional
    fun deleta(@PathVariable id: Long) : HttpResponse<Unit> {

        return if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id)
            HttpResponse.noContent()
        } else {
            HttpResponse.notFound()
        }
    }
}