package DBConnector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("ZigBeeServer");
	public static EntityManager em = factory.createEntityManager();

}

