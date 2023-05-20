package br.dev.yurinogueira.trabalho02.domain.dao.impl;

import br.dev.yurinogueira.trabalho02.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho02.domain.entity.Person;
import br.dev.yurinogueira.trabalho02.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho02.exception.PersonException;
import br.dev.yurinogueira.trabalho02.factory.EntityFactory;
import jakarta.persistence.*;

import java.util.List;

public class PersonDaoImpl implements PersonDao {

    @Override
    public Long create(Person person) throws PersonException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            entityManager.persist(person);

            return person.getId();
        }
        catch (RuntimeException exception) {
            throw new PersonException(exception.getMessage());
        }
    }

    @Override
    public Person read(Long id) throws PersonException {
        EntityManager entityManager = EntityFactory.getEntityManager();
        Person person = entityManager.find(Person.class, id);

        if (person == null) {
            throw new PersonException("Pessoa não encontrada");
        }

        return person;
    }

    @Override
    public void update(Person person) throws PersonException, ObsoleteEntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Person databasePerson = entityManager.find(Person.class, person.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePerson == null) {
                throw new PersonException("Pessoa não encontrada");
            }

            entityManager.merge(person);
        }
        catch (OptimisticLockException exception) {
            throw new ObsoleteEntityException(exception.getMessage());
        }
        catch (RuntimeException exception) {
            throw new PersonException(exception.getMessage());
        }
    }

    @Override
    public void delete(Person person) throws PersonException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Person databasePerson = entityManager.find(Person.class, person.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePerson == null) {
                throw new PersonException("Pessoa não encontrada");
            }

            entityManager.remove(databasePerson);
        }
        catch (RuntimeException exception) {
            throw new PersonException(exception.getMessage());
        }
    }

    @Override
    public List<Person> list() {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT p FROM Person p ORDER BY p.id";
        TypedQuery<Person> query = entityManager.createQuery(queryStr, Person.class);
        return query.getResultList();
    }

}
