package menu;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;

public class Toolbar extends JFrame implements ActionListener, MenuListener {

	public Toolbar() {
		setTitle("MenuTest");
		setSize(500, 350);

		JMenuBar mbar = new JMenuBar();
		setJMenuBar(mbar);

		// Filmenyen inneholder deler som kan være enabled/disabled
		Start.addMenuListener(this);

		mbar.add(MenuGraphics.buildVertikalmenu(Start, new Object[] { "New game", "Give up", null, "Quit" }, this));

		helpMenu.setMnemonic('H');

		mbar.add(MenuGraphics.buildVertikalmenu(helpMenu, new Object[] { new JMenuItem("Tutorial") }, this));

		// viser pop-up-meny

		getContentPane().addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger())
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
			}
		});
		show();
	}

	public void actionPerformed(ActionEvent evt) {
		String arg = evt.getActionCommand();
		System.out.println(arg);
		boolean gameInProgress;

		// NEW GAME
		if (arg.equals("New game")) {
			// if (gameInProgress == true) {
			// System.out.println("Finish the current game first!");
			// return;
			// }
			// create new game
		}

		// GIVE UP
		else if (arg.equals("Give up")) {
			// if (gameInProgress == false) {
			// System.out.println("There is no game in progress!");
			// return;
			// }
			// if (currentPlayer == CheckersData.RED) {
			// System.out.println("RED resigned. BLACK wins.");
			// }
			// else {
			// System.out.println("BLACK resigned. RED winds.");
			// }
		}

		// QUIT
		else if (arg.equals("Quit")) {
			System.exit(0);
		}

		// BRUKERMANUAL
		else if (arg.equals("Tutorial")) {
			if (Desktop.isDesktopSupported()) {
				try {
					File Manual = new File("Brukermanual.pdf");
					Desktop.getDesktop().open(Manual);
				} catch (IOException ex) {
				}

			}
		}

	}

	public void menuSelected(MenuEvent evt) {
		newGame.setEnabled(!startNewGame.isSelected());
		tutorial.setEnabled(!startTutorial.isSelected());
		giveUp.setEnabled(!startGiveUp.isSelected());
	}

	public void menuDeselected(MenuEvent evt) {
	}

	public void menuCanceled(MenuEvent evt) {
	}

	public static void main(String[] args) {
		new Toolbar();
	}

	JMenu Start = new JMenu("Start");
	JMenuItem newGame = new JMenuItem("New game");
	JMenuItem tutorial = new JMenuItem("Tutorial");
	JMenuItem giveUp = new JMenuItem("Give up");

	JCheckBoxMenuItem startNewGame = new JCheckBoxMenuItem("New game");
	JCheckBoxMenuItem startTutorial = new JCheckBoxMenuItem("Start tutorial");
	JCheckBoxMenuItem startGiveUp = new JCheckBoxMenuItem("Give up");
	JMenu helpMenu = new JMenu("Help");
	JPopupMenu popup;
}
