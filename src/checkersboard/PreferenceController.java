package checkersboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.Connector;

public class PreferenceController extends JPanel implements ItemListener,ActionListener{
	private JPanel red,black,diffRed,diffBlack,displayColor;
	private JLabel difficult1,difficult2;
	private JComboBox diffLevelsRed,diffLevelsBlack,chooseRed,chooseBlack;
	private String[] sLevels,controller;
	private JButton hilightColor;
	private File file;

	//Storing preferences
	//only want the numbers!!
	private final String tokenDelim = " \t\n\r\fabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ:,";
	Color hilight;
	int blackPlayer,redPlayer,blackDif,redDif;
	boolean saveToDisk = false;
	boolean changeMade;

	// Network game stuff
	String localUsername,remoteUsername;
	boolean redNetworkPlayer,blackNetworkPlayer;
	Connector connector;
	String ipAddress;
	int port;
	int gameID;

	public PreferenceController(int w, int h){
		setPreferredSize(new Dimension(w,h));

		redNetworkPlayer = false;
		blackNetworkPlayer = false;
		difficult1 = new JLabel("Difficulty Level:");
		difficult2 = new JLabel("Difficulty Level:");
		sLevels = new String[4];
		sLevels[0] = "Choose...   ";
		sLevels[1] = "Ridiculously Easy   ";
		sLevels[2] = "Moderately Easy   ";
		sLevels[3] = "Not Easy  ";

		diffRed = new JPanel();
		diffLevelsRed = new JComboBox(sLevels);
		diffRed.setLayout(new GridLayout(2,1));
		diffRed.add(difficult1);
		diffRed.add(diffLevelsRed);
		diffRed.hide();//Do not show yet

		diffBlack = new JPanel();
		diffLevelsBlack = new JComboBox(sLevels);
		diffBlack.setLayout(new GridLayout(2,1));
		diffBlack.add(difficult2);
		diffBlack.add(diffLevelsBlack);
		diffBlack.hide();

		//Choosing wheather red or black will be controled by
		// the computer or a person
		red = new JPanel();
		red.setBorder(BorderFactory.createTitledBorder("Red Player"));
		black = new JPanel();
		black.setBorder(BorderFactory.createTitledBorder("Black Player"));

		controller = new String[3];
		controller[0] = "Choose...  ";
		controller[1] = "Computer  ";
		controller[2] = "Human  ";

		//Player dimensions------------------
		Dimension d = new Dimension(w,h/4+10);

		chooseRed = new JComboBox(controller);
		chooseRed.addItemListener(this);
		red.setPreferredSize(d);
		red.add(chooseRed);
		red.add(diffRed);
		chooseBlack = new JComboBox(controller);
		chooseBlack.addItemListener(this);
		black.setPreferredSize(d);
		black.add(chooseBlack);
		black.add(diffBlack);

		//---DO the hilight color thing here
		hilightColor = new JButton("Change Hilight Color");
		hilightColor.addActionListener(this);

		displayColor = new JPanel();
		displayColor.setPreferredSize(new Dimension(20,20));
		displayColor.setBackground(hilight);

		add(black);
		add(red);

		add(hilightColor);
		add(displayColor);

		//Load saved values from file
		file = new File("preferences.prf");
		loadValues();
	}

	public void itemStateChanged(ItemEvent event) {
		Object item = event.getItem();
		if(event.getSource() == chooseBlack){
			if(item == controller[0]){
				diffBlack.hide();
			}else if(item == controller[1]){
				diffBlack.setVisible(true);
			}else if(item == controller[2]){
				diffBlack.hide();
			}
		}else if(event.getSource() == chooseRed){
			if(item == controller[0]){
				diffRed.hide();
			}else if(item == controller[1]){
				diffRed.setVisible(true);
			}else if(item == controller[2]){
				diffRed.hide();
			}
		}
		repaint();
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == hilightColor){
			//Open color chooser
			Color tempColor = JColorChooser.showDialog(this,"Choose Hilight Color",hilight);
			if(!tempColor.equals(hilight)){
				//Change in game and save value?
				saveToDisk = true;
				changeMade = true;
				hilight = tempColor;
				displayColor.setBackground(hilight);
			}
		}
	}

	public void savePreferences(){
		if(saveToDisk){//Only if it was ever oked or applied
			blackPlayer = chooseBlack.getSelectedIndex();
			blackDif = diffLevelsBlack.getSelectedIndex();
			redPlayer = chooseRed.getSelectedIndex();
			redDif = diffLevelsRed.getSelectedIndex();
			int r = hilight.getRed();
			int g = hilight.getGreen();
			int b = hilight.getBlue();

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("Black:"+blackPlayer+","+blackDif+"\n");
				writer.write("Red:"+redPlayer+","+redDif+"\n");
				writer.write("Color:"+r+","+g+","+b+"\n");
				writer.close();
			} catch (FileNotFoundException e) {} catch (IOException e) {}
		}
	}

	public void doApply(){
		//computer human stuff is updated on newGame
		//TODO Do not allow changing during the midddle of a game
		boolean showWarning = false;
		if(blackPlayer != chooseBlack.getSelectedIndex()){
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		}else if(redPlayer != chooseRed.getSelectedIndex()){
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		}else if(diffLevelsBlack.isEnabled() && blackDif != diffLevelsBlack.getSelectedIndex()){
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		}else if(diffLevelsRed.isEnabled() && redDif != diffLevelsRed.getSelectedIndex()){
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		}
		if(showWarning)
			JOptionPane.showMessageDialog(this,"Player settings will not take effect until you start a new game","Alert",JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Reset any variables when the preferences windows
	 * is made visible.
	 */
	public void reset(){
		changeMade = false;
	}

	//File Format:
	// black:int,int		blackPlayer		blackDif
	// red:int,int			redPlayer		redDif
	// color:int,int,int	r	g	b
	private void loadValues(){
		if(file.exists()){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				StringTokenizer toke;
				if((line = reader.readLine()) != null){
					toke = new StringTokenizer(line,tokenDelim);
					blackPlayer = Integer.parseInt(toke.nextToken());
					blackDif =Integer.parseInt(toke.nextToken());
				}
				if((line = reader.readLine()) != null){
					toke = new StringTokenizer(line,tokenDelim);
					redPlayer = Integer.parseInt(toke.nextToken());
					redDif =Integer.parseInt(toke.nextToken());
				}
				if((line = reader.readLine()) != null){
					toke = new StringTokenizer(line,tokenDelim);
					Color c = new Color(Integer.parseInt(toke.nextToken()),
							Integer.parseInt(toke.nextToken()),
							Integer.parseInt(toke.nextToken()));
					hilight = c;
				}
				reader.close();
			} catch (FileNotFoundException e){}
			catch (IOException e){}

		}else{
			blackPlayer = 2;
			blackDif = 0;
			redPlayer = 2;
			redDif = 0;
			hilight = Color.BLUE;
		}

		//Updates Please...
		chooseBlack.setSelectedIndex(blackPlayer);
		if(blackPlayer == 1){//Computer player
			diffLevelsBlack.setSelectedIndex(blackDif);
		}
		chooseRed.setSelectedIndex(redPlayer);
		if(redPlayer == 1){//Computer player
			diffLevelsRed.setSelectedIndex(redDif);
		}
		displayColor.setBackground(hilight);

	}

	//Getters and such
	public Color getSelectionColor(){
		return hilight;
	}

	public void newGameApplySettings(){
		blackPlayer = chooseBlack.getSelectedIndex();
		blackDif = diffLevelsBlack.getSelectedIndex();
		redPlayer = chooseRed.getSelectedIndex();
		redDif = diffLevelsRed.getSelectedIndex();
	}

	public boolean isBlackComputer(){
		//System.out.println("isBlackComputer: "+(blackPlayer == 1));
		if(blackPlayer == 1 || isBlackNetworkPlayer())
			return true;
		return false;
	}
	public boolean isRedComputer(){
		//System.out.println("isRedComputer: "+(redPlayer == 1));
		if(redPlayer == 1 || isRedNetworkPlayer())
			return true;
		return false;
	}
	public void setupForNetworkGame(){
		//We dont want our end to become a computer
		redPlayer = 0;
		blackPlayer = 0;
	}
	public boolean isBlackNetworkPlayer(){
		return blackNetworkPlayer;
	}
	public boolean isRedNetworkPlayer(){
		return redNetworkPlayer;
	}
	public void setBlackNetworkPlayer(boolean value){
		blackNetworkPlayer = value;
	}
	public void setRedNetworkPlayer(boolean value){
		redNetworkPlayer = value;
	}
	public int getBlackDifficulty(){
		return blackDif;
	}
	public int getRedDifficulty(){
		return redDif;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}

	public String getRemoteUsername() {
		return remoteUsername;
	}

	public void setRemoteUsername(String remoteUsername) {
		this.remoteUsername = remoteUsername;
	}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void prepareNetworkGame(){
		chooseBlack.setSelectedIndex(2);
		chooseRed.setSelectedIndex(2);
	}

	public void doNetworkConfig(){
		if(getConnector() != null){
			chooseRed.setEnabled(false);
			chooseBlack.setEnabled(false);
		}else{
			chooseRed.setEnabled(true);
			chooseBlack.setEnabled(true);
		}
	}
}
