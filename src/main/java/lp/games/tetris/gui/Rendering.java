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

    private final Pane boardPane;
    private final Pane nextPiecePane;
    private final Game game;
    private final double fieldSize;
    @Getter
    private final StringProperty scoreProperty = new SimpleStringProperty();

    public Rendering(Pane boardPane, Pane nextPiecePane, Game game, double fieldSize) {
        this.boardPane = boardPane;
        this.nextPiecePane = nextPiecePane;
        this.game = game;
        this.fieldSize = fieldSize;
    }

    public void render() {
        scoreProperty.set(String.valueOf(game.getScore()));
        renderBoard();
        renderNextPiece();
    }

    private void renderBoard() {
        List<StackPane> diamonds = new ArrayList<>();
        Board board = game.getBoard();
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.isOccupied(x, y)) {
                    diamonds.add(Diamond.createDiamond(x, y, fieldSize, DiamondColor.SILVER.getImage()));
                }
            }
        }
        if (!game.isGameOver()) {
            diamonds.addAll(renderPiece(game.getCurrentPiece(),
                    game.getPositionOfCurrentPiece().x(),
                    game.getPositionOfCurrentPiece().y()));
        }
        boardPane.getChildren().setAll(diamonds);
    }

    private void renderNextPiece() {
        nextPiecePane.getChildren().setAll(renderPiece(game.getNextPiece(), 0, 0));
    }

    private List<StackPane> renderPiece(Piece piece, double baseX, double baseY) {
        Image pieceImage = DiamondColor.getDiamondColor(piece.getShapeType()).getImage();
        List<StackPane> diamonds = new ArrayList<>();
        for (int x = 0; x < piece.getWidth(); x++) {
            for (int y = 0; y < piece.getHeight(); y++) {
                if (piece.isFilled(y, x)) {
                    diamonds.add(Diamond.createDiamond(x + baseX, y + baseY, fieldSize, pieceImage));
                }
            }
        }
        return diamonds;
    }
}
