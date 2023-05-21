package br.dev.yurinogueira.trabalho03.domain.dao.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Person;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;
import jakarta.persistence.*;
import org.hibernate.JDBCException;

import java.util.List;

public class PersonDaoImpl implements PersonDao {

    @Override
    public Long create(Person person) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            entityManager.persist(person);

            return person.getId();
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public Person read(Long id) throws EntityException {
        EntityManager entityManager = EntityFactory.getEntityManager();
        Person person = entityManager.find(Person.class, id);

        if (person == null) {
            throw new EntityException("Pessoa não encontrada");
        }

        return person;
    }

    @Override
    public void update(Person person) throws EntityException, ObsoleteEntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Person databasePerson = entityManager.find(Person.class, person.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePerson == null) {
                throw new EntityException("Pessoa não encontrada");
            }

            entityManager.merge(person);
        }
        catch (OptimisticLockException exception) {
            throw new ObsoleteEntityException(exception.getMessage());
        }
        catch (JDBCException exception) {
            throw new EntityException("O CPF precisa ser válido e não pode ser repetido!");
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public void delete(Person person) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Person databasePerson = entityManager.find(Person.class, person.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePerson == null) {
                throw new EntityException("Pessoa não encontrada");
            }

            entityManager.remove(databasePerson);
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public List<Person> list(String cpf) {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT p FROM Person p WHERE p.cpf = :cpf ORDER BY p.id";
        TypedQuery<Person> query = entityManager.createQuery(queryStr, Person.class);
        query.setParameter("cpf", cpf);
        return query.getResultList();
    }

    @Override
    public List<Person> list() {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT p FROM Person p ORDER BY p.id";
        TypedQuery<Person> query = entityManager.createQuery(queryStr, Person.class);
        return query.getResultList();
    }

}
