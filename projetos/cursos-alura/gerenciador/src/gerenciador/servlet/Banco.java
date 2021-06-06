package gerenciador.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Banco {
	
	private static List<Empresa> lista = new ArrayList<>();
	private static Integer chaveSequencial = 1;
	
	static {
		Empresa empresa1 = new Empresa();
		Empresa empresa2 = new Empresa();
		empresa1.setNome("Alura");
		empresa2.setNome("Caelum");
		empresa1.setId(chaveSequencial++);
		empresa2.setId(chaveSequencial++);
		lista.add(empresa1);
		lista.add(empresa2);
	}
	
	public void adicionar(Empresa empresa) {
		empresa.setId(chaveSequencial++);
		lista.add(empresa);
	}
	
	public List<Empresa> getEmpresas() {
		return Banco.lista;
	}

	public void removeEmpresa(Integer id) {
		
		Iterator<Empresa> it = lista.iterator();
		
		while(it.hasNext()) {
			
			Empresa empresa = it.next();
			if (empresa.getId() == id) {
				it.remove();
			}
		}
		
	}

	public Empresa buscarEmpresa(Integer id) {
		
		for (Empresa empresa : lista) {
			if (empresa.getId() == id) {
				return empresa;
			}
		}
		
		return null;
		
	}

}
