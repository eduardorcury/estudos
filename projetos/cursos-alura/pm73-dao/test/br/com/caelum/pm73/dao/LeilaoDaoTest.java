package br.com.caelum.pm73.dao;

import br.com.caelum.pm73.dominio.Lance;
import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LeilaoDaoTest {

    private Session session;
    private UsuarioDao usuarioDao;
    private LeilaoDao leilaoDao;

    @Before
    public void setUp() {
        session = new CriadorDeSessao().getSession();
        usuarioDao = new UsuarioDao(session);
        leilaoDao = new LeilaoDao(session);

        session.beginTransaction();

    }

    @After
    public void depois() {
        session.getTransaction().rollback();
        session.close();
    }

    @Test
    public void deveContarLeiloesNaoEncerrados() {

        Leilao leilao1 = new LeilaoBuilder().constroi();
        Leilao leilao2 = new LeilaoBuilder().encerrado().constroi();

        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        long total = leilaoDao.total();
        assertEquals(1L, total);

    }

    @Test
    public void deveRetornarZeroParaNenhumLeilaoNaoEncerrado() {

        Leilao leilao1 = new LeilaoBuilder().encerrado().constroi();
        Leilao leilao2 = new LeilaoBuilder().encerrado().constroi();

        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        long total = leilaoDao.total();
        assertEquals(0L, total);

    }

    @Test
    public void deveRetornarUmLeilaoNaoUsado() {

        Leilao usado = new LeilaoBuilder().usado().constroi();
        Leilao naoUsado = new LeilaoBuilder().constroi();

        leilaoDao.salvar(usado);
        leilaoDao.salvar(naoUsado);

        List<Leilao> novos = leilaoDao.novos();

        assertEquals(1, novos.size());
        assertEquals(naoUsado, novos.get(0));

    }

    @Test
    public void deveRetornarLeiloesAnteriores() {

        Leilao antigo1 = new LeilaoBuilder().diasAtras(10).constroi();
        Leilao antigo2 = new LeilaoBuilder().diasAtras(10).constroi();
        Leilao novo1 = new LeilaoBuilder().constroi();;
        Leilao novo2 = new LeilaoBuilder().constroi();;

        leilaoDao.salvar(novo1);
        leilaoDao.salvar(novo2);
        leilaoDao.salvar(antigo1);
        leilaoDao.salvar(antigo2);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(2, antigos.size());

    }

    @Test
    public void deveRetornarLeiloesAnterioresLimite() {

        Leilao antigo = new LeilaoBuilder().diasAtras(7).constroi();;

        leilaoDao.salvar(antigo);

        List<Leilao> antigos = leilaoDao.antigos();

        assertEquals(1, antigos.size());
        assertEquals(antigo, antigos.get(0));

    }

    @Test
    public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {

        Calendar comecoIntervalo = Calendar.getInstance();
        Calendar fimIntervalo = Calendar.getInstance();
        comecoIntervalo.add(Calendar.DAY_OF_MONTH, -10);

        Leilao leilao1 = new LeilaoBuilder().diasAtras(2).constroi();

        Leilao leilao2 = new LeilaoBuilder().diasAtras(20).constroi();

        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoIntervalo, fimIntervalo);

        assertEquals(1, leiloes.size());
        assertEquals(leilao1, leiloes.get(0));

    }

    @Test
    public void naoDeveTrazerLeilaoEncerradoNoPeriodo() {

        Calendar comecoIntervalo = Calendar.getInstance();
        Calendar fimIntervalo = Calendar.getInstance();
        comecoIntervalo.add(Calendar.DAY_OF_MONTH, -10);

        Leilao leilao1 = new LeilaoBuilder().encerrado().constroi();

        leilaoDao.salvar(leilao1);

        List<Leilao> leiloes = leilaoDao.porPeriodo(comecoIntervalo, fimIntervalo);

        assertEquals(0, leiloes.size());

    }

    @Test
    public void deveRetornarOsLeiloesQueTiveramLancesDoUsuario() {

        Usuario eduardo = new Usuario("eduardo", "email");
        usuarioDao.salvar(eduardo);

        Leilao leilaoSemLance = new LeilaoBuilder().constroi();
        Leilao leilaoComLance = new LeilaoBuilder().constroi();
        leilaoComLance.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilaoComLance));

        leilaoDao.salvar(leilaoSemLance);
        leilaoDao.salvar(leilaoComLance);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(eduardo);
        assertEquals(1, leiloes.size());
        assertEquals(leilaoComLance, leiloes.get(0));

    }

    @Test
    public void naoDeveRetornarNenhumLance() {

        Usuario eduardo = new Usuario("eduardo", "email");
        usuarioDao.salvar(eduardo);

        Leilao leilaoSemLance = new LeilaoBuilder().constroi();

        leilaoDao.salvar(leilaoSemLance);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(eduardo);
        assertEquals(0, leiloes.size());

    }

    @Test
    public void deveRetornarUmLeilao() {

        Usuario eduardo = new Usuario("eduardo", "email");
        Usuario maria = new Usuario("maria", "email");
        usuarioDao.salvar(eduardo);
        usuarioDao.salvar(maria);

        Leilao leilaoComLance = new LeilaoBuilder().constroi();
        leilaoComLance.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilaoComLance));
        leilaoComLance.getLances().add(new Lance(Calendar.getInstance(), maria, 300.0, leilaoComLance));
        leilaoComLance.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilaoComLance));

        leilaoDao.salvar(leilaoComLance);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(eduardo);
        assertEquals(1, leiloes.size());
        assertEquals(leilaoComLance, leiloes.get(0));

    }

    @Test
    public void deveRetornarMediaDoValorInicialDoUsuario() {

        Usuario eduardo = new Usuario("eduardo", "email");
        usuarioDao.salvar(eduardo);

        Leilao leilao1 = new LeilaoBuilder().constroi();
        Leilao leilao2 = new LeilaoBuilder().constroi();
        leilao1.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilao1));
        leilao2.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilao2));

        leilao1.setValorInicial(300.0);
        leilao2.setValorInicial(200.0);

        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        double valorInicialMedioDoUsuario = leilaoDao.getValorInicialMedioDoUsuario(eduardo);
        assertEquals(250.0, valorInicialMedioDoUsuario, 0.00001);

    }

    @Test(expected = NullPointerException.class)
    public void deveRetornarNuloParUsuarioSemParticipacao() {

        Usuario eduardo = new Usuario("eduardo", "email");
        usuarioDao.salvar(eduardo);

        leilaoDao.getValorInicialMedioDoUsuario(eduardo);

    }

    @Test
    public void deveRetornarMediaDoValorInicialDoUsuarioParaUmLeilao() {

        Usuario eduardo = new Usuario("eduardo", "email");
        usuarioDao.salvar(eduardo);

        Leilao leilao1 = new LeilaoBuilder().constroi();
        Leilao leilao2 = new LeilaoBuilder().constroi();
        leilao1.getLances().add(new Lance(Calendar.getInstance(), eduardo, 300.0, leilao1));

        leilao1.setValorInicial(300.0);
        leilao2.setValorInicial(200.0);

        leilaoDao.salvar(leilao1);
        leilaoDao.salvar(leilao2);

        double valorInicialMedioDoUsuario = leilaoDao.getValorInicialMedioDoUsuario(eduardo);
        assertEquals(300.0, valorInicialMedioDoUsuario, 0.00001);

    }

    @Test
    public void devolveAMediaDoValorInicialDosLeiloesQueOUsuarioParticipou(){
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
                .comDono(dono)
                .comValor(50.0)
                .constroi();
        Leilao leilao2 = new LeilaoBuilder()
                .comDono(dono)
                .comValor(250.0)
                .constroi();
        leilao.getLances().add(new Lance(Calendar.getInstance(), comprador, 50.0, leilao));
        leilao2.getLances().add(new Lance(Calendar.getInstance(), comprador, 300.0, leilao2));


        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
    }

    @Test
    public void deveDeletarLeilao() {

        Leilao leilao = new LeilaoBuilder().constroi();
        leilaoDao.salvar(leilao);
        int id = leilao.getId();
        leilaoDao.deleta(leilao);

        session.flush();
        session.clear();

        assertNull(leilaoDao.porId(id));

    }
}