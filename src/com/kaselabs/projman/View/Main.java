package com.kaselabs.projman.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Rick on 6/19/2017.
 */
public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
		primaryStage.setTitle("ProjectManager");
		primaryStage.setScene(new Scene(root, 400, 350));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
