package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.Setter;
import lp.games.tetris.core.Game;

public class GameLoop {

    private static final int DELAY = 500_000_000;
    private final Game game;
    private final ShapeActions shapeActions;
    @Getter
    @Setter
    private int score;

    public GameLoop(Game game, ShapeActions shapeActions) {
        this.game = game;
        this.shapeActions = shapeActions;
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
                    shapeActions.fallDown();
                    stopTime = time;
                }
            }
        }.start();
    }
}
