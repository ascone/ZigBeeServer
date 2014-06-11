package DBConnector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory factory = null;
	public static EntityManager em = null;
	
	public static void load () {
		factory = Persistence.createEntityManagerFactory("ZigBeeServer");
		em = factory.createEntityManager();
	}

}

