package dao;

import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import excecao.ObjetoNaoEncontradoException;
import modelo.Person;

import java.util.List;

public interface PersonDao extends DaoGenerico<Person, Long> {

    @RecuperaLista
    List<Person> recuperaListaDePersons();

    @RecuperaObjeto
    Person recuperaUmaPessoaERoles(Long id) throws ObjetoNaoEncontradoException;


}
