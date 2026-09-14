package lp.games.tetris.core;

import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class Game {

    private Board board;
    private Supplier<Piece> nextPiece;
    private Position position;
    private Piece currentPiece;
    private boolean gameOver;
    private int clearedRows = 0;

    public Game(int boardWidth, int boardHeight) {
        board = new Board(boardWidth, boardHeight);
    }

    public void spawn() {

    }

    public boolean moveLeft() {
        return false;
    }

    public boolean moveRight() {
        return false;
    }

    public boolean rotateClockwise() {
        return false;
    }

    public boolean fall() {
        return false;
    }

}
