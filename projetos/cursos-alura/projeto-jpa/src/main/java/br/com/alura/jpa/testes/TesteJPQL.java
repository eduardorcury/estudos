package br.com.alura.jpa.testes;

import br.com.alura.jpa.modelo.Categoria;
import br.com.alura.jpa.modelo.Conta;
import br.com.alura.jpa.modelo.Movimentacao;

import javax.persistence.*;
import java.util.List;

public class TesteJPQL {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
        EntityManager entityManager = emf.createEntityManager();

        Conta conta = entityManager.find(Conta.class, 2L);
        Categoria categoria = entityManager.find(Categoria.class, 2L);

        String jpql = "select * from Movimentacao m where m.conta = :pConta order by m.valor desc";

        String jpql2 = "select * from Movimentacao m join m.categorias c where c = :pCategoria";

        TypedQuery<Movimentacao> query = entityManager.createQuery(jpql, Movimentacao.class);
        query.setParameter("pConta", conta);
        TypedQuery<Categoria> query2 = entityManager.createQuery(jpql2, Categoria.class);
        query2.setParameter("pCategoria", categoria);
        List<Movimentacao> resultList = query.getResultList();

        for (Movimentacao movimentacao : resultList) {
            System.out.println("Descrição: " + movimentacao.getDescricao());
            System.out.println("Tipo: " + movimentacao.getTipoMovimentacao());
        }

    }

}
