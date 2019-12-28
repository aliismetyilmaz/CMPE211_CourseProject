package chess.pieces;

enum PieceType {
    BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK;

    String abbrev() {
        switch (this) {
        case BISHOP:
            return "bi";
        case KING:
            return "ki";
        case KNIGHT:
            return "kn";
        case PAWN:
            return "pa";
        case QUEEN:
            return "qu";
        case ROOK:
            return "ro";
        default:
            return "-";
        }
    }

}
