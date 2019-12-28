package chess.pieces;

import static chess.pieces.PieceType.*;

import chess.Game;
import chess.moves.Move;
import chess.moves.RegularMove;

public class Rook extends Piece {

	// game this piece belongs to
	private Game game;

	// color of this piece
	private PieceColor color;

	// locations of this piece
	private int x, y;

	// whether this rook has been moved or not for castle
	private boolean moved;

	public Rook(PieceColor color, Game game, int x, int y) {
		this.color = color;
		this.game = game;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canMove(int a, int b) {
		if (game.get(a, b) != null && game.get(a, b).color() == color) {
			return false;
		} else if (a == x) {
			int direction = (b - y) / Math.abs(b - y);
			for (int i = y + direction; i != b; i += direction) {
				if (game.get(x, i) != null) {
					return false;
				}
			}
			Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
		} else if (b == y) {
			int direction = (a - x) / Math.abs(a - x);
			for (int i = x + direction; i != a; i += direction) {
				if (game.get(i, y) != null) {
					return false;
				}
			}
			Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
		} else {
			return false;
		}
	}

	@Override
	public boolean hasMove() {
		if ((x + 1 <= 7 && canMove(x + 1, y)) || (x - 1 >= 0 && canMove(x - 1, y)) || (y + 1 <= 7 && canMove(x, y + 1))
				|| (y - 1 >= 0 && canMove(x, y - 1))) {
			game.undoMove();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canCapture(int a, int b) {
		if (a == x) {
			int dir = (b - y) / Math.abs(b - y);
			for (int i = y + dir; i != b; i += dir) {
				if (game.get(x, i) != null) {
					return false;
				}
			}
			return true;
		} else if (b == y) {
			int dir = (a - x) / Math.abs(a - x);
			for (int i = x + dir; i != a; i += dir) {
				if (game.get(i, y) != null) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	// first makes the move
	// returns false if the move leave the king in check and undo that move
	public boolean checkKing(Move move) {
		game.makeMove(move);
		if (game.inCheck(game.turn().opposite())) {
			game.undoMove();
			return false;
		} else {
			moved = true;
			return true;
		}
	}

	public boolean moved() {
		return moved;
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String imageString() {
		return color.abbrev() + ROOK.abbrev();
	}

	@Override
	public PieceColor color() {
		return color;
	}

	@Override
	public PieceType type() {
		return ROOK;
	}

}
