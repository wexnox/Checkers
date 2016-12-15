package menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Button button;

    public static void main (String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle("the new button");

        button = new Button("Clicking me hurts me");
        button.setOnAction(event -> confirmBox.display("Title of window", "alter box sucks"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
    }
}
