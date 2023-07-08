package dao.impl;

import dao.PersonDao;
import modelo.Person;

import org.springframework.stereotype.Repository;

@Repository
public abstract class PersonDaoImpl extends JPADaoGenerico<Person, Long> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }

}
