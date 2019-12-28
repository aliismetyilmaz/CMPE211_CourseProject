package chess;

import java.util.List;
import chess.GUI.ChessGUI;
import chess.moves.SpecialMove;
import chess.moves.Move;
import chess.moves.RegularMove;
import chess.pieces.*;
import static chess.pieces.PieceColor.*;

import java.util.ArrayList;

public class Game {
	
    private Piece[][] board;
    private ChessGUI gui;
    private PieceColor turn;
    private List<Move> moves; //to store moves
    private King blackKi,whiteKi;
    private int selectedX,selectedY;

    Game() {
        moves = new ArrayList<Move>();
        gui = new ChessGUI("Chess", this);
        newGame();
    }

    public void newGame() {
        initializeBoard();
        moves.clear();
        turn = WHITE;
        selectedX = -1;
        selectedY = -1;
    }

    public void quit() {
        System.exit(0);
    }
    
    public void undoMove() {
        if (moves.size() > 0) {
            Move lastMove = moves.remove(moves.size() - 1);
            makeMove(lastMove.undoMove());
            moves.remove(moves.size() - 1);
        }
    }

    public void makeMove(Move move) {
        moves.add(move);
        if (!move.isSpecial()) {
            RegularMove regularmove = (RegularMove) move;
            performMove(regularmove);
        } else {
            SpecialMove specialmove = (SpecialMove) move;
            performMove(specialmove.move1());
            performMove(specialmove.move2());
        }
        turn = turn.opposite();
    }

    private void performMove(RegularMove move) {
        board[move.x1()][move.y1()] = move.replace();
        if (move.replace() != null) {
            move.replace().setLocation(move.x1(), move.y1());
        }
        board[move.x2()][move.y2()] = move.selected();
        if (move.selected() != null) {
            move.selected().setLocation(move.x2(), move.y2());
        }
        if (move.target() != null) {
            move.target().setLocation(-1, -1);
        }
    }

    // checking the king is being checked 
    public boolean inCheck(PieceColor color) {
        int x = kingX(color);
        int y = kingY(color);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = get(i, j);
                if (p != null && p.color() == color.opposite()
                    && p.canCapture(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    // checking the player to play has no valid moves
    public boolean noMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = get(i, j);
                if (p != null && p.color() == turn) {
                    if (p.hasMove()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean guarded(int x, int y) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = get(i, j);
                if (p != null && p.color() == turn.opposite()
                    && p.canCapture(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Piece get(int i, int j) {
        return board[i][j];
    }

    // Returns the last piece that moved in the game used in pawn class
    public Piece lastMover() {
        return moves.get(moves.size() - 1).movedPiece();
    }

    private void initializeBoard() {
        Piece blackRo0 = new Rook(BLACK, this, 0, 0);
        Piece blackKn0 = new Knight(BLACK, this, 1, 0);
        Piece blackBi0 = new Bishop(BLACK, this, 2, 0);
        Piece blackQu0 = new Queen(BLACK, this, 3, 0);
              blackKi = new King(BLACK, this, 4, 0);
        Piece blackBi1 = new Bishop(BLACK, this, 5, 0);
        Piece blackKn1 = new Knight(BLACK, this, 6, 0);
        Piece blackRo1 = new Rook(BLACK, this, 7, 0);
        Piece blackPa0 = new Pawn(BLACK, this, 0, 1);
        Piece blackPa1 = new Pawn(BLACK, this, 1, 1);
        Piece blackPa2 = new Pawn(BLACK, this, 2, 1);
        Piece blackPa3 = new Pawn(BLACK, this, 3, 1);
        Piece blackPa4 = new Pawn(BLACK, this, 4, 1);
        Piece blackPa5 = new Pawn(BLACK, this, 5, 1);
        Piece blackPa6 = new Pawn(BLACK, this, 6, 1);
        Piece blackPa7 = new Pawn(BLACK, this, 7, 1);
        
        Piece whiteRo0 = new Rook(WHITE, this, 0, 7);
        Piece whiteKn0 = new Knight(WHITE, this, 1, 7);
        Piece whiteBi0 = new Bishop(WHITE, this, 2, 7);
        Piece whiteQu0 = new Queen(WHITE, this, 3, 7);
              whiteKi = new King(WHITE, this, 4, 7);
        Piece whiteBi1 = new Bishop(WHITE, this, 5, 7);
        Piece whiteKn1 = new Knight(WHITE, this, 6, 7);
        Piece whiteRo1 = new Rook(WHITE, this, 7, 7);
        Piece whitePa0 = new Pawn(WHITE, this, 0, 6);
        Piece whitePa1 = new Pawn(WHITE, this, 1, 6);
        Piece whitePa2 = new Pawn(WHITE, this, 2, 6);
        Piece whitePa3 = new Pawn(WHITE, this, 3, 6);
        Piece whitePa4 = new Pawn(WHITE, this, 4, 6);
        Piece whitePa5 = new Pawn(WHITE, this, 5, 6);
        Piece whitePa6 = new Pawn(WHITE, this, 6, 6);
        Piece whitePa7 = new Pawn(WHITE, this, 7, 6);
        //create the board
        Piece[][] newBoard = {
            {blackRo0, blackPa0, null, null, null, null, whitePa0, whiteRo0},
            {blackKn0, blackPa1, null, null, null, null, whitePa1, whiteKn0},
            {blackBi0, blackPa2, null, null, null, null, whitePa2, whiteBi0},
            {blackQu0, blackPa3, null, null, null, null, whitePa3, whiteQu0},
            {blackKi, blackPa4, null, null, null, null, whitePa4, whiteKi},
            {blackBi1, blackPa5, null, null, null, null, whitePa5, whiteBi1},
            {blackKn1, blackPa6, null, null, null, null, whitePa6, whiteKn1},
            {blackRo1, blackPa7, null, null, null, null, whitePa7, whiteRo1} };
        board = newBoard;
    }

    // x-location of the king 
    public int kingX(PieceColor color) {
        if (color == WHITE) {
            return whiteKi.getX();
        } else {
            return blackKi.getX();
        }
    }

    // y-location of the king 
    public int kingY(PieceColor color) {
        if (color == WHITE) {
            return whiteKi.getY();
        } else {
            return blackKi.getY();
        }
    }

    // x-location of the selected piece 
    public void setSelectedX(int x) {
        selectedX = x;
    }

    // y-location of the selected piece 
    public void setSelectedY(int y) {
        selectedY = y;
    }

    public int selectedX() {
        return selectedX;
    }

    public int selectedY() {
        return selectedY;
    }

    public Piece[][] board() {
        return board;
    }

    public PieceColor turn() {
        return turn;
    }

}
