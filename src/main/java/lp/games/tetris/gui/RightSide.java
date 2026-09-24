package lp.games.tetris.gui;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class RightSide extends VBox {

    private static final String SCORE_TEXT = "Skóre";
    private static final Font FONT = new Font(28);
    private final Runnable startNewGameAction;

    public RightSide(double width, StringProperty stringProperty, Runnable startNewGameAction, String newGameText) {
        setPrefWidth(width);
        this.startNewGameAction = startNewGameAction;
        initComponents(width, stringProperty, newGameText);
    }

    private void initComponents(double width, StringProperty stringProperty, String newGameText) {
        createLabel(SCORE_TEXT, width, Pos.CENTER);
        Label scoreLabel = createLabel(null, width, Pos.CENTER_RIGHT);
        scoreLabel.textProperty().bind(stringProperty);

        StackPane nextPiece = new StackPane();


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
