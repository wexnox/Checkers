package checkersboard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;

public class CheckersBoard extends Rectangle {

	private Pieces pieces;
	private PreferenceController preferences;

	public boolean hasPieces() {
		return pieces != null;
	}

	public Pieces getPiece() {
		return pieces;
	}

	public void setPieces(Pieces pieces) {
		this.pieces = pieces;
	}

	public CheckersBoard (boolean light, int x, int y) {
		setWidth (CheckersGame.SQUARE_SIZE);
		setHeight (CheckersGame.SQUARE_SIZE);

		relocate(x * CheckersGame.SQUARE_SIZE, y * CheckersGame.SQUARE_SIZE);

		setFill(light ? Color.valueOf("#ffffff") : Color.valueOf("#000000"));
	}
	public void beginGame(){
		//Automatic
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}

		//TODO what about network --> may not be a problem
		if(turn == RED && preferences.isRedComputer()){
			AI.doComputerMove(turn,checkers,preferences.getRedDifficulty());
		}
	}

	public void setBottomControlPane(BottomControlPane bottomPane){
		this.bottomPane = bottomPane;
		bottomPane.displayMessage("Red Starts");
	}

	public void networkDoMove(int fromRow,int fromCol,int toRow,int toCol){
		if((turn == RED && preferences.isRedNetworkPlayer())||(turn == BLACK && preferences.isBlackNetworkPlayer())){
			AI.doAnimatedMove(checkers,fromRow,fromCol,toRow,toCol);
		}
	}

	public void networkDoEndTurn(){
		changeTurns();
		bottomPane.disableTurnButton();
	}

	public void networkSendEndTurn(){
		preferences.getConnector().sendEndTurn();
		changeTurns();
	}

	public void networkSendMove(int fromRow,int fromCol,int toRow,int toCol){
		if(preferences.getConnector() != null){
			preferences.getConnector().sendMove(fromRow+","+fromCol+","+toRow+","+toCol);
		}
	}

	public void networkStartGame() {
		preferences.setupForNetworkGame();
		newGame();
		beginGame();
	}

	public void networkCleanUp(){
		if(preferences.getConnector() != null){
			// Do preferences clean up
			preferences.setLocalUsername(null);
			preferences.setRemoteUsername(null);
			preferences.setGameID(-1);
			preferences.setPort(-1);
			preferences.setRedNetworkPlayer(false);
			preferences.setBlackNetworkPlayer(false);
			preferences.setConnector(null);
		}
	}

	public PreferenceController getPreferences() {
		return preferences;
	}
}












