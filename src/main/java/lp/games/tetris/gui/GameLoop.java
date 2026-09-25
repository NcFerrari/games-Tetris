package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import lp.games.tetris.core.Game;

public class GameLoop {

    private final Game game;
    private final PieceActions pieceActions;
    private final Rendering rendering;
    private final GameOverDialog gameOverDialog;
    private final int blinkCount;
    private final Duration blinkDuration;
    private final AnimationTimer animationTimer;
    private long lastFallTime;
    private boolean blinkVisible;

    public GameLoop(Game game, PieceActions pieceActions, Rendering rendering, GameOverDialog gameOverDialog,
                    int blinkCount, Duration blinkDuration) {
        this.game = game;
        this.pieceActions = pieceActions;
        this.rendering = rendering;
        this.gameOverDialog = gameOverDialog;
        this.blinkCount = blinkCount;
        this.blinkDuration = blinkDuration;
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                tick(time);
            }
        };
    }

    private void tick(long time) {
        if (game.isGameOver()) {
            stop();
            gameOverDialog.show();
            return;
        }
        if (game.hasRowsToClear()) {
            blinkFilledRows();
            return;
        }
        if (time > lastFallTime + game.getFallDelayNanos()) {
            pieceActions.fallDown();
            lastFallTime = time;
        }
    }

    private void blinkFilledRows() {
        animationTimer.stop();
        if (blinkCount <= 0) {
            finishBlinking();
            return;
        }
        pieceActions.setEnabled(false);
        Timeline blinking = new Timeline(new KeyFrame(blinkDuration, evt -> toggleBlink()));
        blinking.setCycleCount(blinkCount);
        blinking.setOnFinished(evt -> finishBlinking());
        blinking.play();
    }

    private void toggleBlink() {
        blinkVisible = !blinkVisible;
        rendering.setBlinkVisible(blinkVisible);
        rendering.render();
    }

    private void finishBlinking() {
        blinkVisible = false;
        rendering.setBlinkVisible(false);
        game.clearPendingRows();
        rendering.render();
        pieceActions.setEnabled(true);
        start();
    }

    public void start() {
        lastFallTime = System.nanoTime();
        animationTimer.start();
    }

    public void stop() {
        animationTimer.stop();
    }
}
