package br.dev.yurinogueira.trabalho03.domain.dao.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.PeriodDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Period;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PeriodDaoImpl implements PeriodDao {

    @Override
    public Long create(Period period) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            entityManager.persist(period);

            return period.getId();
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public Period read(Long id) throws EntityException {
        EntityManager entityManager = EntityFactory.getEntityManager();
        Period period = entityManager.find(Period.class, id);

        if (period == null) {
            throw new EntityException("Periodo não encontrado");
        }

        return period;
    }

    @Override
    public void update(Period period) throws EntityException, ObsoleteEntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();

            Period databasePeriod = entityManager.find(Period.class, period.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePeriod == null) {
                throw new EntityException("Periodo não encontrado");
            }

            entityManager.merge(period);
        }
        catch (OptimisticLockException exception) {
            throw new ObsoleteEntityException(exception.getMessage());
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public void delete(Period period) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();

            Period databasePeriod = entityManager.find(Period.class, period.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databasePeriod == null) {
                throw new EntityException("Periodo não encontrado");
            }

            entityManager.remove(databasePeriod);
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public List<Period> list(Long personId) {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = " SELECT rp FROM Period rp " +
                          " WHERE rp.person.id = :personId " +
                          " ORDER BY rp.id ";

        TypedQuery<Period> query = entityManager.createQuery(queryStr, Period.class);
        query.setParameter("personId", personId);

        return query.getResultList();
    }

    @Override
    public List<Period> list(Long roleId, Long personId) {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = " SELECT rp FROM Period rp " +
                          " WHERE rp.role.id = :roleId AND rp.person.id = :personId " +
                          " ORDER BY rp.id ";

        TypedQuery<Period> query = entityManager.createQuery(queryStr, Period.class);
        query.setParameter("roleId", roleId);
        query.setParameter("personId", personId);

        return query.getResultList();
    }

    @Override
    public List<Period> list() {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT rp FROM Period rp ORDER BY rp.id";
        TypedQuery<Period> query = entityManager.createQuery(queryStr, Period.class);
        return query.getResultList();
    }

}
