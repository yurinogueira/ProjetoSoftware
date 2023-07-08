package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import excecao.InfraestruturaException;

public class JPAUtil {

	private final EntityManagerFactory emf;

	private static JPAUtil instance;
	private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();
	private static final ThreadLocal<EntityTransaction> threadTransaction = new ThreadLocal<>();


	private JPAUtil() {
		emf = Persistence.createEntityManagerFactory("trabalho05");
	}

	public static void beginTransaction() {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Vai criar transa��o");

		EntityTransaction tx = threadTransaction.get();
		try {
			if (tx == null) {
				tx = getEntityManager().getTransaction();
				tx.begin();
				threadTransaction.set(tx);
			}
		} catch (RuntimeException ex) {
			throw new InfraestruturaException(ex);
		}
	}

	public static EntityManager getEntityManager() {
		EntityManager entityManager;

		try {
			if (instance == null) {
				instance = new JPAUtil();
			}
			entityManager = threadEntityManager.get();
			if (entityManager == null) {
				entityManager = instance.emf.createEntityManager();
				threadEntityManager.set(entityManager);
			}
		}
		catch (RuntimeException ex) {
			throw new InfraestruturaException(ex);
		}

		return entityManager;
	}

	public static void commitTransaction() {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Vai comitar transa��o");

		EntityTransaction tx = threadTransaction.get();
		try {
			if (tx != null && tx.isActive()) {
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (RuntimeException ex) {
			try {
				rollbackTransaction();
			} catch (RuntimeException ignored) {
			}

			throw new InfraestruturaException(ex);
		}
	}

	public static void rollbackTransaction() {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Vai efetuar rollback da transa��o");

		System.out.println("Vai efetuar rollback de transacao");

		EntityTransaction tx = threadTransaction.get();
		try {
			threadTransaction.set(null);
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} catch (RuntimeException ex) {
			throw new InfraestruturaException(ex);
		} finally {
			closeEntityManager();
		}
	}

	public static void closeEntityManager() { // System.out.println("Vai fechar sess�o");

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> Vai fechar o entity manager");

		try {
			EntityManager s = threadEntityManager.get();
			threadEntityManager.set(null);
			if (s != null && s.isOpen()) {
				s.close();
			}

			EntityTransaction tx = threadTransaction.get();
			if (tx != null && tx.isActive()) {
				rollbackTransaction();
				throw new RuntimeException("EntityManager sendo fechado " + "com transa��o ativa.");
			}
		} catch (RuntimeException ex) {
			throw new InfraestruturaException(ex);
		}
	}
}
