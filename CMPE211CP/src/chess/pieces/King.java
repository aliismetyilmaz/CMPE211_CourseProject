package chess.pieces;

import static chess.pieces.PieceType.*;

import chess.Game;
import chess.moves.SpecialMove;
import chess.moves.Move;
import chess.moves.RegularMove;

public class King extends Piece {

	// game this piece belongs to
	private Game game;

	// color of this piece
	private PieceColor color;

	// locations of this piece
	private int x, y;

	// whether this king has been moved or not for castle
	private boolean moved;

	public King(PieceColor color, Game game, int x, int y) {
		this.color = color;
		this.game = game;
		this.x = x;
		this.y = y;
		this.moved = false;
	}

	@Override
	public boolean canMove(int a, int b) {
		if (Math.abs(a - x) <= 1 && Math.abs(b - y) <= 1
				&& (game.get(a, b) == null || game.get(a, b).color() != color)) {
			Move move = new RegularMove(this, x, y, game.get(a, b), a, b);
			return checkKing(move);
			// if castle is possible with rigth rook
		} else if (x == originalX() && y == originalY() && a == originalX() + 2 && b == originalY()
				&& game.get(a - 1, b) == null && game.get(a, b) == null && game.get(a + 1, b) != null
				&& game.get(a + 1, b).type() == ROOK && game.get(a + 1, b).color() == color && !game.inCheck(color)
				&& !game.guarded(a - 1, b) && !game.guarded(a, b) && !moved && !((Rook) game.get(a + 1, b)).moved()) {
			RegularMove move1 = new RegularMove(this, x, y, game.get(a, b), a, b);
			RegularMove move2 = new RegularMove(game.get(a + 1, b), a + 1, b, null, a - 1, b);
			SpecialMove move = new SpecialMove(move1, move2);
			return checkKing(move);
			// if castle is possible with left rook
		} else if (x == originalX() && y == originalY() && a == originalX() - 2 && b == originalY()
				&& game.get(a - 1, b) == null && game.get(a, b) == null && game.get(a + 1, b) == null
				&& game.get(a - 2, b) != null && game.get(a - 2, b).type() == ROOK
				&& game.get(a - 2, b).color() == color && !game.inCheck(color) && !game.guarded(a - 1, b)
				&& !game.guarded(a, b) && !moved && !((Rook) game.get(a - 2, b)).moved()) {
			RegularMove move1 = new RegularMove(this, x, y, game.get(a, b), a, b);
			RegularMove move2 = new RegularMove(game.get(a - 2, b), a - 2, b, null, a + 1, b);
			SpecialMove move = new SpecialMove(move1, move2);
			return checkKing(move);
		} else {
			return false;
		}
	}

	@Override
	public boolean hasMove() {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x + i >= 0 && x + i <= 7 && y + j >= 0 && y + j <= 7) {
					if (canMove(x + i, y + j)) {
						game.undoMove();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canCapture(int a, int b) {
		return Math.abs(a - x) <= 1 && Math.abs(b - y) <= 1;
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// Returns the first x-location of the king
	private int originalX() {
		return 4;
	}

	// Returns the first y-location of the king
	private int originalY() {
		if (color == PieceColor.WHITE) {
			return 7;
		} else {
			return 0;
		}
	}

	@Override
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String imageString() {
		return color.abbrev() + KING.abbrev();
	}

	@Override
	public PieceColor color() {
		return color;
	}

	@Override
	public PieceType type() {
		return KING;
	}

}
