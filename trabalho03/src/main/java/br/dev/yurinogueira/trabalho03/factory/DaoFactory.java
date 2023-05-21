package br.dev.yurinogueira.trabalho03.factory;

import br.dev.yurinogueira.trabalho03.domain.dao.PersonDao;
import br.dev.yurinogueira.trabalho03.domain.dao.RoleDao;
import br.dev.yurinogueira.trabalho03.domain.dao.PeriodDao;
import br.dev.yurinogueira.trabalho03.domain.dao.impl.PersonDaoImpl;
import br.dev.yurinogueira.trabalho03.domain.dao.impl.RoleDaoImpl;
import br.dev.yurinogueira.trabalho03.domain.dao.impl.PeriodDaoImpl;

public class DaoFactory {

    private static final PersonDao PERSON_DAO = new PersonDaoImpl();
    private static final RoleDao ROLE_DAO = new RoleDaoImpl();
    private static final PeriodDao PERIOD_DAO = new PeriodDaoImpl();

    public static <T> T getDao(Class<T> clazz) {
        if (clazz.equals(PersonDao.class)) {
            return clazz.cast(PERSON_DAO);
        }

        if (clazz.equals(RoleDao.class)) {
            return clazz.cast(ROLE_DAO);
        }

        if (clazz.equals(PeriodDao.class)) {
            return clazz.cast(PERIOD_DAO);
        }

        return null;
    }

}
