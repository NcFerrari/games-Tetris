package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import lp.games.tetris.core.Game;

public class GameLoop {

    private static final int DELAY = 500_000_000;
    private final Game game;
    private final PieceActions pieceActions;

    public GameLoop(Game game, PieceActions pieceActions) {
        this.game = game;
        this.pieceActions = pieceActions;
    }

    public void startGame() {
        new AnimationTimer() {
            private long stopTime;

            @Override
            public void handle(long time) {
                if (game.isGameOver()) {
                    stop();
                }
                if (time > stopTime + DELAY) {
                    pieceActions.fallDown();
                    stopTime = time;
                }
            }
        }.start();
    }
}
