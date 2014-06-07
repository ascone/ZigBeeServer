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

//fillDBWithSensorData(1,"gsgwse");
	
	ParseAndWrite("1234.adolf.5.wert");
	}


public static void ParseAndWrite(String ContentString){
	String CSUID= new String() ,ShortID= new String(),SensorID= new String(),Wert= new String();
	int i=0;int pointcountr=0;
	//1.CSUID 2.ShortID 3.SensorID 4.Wert 
	char ContentArray[]= ContentString.toCharArray();
	System.out.println("initial String "+ContentString);
	
	while(i<ContentArray.length){
		/*if(ContentArray[i]=='.'){
			pointcountr++;
		}
		
		if (pointcountr==0){
		CSUID =ContentArray[i]+CSUID;
		}
		else if(pointcountr==1){
			ShortID=ContentArray[i]+ShortID;
		}
		else if(pointcountr==2){
			SensorID=ContentArray[i]+SensorID;
		}
		else if (pointcountr==3){
			Wert=ContentArray[i]+Wert;
		}
		
		else if(ContentArray[i]=='#'){
			pointcountr=0;
		}
		i++;
		*/
		if (ContentArray[i]=='.')
		{
			i++;
			pointcountr++;
		}
		
		else if(ContentArray[i] =='#')
		{
			String rest=null;
			ParseAndWrite(rest);
		}	

		switch (pointcountr)
		{
		case 0: CSUID +=ContentArray[i];break;
		case 1: ShortID+=ContentArray[i];break;
		case 2: SensorID+=ContentArray[i];break;
		case 3: Wert+=ContentArray[i];break;
		default: System.out.println("Fehler");break;
		}
		i++;
	}
	System.out.println(CSUID);
	System.out.println(ShortID);
	System.out.println(SensorID);
	System.out.println(Wert);
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
