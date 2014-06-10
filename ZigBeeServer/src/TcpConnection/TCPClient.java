package TcpConnection;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
	
  public static void main (String args[]) {
	  
	  /*Client starten und bis zur einlol
	   *gabe von "beenden" laufen lassen*/
	  System.out.println("Verbindung aufbauen...");
	  try{
		  int serverPort = 7896;
		  Socket s;
		
	
		  /*Stream Dekleration */
		  @SuppressWarnings("resource")
		  Scanner scanner = new Scanner(System.in);
		  String befehl = new String();	
		  String antwort = new String();
	
	
		  PrintWriter  out;
		  BufferedReader input;
		  
		  while(true)
		  { 
			  try{
				  System.out.println("Connected!");
				  s = new Socket ("194.94.82.231", serverPort);
				  out= new PrintWriter(s.getOutputStream(),true);
				  input =new BufferedReader(new InputStreamReader(s.getInputStream()));
				  /*Benutzer Menü */
				  System.out.println("\tSenden oder Beenden?");
				  befehl = scanner.nextLine();
				  if (befehl == "beenden")
				  {
					  System.out.println("bye :)");
					  s.close();
					  System.exit(0);
				  }
				  
				  else{
				  System.out.print("Eingabe 1: ");
				  befehl = scanner.nextLine();
				  out.println(befehl);
				  out.flush();
				  System.out.println("Befehl gesendet warten Auf AW:");
				  
				  antwort = input.readLine();
				  System.out.println("Antwort erhalten: " + antwort);
				  
				  System.out.print("Eingabe 2: ");
				  befehl = scanner.nextLine();
				  out.println(befehl);
				  out.flush();
				  System.out.println("Befehl gesendet warten Auf AW:");
				  
				  antwort = input.readLine();
				  System.out.println("Antwort erhalten: " + antwort);
				  
				
				  System.out.println("Done");
				  }
				  s.close();
				  
			  	}catch (Exception e){System.out.println("ERR: " + e.getMessage());}
	    }
		  
	  }catch (Exception e){}
  }
 
}