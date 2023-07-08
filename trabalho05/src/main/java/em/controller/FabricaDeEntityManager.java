package em.controller;

import jakarta.persistence.EntityManager;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeEntityManager {

    public static EntityManager getEntityManager() {
        return (EntityManager) Enhancer.create(EntityManager.class, new InterceptadorDeEntityManager());
    }

}
