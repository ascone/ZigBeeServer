package DBConnector;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.xml.sax.ErrorHandler;

import model.Device;
import model.SensorData;
import model.Sensoren;
import model.UuidShortLog;

//zig_feed pw: Efedemini555
public class DBConnection {

	static TcpConnection.ErrorHandler err;
//	public static void main(String args[]){                 Zum Testen Nutzen
//		ParseAndWrite("1.4.1.wert123#1.5.1.2erwert");
//	}
	
public static void ParseAndWrite(String raw){
	EntityManagerUtil.load();
	String CSUID= new String() ,ShortCSUID= new String(),SensorID= new String(),Wert= new String();
	
	//Reihenfolge : 1.CSUID 2.ShortID 3.SensorID 4.Wert 
	 
	String[] split_by_csuid = raw.split("#");
	for (String s : split_by_csuid) {
		String[] split_by_values = s.split("\\.");
	
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
		Query q1 = EntityManagerUtil.em.createQuery("SELECT e FROM SensorData e ");
		List<SensorData> eintraegeSensorData = q1.getResultList();
		return eintraegeSensorData.size();
	}catch(Exception e){
		 err=(TcpConnection.ErrorHandler) new TcpConnection.ErrorHandler("Fehler beim checken der Sensor Daten :" + e.getMessage() );
		return -1;
	}	
}
	
public static boolean TestDB(){
	try{
		Query q1 = EntityManagerUtil.em.createQuery("SELECT e FROM SensorData e ");		
	}catch(Exception e){
		
		 err=(TcpConnection.ErrorHandler) new TcpConnection.ErrorHandler("Fehler: Db offline?! :" + e.getMessage() );
		return false;
	}
	return true;
}
	
	public static void fillDBWithSensorData(int SensorID,String Data){
				
			//Query Test gib mir den Sensor(id), dem ich Sensor_Daten hinzufügen will . Sensor nachricht muss ID beinhalten	
			Query q1 = EntityManagerUtil.em.createQuery("SELECT e FROM Sensoren e ");//returns List of Sensoren
	
			List<Sensoren> eintraegeSensor = q1.getResultList();
		    for(Sensoren e:eintraegeSensor)
		    {
		    	if(e.getIdSensoren()==SensorID){ //Sensor ID !		    		
		    	SensorData neueDaten=new SensorData();		    
		    	neueDaten.setTimeStamp(new java.sql.Date(System.currentTimeMillis()));
		    	neueDaten.setWert(Data);
		    	neueDaten.setSensoren(e);
		    	EntityManagerUtil.em.getTransaction().begin();
		    	EntityManagerUtil.em.persist(neueDaten);
		    	EntityManagerUtil.em.getTransaction().commit();
		    	}		    			    	
		    }		    		
}	
	
public static void checkCsuid(int CSUID,int ShortCSUID){
		
Query q1 = EntityManagerUtil.em.createQuery("SELECT e FROM UuidShortLog e ");
List<UuidShortLog> eintraegeLog=q1.getResultList();

	for(UuidShortLog e:eintraegeLog){
		if(e.getDevice().getIdDevice()==CSUID & e.getUUID_short()==ShortCSUID ){
			System.out.println("SCHON VORHANDEN");
			return;
		}
	}

	System.out.println("Leere Liste/Nicht Vorhanden/Unterschied Festgestellt ");
	Query q2 = EntityManagerUtil.em.createQuery("SELECT e FROM Device e ");
	List<Device> eintraegeDev=q2.getResultList();
	for(Device d:eintraegeDev){
		if(d.getIdDevice()==CSUID){		
			UuidShortLog log =new UuidShortLog();
			log.setTtimestamp(new java.sql.Date(System.currentTimeMillis()));
			log.setDevice(d);
			log.setUUID_short(ShortCSUID);
			EntityManagerUtil.em.getTransaction().begin();
			EntityManagerUtil.em.persist(log);
			EntityManagerUtil.em.getTransaction().commit();
			}
		}
	
}

}


