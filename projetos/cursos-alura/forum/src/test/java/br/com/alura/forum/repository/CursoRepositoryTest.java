package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Curso;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@DataJpaTest
class CursoRepositoryTest {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Curso correto")
    void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {

        String nomeCurso = "HTML 5";

        Curso html5 = new Curso();
        html5.setNome("HTML 5");
        html5.setCategoria("Programação");
        testEntityManager.persist(html5);

        Curso curso = repository.findByNome(nomeCurso);
        Assertions.assertNotNull(curso);
        Assertions.assertEquals(nomeCurso, curso.getNome());

    }

    @Test
    @DisplayName("Curso incorreto")
    void naoDeveriaCarregarCursoQueNaoExiste() {

        String nomeCurso = "JPA";
        Curso curso = repository.findByNome(nomeCurso);
        Assertions.assertNull(curso);

    }
}