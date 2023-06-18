package dao.controle;

import anotacao.PersistenceContext;
import em.ProxyEntityManager;
import em.controller.FabricaDeEntityManager;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class FabricaDeDAOs {

	public static <T> T getDAO(Class<T> tipo) {
		Reflections reflections = new Reflections("dao.impl");

		Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);

		if (classes.size() > 1) {
			throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
		}

		Class<?> classe = classes.iterator().next();
		T instance = tipo.cast(Enhancer.create(classe, new InterceptadorDeDao()));

		try {
			Field[] campos = instance.getClass().getDeclaredFields();
			for (Field campo : campos) {
				if (campo.isAnnotationPresent(PersistenceContext.class)) {
					campo.setAccessible(true);
					campo.set(instance, FabricaDeEntityManager.getEntityManager(ProxyEntityManager.class));
				}
			}

			return instance;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
