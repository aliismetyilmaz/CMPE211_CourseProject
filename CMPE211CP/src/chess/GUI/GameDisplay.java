package chess.GUI;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import chess.Game;
import chess.pieces.Piece;
import java.io.InputStream;
import java.io.IOException;

public class GameDisplay extends Pad {

	private final Game game;
	// boyutlari
	public static final double MULTIPLIER = 0.7;
	// board
	public static final int BOARD = (int) Math.round(700 * MULTIPLIER);
	// pieces
	public static final int CELL = (int) Math.round(74 * MULTIPLIER);

	public static final int MARGIN = (int) Math.round(53 * MULTIPLIER);

	public GameDisplay(Game game) {
		this.game = game;
		setPreferredSize(BOARD, BOARD);
	}

	private Image getImage(String name) {
		InputStream in = getClass().getResourceAsStream("/chess/images/" + name);
		try {
			return ImageIO.read(in);
		} catch (IOException excp) {
			return null;
		}
	}

	private Image getPieceImage(Piece piece) {
		return getImage("pieces/" + piece.imageString() + ".png");
	}

	private void paintPiece(Graphics2D g, Piece piece, int x, int y) {
		if (piece != null) {
			g.drawImage(getPieceImage(piece), x, y, CELL, CELL, null);
		}
	}

	@Override
	public void paintComponent(Graphics2D g) {
		Rectangle b = g.getClipBounds();
		g.fillRect(0, 0, b.width, b.height);
		g.drawImage(getImage("chessboard.jpg"), 0, 0, BOARD, BOARD, null);
		if (game.inCheck(game.turn())) {
			g.drawImage(getImage("inCheck.png"), CELL * game.kingX(game.turn()) + MARGIN,
					CELL * game.kingY(game.turn()) + MARGIN, CELL, CELL, null);
		}
		if (game.selectedX() != -1) {
			g.drawImage(getImage("selected.png"), CELL * game.selectedX() + MARGIN, CELL * game.selectedY() + MARGIN,
					CELL, CELL, null);
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				paintPiece(g, game.get(i, j), CELL * i + MARGIN, CELL * j + MARGIN);
			}
		}
	}
}
