package sorting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * Handles the dataset status file. It checks if the dataset has already been sorted or not. 
 * @author Vaibhav
 *
 */
public class HandleSort {
	
	
	/**
	 * Reads the dataset status file to check the sorted status
	 * @param statusFile
	 * @return true if dataset sorted, false otherwise
	 */
	public static boolean isDataSorted(String statusFile) {
		FileReader file;
		
		try {
	
			file = new FileReader(statusFile);
			BufferedReader reader = new BufferedReader(file);
			String sortedInfo = reader.readLine().split(",")[0];
			reader.close();
			return (sortedInfo.equals("sorted:true"));                      
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	/**
	 * Reads the status from the file and returns the string status
	 * @param statusFile
	 * @return status in String format
	 */
	public static String getCurrentStatus(String statusFile) {
		FileReader file;
		
		try {
	
			file = new FileReader(statusFile);
			BufferedReader reader = new BufferedReader(file);
			String sortedInfo = reader.readLine();
			reader.close();
			return sortedInfo;
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Writes back the status given 
	 * @param datasetStatus updated status
	 * @param isSorted 
	 */
	public static void updateStatus(String datasetStatus, boolean isSorted) {
		List<String> lines = new ArrayList<>();
		String lstUpdated = getCurrentStatus(datasetStatus).split(",")[1];
		if(isSorted)
			lines.add("sorted:true,"+lstUpdated);
		else 
			lines.add("sorted:false,"+lstUpdated);
		
		try {
			Files.write(Paths.get(datasetStatus), lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	

	
}
