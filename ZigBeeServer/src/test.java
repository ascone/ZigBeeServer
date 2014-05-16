import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.SensorData;
import model.Sensoren;




public class test {

	public static void main(String[] args) {
		EntityManagerFactory factory;
		factory=Persistence.createEntityManagerFactory("ZigBeeServer");
		EntityManager em= factory.createEntityManager();
		
		Sensoren testsensor=new Sensoren();
		em.getTransaction().begin();
		em.persist(testsensor);
		em.getTransaction().commit();
		

		//Query Test gib mir den Sensor(id), dem ich Sensor_Daten hinzufügen will . Sensor nachricht muss ID beinhalten
				
		Query q1 = em.createQuery("SELECT e FROM Sensor e ");
		
		List<Sensoren> eintraegeSensor = q1.getResultList();
		    for(Sensoren e:eintraegeSensor){
		    	
		    	if(e.getIdSensoren()==1){ //Sensor ID !
		    	SensorData neueDaten=new SensorData();
		    	neueDaten.setTimeStamp(new java.sql.Date(System.currentTimeMillis()));
		    	neueDaten.setWert("over9000");
		    	neueDaten.setSensoren(e);
		    	em.getTransaction().begin();
		    	em.persist(neueDaten);
		    	em.getTransaction().commit();
		    	}
		    	
		    	
		    }
		    
		    

			
			em.close();

	}

}
