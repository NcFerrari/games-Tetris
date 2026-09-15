package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Piece t;
    private Piece o;
    private Piece rotatedI;

    @BeforeEach
    void setUp() {
        board = new Board(10, 20);
        t = Piece.createT();
        o = Piece.createO();
        rotatedI = Piece.createI().rotateClockwise();
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

    @Test
    void lockPiecesInBoard() {
        board = new Board(5, 5);
        board.lock(t, 2, 1);
        assertEquals("""
                .....
                ..###
                ...#.
                .....
                .....
                """, board.toString());
        board.lock(Piece.createJ(), 3, 2);
        assertEquals("""
                .....
                ..###
                ...##
                ....#
                ...##
                """, board.toString());
    }

    @Test
    void lockRejectsOccupiedPosition() {
        board = new Board(5, 5);
        board.lock(t, 2, 1);
        Piece piece = Piece.createO();
        assertThrows(IllegalArgumentException.class, () -> board.lock(piece, 1, 0));
        assertEquals("""
                .....
                ..###
                ...#.
                .....
                .....
                """, board.toString());
    }

    @Test
    void clearNoRows() {
        assertEquals(0, board.clearRows());
        board.lock(rotatedI, 0, 19);
        board.lock(rotatedI, 4, 19);
        board.lock(rotatedI.rotateClockwise(), 8, 16);
        assertEquals(0, board.clearRows());
        assertEquals("""
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ..........
                ........#.
                ........#.
                ........#.
                #########.
                """, board.toString());
    }

    @Test
    void clearRow() {
        board.lock(rotatedI, 0, 19);
        board.lock(rotatedI, 4, 19);
        board.lock(o, 8, 18);
        assertEquals(1, board.clearRows());
        assertTrue(board.canPieceMoveAt(Piece.createPoint(), 0, 19));
    }

    @Test
    void clearRowAndFallAllDown() {
        board.lock(rotatedI, 0, 19);
        board.lock(rotatedI, 4, 19);
        board.lock(o, 8, 18);
        board.lock(Piece.createT(), 0, 0);
        board.lock(Piece.createS(), 4, 3);
        board.lock(Piece.createZ(), 1, 7);
        board.lock(Piece.createI(), 8, 9);
        board.lock(Piece.createO(), 2, 11);
        board.lock(Piece.createL(), 5, 12);
        board.lock(Piece.createJ(), 0, 14);
        board.lock(Piece.createJ(), 4, 16);
        assertEquals(1, board.clearRows());
        assertEquals("""
                ..........
                ###.......
                .#........
                ..........
                .....##...
                ....##....
                ..........
                ..........
                .##.......
                ..##......
                ........#.
                ........#.
                ..##....#.
                ..##.#..#.
                .....#....
                .#...##...
                .#........
                ##...#....
                .....#....
                ....##..##
                """, board.toString());
    }


    @Test
    void clearMoreRows() {
        board = new Board(5, 5);
        board.lock(rotatedI, 0, 2);
        board.lock(rotatedI, 0, 4);
        board.lock(rotatedI.rotateClockwise(), 4, 1);
        assertEquals(2, board.clearRows());
        assertEquals("""
                .....
                .....
                .....
                ....#
                ....#
                """, board.toString());
    }

    @Test
    void isOccupied() {
        board = new Board(5, 5);
        board.lock(Piece.createL(), 1, 2);
        for (int x = 0; x < board.getHeight(); x++) {
            for (int y = 0; y < board.getWidth(); y++) {
                if (x == 1 && y == 2 || x == 1 && y == 3 || x == 1 && y == 4 || x == 2 && y == 4) {
                    assertTrue(board.isOccupied(x, y));
                    continue;
                }
                assertFalse(board.isOccupied(x, y));
            }
        }
    }
}
