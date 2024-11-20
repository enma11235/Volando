package persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("volandoPuntoUyPU");

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void shutdown() {
		entityManagerFactory.close();
	}
}
