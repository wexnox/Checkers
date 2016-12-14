package constants;

public enum PieceType {
	REGULAR_RED(1),
	REGULAR_WHITE(-1),
	KING_RED(2),
	KING_WHITE(-2);
	
	public final int moveDirection;
	
	PieceType (int moveDirection) {
		this.moveDirection = moveDirection;
	}
}
