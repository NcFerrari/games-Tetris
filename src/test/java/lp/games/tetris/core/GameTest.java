package lp.games.tetris.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(5, 6);
        game = new Game(board, Piece::createT);
        game.spawn();
    }

    @Test
    void spawn() {
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertEquals("""
                ###
                .#.
                """, game.getCurrentPiece().toString());
    }

    @Test
    void moveToSidesWithoutBlocks() {
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertTrue(game.moveRight());
        assertEquals(new Position(2, 0), game.getCurrentPosition());
        assertFalse(game.moveRight());
        assertEquals(new Position(2, 0), game.getCurrentPosition());

        assertTrue(game.moveLeft());
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertTrue(game.moveLeft());
        assertEquals(new Position(0, 0), game.getCurrentPosition());
        assertFalse(game.moveLeft());
        assertEquals(new Position(0, 0), game.getCurrentPosition());
    }

    @Test
    void moveToSidesWithBlocks() {
        board.lock(Piece.createI(), 4, 1);
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertTrue(game.moveRight());
        assertEquals(new Position(2, 0), game.getCurrentPosition());
        assertTrue(game.moveLeft());
        assertEquals(new Position(1, 0), game.getCurrentPosition());

        board.lock(Piece.createPoint(), 4, 0);
        assertFalse(game.moveRight());

        board.lock(Piece.createPoint(), 1, 0);
        assertFalse(game.moveLeft());
    }

    @Test
    void fallDown() {
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertTrue(game.fall());
        assertTrue(game.fall());
        assertTrue(game.fall());
        assertTrue(game.fall());
        assertFalse(game.fall());
        assertEquals("""
                .....
                .....
                .....
                .....
                .###.
                ..#..
                """, board.toString());
        assertEquals(new Position(1, 0), game.getCurrentPosition());
        assertEquals("""
                ###
                .#.
                """, game.getCurrentPiece().toString());
    }

    @Test
    void fallAndRemoveLines() {
        game = new Game(board, () -> Piece.createZ().rotateClockwise());
        game.spawn();
        board.lock(Piece.createI(), 0, 2);
        board.lock(Piece.createJ(), 1, 3);
        board.lock(Piece.createJ().rotateClockwise().rotateClockwise(), 3, 3);
        assertTrue(game.fall());
        assertFalse(game.fall());
        assertEquals("""
                .....
                .....
                ..#..
                ###..
                #.##.
                ####.
                """, board.toString());
        assertEquals(1, game.getClearedRows());
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
            game.fall();
        }
        assertAll(
                () -> assertFalse(game.fall()),
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

        game.spawn();

        assertEquals(1, calls.get());
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
}