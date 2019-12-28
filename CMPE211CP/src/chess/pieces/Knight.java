package chess.pieces;

import static chess.pieces.PieceType.*;

import chess.Game;
import chess.moves.Move;
import chess.moves.RegularMove;

public class Knight extends Piece {

	// game this piece belongs to
	private Game game;

	// color of this piece
	private PieceColor color;

	// locations of this piece
	private int x, y;

	public Knight(PieceColor color, Game game, int x, int y) {
		this.color = color;
		this.game = game;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canMove(int a, int b) {
		if (game.get(a, b) != null && game.get(a, b).color() == color) {
			return false;
		} else if ((Math.abs(a - x) == 2 && Math.abs(b - y) == 1) || (Math.abs(b - y) == 2 && Math.abs(a - x) == 1)) {
			RegularMove move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
		} else {
			return false;
		}
	}

	@Override
	public boolean hasMove() {
		if ((x - 2 >= 0 && y + 1 <= 7 && canMove(x - 2, y + 1)) || (x - 2 >= 0 && y - 1 >= 0 && canMove(x - 2, y - 1))
				|| (x - 1 >= 0 && y + 2 <= 7 && canMove(x - 1, y + 2))
				|| (x - 1 >= 0 && y - 2 >= 0 && canMove(x - 1, y - 2))
				|| (x + 1 <= 7 && y + 2 <= 7 && canMove(x + 1, y + 2))
				|| (x + 1 <= 7 && y - 2 >= 0 && canMove(x + 1, y - 2))
				|| (x + 2 <= 7 && y + 1 <= 7 && canMove(x + 2, y + 1))
				|| (x + 2 <= 7 && y - 1 >= 0 && canMove(x + 2, y - 1))) {
			game.undoMove();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canCapture(int a, int b) {
		return ((Math.abs(a - x) == 2 && Math.abs(b - y) == 1) || (Math.abs(b - y) == 2 && Math.abs(a - x) == 1));
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
		return color.abbrev() + KNIGHT.abbrev();
	}

	@Override
	public PieceColor color() {
		return color;
	}

	@Override
	public PieceType type() {
		return KNIGHT;
	}

}
