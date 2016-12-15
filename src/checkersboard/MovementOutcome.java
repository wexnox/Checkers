package checkersboard;

import constants.MovementType;


public class MovementOutcome {

	private MovementType type;

	public MovementType getType() {

		return type;

	}

	private Pieces pieces;

	public Pieces getPieces() {
		return pieces;
	}

	public MovementOutcome (MovementType type) {
		this (type, null);
	}


	public MovementOutcome (MovementType type, Pieces pieces) {
		this.type = type;
		this.pieces = pieces;
	}

}

