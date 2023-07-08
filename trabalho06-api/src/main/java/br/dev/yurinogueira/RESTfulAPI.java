package br.dev.yurinogueira;

import br.dev.yurinogueira.model.Funcao;
import br.dev.yurinogueira.model.Pessoa;
import br.dev.yurinogueira.repository.FuncaoRepository;
import br.dev.yurinogueira.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RESTfulAPI implements CommandLineRunner {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private FuncaoRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(RESTfulAPI.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		pessoaRepository.deleteAll();
		categoriaRepository.deleteAll();

		Funcao engenheiro = new Funcao("Engenheiro", "Constrói coisas", 40);
		categoriaRepository.save(engenheiro);
		Funcao pai = new Funcao("Pai", "Cuida dos filhos", 40);
		categoriaRepository.save(pai);
		Funcao uber = new Funcao("Uber", "Transporta pessoas", 40);
		categoriaRepository.save(uber);

		Funcao medico = new Funcao("Médico", "Cuida da saúde", 40);
		categoriaRepository.save(medico);
		Funcao mae = new Funcao("Mãe", "Cuida dos filhos", 80);
		categoriaRepository.save(mae);

		Pessoa pessoa1 = new Pessoa("Jorge", "12312312343");
		pessoa1.addFuncao(engenheiro);
		pessoa1.addFuncao(pai);
		pessoa1.addFuncao(uber);
		pessoaRepository.save(pessoa1);

		Pessoa pessoa2 = new Pessoa("Maria", "13212312343");
		pessoa2.addFuncao(medico);
		pessoa2.addFuncao(mae);
		pessoaRepository.save(pessoa2);
	}
}
