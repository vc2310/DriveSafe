package graphing;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing vertex
 * @author shardool
 *
 */
public class Vertex {
	
	@SerializedName("la")
	private double la;
	@SerializedName("lo")
	private double lo;
	@SerializedName("e")
	private List<Edge> e;
	
	/**
	 * Constructor for vertex (called automatically by gson)
	 */
	public Vertex() {
		// TODO Auto-generated constructor stub
	}
	public double getLa() {
		return la;
	}

	public void setLa(double la) {
		this.la = la;
	}

	public double getLo() {
		return lo;
	}

	public void setLo(double lo) {
		this.lo = lo;
	}

	public List<Edge> getE() {
		return e;
	}

	public void setE(List<Edge> e) {
		this.e = e;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s="";
		
		for (int i=0; i<e.size(); i++)
			s+=e.get(i);
		return this.la + " " + this.lo + " " + s;
	}
}
