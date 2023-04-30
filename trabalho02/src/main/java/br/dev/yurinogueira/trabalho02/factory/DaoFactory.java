package br.dev.yurinogueira.trabalho02.factory;

import br.dev.yurinogueira.trabalho02.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho02.domain.dao.impl.PersonDaoImpl;

public class DaoFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getDao(Class<T> clazz) {
        if (clazz.equals(PersonDao.class)) {
            return (T) new PersonDaoImpl();
        }

        return null;
    }

}
