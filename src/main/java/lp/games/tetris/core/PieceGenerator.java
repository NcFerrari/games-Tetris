package lp.games.tetris.core;

import java.util.Random;
import java.util.function.Supplier;

public class PieceGenerator implements Supplier<Piece> {

    private final Random random;

    public PieceGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Piece get() {
        return switch (random.nextInt(9)) {
            case 0 -> Piece.createT();
            case 1 -> Piece.createI();
            case 2 -> Piece.createO();
            case 3 -> Piece.createL();
            case 4 -> Piece.createJ();
            case 5 -> Piece.createS();
            case 6 -> Piece.createZ();
            case 7 -> Piece.createU();
            default -> Piece.createPoint();
        };
    }
}
