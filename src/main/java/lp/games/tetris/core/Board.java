package lp.games.tetris.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private final List<List<Boolean>> grid;
    private final int width;
    private final int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = IntStream.range(0, height)
                .mapToObj(row -> emptyRow())
                .collect(Collectors.toList());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
                    grid.get(y + row).set(x + column, true);
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
                if (piece.isFilled(row, column) && Boolean.TRUE.equals(grid.get(y + row).get(x + column))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return Output.render(grid);
    }

    public int clearRows() {
        int before = grid.size();
        grid.removeIf(row -> !row.contains(Boolean.FALSE));
        int removed = before - grid.size();
        for (int i = 0; i < removed; i++) {
            grid.addFirst(emptyRow());
        }
        return removed;
    }

    private List<Boolean> emptyRow() {
        return IntStream.range(0, width).mapToObj(col -> Boolean.FALSE).collect(Collectors.toCollection(ArrayList::new));
    }
}
