package za.co.wethinkcode.robotworld.common;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworld.common.position.Position;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testConstructorAndGetters() {
        Position position = new Position(3, 5);
        assertEquals(3, position.getX());
        assertEquals(5, position.getY());
    }

    @Test
    void testEqualsSameObject() {
        Position position = new Position(3, 5);
        assertTrue(position.equals(position));
    }

    @Test
    void testEqualsNull() {
        Position position = new Position(3, 5);
        assertFalse(position.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        Position position = new Position(3, 5);
        assertFalse(position.equals("Not a Position"));
    }

    @Test
    void testEqualsDifferentX() {
        Position position1 = new Position(3, 5);
        Position position2 = new Position(4, 5);
        assertFalse(position1.equals(position2));
    }

    @Test
    void testEqualsDifferentY() {
        Position position1 = new Position(3, 5);
        Position position2 = new Position(3, 6);
        assertFalse(position1.equals(position2));
    }

    @Test
    void testEqualsSameCoordinates() {
        Position position1 = new Position(3, 5);
        Position position2 = new Position(3, 5);
        assertTrue(position1.equals(position2));
    }

    @Test
    void testIsInBoundary() {
        Position position = new Position(3, 5);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertTrue(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testIsInBoundaryTopEdge() {
        Position position = new Position(3, 6);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertTrue(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testIsInBoundaryBottomEdge() {
        Position position = new Position(3, 4);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertTrue(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testIsInBoundaryLeftEdge() {
        Position position = new Position(2, 5);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertTrue(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testIsInBoundaryRightEdge() {
        Position position = new Position(4, 5);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertTrue(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testIsNotInBoundary() {
        Position position = new Position(1, 5);
        Position topLeft = new Position(2, 6);
        Position bottomRight = new Position(4, 4);
        assertFalse(position.isIn(topLeft, bottomRight));
    }

    @Test
    void testToString() {
        Position position = new Position(3, 5);
        assertEquals("[3,5]", position.toString());
    }
}

