package network;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javafx.stage.Stage;
import checkersboard.CheckersBoard;
import checkersboard.PreferenceController;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class Connector {

    private Socket socket;
    private ServerSocket serverSocket;
    private CheckersBoard checkersBoard;
    private PreferenceController preferences;

    private int player;
    private boolean showDisconnectError;

    public static final int HOST = 0;
    public static final int CLIENT = 1;

    private final String OP_MOVE = "1";
    private final String OP_ERROR = "2";
    private final String OP_SETUP = "3";
    private final String OP_ENDTURN = "4";

    public static final String DELIMETER = "" + (char) 7;
    private Stage stage;

    private PrintWriter out;
    private BufferedReader in;
    private InputListener inputThread;

    public Connector(int player, CheckersBoard checkersBoard) {
        this.checkersBoard = checkersBoard;
        preferences = checkersBoard.getPreferences();
        this.player = player;
    }

    public void connect() {
        try {
            showDisconnectError = true;
            if (player == HOST) {
                serverSocket = new ServerSocket(preferences.getPort());
                serverSocket.setSoTimeout(0);
                socket = serverSocket.accept();
            } else {
                socket = new Socket(preferences.getIpAddress(), preferences.getPort());
            }
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputThread = new InputListener(in);
            inputThread.start();
            if (player == HOST) {

            } else {

            }
        } catch (UnknownHostException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Unknown host, please check your spelling!");
            alert.showAndWait();
            checkersBoard.networkCleanUp();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("An error occured while connecting to the host!");
            alert.showAndWait();
            checkersBoard.networkCleanUp();
        }
    }

    synchronized private void handleInput(String line) {

        StringTokenizer toke = new StringTokenizer(line, DELIMETER);
        String temp = "";

        temp = toke.nextToken();
        // get the game ID and make sure it's our game;

        temp = toke.nextToken();

        if (temp.equals(OP_ERROR)) {
            doError(toke.nextToken());
        } else if (temp.equals(OP_ENDTURN)) {
            doEndTurn();
        } else {
            doError("Bad Opcode");
        }
    }

    private void doEndTurn() {
        checkersBoard.networkDoEndTurn();
    }

    private void doError(String message) {
        System.err.println(message);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (socket != null) {
            socket.close();
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    class InputListener extends Thread {
        BufferedReader in;

        public InputListener(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("in = " + inputLine);
                    if (inputLine.length() > 0)
                        handleInput(inputLine);
                }
            } catch (IOException e) {
                if (showDisconnectError) {
                    if (player == HOST) {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("The client has been disconnected!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("The client has been disconnected");
                        alert.showAndWait();
                    }
                }
                checkersBoard.networkCleanUp();
            }
        }
    }

    public static void main(String[] args) {
    }
    // closer con
    public void destroyConnection() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        if (out != null) {
            out.close();
        }
        checkersBoard.networkCleanUp();
        preferences.setConnector(null);
    }
    // viser error
    public void showDisconnectError(boolean b) {
        showDisconnectError = b;
    }
}
