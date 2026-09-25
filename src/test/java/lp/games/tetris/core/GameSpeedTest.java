package lp.games.tetris.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameSpeedTest {

    private final GameSpeed speed = new GameSpeed(500, 100, 3);

    @Test
    void delayShrinksWithScore() {
        assertAll(
                () -> assertEquals(500, speed.delayFor(0), "bez skóre je prodleva základní"),
                () -> assertEquals(470, speed.delayFor(10), "deset řádků = deset kroků"),
                () -> assertEquals(200, speed.delayFor(100))
        );
    }

    @Test
    void delayNeverFallsBelowMinimum() {
        assertAll(
                () -> assertEquals(100, speed.delayFor(134), "přesně na hranici"),
                () -> assertEquals(100, speed.delayFor(1_000), "hluboko za hranicí"),
                () -> assertEquals(100, speed.delayFor(Integer.MAX_VALUE), "žádné přetečení")
        );
    }

    @Test
    void rejectsNonsenseSettings() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> new GameSpeed(500, 0, 3)),
                () -> assertThrows(IllegalArgumentException.class, () -> new GameSpeed(50, 100, 3)),
                () -> assertThrows(IllegalArgumentException.class, () -> new GameSpeed(500, 100, -1))
        );
    }
}
