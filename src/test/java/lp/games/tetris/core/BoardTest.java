package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(10, 20);
    }

    @Test
    void checkBoardSize() {
        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());
    }

    @Test
    void checkAddingPiece() {
        Piece piece = Piece.createT();
        assertTrue(board.canPieceMoveAt(piece, 7, 0));
        assertFalse(board.canPieceMoveAt(piece, 8, 0));
        assertFalse(board.canPieceMoveAt(piece, -1, 0));

        assertTrue(board.canPieceMoveAt(piece, 0, 18));
        assertFalse(board.canPieceMoveAt(piece, 0, 19));
        assertFalse(board.canPieceMoveAt(piece, 0, -1));
    }
}