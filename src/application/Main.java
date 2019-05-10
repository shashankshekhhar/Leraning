package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	public Main(){}
	
	
	

    @Override
    public void start(Stage stage) throws Exception {
      
        Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        
        
        Scene scene = new Scene(parent,Color.RED);
        scene.setFill(Color.RED);
        stage.setScene(scene);
        
        stage.setTitle("Student Management System");
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}