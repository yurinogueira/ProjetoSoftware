package br.dev.yurinogueira.trabalho02.domain.service;

import br.dev.yurinogueira.trabalho02.domain.entity.Person;
import br.dev.yurinogueira.trabalho02.exception.PersonException;

import java.util.List;

public interface PersonService {

    List<Person> list();

    Person create(Person person) throws PersonException;

    Person read(Long id) throws PersonException;

    Person update(Person person) throws PersonException;

    void delete(Person person) throws PersonException;

}
