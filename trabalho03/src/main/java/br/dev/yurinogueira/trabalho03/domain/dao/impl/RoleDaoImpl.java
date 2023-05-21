package br.dev.yurinogueira.trabalho03.domain.dao.impl;

import br.dev.yurinogueira.trabalho03.domain.dao.RoleDao;
import br.dev.yurinogueira.trabalho03.domain.entity.Role;
import br.dev.yurinogueira.trabalho03.exception.EntityException;
import br.dev.yurinogueira.trabalho03.exception.ObsoleteEntityException;
import br.dev.yurinogueira.trabalho03.factory.EntityFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import org.hibernate.JDBCException;

import java.util.List;

public class RoleDaoImpl implements RoleDao {

    @Override
    public Long create(Role role) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            entityManager.persist(role);

            return role.getId();
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public Role read(Long id) throws EntityException {
        EntityManager entityManager = EntityFactory.getEntityManager();
        Role role = entityManager.find(Role.class, id);

        if (role == null) {
            throw new EntityException("Função não encontrada");
        }

        return role;
    }

    @Override
    public void update(Role role) throws EntityException, ObsoleteEntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Role databaseRole = entityManager.find(Role.class, role.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databaseRole == null) {
                throw new EntityException("Função não encontrada");
            }

            entityManager.merge(role);
        }
        catch (OptimisticLockException exception) {
            throw new ObsoleteEntityException(exception.getMessage());
        }
        catch (JDBCException exception) {
            throw new EntityException("O Nome não pode ser repetido!");
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public void delete(Role role) throws EntityException {
        try {
            EntityManager entityManager = EntityFactory.getEntityManager();
            Role databaseRole = entityManager.find(Role.class, role.getId(), LockModeType.PESSIMISTIC_WRITE);

            if (databaseRole == null) {
                throw new EntityException("Função não encontrada");
            }

            entityManager.remove(databaseRole);
        }
        catch (RuntimeException exception) {
            throw new EntityException(exception.getMessage());
        }
    }

    @Override
    public List<Role> list(String name) {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT r FROM Role r WHERE r.name = :name ORDER BY r.id";
        TypedQuery<Role> query = entityManager.createQuery(queryStr, Role.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Role> list() {
        EntityManager entityManager = EntityFactory.getEntityManager();
        String queryStr = "SELECT r FROM Role r ORDER BY r.id";
        TypedQuery<Role> query = entityManager.createQuery(queryStr, Role.class);
        return query.getResultList();
    }

}
