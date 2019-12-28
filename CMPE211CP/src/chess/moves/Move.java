package chess.moves;

import chess.pieces.Piece;

public interface Move {
	// move is a double or not
	boolean isSpecial();

	// undoing Move
	Move undoMove();

	// Returns the selected piece that moved in this move
	Piece movedPiece();

}
