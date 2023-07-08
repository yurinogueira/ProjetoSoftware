package dao.impl;

import dao.PersonDao;
import modelo.Person;

public abstract class PersonDaoImpl extends JPADaoGeneric<Person, Long> implements PersonDao {

    public PersonDaoImpl() {
        super(Person.class);
    }

}
