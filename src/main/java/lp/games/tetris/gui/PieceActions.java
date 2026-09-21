package lp.games.tetris.gui;

import javafx.scene.input.KeyCode;
import lp.games.tetris.core.Game;

public class PieceActions {

    private final Game game;
    private final Rendering rendering;

    public PieceActions(Game game, Rendering rendering) {
        this.game = game;
        this.rendering = rendering;
    }

    private void performAction(Runnable action) {
        action.run();
        rendering.renderPane();
    }

    public void keyUsed(KeyCode key) {
        performAction(() -> {
            switch (key) {
                case LEFT -> game.moveLeft();
                case RIGHT -> game.moveRight();
                case DOWN -> game.moveDown();
                case SPACE -> game.rotateClockwise();
                default -> {
                    // Do nothing for unmatched keys
                }
            }
        });
    }

    public void fallDown() {
        performAction(game::moveDown);
    }
}
