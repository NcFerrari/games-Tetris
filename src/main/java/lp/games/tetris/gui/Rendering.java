package lp.games.tetris.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lp.games.tetris.core.Board;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.Piece;
import lp.games.tetris.gui.enums.DiamondColor;

import java.util.ArrayList;
import java.util.List;

public class Rendering {

    private final Pane pane;
    private final Game game;
    private final double fieldSize;
    @Getter
    private final StringProperty scoreProperty;

    public Rendering(Pane pane, Game game, double fieldSize) {
        this.pane = pane;
        this.game = game;
        this.fieldSize = fieldSize;
        scoreProperty = new SimpleStringProperty();
    }

    public void renderPane() {
        pane.getChildren().clear();
        scoreProperty.set(String.valueOf(game.getScore()));
        List<StackPane> images = new ArrayList<>();
        Board board = game.getBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.isOccupied(x, y)) {
                    images.add(Diamond.createDiamond(x, y, fieldSize, DiamondColor.SILVER.getImage()));
                }
            }
        }
        if (!game.isGameOver()) {
            renderPiece(images);
        }
        pane.getChildren().addAll(images);
    }

    private void renderPiece(List<StackPane> images) {
        Piece piece = game.getCurrentPiece();
        double baseX = game.getPositionOfCurrentPiece().x();
        double baseY = game.getPositionOfCurrentPiece().y();
        Image pieceImage = DiamondColor.getDiamondColor(piece.getShapeType()).getImage();
        for (int x = 0; x < piece.getWidth(); x++) {
            for (int y = 0; y < piece.getHeight(); y++) {
                if (piece.isFilled(y, x)) {
                    images.add(Diamond.createDiamond(x + baseX, y + baseY, fieldSize, pieceImage));
                }
            }
        }
    }
}
