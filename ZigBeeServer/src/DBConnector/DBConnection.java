package DBConnector;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Device;
import model.SensorData;
import model.Sensoren;

//zig_feed pw: Efedemini555
public class DBConnection {

public static void main(String[] args) {
	
	//ParseAndWrite("csuid123.00.1.lol#csuid337.01.1.soos#");
	
	System.out.println(HowManyEntriesInSensorData());
	
	}


public static void ParseAndWrite(String raw){
	String CSUID= new String() ,ShortCSUID= new String(),SensorID= new String(),Wert= new String();
	
	//1.CSUID 2.ShortID 3.SensorID 4.Wert 
	 
	String[] split_by_csuid = raw.split("#");
	for (String s : split_by_csuid) {
		String[] split_by_values = s.split("\\.");
		//System.out.println("Teilstring: " + s);
		
		CSUID=split_by_values[0];
		ShortCSUID=split_by_values[1];
		SensorID=split_by_values[2];
		Wert=split_by_values[3];

		
		fillDBWithSensorData(Integer.parseInt(SensorID),Wert);

	}

}

public static int HowManyEntriesInSensorData(){
	
	try{
		EntityManagerFactory factory;
		factory=Persistence.createEntityManagerFactory("ZigBeeServer");
		EntityManager em= factory.createEntityManager();
		Query q1 = em.createQuery("SELECT e FROM SensorData e ");
		List<SensorData> eintraegeSensorData = q1.getResultList();
		return eintraegeSensorData.size();
	}catch(Exception e){
		return -1;
	}	
}
	


public static boolean TestDB(){
	try{
		EntityManagerFactory factory;
		factory=Persistence.createEntityManagerFactory("ZigBeeServer");
		EntityManager em= factory.createEntityManager();
		Query q1 = em.createQuery("SELECT e FROM SensorData e ");
		
		
	}catch(Exception e){
		return false;
	}
	return true;
}

	//param CSUID? SensorID,Daten,(nicht datum)	
	public static void fillDBWithSensorData(int SensorID,String Data){
		
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
		
}
	

	

}
