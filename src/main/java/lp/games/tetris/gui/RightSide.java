package lp.games.tetris.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RightSide extends VBox {

    private static final String NEXT_PIECE_WINDOW = "next-piece-window";
    private static final String SCORE_TEXT = "Skóre";
    private static final Font FONT = new Font(28);

    public RightSide(RightSideValues values) {
        setPrefWidth(values.width());
        initComponents(values);
    }

    private void initComponents(RightSideValues values) {
        double width = values.width();
        createLabel(SCORE_TEXT, width, Pos.CENTER);
        Label scoreLabel = createLabel(null, width, Pos.CENTER_RIGHT);
        scoreLabel.textProperty().bind(values.scoreProperty());

        StackPane nextPieceWindow = new StackPane(values.nextPiecePane());
        nextPieceWindow.getStyleClass().add(NEXT_PIECE_WINDOW);
        nextPieceWindow.setMinHeight(values.nextPieceHeight());

        Button button = new Button(values.newGameText());
        button.setPrefWidth(width);
        button.setOnAction(evt -> values.startNewGameAction().run());
        button.setFocusTraversable(false);
        getChildren().addAll(nextPieceWindow, button);
    }

    private Label createLabel(String text, double width, Pos pos) {
        Label label = new Label(text);
        label.setMaxWidth(width);
        label.setAlignment(pos);
        label.setFont(FONT);
        getChildren().add(label);
        return label;
    }
}
