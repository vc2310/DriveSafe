package app;


import java.io.File;
import java.util.ArrayList;

import dataHandler.CSVParser;
import dataHandler.CSVWriter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sorting.HandleSort;
import sorting.SortList;
//import dataHandler.*;
//import traffic.*;
import traffic.Violation;

/**
 * 
 * Main application class. Extends javafx application to provide a GUI.
 * @author shardool
 *
 */
public class MainApp extends Application {

	private Stage window;
	private Scene startUpScene;
	private Stage loadingStage;
	

	private final static String DATASET = "data/Traffic_Violations.csv"; 
	public final static String DATASET_STATUS = "data/D_status.txt"; 

	
	private static CSVParser parser;
	private static Thread parsingThread;
	private static Thread sortingThread;
	private static Thread writingThread;
	private static ArrayList<Violation> data;
	private static ProgressBar parsingProgress;

	
	public static void main(String[] args) throws Exception{


		//start parser in the background as soon as App starts 
		parser = new CSVParser(DATASET);
		parsingThread = new Thread(parser);

		parsingThread.setPriority(Thread.MAX_PRIORITY);
		parsingThread.setDaemon(true);
		parsingThread.start();

		launch(args);


	}
	
	/**
	 * Starts the javaFX GUI thread
	 */
	@Override
	public void start(Stage mainStage) throws Exception {

		window = mainStage;

		window.setTitle("DriveSafe");
		startUpScene = new Scene(new HBox());
		parsingProgress = new ProgressBar(0);
		parsingProgress.progressProperty().bind(parser.progressProperty());

		parsingProgress.setPrefSize(400, 15);
		
		getLoadingScreen();
		window.setScene(startUpScene);


		parser.setOnSucceeded(e -> {
			data = parser.getValue();

			//if data is not parsed, parse and update the dataset
			if(!HandleSort.isDataSorted(DATASET_STATUS))
			{
				SortList sort = new SortList(data, 0);
				sortingThread = new Thread(sort);
				sortingThread.start();
				try 
				{
					sortingThread.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				parsingProgress.progressProperty().unbind();
				parsingProgress.progressProperty().bind(sort.progressProperty());
				CSVWriter writer = new CSVWriter(data, DATASET);
				writingThread = new Thread(writer);
				writingThread.start();
				HandleSort.updateStatus(DATASET_STATUS, true);

			}

			loadingStage.close();
			showMapScene();
			//window.setFullScreen(true);
			window.getIcons().add(new Image("file:res/appIcon.png"));
			window.setMaximized(true);
			window.show();

			//window.setScene(getNewScene());


		});


	}

	
	/**
	 * Once the parser is done parsing the main map GUI is shown when this method is called
	 */
	public void showMapScene() {
		Map map = new Map();
		GridPane mapView = map.getGooogleMap(data);
		window.setMaximized(true);
		Scene s = new Scene(mapView);
		window.setScene(s);
	}

	/**
	 * Shows the loading screen window
	 */
	public void getLoadingScreen()
	{
		loadingStage = new Stage();
		loadingStage.initStyle(StageStyle.UNDECORATED);
		loadingStage.setScene(getLoadScene());
		loadingStage.sizeToScene();
		loadingStage.show();

	}

	
	/**
	 * Get loading screen 
	 * @return Scene loading javafx scene
	 */
	private Scene getLoadScene()
	{
		Image image = new Image(new File("res/drivesafe.png").toURI().toString());

		GridPane p = new GridPane();
		p.getStyleClass().add("pane2");
		p.setAlignment(Pos.CENTER);
		
		
		
		ImageView iv1 = new ImageView(image);
		iv1.setStyle("-fx-background-color: #7FDBFF");
		
		HBox g = new HBox(parsingProgress);
		g.getStyleClass().add("pane2");
		g.setPadding(new Insets(20));
		g.setAlignment(Pos.CENTER);

		
		
		
		p.add(iv1, 0, 0);
		p.add(g,0,1);
		Scene scene = new Scene(p, 650, 350);

		scene.getStylesheets().add("app.css");
		return scene;

	}
}

