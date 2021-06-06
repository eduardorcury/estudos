package br.com.alura.jpa.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.jpa.modelo.Conta;

public class AlteraSaldo {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager entityManager = emf.createEntityManager();

		Conta conta = entityManager.find(Conta.class, 1L);
		
		entityManager.getTransaction().begin();
		conta.setSaldo(20.0);
		entityManager.getTransaction().commit();

		entityManager.merge(conta);

	}
	
}
