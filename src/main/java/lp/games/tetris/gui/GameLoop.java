package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import lp.games.tetris.core.Game;

public class GameLoop {

    private final AnimationTimer animationTimer;

    public GameLoop(Game game, PieceActions pieceActions, GameOverDialog gameOverDialog) {
        animationTimer = new AnimationTimer() {
            private long lastFallTime;

            @Override
            public void handle(long time) {
                if (game.isGameOver()) {
                    stop();
                    gameOverDialog.show();
                    return;
                }
                if (time > lastFallTime + game.getFallDelayNanos()) {
                    pieceActions.fallDown();
                    lastFallTime = time;
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
