package chess.pieces;

import static chess.pieces.PieceType.*;
import chess.Game;
import chess.moves.Move;
import chess.moves.RegularMove;

public class Bishop extends Piece {

	// game this piece belongs to
	private Game game;

	// color of this piece
	private PieceColor color;

	// locations of this piece
	private int x, y;

	public Bishop(PieceColor color, Game game, int x, int y) {
		this.color = color;
		this.game = game;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canMove(int a, int b) {
		if (game.get(a, b) != null && game.get(a, b).color() == color) {
			return false;
		} else if (a + b == x + y) {
			int direction = (a - x) / Math.abs(a - x);// finding direction
			for (int i = x + direction, j = y - direction; i != a; i += direction, j -= direction) {// finding direction
				if (game.get(i, j) != null) {
					return false;
				}
			}
			// bishop cannot have any singleMove
			Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
		} else if (a - b == x - y) {
			int direction = (a - x) / Math.abs(a - x);// finding direction
			for (int i = x + direction, j = y + direction; i != a; i += direction, j += direction) {// finding direction
				if (game.get(i, j) != null) {
					return false;
				}
			}
			// bishop cannot have any singleMove
			Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
		} else {
			return false;
		}
	}

	@Override
	public boolean hasMove() {
		if ((x + 1 <= 7 && y + 1 <= 7 && canMove(x + 1, y + 1)) || (x + 1 <= 7 && y - 1 >= 0 && canMove(x + 1, y - 1))
				|| (x - 1 >= 0 && y + 1 <= 7 && canMove(x - 1, y + 1))
				|| (x - 1 >= 0 && y - 1 >= 0 && canMove(x - 1, y - 1))) {
			game.undoMove();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canCapture(int a, int b) {
		if (a + b == x + y) {
			int dir = (a - x) / Math.abs(a - x);
			for (int i = x + dir, j = y - dir; i != a; i += dir, j -= dir) {
				if (game.get(i, j) != null) {
					return false;
				}
			}
			return true;
		} else if (a - b == x - y) {
			int dir = (a - x) / Math.abs(a - x);
			for (int i = x + dir, j = y + dir; i != a; i += dir, j += dir) {
				if (game.get(i, j) != null) {
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
			return true;
		}
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String imageString() {
		return color.abbrev() + BISHOP.abbrev();
	}

	@Override
	public PieceColor color() {
		return color;
	}

	@Override
	public PieceType type() {
		return BISHOP;
	}

}
