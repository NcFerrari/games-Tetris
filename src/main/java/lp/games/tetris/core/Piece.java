package lp.games.tetris.core;

import java.util.stream.IntStream;

public class Piece {

    private final boolean[][] shape;

    private Piece(boolean[][] shape) {
        this.shape = shape;
    }

    public static Piece createT() {
        return new Piece(new boolean[][]{{true, true, true}, {false, true, false}});
    }

    public static Piece createI() {
        return new Piece(new boolean[][]{{true}, {true}, {true}, {true}});
    }

    public static Piece createO() {
        return new Piece(new boolean[][]{{true, true}, {true, true}});
    }

    public static Piece createL() {
        return new Piece(new boolean[][]{{true, false}, {true, false}, {true, true}});
    }

    public static Piece createJ() {
        return new Piece(new boolean[][]{{false, true}, {false, true}, {true, true}});
    }

    public static Piece createS() {
        return new Piece(new boolean[][]{{false, true, true}, {true, true, false}});
    }

    public static Piece createZ() {
        return new Piece(new boolean[][]{{true, true, false}, {false, true, true}});
    }

    public static Piece createU() {
        return new Piece(new boolean[][]{{true, false, true}, {true, true, true}});
    }

    public static Piece createPoint() {
        return new Piece(new boolean[][]{{true}});
    }

    public int getWidth() {
        if (shape.length > 0) {
            return shape[0].length;
        }
        return 0;
    }

    public int getHeight() {
        return shape.length;
    }

    public Piece rotateClockwise() {
        if (shape.length == 0) {
            return this;
        }
        boolean[][] rotatedShape = new boolean[shape[0].length][shape.length];
        IntStream.range(0, rotatedShape.length).forEach(i ->
                IntStream.range(0, rotatedShape[i].length).forEach(j ->
                        rotatedShape[i][j] = shape[rotatedShape[i].length - 1 - j][i]
                )
        );
        return new Piece(rotatedShape);
    }

    public boolean isFilled(int row, int column) {
        return shape[row][column];
    }

    @Override
    public String toString() {
        return Output.render(shape);
    }
}
