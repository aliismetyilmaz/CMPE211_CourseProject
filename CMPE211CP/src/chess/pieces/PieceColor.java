package chess.pieces;


public enum PieceColor {
    BLACK, WHITE;

    public String abbrev() {
        switch (this) {
        case BLACK:
            return "b";
        case WHITE:
            return "w";
        default:
            return "-";
        }
    }

    public String string() {
        switch (this) {
        case BLACK:
            return "BLACK";
        case WHITE:
            return "WHITE";
        default:
            return "-";
        }
    }

    public PieceColor opposite() {
        if (this == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public int direction() {
        if (this == WHITE) {
            return -1;
        } else {
            return 1;
        }
    }

}
