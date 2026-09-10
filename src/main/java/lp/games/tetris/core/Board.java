package lp.games.tetris.core;

public class Board {

    private final boolean[][] fields;

    public Board(int width, int height) {
        fields = new boolean[height][width];
    }

    public int getWidth() {
        return fields[0].length;
    }

    public int getHeight() {
        return fields.length;
    }

    public boolean canPieceMoveAt(Piece piece, int x, int y) {
        return willBeInside(piece, x, y) && areFieldsFree(piece, x, y);
    }

    public void lock(Piece piece, int x, int y) {
        if (!canPieceMoveAt(piece, x, y)) {
            throw new IllegalArgumentException("Dílek se na pozici [" + x + ", " + y + "] nevejde");
        }
        for (int row = 0; row < piece.getHeight(); row++) {
            for (int column = 0; column < piece.getWidth(); column++) {
                if (piece.isFilled(row, column)) {
                    fields[y + row][x + column] = true;
                }
            }
        }
    }

    private boolean willBeInside(Piece piece, int x, int y) {
        return x >= 0
                && y >= 0
                && x + piece.getWidth() <= getWidth()
                && y + piece.getHeight() <= getHeight();
    }

    private boolean areFieldsFree(Piece piece, int x, int y) {
        for (int row = 0; row < piece.getHeight(); row++) {
            for (int column = 0; column < piece.getWidth(); column++) {
                if (piece.isFilled(row, column) && fields[y + row][x + column]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return Output.render(fields);
    }
}
