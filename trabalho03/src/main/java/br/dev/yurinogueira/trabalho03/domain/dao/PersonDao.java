package br.dev.yurinogueira.trabalho03.domain.dao;

import br.dev.yurinogueira.trabalho03.domain.entity.Person;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface PersonDao {

    Long create(Person person) throws EntityException;

    Person read(Long id) throws EntityException;

    void update(Person person) throws EntityException, ObsoleteEntityException;

    void delete(Person person) throws EntityException;

    List<Person> list(String cpf);

    List<Person> list();

}
