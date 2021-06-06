package br.com.alura.springdatajpa;

import br.com.alura.springdatajpa.service.CrudCargoService;
import br.com.alura.springdatajpa.service.CrudFuncionarioService;
import br.com.alura.springdatajpa.service.CrudUnidadeService;
import br.com.alura.springdatajpa.service.RelatoriosService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class CursoSpringDataJpaApplication implements CommandLineRunner {

	private final CrudCargoService cargoService;
	private final CrudFuncionarioService funcionarioService;
	private final CrudUnidadeService unidadeService;
	private final RelatoriosService relatoriosService;

	public CursoSpringDataJpaApplication(CrudCargoService cargoService, CrudFuncionarioService funcionarioService, CrudUnidadeService unidadeService, RelatoriosService relatoriosService) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeService = unidadeService;
		this.relatoriosService = relatoriosService;
	}


	public static void main(String[] args) {
		SpringApplication.run(CursoSpringDataJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner scanner = new Scanner(System.in);

		System.out.println("0 - Sair");
		System.out.println("1 - Cargo");
		System.out.println("2 - Funcionário");
		System.out.println("3 - Unidade de trabalho");
		System.out.println("4 - Relatórios");

		int action = scanner.nextInt();

		switch (action) {
			case 1:
				cargoService.inicial(scanner);
				break;
			case 2:
				funcionarioService.inicial(scanner);
				break;
			case 3:
				unidadeService.inicial(scanner);
				break;
			case 4:
				relatoriosService.inicial(scanner);
				break;
		}
	}
}
