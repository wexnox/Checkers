package checkersboard;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.Connector;

//TODO må konverteres til JavaFx

public class PreferenceController extends JPanel implements ItemListener, ActionListener {

	private JPanel red, black, displayColor;
	private JComboBox chooseRed, chooseBlack; 
	private String[] controller;

	private JButton hilightColor;
	private File file;

	private final String tokenDelim = " \t\n\r\fabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ:,";
	Color hilight;
	int blackPlayer, redPlayer;
	boolean saveToDisk = false;
	boolean changeMade;

	String localUsername, remoteUsername; //
	boolean redNetworkPlayer, blackNetworkPlayer; //
	Connector connector;
	String ipAddress;
	int port;
	int gameID;

	public PreferenceController(int w, int h) {
		setPreferredSize(new Dimension(w, h));

		redNetworkPlayer = false;
		blackNetworkPlayer = false;

		red = new JPanel();
		red.setBorder(BorderFactory.createTitledBorder("Red Player"));
		black = new JPanel();
		black.setBorder(BorderFactory.createTitledBorder("Black Player"));

		controller = new String[2];
		controller[0] = "Choose.";
		controller[1] = "Human.";

		Dimension d = new Dimension(w, h / 4 + 10);

		chooseRed = new JComboBox(controller);
		chooseRed.addItemListener(this);
		red.setPreferredSize(d);
		red.add(chooseRed);
		chooseBlack = new JComboBox(controller);
		chooseBlack.addItemListener(this);
		black.setPreferredSize(d);
		black.add(chooseBlack);

		hilightColor = new JButton("Change hilight color");
		hilightColor.addActionListener(this);

		displayColor = new JPanel();
		displayColor.setPreferredSize(new Dimension(20, 20));
		displayColor.setBackground(hilight);

		add(black);
		add(red);
		add(hilightColor);
		add(displayColor);

		file = new File("Preferences.prf");
		loadValues();

	}

	// TODO Må forandres så dersom en velger sort så blir rød borte. ken hende denne er feil
	public void itemStateChanged(ItemEvent event) {
		Object item = event.getItem();
		if (event.getSource() == chooseBlack) {
			if (item == controller[0]) {

			} else if (item == controller[1]) {

			}
		} else if (event.getSource() == chooseRed) {
			if (item == controller[0]) {

			} else if (item == controller[1]) {

			}
		}
		repaint();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == hilightColor) {
			Color tempColor = JColorChooser.showDialog(this, "Choose Hilight Color", hilight);
			if (!tempColor.equals(hilight)) {
				saveToDisk = true;
				changeMade = true;
				hilight = tempColor;
				displayColor.setBackground(hilight);
			}
		}
	}

	public void savePreferences() {
		if (saveToDisk) {
			blackPlayer = chooseBlack.getSelectedIndex();
			redPlayer = chooseRed.getSelectedIndex();
			int r = hilight.getRed();
			int g = hilight.getGreen();
			int b = hilight.getBlue();

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				writer.write("Black:" + blackPlayer + "\n");
				writer.write("Red:" + redPlayer + "\n");
				writer.write("Color:" + r + "," + g + "," + b + "\n");
				writer.close();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			}
		}
	}

	public void doApply() {
		boolean showWarning = false;
		if (blackPlayer != chooseBlack.getSelectedIndex()) {
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		} else if (redPlayer != chooseRed.getSelectedIndex()) {
			showWarning = true;
			changeMade = true;
			saveToDisk = true;
		}
		if (showWarning)
			JOptionPane.showMessageDialog(this, "Player settings will not take effect until you start a new game",
					"Alert", JOptionPane.WARNING_MESSAGE);
	}

	public void reset() {
		changeMade = false;
	}

	private void loadValues() {
		if (file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				StringTokenizer toke;
				if ((line = reader.readLine()) != null) {
					toke = new StringTokenizer(line, tokenDelim);
					blackPlayer = Integer.parseInt(toke.nextToken());
				}
				if ((line = reader.readLine()) != null) {
					toke = new StringTokenizer(line, tokenDelim);
					redPlayer = Integer.parseInt(toke.nextToken());
				}
				if ((line = reader.readLine()) != null) {
					toke = new StringTokenizer(line, tokenDelim);
					Color c = new Color(Integer.parseInt(toke.nextToken()), Integer.parseInt(toke.nextToken()),
							Integer.parseInt(toke.nextToken()));
					hilight = c;
				}
				reader.close();
			} catch (FileNotFoundException e) {

			} catch (IOException e) {

			}
		} else {
			blackPlayer = 2;
			redPlayer = 2;
			hilight = Color.BLUE;
		}
		chooseBlack.setSelectedIndex(blackPlayer);
		chooseRed.setSelectedIndex(redPlayer);
		displayColor.setBackground(hilight);
	}

	public Color getSelcectionColor() {
		return hilight;
	}

	public void newGameApplySettings() {
		blackPlayer = chooseBlack.getSelectedIndex();
		redPlayer = chooseRed.getSelectedIndex();
	}

	public void setupForNetworkGame() {
		redPlayer = 0;
		blackPlayer = 0;
	}

	public boolean isBlackNetworkPlayer() {
		return blackNetworkPlayer;
	}

	public boolean isRedNetworkPlayer() {
		return redNetworkPlayer;
	}

	public void setBlackNetworkPlayer(boolean value) {
		blackNetworkPlayer = value;
	}

	public void setRedNetworkPlayer(boolean value) {
		redNetworkPlayer = value;
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

	public void prepareNetworkGame() {
		chooseBlack.setSelectedIndex(2);
		chooseRed.setSelectedIndex(2);
	}

	public void doNetworkConfig() {
		if (getConnector() != null) {
			chooseRed.setEnabled(false);
			chooseBlack.setEnabled(false);
		} else {
			chooseRed.setEnabled(true);
			chooseBlack.setEnabled(true);
		}
	}

}
