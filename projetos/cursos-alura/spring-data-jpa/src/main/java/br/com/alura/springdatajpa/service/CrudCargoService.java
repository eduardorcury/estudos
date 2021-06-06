package br.com.alura.springdatajpa.service;

import br.com.alura.springdatajpa.orm.Cargo;
import br.com.alura.springdatajpa.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

@Service
public class CrudCargoService {

    private Boolean system = true;

    private final CargoRepository cargoRepository;
    private final EntityManagerFactory entityManagerFactory;

    public CrudCargoService(CargoRepository cargoRepository, EntityManagerFactory entityManagerFactory) {
        this.cargoRepository = cargoRepository;
        this.entityManagerFactory = entityManagerFactory;
    }


    public void inicial(Scanner scanner) {

        while (system) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");

            int action = scanner.nextInt();

            switch (action) {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizarComRepository(scanner);
                    break;
                case 3:
                    visualizar();
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner) {

        System.out.println("Descição do cargo");
        String descricao = scanner.next();

        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);
        System.out.println("Salvo");

    }

    private void atualizar(Scanner scanner) {

        System.out.println("Id");
        int id = scanner.nextInt();

        System.out.println("Nova descrição");
        String descricao = scanner.next();

        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);

        cargoRepository.save(cargo);

    }

    private void visualizar() {
        Iterable<Cargo> cargos = cargoRepository.findAll();
        cargos.forEach(System.out::println);
    }

    private void deletar(Scanner scanner) {
        System.out.println("Id");
        int id = scanner.nextInt();
        cargoRepository.deleteById(id);
        System.out.println("Deletado");
    }

    @Transactional
    private void atualizarComRepository(Scanner scanner) {

        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Nova descrição");
        String descricao = scanner.next();

        Cargo cargo = cargoRepository.findById(id).get();
        cargo.setDescricao(descricao);

    }

    private void atualizarComManager(Scanner scanner) {

        System.out.println("Id");
        int id = scanner.nextInt();
        System.out.println("Nova descrição");
        String descricao = scanner.next();

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        Cargo cargo = entityManager.find(Cargo.class, id);
        cargo.setDescricao(descricao);
        entityManager.getTransaction().commit();

        entityManager.close();

    }

}
