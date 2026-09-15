package lp.games.tetris.core;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceGeneratorTest {

    private static final int SEED = 42;

    @Test
    void sameSeedGivesSameSequence() {
        PieceGenerator first = new PieceGenerator(new Random(SEED));
        PieceGenerator second = new PieceGenerator(new Random(SEED));

        for (int i = 0; i < 50; i++) {
            assertEquals(first.get().toString(), second.get().toString(), "tah č. " + i);
        }
    }

    @Test
    void everyShapeEventuallyAppears() {
        PieceGenerator generator = new PieceGenerator(new Random(SEED));
        Set<String> seenShapes = new HashSet<>();

        for (int i = 0; i < 200; i++) {
            seenShapes.add(generator.get().toString());
        }

        assertEquals(9, seenShapes.size());
    }
}
