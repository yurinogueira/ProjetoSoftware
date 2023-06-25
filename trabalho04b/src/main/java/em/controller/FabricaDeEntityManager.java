package em.controller;

import jakarta.persistence.EntityManager;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeEntityManager {

    public static EntityManager getEntityManager() {
        Reflections reflections = new Reflections("em.impl");

        Set<Class<? extends EntityManager>> classes = reflections.getSubTypesOf(EntityManager.class);

        if (classes.size() > 1) {
            throw new RuntimeException("Somente uma classe pode implementar " + EntityManager.class.getName());
        }

        Class<?> classe = classes.iterator().next();

        return (EntityManager) Enhancer.create(classe, new InterceptadorDeEntityManager());
    }

}
