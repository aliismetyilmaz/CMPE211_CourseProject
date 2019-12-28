package chess.pieces;

import chess.moves.Move;

public abstract class Piece {

    // Returns the string for display of this piece on the GUI
	public abstract String imageString();

    // Returns the color of this piece 
    public abstract PieceColor color();

    // Returns the type of this piece
    public abstract PieceType type();

    // Returns whether the cell at column (a,b) is valid
    public abstract boolean canMove(int a, int b);

    // Returns whether this piece has a valid move
    public abstract boolean hasMove();

    // Returns whether this piece can capture the piece at (a,b)
    public abstract boolean canCapture(int a, int b);
    
    // checks if move leaves king in check
    public abstract boolean checkKing(Move move);

    // Sets the location of the piece on the chessboard 
    public abstract void setLocation(int x, int y);

}
