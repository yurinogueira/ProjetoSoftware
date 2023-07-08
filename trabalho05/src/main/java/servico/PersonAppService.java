package servico;

import java.util.List;

import excecao.PersonNaoEncontradoException;
import modelo.Person;

public interface PersonAppService {

	long inclui(Person umPerson);

	void altera(Person umPerson) throws PersonNaoEncontradoException;

	void exclui(Person id) throws PersonNaoEncontradoException;

	Person recuperaUmPerson(Long id) throws PersonNaoEncontradoException;

	Person recuperaUmPersonERoles(Long id) throws PersonNaoEncontradoException;

	List<Person> recuperaPersons();

}