package br.com.alura.jpa.testes;

import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;
import br.com.alura.jpa.modelo.TipoMovimentacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestRelacionamento {

    public static void main(String[] args) {

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setData(LocalDateTime.now());
        movimentacao.setDescricao("IFood");
        movimentacao.setValor(new BigDecimal(29));
        movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);

        Conta conta = new Conta();
        conta.setSaldo(1000.0);
        conta.setAgencia(1234);
        conta.setTitular("Eduardo");
        conta.setNumero(998);

        movimentacao.setConta(conta);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(conta);
        entityManager.persist(movimentacao);
        entityManager.getTransaction().commit();

        entityManager.close();

    }


}
