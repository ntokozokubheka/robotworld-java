package za.co.wethinkcode.robotworld.common;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.robotworld.common.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworld.common.position.Position;

public class SquareObstacleTest {

    @Test
    public void testInitialization() {
        Position topLeft = new Position(10, 10);
        Position bottomRight = new Position(15, 5);
        SquareObstacle obstacle = new SquareObstacle(topLeft, bottomRight);

        assertEquals(10, obstacle.getBottomLeftX());
        assertEquals(5, obstacle.getBottomLeftY());
        assertEquals(5, obstacle.getSize());
    }

    @Test
    public void testBlocksPosition() {
        Position topLeft = new Position(10, 10);
        Position bottomRight = new Position(15, 5);
        SquareObstacle obstacle = new SquareObstacle(topLeft, bottomRight);

        // Positions inside the obstacle
        assertTrue(obstacle.blocksPosition(new Position(10, 5)));
        assertTrue(obstacle.blocksPosition(new Position(12, 7)));
        assertTrue(obstacle.blocksPosition(new Position(14, 9)));

        // Positions outside the obstacle
        assertFalse(obstacle.blocksPosition(new Position(9, 5)));
        assertFalse(obstacle.blocksPosition(new Position(10, 4)));
        assertFalse(obstacle.blocksPosition(new Position(15, 10)));
        assertFalse(obstacle.blocksPosition(new Position(16, 6)));
    }

}

