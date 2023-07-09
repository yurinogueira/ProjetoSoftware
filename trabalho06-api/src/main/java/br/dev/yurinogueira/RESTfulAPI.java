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
	private FuncaoRepository funcaoRepository;

	public static void main(String[] args) {
		SpringApplication.run(RESTfulAPI.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		funcaoRepository.deleteAll();
		pessoaRepository.deleteAll();

		Pessoa pessoa1 = new Pessoa("Jorge", "12312312343");
		pessoaRepository.save(pessoa1);

		Pessoa pessoa2 = new Pessoa("Maria", "13212312343");
		pessoaRepository.save(pessoa2);

		Funcao engenheiro = new Funcao("Engenheiro", "Constrói coisas", 40);
		engenheiro.setPessoa(pessoa1);
		funcaoRepository.save(engenheiro);
		Funcao pai = new Funcao("Pai", "Cuida dos filhos", 40);
		pai.setPessoa(pessoa1);
		funcaoRepository.save(pai);
		Funcao uber = new Funcao("Uber", "Transporta pessoas", 40);
		uber.setPessoa(pessoa1);
		funcaoRepository.save(uber);

		Funcao medico = new Funcao("Médico", "Cuida da saúde", 40);
		medico.setPessoa(pessoa2);
		funcaoRepository.save(medico);
		Funcao mae = new Funcao("Mãe", "Cuida dos filhos", 80);
		mae.setPessoa(pessoa2);
		funcaoRepository.save(mae);
	}
}
