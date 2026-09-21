package lp.games.tetris.gui;

import javafx.scene.input.KeyCode;

public class ShapeActions {

    private final Shape shape;

    public ShapeActions(Shape shape) {
        this.shape = shape;
    }

    private void performAction(Runnable action) {
        if (action != null) {
            action.run();
        }
    }

    public void keyUsed(KeyCode key) {
        performAction(() -> {
            switch (key) {
                case LEFT -> shape.moveLeft();
                case RIGHT -> shape.moveRight();
                case DOWN -> shape.fall();
                case SPACE -> shape.rotate();
                default -> {
                    // Do nothing for unmatched keys
                }
            }
        });
    }

    public void fallDown() {
        performAction(shape::fall);
    }
}
