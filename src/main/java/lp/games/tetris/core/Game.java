package lp.games.tetris.core;

import lombok.Getter;

import java.util.function.Supplier;

public class Game {

    private final Supplier<Piece> generatedPiece;
    private final GameSpeed speed;
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
    @Getter
    private Piece nextPiece;

    public Game(Board board, Supplier<Piece> generatedPiece) {
        this(board, generatedPiece, GameSpeed.defaultSpeed());
    }

    public Game(Board board, Supplier<Piece> generatedPiece, GameSpeed speed) {
        this.board = board;
        this.generatedPiece = generatedPiece;
        this.speed = speed;
        nextPiece = generatedPiece.get();
        startNewGame();
    }

    private void spawn() {
        int x = (board.getWidth() - nextPiece.getWidth()) / 2;
        int y = 0;
        if (board.canPieceMoveAt(nextPiece, x, y)) {
            currentPiece = nextPiece;
            positionOfCurrentPiece = new Position(x, y);
        } else {
            gameOver = true;
        }
        nextPiece = generatedPiece.get();
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
        score += board.clearFilledRows();
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

    public long getFallDelayNanos() {
        return speed.delayFor(score);
    }

    public void startNewGame() {
        gameOver = false;
        board.clear();
        score = 0;
        spawn();
    }

    @Override
    public String toString() {
        return Output.gameRender(board, currentPiece, positionOfCurrentPiece);
    }
}
