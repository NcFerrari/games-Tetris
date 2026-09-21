package lp.games.tetris.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lp.games.tetris.core.Board;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.PieceGenerator;

import java.util.Random;

public class TetrisApplication extends Application {

    private static final String TETRIS = "Tetris";
    private static final String BACKGROUND = "-fx-background-color: linear-gradient(to top right, #48bbb2, #2238e2)";
    private static final int FIELDS_IN_ROW = 10;
    private static final int FIELDS_IN_COLUMN = 20;
    private static final double FIELD_SIZE = 40;
    private static final double WIDTH = FIELDS_IN_ROW * FIELD_SIZE;
    private static final double HEIGHT = FIELDS_IN_COLUMN * FIELD_SIZE;

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        pane.setStyle(BACKGROUND);

        Game game = new Game(new Board(FIELDS_IN_ROW, FIELDS_IN_COLUMN), new PieceGenerator(new Random()));
        Rendering rendering = new Rendering(pane, game, FIELD_SIZE);
        PieceActions pieceActions = new PieceActions(game, rendering);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setOnKeyPressed(evt -> pieceActions.keyUsed(evt.getCode()));

        stage.setTitle(TETRIS);
        stage.setScene(scene);
        stage.show();

        GameLoop gameLoop = new GameLoop(game, pieceActions);
        gameLoop.startGame();
        rendering.renderPane();
    }
}
