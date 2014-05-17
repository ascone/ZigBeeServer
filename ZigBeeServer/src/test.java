import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Device;
import model.SensorData;
import model.Sensoren;


public class test {

public static void main(String[] args) {
		
fillDBWithSensorData(1,"FUCKYOU");		
	}
	
	//param CSUID? SensorID,Daten,(nicht datum)	
	public static void fillDBWithSensorData(int SensorID,String Data){
		EntityManagerFactory factory;
		factory=Persistence.createEntityManagerFactory("ZigBeeServer");
		EntityManager em= factory.createEntityManager();	
		
//Query Test gib mir den Sensor(id), dem ich Sensor_Daten hinzufügen will . Sensor nachricht muss ID beinhalten	
	Query q1 = em.createQuery("SELECT e FROM Sensoren e ");//returns List of Sensoren
	
	List<Sensoren> eintraegeSensor = q1.getResultList();
	    for(Sensoren e:eintraegeSensor){
	    	if(e.getIdSensoren()==SensorID){ //Sensor ID !		    		
	    	SensorData neueDaten=new SensorData();		    
	    	neueDaten.setTimeStamp(new java.sql.Date(System.currentTimeMillis()));
	    	neueDaten.setWert(Data);
	    	neueDaten.setSensoren(e);
	    	em.getTransaction().begin();
	    	em.persist(neueDaten);
	    	em.getTransaction().commit();
	    	}		    			    	
	    }		    
		em.close();

}

}
