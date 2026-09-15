package lp.games.tetris.core;

import lombok.Getter;

import java.util.function.Supplier;

public class Game {

    private final Supplier<Piece> nextPiece;
    private final Board board;

    @Getter
    private Position currentPosition;
    @Getter
    private Piece currentPiece;
    @Getter
    private int clearedRows;
    @Getter
    private boolean gameOver;

    public Game(Board board, Supplier<Piece> nextPiece) {
        this.board = board;
        this.nextPiece = nextPiece;
    }

    public void spawn() {
        Piece newPiece = nextPiece.get();
        int x = (board.getWidth() - newPiece.getWidth()) / 2;
        int y = 0;
        if (board.canPieceMoveAt(newPiece, x, y)) {
            currentPiece = newPiece;
            currentPosition = new Position(x, y);
        } else {
            gameOver = true;
        }
    }

    private boolean move(int moveByX, int moveByY) {
        if (gameOver) {
            return false;
        }
        int x = currentPosition.x() + moveByX;
        int y = currentPosition.y() + moveByY;
        if (board.canPieceMoveAt(currentPiece, x, y)) {
            currentPosition = new Position(x, y);
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

    public boolean fall() {
        if (gameOver) {
            return false;
        }
        if (move(0, 1)) {
            return true;
        }
        board.lock(currentPiece, currentPosition.x(), currentPosition.y());
        clearedRows += board.clearRows();
        spawn();
        return false;
    }

    public boolean rotateClockwise() {
        if (gameOver) {
            return false;
        }
        Piece possibleRotate = currentPiece.rotateClockwise();
        if (board.canPieceMoveAt(possibleRotate, currentPosition.x(), currentPosition.y())) {
            currentPiece = possibleRotate;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return Output.gameRender(board, currentPiece, currentPosition);
    }
}
