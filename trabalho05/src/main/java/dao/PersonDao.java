package dao;

import anotacao.RecuperaLista;
import modelo.Person;

import java.util.List;

public interface PersonDao extends DaoGenerico<Person, Long> {

    @RecuperaLista
    List<Person> recuperaListaDePersons();

}
