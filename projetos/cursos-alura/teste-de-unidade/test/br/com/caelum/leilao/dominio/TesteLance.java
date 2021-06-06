package br.com.caelum.leilao.dominio;

import org.junit.Test;

public class TesteLance {
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoSeValorForZero() {
		
		Lance lance = new Lance(new Usuario("eduardo"), 0.0);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoSeValorForNegativo() {
		
		Lance lance = new Lance(new Usuario("eduardo"), -100.0);
		
	}
	

}
