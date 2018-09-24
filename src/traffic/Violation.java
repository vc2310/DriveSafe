package traffic;

import java.util.Arrays;

/**
 * Violation ADT
 * @author shardool
 *
 */
public class Violation {

	/*

	 List of columns in the data-set:
	
	 [Date Of Stop, Time Of Stop, Agency, SubAgency, 
	 Description, Location, Latitude, Longitude, Accident,
	 Belts, Personal Injury, Property Damage, Fatal, Commercial License,
	 HAZMAT, Commercial Vehicle, Alcohol, Work Zone, State, VehicleType,
	 Year, Make, Model, Color, Violation Type, Charge, Article, 
	 Contributed To Accident, Race, Gender, Driver City, Driver State, 
	 DL State, Arrest Type, Geolocation]
	 
	 
	 
	 */
	
	
	private String timeOfStop;
	private String agency;
	private String[] latlong;
	private String description;
	private String location;
	private String state;
	private String dateOfViolation;
	
	
	/**
	 * Constructor for violation ADT
	 * @param dateOfViolation
	 * @param timeOfStop
	 * @param agency
	 * @param latlong
	 * @param description
	 * @param location
	 * @param state
	 */
	public Violation(String dateOfViolation, String timeOfStop, String agency, String[] latlong, String description, String location, String state) {
		
		this.dateOfViolation = dateOfViolation;
		this.timeOfStop = timeOfStop;
		this.agency = agency;
		this.latlong = latlong;
		this.description = description;
		this.location = location;
		this.state = state;
		
	}

	/**
	 * @return String time of stop
	 */
	public String getTimeOfStop() {
		return timeOfStop;
	}

	/**
	 * @return String agency violation was reported to
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * @return String[] latlong of where the violation happened
	 */
	public String[] getLatlong() {
		return latlong;
	}

	
	/**
	 * @return String description of violation
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @return String street of where violation happened
	 */
	public String getLocation() {
		return location;
	}
	

	/**
	 * 
	 * @return String violation state
	 */
	public String getState() {
		return state;							
	}
	
	
	/**
	 * 
	 * @return String - date of violation
	 */
	public String getDateOfViolation() {
		return dateOfViolation;
	}

	
	/**
	 * Returns the string representation of the violation 
	 */
	@Override
	public String toString() {

		return this.description + " " + Arrays.toString(this.getLatlong());
	}
	
	
	/**
	 * To write back to the dataset file
	 * @return csv format of the violation object
	 */
	public String csvFormat() {
		return this.getDateOfViolation() + "," +
			   this.getTimeOfStop() + "," +
			   this.getAgency() + "," + "N,A"+ "," +
			   this.getDescription() + "," +
			   this.getLocation() + "," +
			   this.getLatlong()[0] + "," +
			   this.getLatlong()[1] + ",N/A" + ",N/A"  + ",N/A" +
			   ",N/A" + ",N/A"  + ",N/A" +
			   ",N/A" + ",N/A"  + ",N/A" +
			   ",N/A" + ",N/A"  + "," +
			   this.getState() + 
			   "," + ","  + "," +
			   "," + ","  + "," +
			   "," + ","  + "," +
			   "," + ","  + "," +
			   "," + ","  + ",," +
			   "("+this.getLatlong()[0] +"," + this.getLatlong()[1] + ")";
			   
	}
	/*

	 List of columns in the data-set:
	
	 [Date Of Stop, Time Of Stop, Agency, SubAgency, 
	 Description, Location, Latitude, Longitude, Accident,
	 Belts, Personal Injury, Property Damage, Fatal, Commercial License,
	 HAZMAT, Commercial Vehicle, Alcohol, Work Zone, State, VehicleType,
	 Year, Make, Model, Color, Violation Type, Charge, Article, 
	 Contributed To Accident, Race, Gender, Driver City, Driver State, 
	 DL State, Arrest Type, Geolocation]
	 
	 
	 
	 */
}
