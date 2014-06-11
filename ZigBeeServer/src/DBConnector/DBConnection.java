package DBConnector;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Device;
import model.SensorData;
import model.Sensoren;
import model.UuidShortLog;

//zig_feed pw: Efedemini555
public class DBConnection {
	
	public static void main(String args[]){
		ParseAndWrite("1.2.1.wert123#1.3.1.2erwert");
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

		checkCsuid(Integer.parseInt(CSUID),Integer.parseInt(ShortCSUID));
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
	
public static void checkCsuid(int CSUID,int ShortCSUID){

EntityManagerFactory factory;
factory=Persistence.createEntityManagerFactory("ZigBeeServer");
EntityManager em= factory.createEntityManager();	
Query q1 = em.createQuery("SELECT e FROM UuidShortLog e ");
List<UuidShortLog> eintraegeLog=q1.getResultList();
if(eintraegeLog.size()==0){
	System.out.println("LOG EMPTY");
	Query q2 = em.createQuery("SELECT e FROM Device e ");
	List<Device> eintraegeDev=q2.getResultList();
	for(Device d:eintraegeDev){
		if(d.getIdDevice()==CSUID){		
			UuidShortLog log =new UuidShortLog();
			log.setTtimestamp(new java.sql.Date(System.currentTimeMillis()));
			log.setDevice(d);
			log.setUUID_short(ShortCSUID);
			em.getTransaction().begin();
	    	em.persist(log);
	    	em.getTransaction().commit();
}

}
	}

else{
	for(UuidShortLog e:eintraegeLog){
		if(e.getDevice().getIdDevice()==CSUID & e.getUUID_short()==ShortCSUID ){
			System.out.println("SCHON VORHANDEN");
			return;
		}
	}
		
			System.out.println("UNTERSCHIED zwischen CSUID UND SHORTCSUID FESTGESTELLT");
			
			Query q2 = em.createQuery("SELECT e FROM Device e ");
			List<Device> eintraegeDev=q2.getResultList();
			for(Device d:eintraegeDev){
				if(d.getIdDevice()==CSUID){		
					UuidShortLog log =new UuidShortLog();
					log.setTtimestamp(new java.sql.Date(System.currentTimeMillis()));
					log.setDevice(d);
					log.setUUID_short(ShortCSUID);
					em.getTransaction().begin();
			    	em.persist(log);
			    	em.getTransaction().commit();
					}
				
			}			
			
		}
		
	
}

}


