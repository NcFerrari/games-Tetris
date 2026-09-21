package lp.games.tetris.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lp.games.tetris.core.Board;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.PieceGenerator;
import lp.games.tetris.gui.enums.DiamondColor;

import java.util.Random;

public class TetrisApplication extends Application {

    private static final String TETRIS = "Tetris";
    private static final String BACKGROUND = "-fx-background-color: linear-gradient(to top right, #48bbb2, #2238e2)";
    private static final int FIELDS_IN_ROW = 10;
    private static final int FIELDS_IN_COLUMN = 20;
    private static final double FIELD_SIZE = 40;
    private static final double WIDTH = FIELDS_IN_ROW * FIELD_SIZE;
    private static final double HEIGHT = FIELDS_IN_COLUMN * FIELD_SIZE;
    private Pane pane;
    private Game game;
    private GameLoop gameLoop;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        pane.setStyle(BACKGROUND);

        game = new Game(new Board(FIELDS_IN_ROW, FIELDS_IN_COLUMN), new PieceGenerator(new Random()));
        Shape shape = new Shape(game, pane, FIELD_SIZE, afterFallAction());
        ShapeActions shapeActions = new ShapeActions(shape);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setOnKeyPressed(evt -> shapeActions.keyUsed(evt.getCode()));

        stage.setTitle(TETRIS);
        stage.setScene(scene);
        stage.show();

        gameLoop = new GameLoop(game, shapeActions);
        gameLoop.startGame();
    }

    private Runnable afterFallAction() {
        return () -> {
            gameLoop.setScore(game.getScore());
            pane.getChildren().clear();
            fillPaneGrid();
        };
    }

    private void fillPaneGrid() {
        Group grid = new Group();
        pane.getChildren().add(grid);
        Board board = game.getBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.isOccupied(x, y)) {
                    grid.getChildren().add(Diamond.createDiamond(x, y, FIELD_SIZE, DiamondColor.SILVER.getImage()));
                }
            }
        }
    }
}
