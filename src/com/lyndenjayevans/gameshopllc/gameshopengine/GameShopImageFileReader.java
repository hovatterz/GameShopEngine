package com.lyndenjayevans.gameshopllc.gameshopengine;
import java.io.File;
import java.util.Scanner;

public class GameShopImageFileReader {

	public GameShopImageFileReader() {
		
		
	}
	
	public GameShopImage readFile() {
		String currentLine = "";
		try  
		{  
		File file=new File("starter.jshopimage");   
		Scanner sc = new Scanner(file);     //file to be scanned  
		while (sc.hasNextLine())        //returns true if and only if scanner has another token  
		//System.out.println(sc.nextLine());  
		 currentLine = new String(sc.nextLine());
		 char[] currentChars = currentLine.toCharArray();
		 String currentCmd = "";
		 String currentClause = "";
		 boolean cmdRegistered = false;
		 
		 for (char c: currentChars) {
			 if (!cmdRegistered && c != '(') 
			 {
				 currentCmd = new String(currentCmd + c);
			 } else {
				 cmdRegistered = true;
				 if (currentCmd.equals("Image")) {
					 
				 } 
				 else if (currentCmd.equals("Layer")) {
					 
				 } 
				 else if (currentCmd.equals("PixelOfInterest")) {
					 
				 }
				 else if (currentCmd.equals("Segment")) {
					 
				 } 
				 else if (currentCmd.equals("Pixel")) {
	 
				 } 
			 }
		 }
		}  
		catch(Exception e)  
		{  
		e.printStackTrace();  
		}  
		return null;
		} 
	
}
