package dataHandler;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.concurrent.Task;
import traffic.Violation;


/**
 * This class writes the sorted data to the CSV file: Traffic_Violation.csv under the data directory.
 * This class extends the class Task<?> and therefore has the ability to run in the background without blocking UI updates.
 * 
 */
public class CSVWriter extends Task<Void>{
	
	private ArrayList<Violation> data;
	private String file;
	
	
	public CSVWriter(ArrayList<Violation> data, String file) {
		this.data = data;
		this.file = file;
	}
	
	/**
	 * This method is inherited from the superclass Task<?> and is called whenever an instance of 
	 * CSVWriter is started via: new Thread(writer).start(), where writer is an instance of CSVParser.
	 * Upon successful completion of this method you can call writer.get()
	 * to obtain the ArrayList that the data was stored into.
	 * @throws Exception an unhandled exception which occurred during the background operation
	 */
	@Override
	protected Void call() throws Exception {
		//write data into Traffic_Violation.csv
		
		try {
		
			ArrayList<String> lines = new ArrayList<>();
			for(Violation v: data)
				lines.add(v.csvFormat());
			
			Files.write(Paths.get(file), lines, Charset.forName("UTF-8"));  
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
