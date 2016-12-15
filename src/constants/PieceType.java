package constants;

public enum PieceType {
	REGULAR_RED(1),
	REGULAR_BLUE(-1),
	KING_RED(1),
	KING_BLUE(-1);

	public final int moveDirection;

	PieceType (int moveDirection) {
		this.moveDirection = moveDirection;
	}
}
