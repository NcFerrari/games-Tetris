package lp.games.tetris.core;

import java.util.List;

public class Output {

    private static final char FILL_SYMBOL = '#';
    private static final char EMPTY_SYMBOL = '.';
    private static final char SPAWN_SYMBOL = 'X';

    private Output() {

    }

    public static String render(List<List<Boolean>> grid) {
        StringBuilder result = new StringBuilder();
        for (List<Boolean> row : grid) {
            for (boolean field : row) {
                result.append(field ? FILL_SYMBOL : EMPTY_SYMBOL);
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static String gameRender(Board board, Piece piece, Position piecePosition) {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (pieceCovers(piece, piecePosition, x, y)) {
                    result.append(SPAWN_SYMBOL);
                } else if (board.isOccupied(x, y)) {
                    result.append(FILL_SYMBOL);
                } else {
                    result.append(EMPTY_SYMBOL);
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    private static boolean pieceCovers(Piece piece, Position piecePosition, int x, int y) {
        if (piece == null || piecePosition == null) {
            return false;
        }
        int column = x - piecePosition.x();
        int row = y - piecePosition.y();
        boolean insideBox = column >= 0 && column < piece.getWidth()
                && row >= 0 && row < piece.getHeight();
        return insideBox && piece.isFilled(row, column);
    }
}
