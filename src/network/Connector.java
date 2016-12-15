
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import checkersboard.CheckersBoard;
import checkersboard.PreferenceController;

public class Connector {

    public static final int HOST = 0;
    public static final int CLIENT = 1;

    private final String OP_CHAT = "0";
    private final String OP_MOVE = "1";
    private final String OP_ERROR = "2";
    private final String OP_SETUP = "3";
    private final String OP_ENDTURN = "4";

    public static final String DELIMETER = ""+(char)7;

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private CheckersBoard checkerBoard;
    private PreferenceController preferences;
    private InputListener inputThread;
    private ServerSocket serverSocket;
    private int player;
    private boolean showDisconnectError;

    public Connector(int player,CheckersBoard checkerBoard){
        this.checkersBoard = checkersBoard;
        preferences = checkersBoard.getPreferences();
        this.player = player;
    }

    public void connect(){
        try {
            showDisconnectError = true;
            if(player == HOST){
                serverSocket = new ServerSocket(preferences.getPort());
                serverSocket.setSoTimeout(0);
                socket = serverSocket.accept();
            }else{
                socket = new Socket(preferences.getIpAddress(), preferences.getPort());
            }
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            inputThread = new InputListener(in);
            inputThread.start();
            if(player == HOST){
                sendSetup(preferences.getLocalUsername()+","+preferences.getGameID());
            }else{
                sendSetup(preferences.getLocalUsername()+",0");
            }
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(checkersBoard,
                    "Unknown host, please check your spelling",
                    "Unknown Host",
                    JOptionPane.WARNING_MESSAGE);
            checkerBoard.networkCleanUp();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(checkersBoard,
                    "An error occured while connecting to the host",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            checkerBoard.networkCleanUp();
        }
    }

    synchronized private void handleInput(String line){

        StringTokenizer toke = new StringTokenizer(line,DELIMETER);
        String temp = "";

        temp = toke.nextToken();
        // get the game ID and make sure it's our game;
        if(preferences.getGameID() != Integer.parseInt(temp) && preferences.getGameID() != -1){
            doError("Invalid Game ID, Continue playing at your own risk");
        }

        temp = toke.nextToken();

        if (temp.equals(OP_CHAT)){
            doChat(toke.nextToken());
        }
        else if (temp.endsWith(OP_MOVE)){
            doMove(toke.nextToken());
        }
        else if (temp.equals(OP_ERROR)){
            doError(toke.nextToken());
        }
        else if (temp.equals(OP_SETUP)){
            System.out.println("setup");
            doSetup(toke.nextToken());
        }
        else if (temp.equals(OP_ENDTURN)){
            doEndTurn();
        }
        else {
            doError("Bad Opcode");
        }
    }


    private void doEndTurn() {
        checkersBoard.networkDoEndTurn();
    }

    private void doSetup(String message) {
        StringTokenizer tokens = new StringTokenizer(message,",");
        preferences.setRemoteUsername(tokens.nextToken());
        int id = Integer.parseInt(tokens.nextToken());
        if(id != 0)
            preferences.setGameID(id);
    }

    private void doChat(String message){
        checkersBoard.networkPrintChat(message);
    }

    private void doMove(String message){
        StringTokenizer tokens = new StringTokenizer(message,",");
        int fRow = Integer.parseInt(tokens.nextToken());
        int fCol = Integer.parseInt(tokens.nextToken());
        int row = Integer.parseInt(tokens.nextToken());
        int col = Integer.parseInt(tokens.nextToken());
        checkersBoard.networkDoMove(fRow,fCol,row,col);
    }

    private void doError(String message){
        System.err.println(message);
    }

    private void sendSetup(String message){
        String send = (preferences.getGameID()+DELIMETER+OP_SETUP+DELIMETER+message+"\n");
        out.println(send);
    }

    public void sendChat(String message){
        String send = (preferences.getGameID()+DELIMETER+OP_CHAT+DELIMETER+message+"\n");
        out.println(send);
    }

    public void sendMove(String message){
        System.out.println("Sending Move");
        String send = (preferences.getGameID()+DELIMETER+OP_MOVE+DELIMETER+message+"\n");
        out.println(send);
    }

    public void sendError(String message){
        String send = (preferences.getGameID()+DELIMETER+OP_ERROR+DELIMETER+message+"\n");
        out.println(send);
    }

    public void sendEndTurn() {
        out.println(preferences.getGameID()+DELIMETER+OP_ENDTURN+DELIMETER+"This is an end Turn\n");
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if(serverSocket != null){
            serverSocket.close();
        }
        if(socket != null){
            socket.close();
        }
        if(in != null){
            in.close();
        }
        if(out != null){
            out.close();
        }
    }
    class InputListener extends Thread{
        BufferedReader in;
        public InputListener(BufferedReader in){
            this.in = in;
        }

        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("in = "+inputLine);
                    if(inputLine.length() > 0)
                        handleInput(inputLine);
                }
            } catch (IOException e){
                if(showDisconnectError){
                    if(player == HOST){
                        JOptionPane.showMessageDialog(checkersBoard,
                                "The client has been disconnected",
                                "Lost connection",
                                JOptionPane.WARNING_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(checkersBoard,
                                "The host has been disconnected",
                                "Lost connection",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
                checkersBoard.networkCleanUp();
            }
        }
    }

    public static void main(String[] args) {
    }

    public void destroyConnection() {
        if(serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {}
        }
        if(socket != null){
            try {
                socket.close();
            } catch (IOException e) {}
        }
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {}
        }
        if(out != null){
            out.close();
        }
        checkersBoard.networkCleanUp();
        preferences.setConnector(null);
    }

    public void showDisconnectError(boolean b) {
        showDisconnectError = b;
    }
}
