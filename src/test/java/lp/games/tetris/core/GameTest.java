package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(5, 6);
        game = new Game(board, Piece::createT);
    }

    @Test
    void spawn() {
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertEquals("""
                ###
                .#.
                """, game.getCurrentPiece().toString());
    }

    @Test
    void moveToSidesWithoutBlocks() {
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertTrue(game.moveRight());
        assertEquals(new Position(2, 0), game.getPositionOfCurrentPiece());
        assertFalse(game.moveRight());
        assertEquals(new Position(2, 0), game.getPositionOfCurrentPiece());

        assertTrue(game.moveLeft());
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertTrue(game.moveLeft());
        assertEquals(new Position(0, 0), game.getPositionOfCurrentPiece());
        assertFalse(game.moveLeft());
        assertEquals(new Position(0, 0), game.getPositionOfCurrentPiece());
    }

    @Test
    void moveToSidesWithBlocks() {
        board.lock(Piece.createI(), 4, 1);
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertTrue(game.moveRight());
        assertEquals(new Position(2, 0), game.getPositionOfCurrentPiece());
        assertTrue(game.moveLeft());
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());

        board.lock(Piece.createPoint(), 4, 0);
        assertFalse(game.moveRight());

        board.lock(Piece.createPoint(), 1, 0);
        assertFalse(game.moveLeft());
    }

    @Test
    void moveDown() {
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertTrue(game.moveDown());
        assertTrue(game.moveDown());
        assertTrue(game.moveDown());
        assertTrue(game.moveDown());
        assertFalse(game.moveDown());
        assertEquals("""
                .....
                .....
                .....
                .....
                .###.
                ..#..
                """, board.toString());
        assertEquals(new Position(1, 0), game.getPositionOfCurrentPiece());
        assertEquals("""
                ###
                .#.
                """, game.getCurrentPiece().toString());
    }

    @Test
    void moveDownAndRemoveLines() {
        game = new Game(board, () -> Piece.createZ().rotateClockwise());
        board.lock(Piece.createI(), 0, 2);
        board.lock(Piece.createJ(), 1, 3);
        board.lock(Piece.createJ().rotateClockwise().rotateClockwise(), 3, 3);
        assertTrue(game.moveDown());
        assertFalse(game.moveDown());
        game.clearPendingRows();
        assertEquals("""
                .....
                .....
                ..#..
                ###..
                #.##.
                ####.
                """, board.toString());
        assertEquals(1, game.getScore());
    }

    @Test
    void filledRowsWaitForClearing() {
        game = new Game(board, () -> Piece.createZ().rotateClockwise());
        board.lock(Piece.createI(), 0, 2);
        board.lock(Piece.createJ(), 1, 3);
        board.lock(Piece.createJ().rotateClockwise().rotateClockwise(), 3, 3);
        game.moveDown();
        game.moveDown();

        assertAll(
                () -> assertEquals(List.of(3), game.getRowsToClear(), "plný řádek zatím jen čeká"),
                () -> assertEquals(0, game.getScore(), "skóre se počítá až při smazání"),
                () -> assertTrue(board.isOccupied(4, 3), "řádek je pořád na desce"),
                () -> assertFalse(game.moveLeft(), "během čekání se nehraje"),
                () -> assertFalse(game.moveRight()),
                () -> assertFalse(game.rotateClockwise())
        );

        game.clearPendingRows();
        assertAll(
                () -> assertFalse(game.hasRowsToClear()),
                () -> assertEquals(1, game.getScore()),
                () -> assertFalse(board.isOccupied(4, 3))
        );
    }

    @Test
    void moveDownClearsPendingRowsWhenNobodyElseDoes() {
        game = new Game(board, () -> Piece.createZ().rotateClockwise());
        board.lock(Piece.createI(), 0, 2);
        board.lock(Piece.createJ(), 1, 3);
        board.lock(Piece.createJ().rotateClockwise().rotateClockwise(), 3, 3);
        game.moveDown();
        game.moveDown();

        assertFalse(game.moveDown());
        assertAll(
                () -> assertFalse(game.hasRowsToClear()),
                () -> assertEquals(1, game.getScore())
        );
    }

    @Test
    void rotateClockwise() {
        assertEquals("""
                ###
                .#.
                """, game.getCurrentPiece().toString());
        assertTrue(game.rotateClockwise());
        assertEquals("""
                .#
                ##
                .#
                """, game.getCurrentPiece().toString());
        assertTrue(game.moveRight());
        assertTrue(game.moveRight());
        assertFalse(game.moveRight());
        assertFalse(game.rotateClockwise());
        assertNotEquals("""
                .#.
                ###
                """, game.getCurrentPiece().toString());
        assertTrue(game.moveLeft());
        assertTrue(game.rotateClockwise());
        assertEquals("""
                .#.
                ###
                """, game.getCurrentPiece().toString());
    }

    @Test
    void commandsDoNothingAfterGameOver() {
        while (!game.isGameOver()) {
            game.moveDown();
        }
        assertAll(
                () -> assertFalse(game.moveDown()),
                () -> assertFalse(game.moveLeft()),
                () -> assertFalse(game.moveRight()),
                () -> assertFalse(game.rotateClockwise())
        );
    }

    @Test
    void spawnTakesExactlyOnePieceFromSupplier() {
        AtomicInteger calls = new AtomicInteger();
        game = new Game(board, () -> {
            calls.incrementAndGet();
            return Piece.createT();
        });
        assertEquals(2, calls.get());
    }

    @Test
    void gameVisual() {
        board.lock(Piece.createS(), 0, 4);
        board.lock(Piece.createI(), 4, 2);
        assertEquals("""
                .XXX.
                ..X..
                ....#
                ....#
                .##.#
                ##..#
                """, game.toString());
    }

    @Test
    void newGame() {
        game = new Game(board, Piece::createI);

        board.lock(Piece.createI(), 0, 2);
        board.lock(Piece.createI(), 1, 2);
        board.lock(Piece.createI(), 3, 2);
        board.lock(Piece.createI(), 4, 2);
        game.moveDown();
        game.moveDown();
        game.moveDown();
        assertEquals(0, game.getScore());
        game.clearPendingRows();
        assertEquals(4, game.getScore());

        game.moveDown();
        game.moveDown();
        game.moveDown();
        assertTrue(game.isGameOver());

        game.startNewGame();
        assertFalse(game.isGameOver());
        assertEquals(0, game.getScore());
        assertEquals("""
                .....
                .....
                .....
                .....
                .....
                .....
                """, board.toString());
    }
}