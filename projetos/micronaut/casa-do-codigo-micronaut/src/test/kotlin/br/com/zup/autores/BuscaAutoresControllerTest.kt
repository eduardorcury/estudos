package br.com.zup.autores

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class BuscaAutoresControllerTest {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @field:Inject
    lateinit var autorRepository: AutorRepository

    lateinit var autor: Autor

    @BeforeEach
    internal fun setUp() {

        autor = EnderecoResponse("rua x", "RJ", "RJ")
            .let { enderecoResponse ->  Endereco(enderecoResponse, "111") }
            .let { endereco -> Autor("Eduardo", "email@gmail.com", "dev junior", endereco) }

        autorRepository.save(autor)

    }

    @AfterEach
    internal fun tearDown() {
        autorRepository.deleteAll()
    }

    @Test
    internal fun `deve retornar os detalhes de um ator`() {

        val response = client.toBlocking()
            .exchange("/autores?email=${autor.email}", DetalhesAutorResponse::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(autor.id, response.body()!!.id)
        assertEquals(autor.nome, response.body()!!.nome)
        assertEquals(autor.descricao, response.body()!!.descricao)

    }
}