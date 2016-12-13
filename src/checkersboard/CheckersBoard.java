package checkersboard;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CheckersBoard extends Rectangle implements KeyListener {

	private Pieces pieces;

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
		// changeTurns();
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

	public boolean hasPieces() {
		return pieces != null;
	}

	public Pieces getPiece() {
		return pieces;
	}

	public void setPieces(Pieces pieces) {
		this.pieces = pieces;
	}

	public CheckersBoard(boolean light, int x, int y) {
		setWidth(CheckersGame.SQUARE_SIZE);
		setHeight(CheckersGame.SQUARE_SIZE);

		relocate(x * CheckersGame.SQUARE_SIZE, y * CheckersGame.SQUARE_SIZE);

		setFill(light ? Color.valueOf("#ffffff") : Color.valueOf("#000000"));
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
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
