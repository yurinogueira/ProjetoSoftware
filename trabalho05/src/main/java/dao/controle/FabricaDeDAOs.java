package dao.controle;

import dao.impl.PersonDaoImpl;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FabricaDeDAOs {

	@Bean
	public static PersonDaoImpl getPersonDao() {
		return getDao(dao.impl.PersonDaoImpl.class);
	}

	public static <T> T getDao(Class<T> classeDoDao) {
		return classeDoDao.cast(Enhancer.create(classeDoDao, new InterceptadorDeDao()));
	}

}
