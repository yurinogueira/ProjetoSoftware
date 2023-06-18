package em.controller;

import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeEntityManager {

    public static <T> T getEntityManager(Class<T> tipo) {
        Reflections reflections = new Reflections("em.impl");

        Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);

        if (classes.size() > 1) {
            throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
        }

        Class<?> classe = classes.iterator().next();

        return tipo.cast(Enhancer.create(classe, new InterceptadorDeEntityManager()));
    }

}
