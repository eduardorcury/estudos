package br.com.alura.springdatajpa.repository;

import br.com.alura.springdatajpa.orm.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
}
