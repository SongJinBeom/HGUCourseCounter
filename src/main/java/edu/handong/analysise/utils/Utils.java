package edu.handong.analysise.utils;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Utils {
	
	public static ArrayList<String> getLines(String file, boolean removeHeader){	
		ArrayList<String> temp = new ArrayList<String>();
		Scanner inputStream = null;
		try {
			
			File csv = new File(file);
			if(csv.exists() == false) {
				throw new NotEnoughArgumentException("The file path does not exist. Please check your CLI argument!");
			}
			inputStream = new Scanner(new File(file));
			
			while (inputStream.hasNextLine ()) {
				String line = inputStream.nextLine();
				temp.add(line);		
			}
			
			if(removeHeader == true) temp.remove(0);
		}  catch (FileNotFoundException e) {
			System.out.println ("Error opening the file " + file);
			System.exit (0);
		}catch(NotEnoughArgumentException e) {
			System.out.println(e);
		}
		inputStream.close();
		return temp;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + targetFileName);
			System.exit(0);
		}
        for (String line : lines) {
            outputStream.println (line);
        }
        outputStream.close();
	}
}