package TcpConnection;
/*
 * ZigBee Server -- ErrorHandler.java
 * von Tobias Orth	951311
 * 15.05.2014
 * 
 * Die Klasse Errorhandler wird im Falle eines Fehlers im 
 * Programm aufgerufen und genriert daraufhin einen Fehlercode mit 
 * aktuellen Zeitstempel und speichert diese in einen Unterordner
 * "errorlogs" in eine datei.. 
 * 
 * Die Datenbank wird �ber die JPA-API gestellt(Anton Schneider) 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ErrorHandler {
	String errmsg;												//Halter f�r die �bergebene Fehlermeldung
	
	static final String HEADER ="Error Log ZigBitServer";		//Header zum Erstellen des Logs
	
	static File dir = new File("/ZigBeeServer/errlogs");						//Ordnername
	static File file =new File("/ZigBeeServer/errlogs/err.log");			//Pfad und Filename
	
	/*Konstruktor*/
	public ErrorHandler(String msg)
	{
		errmsg=msg;		//Fehlermeldung entgegen nehmen
		
		/*Versuche: 1. Exestiert der Ordner , 2 Exestiert die Datei. Wenn nicht erstellen */
		try 
		{	
			/*Auf ordner Pr�fen*/
			if (!dir.exists())
			{
				dir.mkdir();	//Ordner erstellen falls nicht exestent
			}
			
			
			 
    		/*Auf das file Pr�fen*/
    		if(!file.exists())
    		{
    			
    			file.createNewFile();													//File Erstellen
    			FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),false);	//FileWriter Initzialisieren, False gibt an das kein vorheriger Content behalten wird
        	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);			//Buffered Filewriter initzialisieren
        	    
        	    bufferWritter.write(HEADER);	//Header schreiben
        	    bufferWritter.newLine();		//newline
        	    bufferWritter.close();			//bufferedwriter Schlie�en
    		}
 
    		/*Einf�gen des Fehlers*/
    		FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),true);	//appaned mode true(Textanh�ngen)
    	    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	    
    	    bufferWritter.write(createErrorprint(errmsg));		//Schreiben der Fehlermeldung die durch createErrorprint generiert wird
    	    bufferWritter.newLine();
    	    bufferWritter.close();
    	    /*Fertig*/
    	   
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	/*Funktion zur generierung des Zeitstempels*/
	static String getTime()
	{
		String currentTime;					//R�ckgabe String
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss"); 		//Format des Zeitstempels 
		Calendar cal = Calendar.getInstance();									//Calender mit aktuellen Zeitstempel erstellen
		
		currentTime = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()); //Konvertieren
		return currentTime;			//R�ckgabe
	}
	
	/*Funktion zur generierung des Fehlers*/
	static String createErrorprint(String description)
	{	
		String printmessage;
		
		printmessage = getTime();
		printmessage += " Fehler: \"" + description +"\" ";
		
		return printmessage;
	}
}

