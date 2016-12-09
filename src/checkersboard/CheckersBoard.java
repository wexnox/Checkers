package checkersboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class CheckersBoard extends JPanel implements KeyListener, MouseListener {

	private PreferenceController preferences;
	private int turn;
	public final int RED = 1, BLACK = 2, STALE_MATE = 3;

	public void beginGame() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	public void newGame() {

	}

	public void networkDoMove(int fromRow, int fromCol, int toRow, int toCol) {
		if (turn == RED && preferences.isRedNetworkPlayer() || (turn == BLACK && preferences.isBlackNetworkPlayer())) {

		}
	}

	public PreferenceController getPreferences() {
		return preferences;
	}

	public void networkDoEndTurn() {
		// changeTurns();
	}

	public void networkSendEndTurn() {
		preferences.getConnector().sendEndTurn();
//		 changeTurns();
	}

	public void networkSendMove(int fromRow, int fromCol, int toRow, int toCol) {
		if (preferences.getConnector() != null) {
			preferences.getConnector().sendMove(fromRow + "," + fromCol + "," + toRow + "," + toCol);
		}
	}

	public void networkStartGame() {
		preferences.setupForNetworkGame();
		newGame();
		beginGame();
	}

	public void networkCleanUp() {
		if (preferences.getConnector() != null) {
			preferences.setLocalUsername(null);
			preferences.setRemoteUsername(null);
			preferences.setGameID(-1);
			preferences.setPort(-1);
			preferences.setRedNetworkPlayer(false);
			preferences.setBlackNetworkPlayer(false);
			preferences.setConnector(null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
