package br.dev.yurinogueira.trabalho02.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class EntityFactory {

    private static EntityFactory entityFactory = null;
    private final EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();
    private static final ThreadLocal<EntityTransaction> threadTransaction = new ThreadLocal<>();


    private EntityFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("trabalho02");
    }

    public static void beginTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        if (transaction == null) {
            transaction = getEntityManager().getTransaction();
            transaction.begin();
            threadTransaction.set(transaction);
        }
    }

    public static EntityManager getEntityManager() {
        if (entityFactory == null) {
            entityFactory = new EntityFactory();
        }

        EntityManager entityManager = threadEntityManager.get();
        if (entityManager == null) {
            entityManager = entityFactory.entityManagerFactory.createEntityManager();
            threadEntityManager.set(entityManager);
        }
        return entityManager;
    }

    public static void commitTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        try {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
            threadTransaction.set(null);
        }
        catch (RuntimeException ignored) {
            rollbackTransaction();
        }
    }

    public static void rollbackTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        try {
            threadTransaction.set(null);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        finally {
            closeEntityManager();
        }
    }

    public static void closeEntityManager() {
        EntityTransaction transaction = threadTransaction.get();
        if (transaction != null && transaction.isActive()) {
            rollbackTransaction();
        }

        EntityManager entityManager = threadEntityManager.get();
        threadEntityManager.set(null);
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
