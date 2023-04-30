package br.dev.yurinogueira.trabalho02.domain.dao;

import br.dev.yurinogueira.trabalho02.domain.entity.Person;
import br.dev.yurinogueira.trabalho02.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho02.exception.PersonException;

import java.util.List;

public interface PersonDao {

    Long create(Person person) throws PersonException;

    Person read(Long id) throws PersonException;

    void update(Person person) throws PersonException, ObsoleteEntityException;

    void delete(Person person) throws PersonException;

    List<Person> list();

}
