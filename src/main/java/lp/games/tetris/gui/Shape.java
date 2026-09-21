package lp.games.tetris.gui;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import lp.games.tetris.core.Game;
import lp.games.tetris.core.Piece;
import lp.games.tetris.gui.enums.DiamondColor;

public class Shape {

    private Group group;
    private final Game game;
    private final Pane pane;
    private final double fieldSize;
    private final Runnable afterFallAction;

    public Shape(Game game, Pane pane, double fieldSize, Runnable afterFallAction) {
        this.game = game;
        this.fieldSize = fieldSize;
        this.pane = pane;
        this.afterFallAction = afterFallAction;
        createInitialShape();
    }

    private void createInitialShape() {
        group = new Group();
        recountShape(game.getCurrentPiece());
        setCurrentShapePositionFromGameParameter();
        pane.getChildren().add(group);
    }

    public void createShape() {
        createInitialShape();
    }

    private void recountShape(Piece piece) {
        group.getChildren().clear();
        for (int y = 0; y < piece.getHeight(); y++) {
            for (int x = 0; x < piece.getWidth(); x++) {
                generateNewPieceInShape(piece, x, y);
            }
        }
    }

    private void generateNewPieceInShape(Piece piece, int x, int y) {
        if (piece.isFilled(y, x)) {
            group.getChildren().add(Diamond.createDiamond(x, y, fieldSize, DiamondColor.getDiamondColor(piece.getShapeType()).getImage()));
        }
    }

    public void fall() {
        if (game.moveDown()) {
            group.setLayoutY(game.getPositionOfCurrentPiece().y() * fieldSize);
        } else {
            afterFallAction.run();
            if (!game.isGameOver()) {
                createShape();
            }
        }
    }

    public void rotate() {
        game.rotateClockwise();
        recountShape(game.getCurrentPiece());
    }

    public void moveRight() {
        game.moveRight();
        setCurrentShapePositionFromGameParameter();
    }

    public void moveLeft() {
        game.moveLeft();
        setCurrentShapePositionFromGameParameter();
    }

    private void setCurrentShapePositionFromGameParameter() {
        group.setLayoutX(game.getPositionOfCurrentPiece().x() * fieldSize);
        group.setLayoutY(game.getPositionOfCurrentPiece().y() * fieldSize);
    }
}
