package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(10, 20);
    }

    @Test
    void spawn() {
        game.spawn();
        System.out.println(game.getBoard());
    }
}