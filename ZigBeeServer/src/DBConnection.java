import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Device;
import model.SensorData;
import model.Sensoren;


public class DBConnection {

public static void main(String[] args) {
	
	ParseAndWrite("csuid123.00.1.hehe#csuid337.01.1.lel");
	}


public static void ParseAndWrite(String raw){
	String CSUID= new String() ,ShortID= new String(),SensorID= new String(),Wert= new String();
	
	//1.CSUID 2.ShortID 3.SensorID 4.Wert 
	 
	String[] split_by_csuid = raw.split("#");
	for (String s : split_by_csuid) {
		String[] split_by_values = s.split("\\.");
		//System.out.println("Teilstring: " + s);
		
		CSUID=split_by_values[0];
		ShortID=split_by_values[1];
		SensorID=split_by_values[2];
		Wert=split_by_values[3];
				
//		System.out.println("CSUID: " + CSUID);
//		System.out.println("ShortID: " + ShortID);
//		System.out.println("SensorID: " + SensorID);
//		System.out.println("Wert: " + Wert);
		
		fillDBWithSensorData(Integer.parseInt(SensorID),Wert);

	}

}


	//param CSUID? SensorID,Daten,(nicht datum)	
	public static boolean fillDBWithSensorData(int SensorID,String Data){
		try
		{
			EntityManagerFactory factory;
			factory=Persistence.createEntityManagerFactory("ZigBeeServer");
			EntityManager em= factory.createEntityManager();	
	
			//Query Test gib mir den Sensor(id), dem ich Sensor_Daten hinzufügen will . Sensor nachricht muss ID beinhalten	
			Query q1 = em.createQuery("SELECT e FROM Sensoren e ");//returns List of Sensoren
	
			List<Sensoren> eintraegeSensor = q1.getResultList();
		    for(Sensoren e:eintraegeSensor)
		    {
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
		}catch(Exception e)
			{
				return false;
			}
		return true;
		
		

}

}
