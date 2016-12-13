package checkers;

import static checkers.CheckersGame.SQUARE_SIZE;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Pieces extends StackPane {
	
	private PieceType type;
	
	private double mouseX, mouseY;
	private double oldX, oldY;
	
	public PieceType getType() {
		return type;
	}
	
	public double getOldX() {
		return oldX;
	}
	
	public double getOldY() {
		return oldY;
	}
	
	public Pieces (PieceType type, int x, int y) {
		this.type = type;
		
		movement (x, y);
		Ellipse regularPiece = new Ellipse(SQUARE_SIZE * 0.3200, SQUARE_SIZE * 0.30);
		regularPiece.setFill(type == PieceType.REGULAR_RED 
				? Color.valueOf("#a30000") : Color.valueOf("#ffffff"));
		
		regularPiece.setStroke(Color.BLACK);
		regularPiece.setStrokeWidth(SQUARE_SIZE * 0.05); //Outline på brikkene
		
		regularPiece.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3200 * 2) / 2);
		regularPiece.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.30 * 2) / 2);
		

	    private void initializeContent(AnchorPane root) {
	        Image image = new Image(
	                "http://takeinsocialmedia.com/wp-content/uploads/2014/05/landscape-art-painting-wallpaper-images-photos-0517193352.jpg"
	        );
	        ceiling_image = new ImageView(image);
	        ceiling_image.setClip(ceiling);
	        root.getChildren().add(ceiling_image);
	    }
		
		Ellipse kingPiece = new Ellipse (SQUARE_SIZE * 0.3200, SQUARE_SIZE * 0.30);
			kingPiece.setFill(type == PieceType.KING_RED 
					? Color.valueOf("#000000") : Color.valueOf("#000000"));
			
			kingPiece.setStroke(Color.BLACK);
			kingPiece.setStrokeWidth(SQUARE_SIZE * 0.05); //Outline på brikkene
			
			kingPiece.setTranslateX((SQUARE_SIZE - SQUARE_SIZE * 0.3200 * 2) / 2);
			kingPiece.setTranslateY((SQUARE_SIZE - SQUARE_SIZE * 0.30 * 2) / 2);
			
			
		
		getChildren().addAll(kingPiece, regularPiece);
		
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
