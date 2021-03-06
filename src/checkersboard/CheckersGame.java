package checkersboard;

import constants.MovementType;
import constants.PieceType;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersGame extends Application {

	public static final int SQUARE_SIZE = 80;
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public int redScore;
	public int blueScore;

	private CheckersBoard[][] board = new CheckersBoard [WIDTH][HEIGHT];

	private Group squareGroup = new Group();
	private Group piecesGroup = new Group();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btnNewGame = new Button("New Game");
		Button btnConcede = new Button("Concede");
		Button btnNetwork = new Button("Network");
		Button btnTutorial = new Button("Tutorial");

		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll( new Separator(), btnNewGame, btnConcede, btnNetwork, btnTutorial);

		BorderPane pane = new BorderPane();

		pane.setTop(toolBar);
		pane.setCenter(createContent());

		Scene scene = new Scene(pane, 640, 675);

		primaryStage.setTitle("Dam spill - OBJ2000 Eksamen 2016");
		primaryStage.setScene(scene);
		primaryStage.show();
	}





	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize (WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);
		root.getChildren().addAll(squareGroup, piecesGroup);
		blueScore =0;
		redScore =0;
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				CheckersBoard checkersBoard = new CheckersBoard ((x + y) % 2 == 0, x, y);
				board[x][y] = checkersBoard;


				squareGroup.getChildren().add(checkersBoard);

				Pieces pieces = null;

				if (y <= 2 && (x + y) % 2 != 0) {
					pieces = makePieces (PieceType.REGULAR_RED, x, y);
				}

				if (y >= 5 && (x + y) % 2 != 0) {
					pieces = makePieces (PieceType.REGULAR_BLUE, x, y);
				}

				if (pieces != null) {
					checkersBoard.setPieces(pieces);
					piecesGroup.getChildren().add(pieces);
				}
			}
		}

		return root;
	}

	private MovementOutcome tryMove(Pieces pieces, int newX, int newY) {
		if (board [newX] [newY].hasPieces() || (newX + newY) % 2 == 0) {
			return new MovementOutcome (MovementType.NONE);
		}

		int x0 = toBoard (pieces.getOldX());
		int y0 = toBoard (pieces.getOldY());

		if (Math.abs(newX - x0) == 1 && newY - y0 == pieces.getType().moveDirection) {

			if(newY==0){
				return new MovementOutcome (MovementType.BLUE_KING);
			}
			if(newY==7){
				return new MovementOutcome (MovementType.RED_KING);
			}
			return new MovementOutcome (MovementType.NORMAL);
		}

		else if (Math.abs(newX - x0) == 2 && newY - y0 == pieces.getType().moveDirection * 2) {

			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;

			if (board [x1] [y1].hasPieces() && board [x1] [y1].getPiece().getType() != pieces.getType()) {
				return new MovementOutcome (MovementType.KILL, board[x1] [y1].getPiece());
			}
		}

		return new MovementOutcome (MovementType.NONE);
	}

	private int toBoard (double pixel) {
		return (int) (pixel + SQUARE_SIZE /2) / SQUARE_SIZE;
	}

	private Pieces makePieces (PieceType type, int x, int y) {
		Pieces pieces = new Pieces(type, x, y);



		pieces.setOnMouseReleased( e -> {
			int newX = toBoard (pieces.getLayoutX());
			int newY = toBoard (pieces.getLayoutY());

			MovementOutcome outcome = tryMove(pieces, newX, newY);

			int oldX = toBoard (pieces.getOldX());
			int oldY = toBoard (pieces.getOldY());





			switch (outcome.getType()) {

				case NONE:
					pieces.cancelMove();
					break;

				case NORMAL:
					pieces.movement (newX, newY);
					board [oldX] [oldY].setPieces(null);
					board [newX] [newY].setPieces(pieces);
					break;

				case KILL:

					pieces.movement (newX, newY);
					board [oldX] [oldY].setPieces(null);
					board [newX] [newY].setPieces(pieces);

					Pieces otherPieces = outcome.getPieces();
					board[toBoard(otherPieces.getOldX())] [toBoard(otherPieces.getOldY())].setPieces(null);
					piecesGroup.getChildren().remove(otherPieces);
					if(type==PieceType.REGULAR_RED){
						redScore = redScore+1;
						System.out.println("Red score: "+redScore);
					}
					if(type==PieceType.KING_RED){
						redScore = redScore+1;
						System.out.println("Red score: "+redScore);
					}
					if(type==PieceType.REGULAR_BLUE){
						blueScore = blueScore+1;
						System.out.println("Blue score: "+blueScore);
					}
					if(type==PieceType.KING_BLUE){
						blueScore = blueScore+1;
						System.out.println("Blue score: "+blueScore);
					}
					if(blueScore==12){
						System.out.println("Blue wins");
					}
					if(redScore==12){
						System.out.println("Red wins");
					}
					break;

				case RED_KING:

					pieces.movement (newX, newY);
					Pieces makeKingsRed = new Pieces(PieceType.KING_RED, newX, newY);
					board [oldX] [oldY].setPieces(null);
					board [newX] [newY].setPieces(pieces);


					piecesGroup.getChildren().remove(pieces);
					piecesGroup.getChildren().add(makeKingsRed);
					break;

				case BLUE_KING:
					pieces.movement (newX, newY);
					Pieces makeKingsBlue = new Pieces(PieceType.KING_BLUE, newX, newY);
					board [oldX] [oldY].setPieces(null);
					board [newX] [newY].setPieces(pieces);


					piecesGroup.getChildren().remove(pieces);
					piecesGroup.getChildren().add(makeKingsBlue);
					break;
			}
		});

		return pieces;
	}


	public static void main(String[] args) {
		launch(args);
	}
}