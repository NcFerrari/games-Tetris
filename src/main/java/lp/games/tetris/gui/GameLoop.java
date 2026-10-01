package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import lp.games.tetris.core.Game;
import lp.games.tetris.gui.dialogs.GameOverDialog;
import lp.games.tetris.gui.dialogs.PauseDialog;

public class GameLoop {

    private final AnimationTimer animationTimer;

    public GameLoop(Game game, PieceActions pieceActions, GameOverDialog gameOverDialog, PauseDialog pauseDialog) {
        animationTimer = new AnimationTimer() {
            private long lastFallTime;

            @Override
            public void handle(long time) {
                if (game.isGameOver()) {
                    stop();
                    gameOverDialog.show();
                    return;
                }
                if (game.isPaused()) {
                    lastFallTime = time;
                    if (!pauseDialog.isShowing()) {
                        pauseDialog.show();
                    }
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
