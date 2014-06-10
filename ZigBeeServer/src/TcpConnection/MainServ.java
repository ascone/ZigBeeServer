package TcpConnection;
/*
 * ZigBee Server -- MainServ.java
 * von Tobias Orth	951311
 * 13.05.2014 - V1.0
 * F�r das ZigBee Projekt wird ein Server ben�tigt
 * der die Daten der einzelnen Sensoren entgegen nimmt 
 * und f�r die Speicherung in der Datenbank vorbereitet. 
 * 
 * 15.05.214 UPDATE V1.1
 * -Errhandler samt Logfunktion hinzugef�gt
 * -Code Cleanup
 * -TCP Verbindung erfolgreich getestet (Empfangen und Verarbeiten von Daten)
 * 
 * Die Datenbank wird �ber die JPA-API gestellt(Anton Schneider) 
 */


/*Imports*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import DBConnector.DBConnection;


/*Eigene Klasse*/
public class MainServ {

	private static final String VERSION = "1.5.6";
	static ErrorHandler error;
	
	  public static void main (String args[]) {   														
		  try{
			  System.out.println("Server startet");
			  
			  /*Server Initialisieren*/
			  int serverPort = 7896;									  //Port
			  ServerSocket listenSocket = new ServerSocket (serverPort); //Port Binden
	  	  
	  		 /*Server starten*/
			  while(true) 
			  {
				  Socket clientSocket = listenSocket.accept();			//Conections Aktzeptieren
				  System. out.println("Neue Verbindung");
				  Connection c = new Connection(clientSocket);			//Conection binden
			  }
		    } catch( IOException e) {error= new ErrorHandler(" Listen : " + e.getMessage());System.out.println("Listen Fehler siehe log!\n");}
		  
	  }

	public static String getVersion() {
		return VERSION;
	}
}

class Connection extends Thread {
	  
	  /*Ben�tiogte Objekte (Inputstream & Socket)*/
	  PrintWriter  out;
	  BufferedReader in;
	  Socket clientSocket;
	  static ErrorHandler error;

	  public Connection (Socket aClientSocket)
	  {
	    try {
	    		clientSocket = aClientSocket;
	    		in =new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
	    		out = new PrintWriter(clientSocket.getOutputStream(),true);   
	    		this.start();
	    	} catch( IOException e) 
	    				{
	    					error = new ErrorHandler(" IO: "+ e.toString());
	    					System.out.println("Connection Fehler siehe log!\n");
	    				}
	 }
	  
	  /*Wenn eine Verbindung besteht wird Automatisch die funktion run() aufgerufen*/
	  public void run(){
		    try {
		    	//Befehl empfangen
		    	String input = null;
		    	String befehl = in.readLine();
		 
		    	/*
		    	 * Auswahl des befehls
		     	 */
		    
		    	switch(befehl)
		    	{
			    	case "Daten": input = getInstructions();
			    				  testMode(input);
			    				  break;
			    				  
			    	case "Admin": adminMenue();	
			    				  break;
			    				  
			    	   /*Wurde ein nicht bekannter Befehl gesendet ? */
					default:	 gprsData(befehl);
					   			 break;
		    	}	
		    	
		    	
		    	clientSocket.close();
		    	
		    
		    } catch( EOFException e) 
		    		{
		    			error = new ErrorHandler(" EOF:"+ e.getMessage());
		    			
		    		} 
		      catch( IOException e) 
		      		{
		    	  		if(e.getMessage() == "Connection reset")
		    	  		{	
		    	  			/*Mache nichts da Client die Verbindung beendet hat*/
		    	  		}
		    	  		
		    	  		else
		    	  		{
		    	  			error = new ErrorHandler(" IO: "+ e.toString());
		    	  		}
		      		}
		    
		  }
	 


	private void gprsData(String befehl) {
		System.out.println("GPRS DATEN: " + befehl);
		if (befehl.length() <= 10)
		{
			error=new ErrorHandler("Input Fehler: Befehl nicht gefunden! : " +befehl);
		}
		testMode(befehl);
		
	}

	private void adminMenue() throws IOException {
		Boolean runner = true;
		out.println("ok");
		
			String handler = in.readLine();
		
			switch (handler)
			{
				case "version": out.println(MainServ.getVersion());break;
				case "restart": Runtime.getRuntime().exec("/ZigBeeServer/Scripts/toggleServer.sh"); break;
				case "dbstatus": out.println(DBConnection.TestDB());break;
				case "dbentrys": out.println(DBConnection.HowManyEntriesInSensorData());break;
				case "auswertung": break;
				default: break;
			
			}
		
	}

	private void testMode(String input) {
		
		String HEADER ="Daten Log f�r Test Verbindungen";		
		
		File dir = new File("/ZigBeeServer/TestDaten");						
		File file =new File("/ZigBeeServer/TestDaten/daten.log");		
		
		
			if(!dir.exists())
			{
				dir.mkdir();
			}
		
			if(!file.exists())
    		{
    			try
    			{
	    			file.createNewFile();													//File Erstellen
	    			FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),false);	//FileWriter Initzialisieren, False gibt an das kein vorheriger Content behalten wird
	        	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);			//Buffered Filewriter initzialisieren
	        	    
	        	    bufferWritter.write(HEADER);	//Header schreiben
	        	    bufferWritter.newLine();		//newline
	        	    bufferWritter.close();			//bufferedwriter Schlie�en
    			}
    			catch (Exception e){}
    		}
			
			
			try
			{
				FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),true);	//appaned mode true(Textanh�ngen)
	    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    	    String daten = "Daten erhalten!: WERT: " + input;
	    	    
	    	    bufferWritter.write(daten);
	    	    bufferWritter.newLine();
	    	    bufferWritter.close();
	    	    
			}
			catch(Exception e){}
    	    
	}

	
	String getInstructions() throws IOException
	  {		
		
		//Calender mit aktuellen Zeitstempel erstellen
		 out.println("ok");
		  String dataPack=null;
		  dataPack=in.readLine();
		  out.println("complete");
		  


		  return dataPack;
	  }

}