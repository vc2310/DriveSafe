package app;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import dataHandler.BoundBox;
import dataHandler.ParseJSON;
import graphing.DijkstraUndirectedSP;
import graphing.Edge;
import graphing.Graph;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import traffic.Violation;

/**
 * The Map class provides a google map and the basic control of the application, i.e 
 * it handles user requests and updates the application appropriately.
 * @author shardool
 *
 */
public class Map {


	private double bounds[];
	private static boolean graphMode;

/**
 * This method returns the main view of the application. The majority of the view is a google map view embedded using
 * javafx's webview class. The google map is obtained from googlemap.html and map.js, which together provide the
 * interactive ability for users to control what they see on the map. 
 * The view ,in addition to google maps, also provides control buttons for switching between modes.
 * @param data The parsed data of violation objects
 * @return The GridPane view of the google map 
 */
	public GridPane getGooogleMap(ArrayList<Violation> data){

		graphMode = false;
		this.bounds = new double[4];
		GridPane mapPane = new GridPane();


		HashMap<String, Integer> trafficData = hashData(data);
		ParseJSON parse = new ParseJSON();
		Graph graph = new Graph(parse.getVertices());

		Graph.addViolationWeight(graph,  trafficData);
		

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();


		final URL googleMap = getClass().getResource("googlemap.html");


		ProgressBar prog = new ProgressBar(0);
		prog.setPadding(new Insets(15,0,0,44));
		prog.setPrefWidth(400);
		prog.progressProperty().unbind();
		prog.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
		webEngine.load(googleMap.toExternalForm());

	


		webEngine.setJavaScriptEnabled(true);
		JavaScriptUpCall jsObj = new JavaScriptUpCall();

		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<Worker.State>() {
					public void changed(ObservableValue<? extends State> ov, Worker.State oldState, Worker.State newState) {
						if (newState == Worker.State.SUCCEEDED) {                
							JSObject win = (JSObject) webEngine.executeScript("window");
							win.setMember("app",  jsObj);
						}

					}
				});
		webEngine.setOnError(e -> {
			System.out.println(e);
		});



		Slider slider = new Slider();
		slider.setMaxHeight(100);
		slider.setPrefWidth(300);
		slider.setShowTickMarks(true);



		//change bounding box when slider is dragged
		slider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasChanging, Boolean isNowChanging) {
				if (!isNowChanging) {
					double lat = jsObj.getLastLat();
					double lng = jsObj.getLastLng();
					if(jsObj.length()>=2) jsObj.emptyList(); 
					
					double lat1 = lat-(0.001*slider.getValue());
					double lng1 = lng-(0.001*slider.getValue());
					double lat2 = lat+(0.001*slider.getValue());
					double lng2 = lng+(0.001*slider.getValue());
					setBounds(lat1, lng1, lat2, lng2);
					if(!graphMode)
					{
						
						webEngine.executeScript("" +
								"window.x1 = " +  (lat1)  + ";" +
								"window.y1 = " +  (lng1)  + ";" +
								"window.x2 = " +  (lat2)  + ";" +
								"window.y2 = " +  (lng2)  + ";" +
								"showBox(window.x1, window.y1, window.x2, window.y2);"
								);
					}
					
				}
			}
		});

		
		Button toggleGraphMode = new Button("Mode : HeatMap");
		toggleGraphMode.setTooltip(new Tooltip("Click to switch modes"));
		
		
		

		GridPane controls = new GridPane();
		controls.setPadding(new Insets(10,20,10,10));
		
		controls.add(slider, 2, 0);
		controls.add(toggleGraphMode, 0 , 0);
		
		
		
		Button find = new Button("find");
	
		GridPane.setHalignment(find, HPos.RIGHT);
		find.setOnAction(e->{
			int startLoc = graph.getVertexID(Double.toString(jsObj.getLat(0)), Double.toString(jsObj.getLng(0)));
			int endLoc = graph.getVertexID(Double.toString(jsObj.getLat(1)), Double.toString(jsObj.getLng(1)));
			if(startLoc == -1 || endLoc == -1)
			{
				//reset google map
				webEngine.executeScript("clearMap();");
				webEngine.executeScript("toggleGraphMode();");
				webEngine.executeScript("toggleGraphMode();");
				jsObj.emptyList();
				
				//show error message if path not found
				Stage err = new Stage();
				err.initModality(Modality.APPLICATION_MODAL);
				err.setScene(new Scene(new Button("Path not Found")));
				err.show();
				return;
			}
			DijkstraUndirectedSP sp = new DijkstraUndirectedSP(graph, startLoc);
			webEngine.executeScript("clearPathPoints();");
			for(Edge edge: sp.pathTo(endLoc))
			{
				webEngine.executeScript("" +
						"window.lat = " +  graph.getVertex(edge.getI()).getLa() /*loc.lat*/ + ";" +
						"window.lon = " +  graph.getVertex(edge.getI()).getLo() /*loc.lon*/ + ";"+
						"addPathPoints(window.lat, window.lon);"
						);
			}
			webEngine.executeScript("showLine();");
			jsObj.emptyList();
			
		});




		Button showHeatMap = new Button("Show HeatMap");
		controls.add(showHeatMap, 1, 0);
		
		showHeatMap.setOnAction(e->{
			ArrayList<Violation> t = null; //fix
			if(bounds[0]!=0) {
				BoundBox b = new BoundBox();
				t = b.Bounding(data, bounds[0], bounds[2], bounds[1], bounds[3]);
				webEngine.executeScript("clearDataPoints();");
			}
			
			for(Violation v: t) {
				webEngine.executeScript("" +
						"window.lat = " + v.getLatlong()[0] /*loc.lat*/ + ";" +
						"window.lon = " + v.getLatlong()[1]/*loc.lon*/ + ";"+
						"addDataPoint(window.lat, window.lon);"
						);			}
			
			webEngine.executeScript("showHeatMap();");
			webEngine.executeScript("" +
					"window.lat = " + graph.getVertex(0).getLa()/*loc.lat*/ + ";" +
					"window.lon = " + graph.getVertex(0).getLo()/*loc.lon*/ + ";"+
					"addPathPoints(window.lat, window.lon);"
					);
			
			
		});
		
		//add help button if user need to know how to use the app
		Button help = new Button("Help");
		help.setOnAction(e->{
			Stage helpS = new Stage();
			Text helpInfo;
			
			if(graphMode)
			helpInfo = new Text("Click on the map to drop two markers representating the starting location"
					+ "and the destination location. Click find Path to find the path between these markers");
			else
				helpInfo = new Text("Click on the map to drop a marker and adjust the bounding box."
						+ "Click on show heatmap button to show the rates of violations in this area.");
			helpInfo.setWrappingWidth(200);
			helpInfo.setTextAlignment(TextAlignment.CENTER);
			helpS.setScene(new Scene(new HBox(helpInfo)));
			
			helpS.show();
			helpS.initModality(Modality.WINDOW_MODAL);
		});
		
		controls.add(help,3,0);
		GridPane.setMargin(help, new Insets(0,0,0,30));
		GridPane.setMargin(find, new Insets(0,0,0,20));
		GridPane.setMargin(showHeatMap, new Insets(0,0,0,20));
		GridPane.setMargin(slider, new Insets(0,5,0,1230));
		
		//toggle between modes
		toggleGraphMode.setOnAction(e->{
			if(!graphMode)
				{
				toggleGraphMode.setText("Mode : PathFind");
				controls.getChildren().remove(slider);
				controls.getChildren().remove(showHeatMap);
				controls.add(find, 1, 0);
				}
			else
			{
				toggleGraphMode.setText("Mode : HeatMap");
				controls.add(slider, 2, 0);
				controls.add(showHeatMap, 1, 0);
				controls.getChildren().remove(find);
				
			}
				
			
			graphMode=(!graphMode);
			jsObj.emptyList();
			webEngine.executeScript("toggleGraphMode();");
			
		});
		controls.setPrefHeight(50);
		
		GridPane.setHgrow(webView, Priority.ALWAYS);
		GridPane.setVgrow(webView, Priority.ALWAYS);
		mapPane.add(controls, 0, 0);
		mapPane.add(webView, 0, 1);
		mapPane.setPadding(new Insets(0));
		mapPane.setStyle("-fx-background-color: #242424");


		return mapPane;

	}



/**
 * Helper method that stores the RATE of violation based on the latlng (upto 3 decimal points).
 * This helps the application to access the rates near an area in constant time
 * @param data
 * @return HashMap<String, Integer> count of violations in the latlng specified by the string. 
 */
	private HashMap<String, Integer> hashData(ArrayList<Violation> data) {
		HashMap<String, Integer> latlngViolations = new HashMap<>();
		for(Violation v: data)
		{ 
			String[] latLng = v.getLatlong();
			String key= "";
			key = latLng[0].trim().substring(0, Math.min(latLng[0].length(), 6)) + latLng[1].trim().substring(0, Math.min(latLng[1].length()-1, 7));
			if(latlngViolations.containsKey(key))
				latlngViolations.put(key, latlngViolations.get(key)+1);
			else
				latlngViolations.put(key, 1);
		}
		return latlngViolations;
	}



/**
 * Stores the bounds for the bounding box 
 * @param lat1 left corner latitude
 * @param lng1 left corner longitude
 * @param lat2 right corner latitude
 * @param lng2 right corner longitude
 */
	private void setBounds(double lat1, double lng1, double lat2, double lng2)
	{
		this.bounds[0] = lat1;
		this.bounds[1] = lng1;
		this.bounds[2] = lat2;
		this.bounds[3] = lng2; 
	}


	


}
