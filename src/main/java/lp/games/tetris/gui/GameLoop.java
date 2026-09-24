package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import lp.games.tetris.core.Game;

public class GameLoop {

    private static final int DELAY = 500_000_000;
    private static final long DELAY_SPEED = 5_000_000;
    private final AnimationTimer animationTimer;

    public GameLoop(Game game, PieceActions pieceActions, GameOverDialog gameOverDialog) {
        animationTimer = new AnimationTimer() {
            private long stopTime;

            @Override
            public void handle(long time) {
                if (game.isGameOver()) {
                    stop();
                    gameOverDialog.show();
                    return;
                }
                if (time > stopTime + DELAY - game.getScore() * DELAY_SPEED) {
                    pieceActions.fallDown();
                    stopTime = time;
                }
            }
        };
    }

    public void start() {
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }
}
