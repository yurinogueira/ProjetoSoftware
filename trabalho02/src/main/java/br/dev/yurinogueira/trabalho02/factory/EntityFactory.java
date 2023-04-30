package br.dev.yurinogueira.trabalho02.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityFactory {

    private static EntityFactory entityFactory = null;
    private final EntityManagerFactory entityManagerFactory;

    private EntityFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("trabalho02");
    }

    public static EntityManager createEntityManager() {
        if (entityFactory == null) {
            entityFactory = new EntityFactory();
        }

        return entityFactory.entityManagerFactory.createEntityManager();
    }

}
