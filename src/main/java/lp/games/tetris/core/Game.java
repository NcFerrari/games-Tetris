package lp.games.tetris.core;

import lombok.Getter;

import java.util.function.Supplier;

public class Game {

    private final Supplier<Piece> nextPiece;
    @Getter
    private final Board board;

    @Getter
    private Position positionOfCurrentPiece;
    @Getter
    private Piece currentPiece;
    @Getter
    private int score;
    @Getter
    private boolean gameOver;

    public Game(Board board, Supplier<Piece> nextPiece) {
        this.board = board;
        this.nextPiece = nextPiece;
        spawn();
    }

    private void spawn() {
        Piece newPiece = nextPiece.get();
        int x = (board.getWidth() - newPiece.getWidth()) / 2;
        int y = 0;
        if (board.canPieceMoveAt(newPiece, x, y)) {
            currentPiece = newPiece;
            positionOfCurrentPiece = new Position(x, y);
        } else {
            gameOver = true;
        }
    }

    private boolean move(int moveByX, int moveByY) {
        if (gameOver) {
            return false;
        }
        int x = positionOfCurrentPiece.x() + moveByX;
        int y = positionOfCurrentPiece.y() + moveByY;
        if (board.canPieceMoveAt(currentPiece, x, y)) {
            positionOfCurrentPiece = new Position(x, y);
            return true;
        }
        return false;
    }

    public boolean moveLeft() {
        return move(-1, 0);
    }

    public boolean moveRight() {
        return move(1, 0);
    }

    public boolean moveDown() {
        if (gameOver) {
            return false;
        }
        if (move(0, 1)) {
            return true;
        }
        board.lock(currentPiece, positionOfCurrentPiece.x(), positionOfCurrentPiece.y());
        score += board.clearRows();
        spawn();
        return false;
    }

    public boolean rotateClockwise() {
        if (gameOver) {
            return false;
        }
        Piece possibleRotate = currentPiece.rotateClockwise();
        if (board.canPieceMoveAt(possibleRotate, positionOfCurrentPiece.x(), positionOfCurrentPiece.y())) {
            currentPiece = possibleRotate;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Output.gameRender(board, currentPiece, positionOfCurrentPiece);
    }

}
