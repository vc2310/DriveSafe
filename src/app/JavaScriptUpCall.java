package app;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the upcalls made by javascript file map.js based on the user input (clicks) on the google map
 * @author shardool
 *
 */
public class JavaScriptUpCall {

	protected List<double[]> latLng;
	
	/**
	 * Constructor
	 */
	public JavaScriptUpCall() {
		this.latLng = new ArrayList<>();
	}
	
	/**
	 * Called by map.js whenever the user clicks on the map. The call passes the latlng as arguments.
	 * @param ltlng
	 */
	public void changeLatLong(String ltlng)	
	{
		try {
			String[] latlng = ltlng.replaceAll("[()]", "").split(",");
			this.latLng.add(new double[] {Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1])});
		} catch (Exception e) {
			System.err.println(e);
		}
		
		
	}
	
	/**
	 * Get latitude at i
	 * @param i
	 * @return latitude
	 */
	public double getLat(int i)
	{
		return this.latLng.get(i)[0];
	}
	
	/**
	 * Get last latitude
	 * @return latitude
	 */
	public double getLastLat()
	{
		return this.latLng.get(latLng.size()-1)[0];
	}
	
	
	public double getLastLng()
	{
		return this.latLng.get(latLng.size()-1)[1];
	}
	public double getLng(int i)
	{
		return this.latLng.get(i)[1];
	}
	public int length()
	{
		return this.latLng.size();
	}
	public void emptyList()
	{
		this.latLng.clear();
	}
	
	
	
}
