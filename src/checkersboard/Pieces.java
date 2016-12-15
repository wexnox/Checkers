package checkersboard;

import static checkersboard.CheckersGame.SQUARE_SIZE;

import constants.PieceType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

public class Pieces extends StackPane {

	private PieceType type;

	private double mouseX, mouseY;
	private double oldX, oldY;
	private int init = 0;

	public PieceType getType() {
		return type;
	}

	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}

	Image kingRed = new Image("file:kingRed.png");
	Image kingBlue = new Image("file:kingBlue.png");



	public Pieces (PieceType type, int x, int y) {
		init = init+1;
		this.type = type;
		System.out.println(type);

		movement (x, y);
		if(type==PieceType.KING_BLUE){
			System.out.println("BLUE KING");

			Ellipse blueKing = new Ellipse(SQUARE_SIZE * 0.3200, SQUARE_SIZE * 0.30);
			blueKing.setFill(new ImagePattern(kingBlue, 1, 1, 1, 1, true));


			blueKing.setStroke(Color.BLACK);
			blueKing.setStrokeWidth(SQUARE_SIZE * 0.02); //Outline p� brikkene

			blueKing.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3200 * 2) / 2);
			blueKing.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.30 * 2) / 2);
			getChildren().addAll(blueKing);
		}

		else if(type==PieceType.KING_RED){
			System.out.println("RED KING");

			Ellipse redKing = new Ellipse(SQUARE_SIZE * 0.3200, SQUARE_SIZE * 0.30);
			redKing.setFill(new ImagePattern(kingRed, 1, 1, 1, 1, true));


			redKing.setStroke(Color.BLACK);
			redKing.setStrokeWidth(SQUARE_SIZE * 0.02); //Outline p� brikkene

			redKing.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3200 * 2) / 2);
			redKing.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.30 * 2) / 2);
			getChildren().addAll(redKing);
		}

		else {

			Ellipse regularPiece = new Ellipse(SQUARE_SIZE * 0.3200, SQUARE_SIZE * 0.30);
			regularPiece.setFill(type == PieceType.REGULAR_RED
					? Color.valueOf("#a30000") : Color.valueOf("#000f82"));

			regularPiece.setStroke(Color.BLACK);
			regularPiece.setStrokeWidth(SQUARE_SIZE * 0.02); //Outline p� brikkene

			regularPiece.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3200 * 2) / 2);
			regularPiece.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.30 * 2) / 2);


			getChildren().addAll(regularPiece);
		}
		setOnMousePressed (e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});

		setOnMouseDragged (e -> {
			relocate (e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
		});
	}

	public void movement (int x, int y) {
		oldX = x * SQUARE_SIZE;
		oldY = y * SQUARE_SIZE;
		relocate (oldX, oldY);
	}

	public void cancelMove() {
		relocate (oldX, oldY);
	}
}