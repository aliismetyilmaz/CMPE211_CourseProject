package chess.moves;

import chess.pieces.Piece;

public class RegularMove implements Move {

	private Piece selected;

	private Piece target;

	private Piece replace;

	private int x1, y1;

	private int x2, y2;

	public RegularMove(Piece s, int x1, int y1, Piece t, int x2, int y2) {
		this.selected = s;
		this.x1 = x1;
		this.y1 = y1;
		this.target = t;
		this.x2 = x2;
		this.y2 = y2;
		this.replace = null;
	}

	private RegularMove(Piece s, int x1, int y1, Piece t, int x2, int y2, Piece r) {
		this.selected = s;
		this.x1 = x1;
		this.y1 = y1;
		this.target = t;
		this.x2 = x2;
		this.y2 = y2;
		this.replace = r;
	}

	@Override
	public boolean isSpecial() {
		return false;
	}

	@Override
	public Move undoMove() {
		return new RegularMove(selected, x2, y2, null, x1, y1, target);
	}

	@Override
	public Piece movedPiece() {
		return selected;
	}

	public Piece selected() {
		return selected;
	}

	public Piece target() {
		return target;
	}

	public Piece replace() {
		return replace;
	}

	public int x1() {
		return x1;
	}

	public int y1() {
		return y1;
	}

	public int x2() {
		return x2;
	}

	public int y2() {
		return y2;
	}

}
