package graphing;

/**
 * Class representing an Edge
 * @author shardool
 *
 */
public class Edge {

	private int i;
	private int w;
	private int from;
	
	/**
	 * Constructor (called automatically by gson when constructing the graph) 
	 * @param i the index of the vertex it connects to
	 * @param w weight of the edge
	 */
	public Edge(int i, int w) {
		this.i = i;
		this.w = i;
	}

	public int getI() {
		return i;
	}
	
	public void setFrom(int vertex)
	{
		this.from = vertex;
	}
	public int other(int vertex) {
		if(vertex==i) return from;
		else return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.i + " " + this.w;
	}

}
