package servico.impl;

import java.util.List;

import dao.PersonDao;
import excecao.ObjetoNaoEncontradoException;
import excecao.PersonNaoEncontradoException;
import modelo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import servico.PersonAppService;

public class PersonAppServiceImpl implements PersonAppService {

	private final PersonDao personDao;

	@Autowired
	public PersonAppServiceImpl(PersonDao personDao) {
		this.personDao = personDao;
	}

	@Override
	@Transactional
	public long inclui(Person umPerson) {
		return personDao.inclui(umPerson).getId();
	}

	@Override
	@Transactional
	public void altera(Person umPerson) throws PersonNaoEncontradoException {
		try {
			personDao.altera(umPerson);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Pessoa não encontrada");
		}
	}

	@Override
	@Transactional
	public void exclui(Person umPerson) throws PersonNaoEncontradoException {
		try {
			personDao.exclui(umPerson);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Pessoa não encontrada");
		}
	}

	@Override
	public Person recuperaUmPerson(Long id) throws PersonNaoEncontradoException {
		try {
			return personDao.getPorId(id);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Pessoa não encontrada");
		}
	}

	@Override
	public Person recuperaUmPersonERoles(Long id) throws PersonNaoEncontradoException {
		try {
			return personDao.recuperaUmaPessoaERoles(id);
		} catch (ObjetoNaoEncontradoException e) {
			throw new PersonNaoEncontradoException("Pessoa não encontrada");
		}
	}

	@Override
	public List<Person> recuperaPersons() {
		return personDao.recuperaListaDePersons();
	}

}