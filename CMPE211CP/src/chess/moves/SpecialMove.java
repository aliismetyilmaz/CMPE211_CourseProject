package chess.moves;

import chess.pieces.Piece;

public class SpecialMove implements Move {

	private RegularMove move1, move2;

	public SpecialMove(RegularMove move1, RegularMove move2) {
		this.move1 = move1;
		this.move2 = move2;
	}

	@Override
	public boolean isSpecial() {
		return true;
	}

	@Override
	public Move undoMove() {
		return new SpecialMove((RegularMove) move2.undoMove(), (RegularMove) move1.undoMove());
	}

	@Override
	public Piece movedPiece() {
		return move1.movedPiece();
	}

	public RegularMove move1() {
		return move1;
	}

	public RegularMove move2() {
		return move2;
	}
}
