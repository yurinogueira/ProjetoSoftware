package br.dev.yurinogueira.trabalho03.factory;

import br.dev.yurinogueira.trabalho03.exception.InfraException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityFactory {

    private static final Logger logger = LoggerFactory.getLogger(EntityFactory.class);

    private static EntityFactory entityFactory = null;
    private final EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();
    private static final ThreadLocal<EntityTransaction> threadTransaction = new ThreadLocal<>();
    private static final ThreadLocal<Integer> threadTransactionCount = new ThreadLocal<>();

    private EntityFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("trabalho03");
    }

    public static void beginTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();

        if (transaction == null) {
            try {
                transaction = getEntityManager().getTransaction();
                transaction.begin();
                transactionCount = 1;
                threadTransactionCount.set(transactionCount);
                threadTransaction.set(transaction);
                logger.info(">>>> Criou a transação");
            }
            catch (RuntimeException ex) {
                throw new InfraException(ex.getMessage());
            }
        }
        else {
            transactionCount++;
            threadTransactionCount.set(transactionCount);
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager;

        try {
            if (entityFactory == null) {
                entityFactory = new EntityFactory();
            }
            entityManager = threadEntityManager.get();
            if (entityManager == null) {
                entityManager = entityFactory.entityManagerFactory.createEntityManager();
                threadEntityManager.set(entityManager);
                logger.info(">>>> Criou o entity manager");
            }
        }
        catch (RuntimeException ex) {
            throw new InfraException(ex.getMessage());
        }

        return entityManager;
    }

    public static void commitTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();

        try {
            if (transaction != null && transaction.isActive()) {
                transactionCount--;
                if (transactionCount == 0) {
                    transaction.commit();
                    threadTransaction.set(null);
                    threadTransactionCount.set(null);
                    logger.info(">>>> Comitou a transação");
                }
                else {
                    threadTransactionCount.set(transactionCount);
                }
            }
        }
        catch (RuntimeException exception) {
            try {
                rollbackTransaction();
            } catch (RuntimeException innerException) {
                throw new InfraException(innerException.getMessage());
            }
            throw new InfraException(exception.getMessage());
        }
    }

    public static void rollbackTransaction() {
        EntityTransaction transaction = threadTransaction.get();

        try {
            threadTransaction.set(null);
            threadTransactionCount.set(null);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                logger.info(">>>> Deu rollback na transação");
            }
        }
        catch (RuntimeException ex) {
            throw new InfraException(ex.getMessage());
        }
        finally {
            closeEntityManager();
        }
    }

    public static void closeEntityManager() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();
        if (transactionCount == null) {
            if (transaction != null && transaction.isActive()) {
                rollbackTransaction();
            }

            EntityManager entityManager = threadEntityManager.get();

            threadEntityManager.set(null);
            threadTransactionCount.set(null);

            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    logger.info(">>>> Fechou o entity manager");
                }
            }
            catch (RuntimeException ex) {
                throw new InfraException(ex.getMessage());
            }
        }
    }

}
