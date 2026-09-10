package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Piece t;

    @BeforeEach
    void setUp() {
        board = new Board(10, 20);
        t = Piece.createT();
    }

    @Test
    void boardHasGivenSize() {
        assertEquals(10, board.getWidth());
        assertEquals(20, board.getHeight());
    }

    @Test
    void pieceFitsInsideBoard() {
        assertAll(
                () -> assertTrue(board.canPieceMoveAt(t, 0, 0), "levý horní roh"),
                () -> assertTrue(board.canPieceMoveAt(t, 7, 0), "poslední sloupec"),
                () -> assertTrue(board.canPieceMoveAt(t, 0, 18), "poslední řádek"),
                () -> assertTrue(board.canPieceMoveAt(t, 7, 18), "pravý dolní roh")
        );
    }

    @Test
    void pieceDoesNotFitBeyondEdges() {
        assertAll(
                () -> assertFalse(board.canPieceMoveAt(t, 8, 0), "za pravým okrajem"),
                () -> assertFalse(board.canPieceMoveAt(t, 0, 19), "pod dnem"),
                () -> assertFalse(board.canPieceMoveAt(t, -1, 0), "za levým okrajem"),
                () -> assertFalse(board.canPieceMoveAt(t, 0, -1), "nad stropem")
        );
    }

    @Test
    void limitsFollowPieceSize() {
        Piece i = Piece.createI();
        assertAll(
                () -> assertTrue(board.canPieceMoveAt(i, 9, 16), "I je 1×4, vejde se do pravého dolního rohu"),
                () -> assertFalse(board.canPieceMoveAt(i, 10, 16), "sloupec 10 neexistuje"),
                () -> assertFalse(board.canPieceMoveAt(i, 9, 17), "I je 4 vysoké")
        );
    }
}
