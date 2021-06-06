package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Avaliador;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import junit.framework.Assert;

public class TesteDoAvaliador {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	
	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
		
		joao = new Usuario("João");
        jose = new Usuario("José");
        maria = new Usuario("Maria");
	}

	@Test
    public void deveEntenderLancesEmOrdemCrescente() {
        
        Leilao leilao = new CriadorDeLeilao().para("PS5")
        		.lance(joao,  250.0)
        		.lance(jose, 300.0)
        		.lance(maria, 400.0)
        		.constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }
	
	@Test
    public void deveEntenderUmLance() {
        
        Leilao leilao = new CriadorDeLeilao().para("PS4")
        		.lance(joao,  1000.0)
        		.constroi();

        leiloeiro.avalia(leilao);

        double maiorEsperado = 1000;
        double menorEsperado = 1000;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
    }
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {

        Leilao leilao = new CriadorDeLeilao().para("XBOX")
        		.lance(joao, 300.0)
        		.lance(jose, 350.0)
        		.lance(maria, 400.0)
        		.constroi();
        
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getMaiores();
        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
    }
	
	@Test
	public void deveEncontrarOsDoisMaioresLances() {
        
        Leilao leilao = new CriadorDeLeilao().para("XBOX")
        		.lance(maria, 300.0)
        		.lance(jose, 400.0)
        		.constroi();

        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getMaiores();
        assertEquals(2, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.0001);
        assertEquals(leilao.getDescricao(), leilao.getDescricao());
    }
	
	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoParaLeilaoSemLance() {
		
		Leilao leilao = new CriadorDeLeilao().para("XBOX").constroi();
		
		leiloeiro.avalia(leilao);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoAvaliacaoNula() {
		
		RespostaQuestao resposta = new RespostaQuestao(null, aluno, nota);
		assertEquals(resposta.getAluno().getNome(), aluno.getNome());
		assertEquals(resposta.getAvaliacao().getNome(), avaliacao.getNome());
		assertEquals(resposta.getNota, 5);
		
	}
	

}
