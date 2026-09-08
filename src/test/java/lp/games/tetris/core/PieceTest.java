package lp.games.tetris.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void initT() {
        Piece t = Piece.createT();
        assertEquals(3, t.getWidth());
        assertEquals(2, t.getHeight());
        assertEquals("""
                ###
                .#.
                """, t.toString());
    }

    @Test
    void rotateClockwiseT() {
        Piece t = Piece.createT();
        t = t.rotateClockwise();
        assertEquals(2, t.getWidth());
        assertEquals(3, t.getHeight());
        assertEquals("""
                .#
                ##
                .#
                """, t.toString());
        t = t.rotateClockwise();
        assertEquals(3, t.getWidth());
        assertEquals(2, t.getHeight());
        assertEquals("""
                .#.
                ###
                """, t.toString());
        t = t.rotateClockwise();
        assertEquals(2, t.getWidth());
        assertEquals(3, t.getHeight());
        assertEquals("""
                #.
                ##
                #.
                """, t.toString());
        t = t.rotateClockwise();
        assertEquals(3, t.getWidth());
        assertEquals(2, t.getHeight());
        assertEquals("""
                ###
                .#.
                """, t.toString());
    }

    @Test
    void initI() {
        Piece i = Piece.createI();
        assertEquals(1, i.getWidth());
        assertEquals(4, i.getHeight());
        assertEquals("""
                #
                #
                #
                #
                """, i.toString());
    }

    @Test
    void rotateClockwiseI() {
        Piece i = Piece.createI();
        i = i.rotateClockwise();
        assertEquals(4, i.getWidth());
        assertEquals(1, i.getHeight());
        assertEquals("""
                ####
                """, i.toString());
        i = i.rotateClockwise();
        assertEquals(1, i.getWidth());
        assertEquals(4, i.getHeight());
        assertEquals("""
                #
                #
                #
                #
                """, i.toString());
    }

    @Test
    void initO() {
        Piece o = Piece.createO();
        assertEquals(2, o.getWidth());
        assertEquals(2, o.getHeight());
        assertEquals("""
                ##
                ##
                """, o.toString());
    }

    @Test
    void rotateClockwiseO() {
        Piece o = Piece.createO();
        o = o.rotateClockwise();
        assertEquals(2, o.getWidth());
        assertEquals(2, o.getHeight());
        assertEquals("""
                ##
                ##
                """, o.toString());
    }

    @Test
    void initL() {
        Piece l = Piece.createL();
        assertEquals(2, l.getWidth());
        assertEquals(3, l.getHeight());
        assertEquals("""
                #.
                #.
                ##
                """, l.toString());
    }

    @Test
    void rotateClockwiseL() {
        Piece l = Piece.createL();
        l = l.rotateClockwise();
        assertEquals(3, l.getWidth());
        assertEquals(2, l.getHeight());
        assertEquals("""
                ###
                #..
                """, l.toString());
        l = l.rotateClockwise();
        assertEquals(2, l.getWidth());
        assertEquals(3, l.getHeight());
        assertEquals("""
                ##
                .#
                .#
                """, l.toString());
        l = l.rotateClockwise();
        assertEquals(3, l.getWidth());
        assertEquals(2, l.getHeight());
        assertEquals("""
                ..#
                ###
                """, l.toString());
        l = l.rotateClockwise();
        assertEquals(2, l.getWidth());
        assertEquals(3, l.getHeight());
        assertEquals("""
                #.
                #.
                ##
                """, l.toString());
    }

    @Test
    void initJ() {
        Piece j = Piece.createJ();
        assertEquals(2, j.getWidth());
        assertEquals(3, j.getHeight());
        assertEquals("""
                .#
                .#
                ##
                """, j.toString());
    }

    @Test
    void rotateClockwiseJ() {
        Piece j = Piece.createJ();
        j = j.rotateClockwise();
        assertEquals(3, j.getWidth());
        assertEquals(2, j.getHeight());
        assertEquals("""
                #..
                ###
                """, j.toString());
        j = j.rotateClockwise();
        assertEquals(2, j.getWidth());
        assertEquals(3, j.getHeight());
        assertEquals("""
                ##
                #.
                #.
                """, j.toString());
        j = j.rotateClockwise();
        assertEquals(3, j.getWidth());
        assertEquals(2, j.getHeight());
        assertEquals("""
                ###
                ..#
                """, j.toString());
        j = j.rotateClockwise();
        assertEquals(2, j.getWidth());
        assertEquals(3, j.getHeight());
        assertEquals("""
                .#
                .#
                ##
                """, j.toString());
    }

    @Test
    void initS() {
        Piece s = Piece.createS();
        assertEquals(3, s.getWidth());
        assertEquals(2, s.getHeight());
        assertEquals("""
                .##
                ##.
                """, s.toString());
    }

    @Test
    void rotateClockwiseS() {
        Piece s = Piece.createS();
        s = s.rotateClockwise();
        assertEquals(2, s.getWidth());
        assertEquals(3, s.getHeight());
        assertEquals("""
                #.
                ##
                .#
                """, s.toString());
        s = s.rotateClockwise();
        assertEquals(3, s.getWidth());
        assertEquals(2, s.getHeight());
        assertEquals("""
                .##
                ##.
                """, s.toString());
    }

    @Test
    void initZ() {
        Piece z = Piece.createZ();
        assertEquals(3, z.getWidth());
        assertEquals(2, z.getHeight());
        assertEquals("""
                ##.
                .##
                """, z.toString());
    }

    @Test
    void rotateClockwiseZ() {
        Piece z = Piece.createZ();
        z = z.rotateClockwise();
        assertEquals(2, z.getWidth());
        assertEquals(3, z.getHeight());
        assertEquals("""
                .#
                ##
                #.
                """, z.toString());
        z = z.rotateClockwise();
        assertEquals(3, z.getWidth());
        assertEquals(2, z.getHeight());
        assertEquals("""
                ##.
                .##
                """, z.toString());
    }

    @Test
    void initU() {
        Piece u = Piece.createU();
        assertEquals(3, u.getWidth());
        assertEquals(2, u.getHeight());
        assertEquals("""
                #.#
                ###
                """, u.toString());
    }

    @Test
    void rotateClockwiseU() {
        Piece u = Piece.createU();
        u = u.rotateClockwise();
        assertEquals(2, u.getWidth());
        assertEquals(3, u.getHeight());
        assertEquals("""
                ##
                #.
                ##
                """, u.toString());
        u = u.rotateClockwise();
        assertEquals(3, u.getWidth());
        assertEquals(2, u.getHeight());
        assertEquals("""
                ###
                #.#
                """, u.toString());
        u = u.rotateClockwise();
        assertEquals(2, u.getWidth());
        assertEquals(3, u.getHeight());
        assertEquals("""
                ##
                .#
                ##
                """, u.toString());
        u = u.rotateClockwise();
        assertEquals(3, u.getWidth());
        assertEquals(2, u.getHeight());
        assertEquals("""
                #.#
                ###
                """, u.toString());
    }

    @Test
    void initPoint() {
        Piece point = Piece.createPoint();
        assertEquals(1, point.getWidth());
        assertEquals(1, point.getHeight());
        assertEquals("#\n", point.toString());
    }

    @Test
    void rotateClockwisePoint() {
        Piece point = Piece.createPoint();
        point = point.rotateClockwise();
        assertEquals(1, point.getWidth());
        assertEquals(1, point.getHeight());
        assertEquals("#\n", point.toString());
    }
}