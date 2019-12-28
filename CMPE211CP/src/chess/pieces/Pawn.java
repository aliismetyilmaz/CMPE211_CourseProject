package chess.pieces;
// sadece quenn olabiliyo su an

import static chess.pieces.PieceType.*;

import chess.Game;
import chess.moves.SpecialMove;
import chess.moves.Move;
import chess.moves.RegularMove;

public class Pawn extends Piece {

	// game this piece belongs to
	private Game game;

	// color of this piece
	private PieceColor color;

	// locations of this piece
	private int x, y;

	public Pawn(PieceColor color, Game game, int x, int y) {
		this.color = color;
		this.game = game;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean canMove(int a, int b) {
		if (y == start()) {
			if (b == y + 2 * direction()) {
				if (a == x && game.get(a, y + direction()) == null && game.get(a, b) == null) {
					Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
					return checkKing(move);
				} else {
					return false;
				}
			}
		}
		if (b == y + direction()) {
			if (a == x && game.get(a, b) == null) {
				if (b == start() + 6 * direction()) {
					RegularMove move1 = new RegularMove(this, x, y, game.get(a, b), a, b);
					Piece newQu = new Queen(color, game, a, b);
					RegularMove move2 = new RegularMove(newQu, a, b, this, a, b);
					SpecialMove move = new SpecialMove(move1, move2);
					return checkKing(move);
				} else {
					Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
					return checkKing(move);
				}
			} else if (Math.abs(a - x) == 1 && game.get(a, b) != null && game.get(a, b).color() != color) {
				if (b == start() + 6 * direction()) {
					RegularMove move1 = new RegularMove(this, x, y, game.get(a, b), a, b);
					Piece newQueen = new Queen(color, game, a, b);
					RegularMove move2 = new RegularMove(newQueen, a, b, this, a, b);
					SpecialMove move = new SpecialMove(move1, move2);
					return checkKing(move);
				} else {
					Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
					return checkKing(move);
				}
			} else if (Math.abs(a - x) == 1 && game.get(a, b) == null && y == start() + 3 * direction()
					&& game.get(a, y) != null && game.get(a, y).color() != color && game.get(a, y).type() == PAWN
					&& game.get(a, y) == game.lastMover()) {
				RegularMove move1 = new RegularMove(this, x, y, game.get(a, b), a, b);
				RegularMove move2 = new RegularMove(null, x, y, game.get(a, b - direction()), a, b - direction());
				SpecialMove move = new SpecialMove(move1, move2);
				return checkKing(move);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean hasMove() {
		if ((x + 1 <= 7 && canMove(x + 1, y + 1)) || (canMove(x, y + 1)) || (x - 1 >= 0 && canMove(x - 1, y + 1))) {
			game.undoMove();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canCapture(int a, int b) {
		return (b == y + direction() && Math.abs(a - x) == 1);
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

	// Returns -1 if white and +1 if black
	private int direction() {
		return color.direction();
	}

	// Returns 1 if white and 6 if black
	private int start() {
		if (color == PieceColor.WHITE) {
			return 6;
		} else {
			return 1;
		}
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String imageString() {
		return color.abbrev() + PAWN.abbrev();
	}

	@Override
	public PieceColor color() {
		return color;
	}

	@Override
	public PieceType type() {
		return PAWN;
	}

}
//alah senin belani versin