package lp.games.tetris.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class RightSide extends VBox {

    private static final String NEXT_PIECE_WINDOW = "next-piece-window";
    private static final String SCORE_TEXT = "Skóre";
    private static final Font FONT = new Font(28);
    private final Runnable startNewGameAction;
    private final double maxHeight;

    public RightSide(double width, StringProperty stringProperty, ObjectProperty<List<StackPane>> pieceProperty, Runnable startNewGameAction, String newGameText, double maxHeight) {
        setPrefWidth(width);
        this.startNewGameAction = startNewGameAction;
        this.maxHeight = maxHeight;
        initComponents(width, stringProperty, pieceProperty, newGameText);
    }

    private void initComponents(double width, StringProperty stringProperty, ObjectProperty<List<StackPane>> pieceProperty, String newGameText) {
        createLabel(SCORE_TEXT, width, Pos.CENTER);
        Label scoreLabel = createLabel(null, width, Pos.CENTER_RIGHT);
        scoreLabel.textProperty().bind(stringProperty);

        StackPane nextPiece = new StackPane();
        nextPiece.getStyleClass().add(NEXT_PIECE_WINDOW);
        nextPiece.setMinHeight(maxHeight);
        Group centralNextPiece = new Group();
        nextPiece.getChildren().add(centralNextPiece);
        pieceProperty.addListener((obs, oldValue, newValue) -> {
            centralNextPiece.getChildren().clear();
            centralNextPiece.getChildren().addAll(newValue);
        });

        Button button = new Button(newGameText);
        button.setPrefWidth(width);
        button.setOnAction(evt -> startNewGameAction.run());
        button.setFocusTraversable(false);
        getChildren().addAll(nextPiece, button);
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
