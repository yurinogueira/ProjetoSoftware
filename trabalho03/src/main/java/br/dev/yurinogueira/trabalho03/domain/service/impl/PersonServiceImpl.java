package br.dev.yurinogueira.trabalho03.domain.service.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Person;
import br.dev.yurinogueira.trabalho03.domain.service.PersonService;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.factory.DaoFactory;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private static PersonService personService;
    private final PersonDao personDao;

    private PersonServiceImpl() {
        this.personDao = DaoFactory.getDao(PersonDao.class);
    }

    public static PersonService getInstance() {
        if (personService == null) {
            personService = new PersonServiceImpl();
        }

        return personService;
    }

    @Override
    public List<Person> list() {
        return personDao.list();
    }

    @Override
    public Person create(Person person) throws EntityException {
        EntityFactory.beginTransaction();

        Long id = personDao.create(person);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        person.setId(id);

        return person;
    }

    @Override
    public Person read(Long id) throws EntityException {
        return personDao.read(id);
    }

    @Override
    public Person update(Person person) throws EntityException {
        EntityFactory.beginTransaction();

        try {
            List<Person> personWithCpf = personDao.list(person.getCpf());
            for (Person p : personWithCpf) {
                if (!p.getId().equals(person.getId())) {
                    throw new EntityException("Já existe uma pessoa cadastrada com esse CPF!");
                }
            }

            personDao.update(person);
        }
        catch (EntityException exception) {
            EntityFactory.rollbackTransaction();
            throw new EntityException(exception.getMessage());
        }
        catch (ObsoleteEntityException exception) {
            EntityFactory.rollbackTransaction();
            throw new EntityException(
                    "Operação não efetuada, os dados que você tentou salvar foram modificados por outro usuário!"
            );
        }

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();

        return person;
    }

    @Override
    public void delete(Person person) throws EntityException {
        EntityFactory.beginTransaction();

        personDao.delete(person);

        EntityFactory.commitTransaction();
        EntityFactory.closeEntityManager();
    }
}
