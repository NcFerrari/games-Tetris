package lp.games.tetris.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import lp.games.tetris.core.Board;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.Piece;
import lp.games.tetris.core.PieceGenerator;

import java.util.Random;

public class TetrisApplication extends Application {

    private static final String TETRIS = "Tetris";
    private static final String BACKGROUND = "-fx-background-color: linear-gradient(to top right, #48bbb2, #2238e2)";
    private static final double FIELD_SIZE = 40;
    private static final int FIELDS_IN_ROW = 10;
    private static final int FIELDS_IN_COLUMN = 20;
    private static final double WIDTH = FIELDS_IN_ROW * FIELD_SIZE;
    private static final double HEIGHT = FIELDS_IN_COLUMN * FIELD_SIZE;
    private Game game;
    private Group currentShape;
    private boolean inAction;
    private Pane pane;

    @Override
    public void start(Stage stage) {
        game = new Game(new Board(FIELDS_IN_ROW, FIELDS_IN_COLUMN), new PieceGenerator(new Random()));

        pane = new Pane();
        createShape();
        pane.setStyle(BACKGROUND);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setOnKeyPressed(evt -> performAction(evt.getCode()));
        stage.setTitle(TETRIS);
        stage.setScene(scene);
        stage.show();

        gameLoop();
    }

    private void gameLoop() {
        new AnimationTimer() {
            private long stopTime;

            @Override
            public void handle(long time) {
                if (game.isGameOver()) {
                    stop();
                }
                if (time > stopTime + 500_000_000) {
                    performAction(KeyCode.DOWN);
                    stopTime = time;
                }
            }
        }.start();
    }

    private void createShape() {
        currentShape = new Group();
        recountShape(game.getCurrentPiece());
        setCurrentShapePositionFromGameParameter();
        pane.getChildren().add(currentShape);
    }

    private void recountShape(Piece piece) {
        currentShape.getChildren().clear();
        for (int y = 0; y < piece.getHeight(); y++) {
            for (int x = 0; x < piece.getWidth(); x++) {
                if (piece.isFilled(y, x)) {
                    Rectangle rectangle = new Rectangle(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
                    currentShape.getChildren().add(rectangle);
                }
            }
        }
    }

    private void performAction(KeyCode key) {
        if (inAction) {
            return;
        }
        inAction = true;
        Runnable action = keyBinding(key);
        if (action != null) {
            action.run();
        }
        inAction = false;
    }

    private Runnable keyBinding(KeyCode key) {
        return switch (key) {
            case LEFT -> this::moveLeft;
            case RIGHT -> this::moveRight;
            case DOWN -> this::fall;
            case SPACE -> this::rotate;
            default -> null;
        };
    }

    private void fall() {
        if (game.fall()) {
            currentShape.setLayoutY(game.getCurrentPosition().y() * FIELD_SIZE);
        } else {
            createShape();
        }
    }

    private void rotate() {
        game.rotateClockwise();
        recountShape(game.getCurrentPiece());
    }

    private void moveRight() {
        game.moveRight();
        setCurrentShapePositionFromGameParameter();
    }

    private void moveLeft() {
        game.moveLeft();
        setCurrentShapePositionFromGameParameter();
    }

    private void setCurrentShapePositionFromGameParameter() {
        currentShape.setLayoutX(game.getCurrentPosition().x() * FIELD_SIZE);
        currentShape.setLayoutY(game.getCurrentPosition().y() * FIELD_SIZE);
    }
}
