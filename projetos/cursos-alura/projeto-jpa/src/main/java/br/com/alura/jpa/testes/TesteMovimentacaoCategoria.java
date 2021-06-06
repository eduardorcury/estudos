package br.com.alura.jpa.testes;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;
import br.com.alura.jpa.modelo.TipoMovimentacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class TesteMovimentacaoCategoria {

    public static void main(String[] args) {

        Categoria categoria1 = new Categoria("Viagem");
        Categoria categoria2 = new Categoria("Negócios");

        Conta conta = new Conta();
        conta.setId(2L);

        Movimentacao movimentacao1 = new Movimentacao();
        movimentacao1.setData(LocalDateTime.now());
        movimentacao1.setValor(new BigDecimal(300));
        movimentacao1.setDescricao("Viagem à SP");
        movimentacao1.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacao1.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao1.setConta(conta);

        Movimentacao movimentacao2 = new Movimentacao();
        movimentacao2.setData(LocalDateTime.now());
        movimentacao2.setValor(new BigDecimal(400));
        movimentacao2.setDescricao("Viagem à RJ");
        movimentacao2.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        movimentacao2.setCategorias(Arrays.asList(categoria1, categoria2));
        movimentacao2.setConta(conta);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(categoria1);
        entityManager.persist(categoria2);
        entityManager.persist(movimentacao1);
        entityManager.persist(movimentacao2);
        entityManager.getTransaction().commit();

        entityManager.close();

    }

}
