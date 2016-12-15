package checkersboard;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CheckersBoard extends Rectangle {

	private Pieces pieces;

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
}












