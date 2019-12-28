package chess.GUI;

import static chess.GUI.GameDisplay.*;
//creatori kullanarak pencereyi tasarla
//pencerenin buyuyup kuculebilmesini sagla

import java.awt.event.MouseEvent;
import chess.Game;
import chess.pieces.Piece;

public class ChessGUI extends ChessGUIcreator {
	
	private final GameDisplay display;

    private final Game game;

    //new window with title and displaying game inside
    public ChessGUI(String title, Game game) {
    	super(title, true);
        this.game = game;
        addLabel("ZATRANC \n" + "WHITE's turn.", "turn",new LayoutSpec("y", 0, "x", 0));
        addMenuButton("Quit->are you sure->yes","quit");
        addMenuButton("Game->Undo", "undo");
        addMenuButton("Game->New Game", "newGame");
        display = new GameDisplay(game);
        add(display, new LayoutSpec("y", 2, "width", 2));
        display.setMouseHandler("press", this, "mousePressed");
        display(true);
    }

    public void newGame(String crow) {
        game.newGame();
        repaint(true);
    }

    public void quit(String crow) {
        game.quit();
    }

    public void undo(String crow) {
        game.undoMove();
        game.setSelectedX(-1);
        game.setSelectedY(-1);
        repaint(true);
    }
   
    public void mousePressed(MouseEvent event) {
        if (game.selectedX() == -1) {
            int pressedX = (event.getX() - MARGIN) / CELL;
            int pressedY = (event.getY() - MARGIN) / CELL;
            Piece selected = game.get(pressedX, pressedY);
            if (selected != null && selected.color() == game.turn()) {
                game.setSelectedX(pressedX);
                game.setSelectedY(pressedY);
                display.repaint();
            }
        } else {
            int releasedX = (event.getX() - MARGIN) / CELL;
            int releasedY = (event.getY() - MARGIN) / CELL;
            Piece selected = game.get(game.selectedX(), game.selectedY());
            game.setSelectedX(-1);
            game.setSelectedY(-1);
            repaint(selected.canMove(releasedX, releasedY));
        }
    }

    public void repaint(boolean validMove) {
    	 String label;
         if (validMove) {
             if (game.noMoves()) {
                 if (game.inCheck(game.turn())) {
                     label = "CHECKMATE, " + game.turn().opposite().string()
                         + " wins.";
                 } else {
                     label = "STALEMATE, game ends in draw.";
                 }
             } else {
                 label = game.turn().string() + "'s turn.";
             }
         } else {
             label = "Invalid Move. " + game.turn().string() + "'s turn.";
         }
         setLabel("turn", label);
         display.repaint();
    }

}
