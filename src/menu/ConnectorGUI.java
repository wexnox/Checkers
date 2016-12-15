package menu;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.text.Text;
import javafx.scene.text.Font.*;
import checkersboard.CheckersBoard;

public class ConnectorGUI extends Application {

    private TextField joinHostIP;
    private TextField joinPort;
    private TextField hostPort;
    private Button hostButton;
    private Button joinButton;
    private Pane joinPanel;
    private Pane hostPanel;
    private Button connectWait;
    private Button cancel;
    private CheckersBoard checkersBoard;
//    private ConnectionWaiter hostWait;
    private Stage stage;
    private Text text;
    private TextField textField;
    private Label label;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane network = new StackPane();
        stage = primaryStage;
        stage.setTitle("Network Setup");
        stage
        Text t = new Text();
        t.setText("Host Port");
//        TextField();
        Label label1 = new Label("Name:");
        TextField textField = new TextField ();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);


//        Button = new Button(joinButton);

    }
}


