package network;


import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import checkersboard.CheckersBoard;
import checkersboard.PreferenceController;

public class ConnectorGUI extends JDialog implements ActionListener{

    private JPanel container;
    private JTextField joinUserName;
    private JTextField joinHostIP;
    private JTextField joinPort;
    private JTextField hostUserName;
    private JTextField hostPort;
    private JRadioButton hostButton;
    private JRadioButton joinButton;
    private JPanel joinPanel;
    private JPanel hostPanel;
    private JButton connectWait;
    private JButton cancel;
    private PreferenceController preferences;
    private CheckersBoard checkerBoard;
    private ConnectionWaiter hostWait;
    private JFrame parent;
	
    public ConnectGUI(JFrame frame,String name, CheckerBoard checkerBoard){
        super(frame,name,true);
        parent = frame;
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.preferences = checkerBoard.getPreferences();
        // Destroy any current connection
        if(preferences.getConnector() != null){
            preferences.getConnector().showDisconnectError(false);
            preferences.getConnector().destroyConnection();
        }
        
        this.checkerBoard = checkerBoard;
        setResizable(false);
        JPanel temp,temp2;
        
        container = new JPanel();
        container.setPreferredSize(new Dimension(450,310));
        container.setLayout(new FlowLayout(FlowLayout.LEFT));
        //container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        
        ButtonGroup group = new ButtonGroup();
        hostButton = new JRadioButton("Host");
        hostButton.addActionListener(this);
        joinButton = new JRadioButton("Join");
        joinButton.addActionListener(this);
        group.add(joinButton);
        group.add(hostButton);        
        
        joinPanel = new JPanel();
        joinPanel.setBorder(BorderFactory.createEtchedBorder());
        joinPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        joinPanel.setPreferredSize(new Dimension(400,100));
///////// Begin JoinUserName        
        temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(80,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Username ");
        temp2.add(label);
        temp.add(temp2,BorderLayout.WEST);
        
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(250,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        joinUserName = new JTextField();
        joinUserName.setColumns(16);
        temp2.add(joinUserName);
        temp.add(temp2,BorderLayout.EAST);
        joinPanel.add(temp);
///////// Begin JoinHostIP        
        temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(80,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Host IP ");
        temp2.add(label);
        temp.add(temp2,BorderLayout.WEST);
        
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(250,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        joinHostIP = new JTextField();
        joinHostIP.setColumns(15);
        temp2.add(joinHostIP);
        temp.add(temp2,BorderLayout.EAST);
        joinPanel.add(temp);
///////// Begin JoinPort       
        temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(80,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Port ");
        temp2.add(label);
        temp.add(temp2,BorderLayout.WEST);
        
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(250,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        joinPort = new JTextField();
        joinPort.setColumns(5);
        temp2.add(joinPort);
        temp.add(temp2,BorderLayout.EAST);
        joinPanel.add(temp);
        
        hostPanel = new JPanel();
        hostPanel.setBorder(BorderFactory.createEtchedBorder());
        hostPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        hostPanel.setPreferredSize(new Dimension(400,70));
///////// Begin HostUserName        
        temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(80,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Username ");
        temp2.add(label);
        temp.add(temp2,BorderLayout.WEST);
        
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(250,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        hostUserName = new JTextField();
        hostUserName.setColumns(16);
        temp2.add(hostUserName);
        temp.add(temp2,BorderLayout.EAST);
        hostPanel.add(temp);
///////// Begin HostPort       
        temp = new JPanel();
        temp.setLayout(new BorderLayout());
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(80,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel("Port ");
        temp2.add(label);
        temp.add(temp2,BorderLayout.WEST);
        
        temp2 = new JPanel();
        temp2.setPreferredSize(new Dimension(250,25));
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        hostPort = new JTextField();
        hostPort.setColumns(5);
        temp2.add(hostPort);
        temp.add(temp2,BorderLayout.EAST);
        hostPanel.add(temp);
        
//////////////////////// Cunstruct the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setPreferredSize(new Dimension(440,70));
        
        connectWait = new JButton();
        connectWait.addActionListener(this);
        connectWait.setText("Connect / Wait");
        buttonPanel.add(connectWait);
        
        cancel = new JButton();
        cancel.addActionListener(this);
        cancel.setText("Cancel");
        buttonPanel.add(cancel);
        
        
///////// Add Join Radio Button        
        temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.LEFT));
        temp.add(joinButton);
        container.add(temp);
////////// Add the join panel  
        temp = new JPanel();
        temp.setPreferredSize(new Dimension(440,105));
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));
        temp.add(joinPanel);
        container.add(temp);
////////// Add the host Raio Button
        temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.LEFT));
        temp.add(hostButton);
        container.add(temp);
//////////Add the join panel  
        temp = new JPanel();
        temp.setPreferredSize(new Dimension(440,75));
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));
        temp.add(hostPanel);
        container.add(temp);
///////// Add the button panel
        container.add(buttonPanel);
        
        getContentPane().add(container);
        
        joinButton.setSelected(true);
        setHostEnabled(false);
       
        //TODO temp
        hostPort.setText("879");
        joinPort.setText("879");
        hostUserName.setText("UserHost");
        joinUserName.setText("UserClient");
        joinHostIP.setText("127.0.0.1");
    }
   
    
    public void show(){
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (parent.getSize().width-w)/2+parent.getLocation().x;
	    int y = (parent.getSize().height-h)/2+parent.getLocation().y;
	    
	    setLocation(x, y);
        super.show();
    }
    
    private void setHostEnabled(boolean value){
        hostUserName.setEnabled(value);
        hostPort.setEnabled(value);
        hostPanel.setEnabled(value);
    }
    
    private void setJoinEnabled(boolean value){
        joinUserName.setEnabled(value);
        joinHostIP.setEnabled(value);
        joinPort.setEnabled(value);
        joinPanel.setEnabled(value);
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == hostButton || event.getSource() == joinButton){
            setHostEnabled(hostButton.isSelected());
            setJoinEnabled(joinButton.isSelected());
        }else if(event.getSource() == connectWait){
            if(validInput()){
	            connectWait.setEnabled(false);
	            if(hostButton.isSelected())
	                setHostEnabled(false);
	            if(joinButton.isSelected())
	                setJoinEnabled(false);
	            joinButton.setEnabled(false);
	            hostButton.setEnabled(false);
                createConnector();
            }
        }else if(event.getSource() == cancel){
            if(hostWait != null){
                cancelHostWait();
            }else{
                checkerBoard.networkCleanUp();
                dispose();
            }
        }
    }

    private void createConnector() {
        
        Connector connector;
        preferences.setGameID(-1);
        if(this.hostButton.isSelected()){
            preferences.setLocalUsername(hostUserName.getText());
            preferences.setGameID((int)(Math.random()*10000+12345));
            preferences.setPort(Integer.parseInt(hostPort.getText()));
            preferences.setRedNetworkPlayer(true);
            preferences.setBlackNetworkPlayer(false);
            connectWait.setText("Waiting...");
            connectWait.disable();
            hostWait = new ConnectionWaiter(checkerBoard);
            hostWait.start();
        }else{
            preferences.setLocalUsername(joinUserName.getText());
            preferences.setPort(Integer.parseInt(joinPort.getText()));
            preferences.setIpAddress(joinHostIP.getText());
            preferences.setRedNetworkPlayer(false);
            preferences.setBlackNetworkPlayer(true);
            connector = new Connector(Connector.CLIENT,checkerBoard);
            preferences.setConnector(connector);
            connector.connect();
            dispose();
        }
    }

    private void showWarning(String message){
        JOptionPane.showMessageDialog(checkerBoard,
                message,
                "Error",
                JOptionPane.WARNING_MESSAGE);
    }

    private boolean validInput() {
        if(hostButton.isSelected()){
            String hp = hostPort.getText();
            String hn = hostUserName.getText();
            if(hp != null && hp.trim().length() > 0){
                if(hn != null && hn.trim().length() > 0){
                    try {
                        int num = Integer.parseInt(hp);
                        if(num < 1 || num > 10000)
                            showWarning("Invalid port number.  Must be between 1-10000");
                        else
                            return true;
                    } catch (NumberFormatException e) {
                        showWarning("Invalid port number.  Must be between 1-10000");
                    }
                }else{
                    showWarning("You must enter a username");
                }
            }else{
                showWarning("You must enter a port number");
            }
        }else{
            String jp = joinPort.getText();
            String jn = joinUserName.getText();
            String jhip = joinHostIP.getText();
            if(jn != null && jn.length() > 0){
                if(jhip != null && jhip.length() > 0){
                    if(jp != null && jp.length() > 0){
                        try {
                            int num = Integer.parseInt(jp);
                            if(num < 1 || num > 10000)
                                showWarning("Invalid port number.  Must be between 1-10000");
                            else
                                return true;
                        } catch (NumberFormatException e) {
                            showWarning("Invalid port number.  Must be between 1-10000");
                        }
                    }else{
                        showWarning("You must enter a port number");
                    }
                } else{
                    showWarning("You must enter a host IP address");
                }
            }else{
                showWarning("You must enter a username");
            }
        }
        return false;
    }
    
    private void startGame(){
        if(hostWait != null && connectWait.getText().equals("Waiting...")){
	        hostWait = null;
	        checkerBoard.networkStartGame();
	        dispose();
        }
    }
    
    private void cancelHostWait(){
        hostWait.stop();
        hostWait = null;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        preferences.getConnector().destroyConnection();
        preferences.setConnector(null);
        joinButton.setEnabled(true);
        hostButton.setEnabled(true);
        setHostEnabled(true);
        connectWait.setText("Connect / Wait");
        connectWait.setEnabled(true);
    }
    
    private class ConnectionWaiter extends Thread{
        private CheckerBoard checkerBoard;

        public ConnectionWaiter(CheckerBoard checkerBoard) {
            this.checkerBoard = checkerBoard;
        }

        public void run(){
            Connector connector = new Connector(Connector.HOST,checkerBoard);
            preferences.setConnector(connector);
            connector.connect();
            startGame();
        }
    }
    

    public void dispose() {
        super.dispose();
    }
}
