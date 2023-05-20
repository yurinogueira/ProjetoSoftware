package br.dev.yurinogueira.trabalho02.domain.service.impl;

import br.dev.yurinogueira.trabalho02.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho02.domain.entity.Person;
import br.dev.yurinogueira.trabalho02.domain.service.PersonService;
import br.dev.yurinogueira.trabalho02.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho02.exception.PersonException;
import br.dev.yurinogueira.trabalho02.factory.DaoFactory;
import br.dev.yurinogueira.trabalho02.factory.EntityFactory;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;

    public PersonServiceImpl() {
        this.personDao = DaoFactory.getDao(PersonDao.class);
    }

    @Override
    public List<Person> list() {
        return personDao.list();
    }

    @Override
    public Person create(Person person) throws PersonException {
        EntityFactory.beginTransaction();

        Long id = personDao.create(person);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        return personDao.read(id);
    }

    @Override
    public Person read(Long id) throws PersonException {
        return personDao.read(id);
    }

    @Override
    public Person update(Person person) throws PersonException {
        EntityFactory.beginTransaction();

        try {
            personDao.update(person);
        }
        catch (PersonException exception) {
            EntityFactory.rollbackTransaction();
            throw new PersonException(exception.getMessage());
        }
        catch (ObsoleteEntityException exception) {
            EntityFactory.rollbackTransaction();
            throw new PersonException(
                    "Operação não efetuada, os dados que você tentou salvar foram modificados por outro usuário!"
            );
        }

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        return personDao.read(person.getId());
    }

    @Override
    public void delete(Person person) throws PersonException {
        EntityFactory.beginTransaction();

        personDao.delete(person);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();
    }
}
