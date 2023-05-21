package br.dev.yurinogueira.trabalho03.domain.service;

import br.dev.yurinogueira.trabalho03.domain.entity.Person;
import br.dev.yurinogueira.trabalho03.exception.EntityException;

import java.util.List;

public interface PersonService {

    List<Person> list();

    Person create(Person person) throws EntityException;

    Person read(Long id) throws EntityException;

    Person update(Person person) throws EntityException;

    void delete(Person person) throws EntityException;

}
