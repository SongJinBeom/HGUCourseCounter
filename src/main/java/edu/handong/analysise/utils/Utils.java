package edu.handong.analysise.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.PrintWriter;

public class Utils {
	public static ArrayList<CSVRecord> getLines(String file, boolean removeHeader) {

		ArrayList<CSVRecord> tempCSVarray = new ArrayList<CSVRecord>();

		FileReader fr = null;
		CSVParser csvFP = null;
		CSVFormat csvFF = CSVFormat.EXCEL.withIgnoreEmptyLines(true).withTrim();

		try {
			fr = new FileReader(file);
			csvFP = csvFF.parse(fr);
		} catch(Exception e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
		}
		Iterator<CSVRecord> iter = csvFP.iterator();
		try {
			while (iter.hasNext()) {
			tempCSVarray.add(iter.next());
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("iter.hasNext() exception");
		}
		if (removeHeader == true) {
			tempCSVarray.remove(0);
		}

		try {
			fr.close();
		} catch (Exception e) {

		}

		return tempCSVarray;

	}

	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + targetFileName);
			System.exit(0);
		}
		for (String line : lines) {
			outputStream.println(line);
		}
		outputStream.close();
	}
}