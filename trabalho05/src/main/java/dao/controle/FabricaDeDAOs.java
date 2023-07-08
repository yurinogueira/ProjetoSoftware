package dao.controle;

import net.sf.cglib.proxy.Enhancer;
import org.reflections.Reflections;

import java.util.Set;

public class FabricaDeDAOs {

	public static <T> T getDAO(Class<T> tipo) {
		Reflections reflections = new Reflections("dao.impl");

		Set<Class<? extends T>> classes = reflections.getSubTypesOf(tipo);

		if (classes.size() > 1) {
			throw new RuntimeException("Somente uma classe pode implementar " + tipo.getName());
		}

		Class<?> classe = classes.iterator().next();

		return tipo.cast(Enhancer.create(classe, new InterceptadorDeDao()));
	}

}
