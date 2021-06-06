package br.com.caelum.pm73.dao;

import br.com.caelum.pm73.dominio.Usuario;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UsuarioDaoTest {

    private Session session;
    private UsuarioDao usuarioDao;

    @Before
    public void setUp() {
        session = new CriadorDeSessao().getSession();
        usuarioDao = new UsuarioDao(session);
        session.beginTransaction();
    }

    @After
    public void depois() {
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveEncontrarPeloNomeEmailMockado() {

        Usuario newUsuario = new Usuario("João", "email");
        usuarioDao.salvar(newUsuario);

        Usuario usuario = usuarioDao.porNomeEEmail("João", "email");
        assertEquals("João", usuario.getNome());
        assertEquals("email", usuario.getEmail());

    }

    @Test
    public void deveRetornarNuloSeNaoExistirUsuario() {

        Usuario usuario = usuarioDao.porNomeEEmail("João", "email");
        assertNull(usuario);

    }

    @Test
    public void deveDeletarUmUsuario() {

        Usuario usuario = new Usuario("João", "email");

        usuarioDao.salvar(usuario);
        usuarioDao.deletar(usuario);

        session.flush();
        session.clear();

        Usuario usuarioBanco = usuarioDao.porNomeEEmail("Jõao", "email");

        assertNull(usuarioBanco);

    }

    @Test
    public void deveAlterarUmUsuario() {

        Usuario usuario = new Usuario("João", "email");
        usuarioDao.salvar(usuario);

        usuario.setNome("José");
        usuarioDao.atualizar(usuario);

        session.flush();
        session.clear();

        Usuario usuarioAntigo = usuarioDao.porNomeEEmail("João", "email");
        Usuario usuarioNovo = usuarioDao.porNomeEEmail("José", "email");
        assertNull(usuarioAntigo);
        assertEquals("José", usuarioNovo.getNome());

    }

}