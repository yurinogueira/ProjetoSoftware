package servico.impl;

import java.util.List;

import anotacao.Autowired;
import anotacao.Transactional;
import dao.PersonDao;
import excecao.PersonNaoEncontradoException;
import modelo.Person;
import servico.PersonAppService;

public class PersonAppServiceImpl implements PersonAppService {

	@Autowired
	protected PersonDao personDao;

	@Transactional
	public long inclui(Person umPerson) {
		return personDao.inclui(umPerson).getId();
	}

	@Transactional(rollbackFor = { PersonNaoEncontradoException.class })
	public void altera(Person umPerson) throws PersonNaoEncontradoException {
		try {
			personDao.altera(umPerson);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Produto não encontrado");
		}
	}

	@Transactional
	public void exclui(Person umPerson) throws PersonNaoEncontradoException {
		try {
			personDao.exclui(umPerson);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Produto não encontrado");
		}
	}

	public Person recuperaUmPerson(Long id) throws PersonNaoEncontradoException {
		try {
			return personDao.getPorId(id);
		}
		catch (Exception e) {
			throw new PersonNaoEncontradoException("Produto não encontrado");
		}
	}

	public List<Person> recuperaPersons() {
		return personDao.recuperaListaDePersons();
	}

}