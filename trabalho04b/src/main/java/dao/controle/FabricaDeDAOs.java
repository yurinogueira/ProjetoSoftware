package dao.controle;

import anotacao.Autowired;
import em.controller.FabricaDeEntityManager;
import jakarta.persistence.EntityManager;
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

		try {
			T instance = tipo.cast(classe.getDeclaredConstructor().newInstance());

			Field[] campos = instance.getClass().getDeclaredFields();
			for (Field campo : campos) {
				if (campo.isAnnotationPresent(Autowired.class)) {
					campo.setAccessible(true);
					campo.set(instance, FabricaDeEntityManager.getEntityManager());
				}
			}

			return instance;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
