package lp.games.tetris.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lp.games.tetris.core.Board;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.PieceGenerator;

import java.util.Objects;
import java.util.Random;

public class TetrisApplication extends Application {

    private static final String TETRIS = "Tetris";
    private static final String START_NEW_GAME_TEXT = "Nová hra";
    private static final String GAME_OVER_TEXT = "GAME OVER";
    private static final String GAME_CLOSE_TEXT = "Ukončit hru";
    private static final String CSS_FILE_NODES = "/css/nodes.css";
    private static final String CSS_FILE_PANE = "/css/pane.css";
    private static final int FIELDS_IN_ROW = 10;
    private static final int FIELDS_IN_COLUMN = 20;
    private static final double FIELD_SIZE = 40;
    private static final double GAME_WIDTH = FIELDS_IN_ROW * FIELD_SIZE;
    private static final double GAME_HEIGHT = FIELDS_IN_COLUMN * FIELD_SIZE;
    private static final double STATISTIC_PANE_WIDTH = 120;

    private GameLoop gameLoop;
    private Game game;
    private Rendering rendering;

    @Override
    public void start(Stage stage) {
        BorderPane mainPane = new BorderPane();

        Pane pane = new Pane();
        pane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        mainPane.setCenter(pane);

        game = new Game(new Board(FIELDS_IN_ROW, FIELDS_IN_COLUMN), new PieceGenerator(new Random()));
        rendering = new Rendering(pane, game, FIELD_SIZE);
        PieceActions pieceActions = new PieceActions(game, rendering);

        Scene scene = new Scene(mainPane, GAME_WIDTH + STATISTIC_PANE_WIDTH, GAME_HEIGHT);
        scene.setOnKeyPressed(evt -> pieceActions.keyUsed(evt.getCode()));
        scene.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource(CSS_FILE_NODES)).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(CSS_FILE_PANE)).toExternalForm()
        );

        stage.setTitle(TETRIS);
        stage.setScene(scene);
        stage.show();

        GameOverDialog gameOverDialog = new GameOverDialog(START_NEW_GAME_TEXT, GAME_OVER_TEXT, GAME_CLOSE_TEXT, this::startNewGame);
        gameLoop = new GameLoop(game, pieceActions, gameOverDialog);
        mainPane.setRight(new RightSide(STATISTIC_PANE_WIDTH, rendering.getScoreProperty(), this::startNewGame, START_NEW_GAME_TEXT));

        startNewGame();
    }

    private void startNewGame() {
        gameLoop.stop();
        game.startNewGame();
        gameLoop.start();
        rendering.renderPane();
    }
}
