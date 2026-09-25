package lp.games.tetris.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.StackPane;

import java.util.List;

public record RightSideValues(double width, StringProperty stringProperty,
                              ObjectProperty<List<StackPane>> pieceProperty,
                              Runnable startNewGameAction, String newGameText, double maxHeight) {

}
