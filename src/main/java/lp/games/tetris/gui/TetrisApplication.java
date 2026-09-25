package lp.games.tetris.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lp.games.tetris.config.TetrisConfig;
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
    private static final String CONFIG_ERROR_TITLE = "Chybné nastavení";
    private static final String CSS_FILE_NODES = "/css/nodes.css";
    private static final String CSS_FILE_PANE = "/css/pane.css";
    private static final String PANE_ID = "pane";
    private static final double STATISTIC_PANE_WIDTH = 120;
    private static final int NEXT_PIECE_FIELDS = 4;

    private GameLoop gameLoop;
    private Game game;
    private Rendering rendering;

    @Override
    public void start(Stage stage) {
        TetrisConfig config = readConfig();
        if (config == null) {
            return;
        }
        double fieldSize = config.fieldSize();
        double gameWidth = config.boardWidth() * fieldSize;
        double gameHeight = config.boardHeight() * fieldSize;

        BorderPane mainPane = new BorderPane();
        Pane boardPane = new Pane();
        boardPane.getStyleClass().add(PANE_ID);
        boardPane.setPrefSize(gameWidth, gameHeight);
        mainPane.setCenter(boardPane);

        Pane nextPiecePane = new Pane();

        game = new Game(new Board(config.boardWidth(), config.boardHeight()),
                new PieceGenerator(new Random()),
                config.toGameSpeed());
        rendering = new Rendering(boardPane, nextPiecePane, game, fieldSize);
        PieceActions pieceActions = new PieceActions(game, rendering);

        Scene scene = new Scene(mainPane, gameWidth + STATISTIC_PANE_WIDTH, gameHeight);
        scene.setOnKeyPressed(evt -> pieceActions.keyUsed(evt.getCode()));
        scene.getStylesheets().addAll(
                Objects.requireNonNull(getClass().getResource(CSS_FILE_NODES)).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(CSS_FILE_PANE)).toExternalForm()
        );

        stage.setTitle(TETRIS);
        stage.setScene(scene);
        stage.show();

        GameOverDialog gameOverDialog = new GameOverDialog(START_NEW_GAME_TEXT, GAME_OVER_TEXT, GAME_CLOSE_TEXT, this::startNewGame);
        gameLoop = new GameLoop(game, pieceActions, rendering, gameOverDialog,
                config.blinkCount(), Duration.millis(config.blinkDurationMillis()));
        mainPane.setRight(new RightSide(new RightSideValues(
                STATISTIC_PANE_WIDTH,
                rendering.getScoreProperty(),
                nextPiecePane,
                this::startNewGame,
                START_NEW_GAME_TEXT,
                fieldSize * NEXT_PIECE_FIELDS
        )));

        startNewGame();
    }

    private TetrisConfig readConfig() {
        try {
            return TetrisConfig.load();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.setHeaderText(CONFIG_ERROR_TITLE);
            alert.showAndWait();
            Platform.exit();
            return null;
        }
    }

    private void startNewGame() {
        gameLoop.stop();
        game.startNewGame();
        rendering.render();
        gameLoop.start();
    }
}
