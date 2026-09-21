package lp.games.tetris.core;

import lombok.Getter;
import lp.games.tetris.common.ShapeType;

import java.util.ArrayList;
import java.util.List;

public class Piece {

    private static final char FILL_SYMBOL = '#';
    private final List<List<Boolean>> shape;
    @Getter
    private final ShapeType shapeType;
    @Getter
    private final int width;
    @Getter
    private final int height;

    private Piece(String graphicShape, ShapeType shapeType) {
        this.shapeType = shapeType;
        shape = new ArrayList<>();
        String[] splitGraphicShape = graphicShape.split("\n");
        height = splitGraphicShape.length;
        width = splitGraphicShape[0].length();
        for (int i = 0; i < height; i++) {
            String splitLine = splitGraphicShape[i];
            List<Boolean> symbols = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if (FILL_SYMBOL == splitLine.charAt(j)) {
                    symbols.add(Boolean.TRUE);
                } else {
                    symbols.add(Boolean.FALSE);
                }
            }
            shape.add(symbols);
        }
    }

    private Piece(List<List<Boolean>> shape, ShapeType shapeType) {
        this.shape = shape;
        this.shapeType = shapeType;
        height = shape.size();
        width = shape.getFirst().size();
    }

    public static Piece createT() {
        return new Piece("""
                ###
                .#.
                """, ShapeType.T);
    }

    public static Piece createI() {
        return new Piece("""
                #
                #
                #
                #
                """, ShapeType.I);
    }

    public static Piece createO() {
        return new Piece("""
                ##
                ##
                """, ShapeType.O);
    }

    public static Piece createL() {
        return new Piece("""
                #.
                #.
                ##
                """, ShapeType.L);
    }

    public static Piece createJ() {
        return new Piece("""
                .#
                .#
                ##
                """, ShapeType.J);
    }

    public static Piece createS() {
        return new Piece("""
                .##
                ##.
                """, ShapeType.S);
    }

    public static Piece createZ() {
        return new Piece("""
                ##.
                .##
                """, ShapeType.Z);
    }

    public static Piece createU() {
        return new Piece("""
                #.#
                ###
                """, ShapeType.U);
    }

    public static Piece createPoint() {
        return new Piece("""
                #
                """, ShapeType.POINT);
    }

    public Piece rotateClockwise() {
        List<List<Boolean>> rotated = new ArrayList<>();
        for (int column = 0; column < width; column++) {
            List<Boolean> newRow = new ArrayList<>();
            for (int row = 0; row < height; row++) {
                newRow.add(shape.get(height - 1 - row).get(column));
            }
            rotated.add(newRow);
        }
        return new Piece(rotated, getShapeType());
    }

    public boolean isFilled(int row, int column) {
        return Boolean.TRUE.equals(shape.get(row).get(column));
    }

    @Override
    public String toString() {
        return Output.render(shape);
    }
}