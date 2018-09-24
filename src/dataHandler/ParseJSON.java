package dataHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import graphing.Vertex;

/**
 * This class uses external library gson to build graph out of JSON file with nodes and edges
 * @author shardool
 *
 */
public class ParseJSON {

	private Vertex[] graph;

	/**
	 * Constructor parses JSON to construct a graph - array of vertex
	 */
	public ParseJSON() {
		
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		try {
			String data = new String(Files.readAllBytes(Paths.get("data/graph.json")));
			this.graph = g.fromJson(data, Vertex[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get vertices
	 * @return The graph represented by the array of vertex
	 */
	public Vertex[] getVertices()
	{
		return this.graph;
	}
	
}

