package lp.games.tetris.gui;

import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;

public record RightSideValues(double width, StringProperty scoreProperty, Pane nextPiecePane,
                              Runnable startNewGameAction, String newGameText, double nextPieceHeight) {

}
