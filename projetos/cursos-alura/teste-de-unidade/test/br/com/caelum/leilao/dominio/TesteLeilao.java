package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TesteLeilao {

	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Macbook Pro");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		
	}
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new Leilao("Macbook Pro");
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		leilao.propoe(new Lance(new Usuario("Eduardo"), 2500));
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(2500.0, leilao.getLances().get(1).getValor(), 0.00001);
		
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Macbook Pro");
		Usuario usuario = new Usuario("Steve Jobs");
		
		leilao.propoe(new Lance(usuario, 2000));
		leilao.propoe(new Lance(usuario, 3000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		
	}
	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDoMesmoUsuario() {
		
		Leilao leilao = new Leilao("Macbook Pro");
		Usuario usuario1 = new Usuario("Steve Jobs");
		Usuario usuario2 = new Usuario("Bill Dipperly");
		
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 3000));
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 3000));
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 3000));
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 3000));
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 10000));
		
		leilao.propoe(new Lance(usuario1, 11000));
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(10000.0, leilao.getLances().get((leilao.getLances().size() - 1)).getValor(), 0.00001);
		
	}
	
	@Test
	public void deveDobrarLance() {
		
		Leilao leilao = new Leilao("Macbook Pro");
		Usuario usuario1 = new Usuario("Steve Jobs");
		Usuario usuario2 = new Usuario("Bill Dipperly");
		
		leilao.propoe(new Lance(usuario1, 2000));
		leilao.propoe(new Lance(usuario2, 1000));
		leilao.dobraLance(usuario1);
		
		assertEquals(3, leilao.getLances().size());
		assertEquals(4000.0, leilao.getLances().get((leilao.getLances().size() - 1)).getValor(), 0.00001);
		
	}
	

}
