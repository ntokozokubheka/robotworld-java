package za.co.wethinkcode.robotworld.server.core;

import za.co.wethinkcode.robotworld.common.interfaces.IObstacle;
import za.co.wethinkcode.robotworld.common.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworld.common.position.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * WorldObstaclesRandom class generates a list of random obstacles for a simulated world.
 */
public class WorldObstaclesRandom {

    private final List<IObstacle> obstacles;

    /**
     * Constructor initializes an empty list of obstacles.
     */
    public WorldObstaclesRandom() {
        this.obstacles = new ArrayList<>();
    }

    /**
     * Generates and returns a list of randomly positioned obstacles.
     * Each obstacle is a SquareObstacle object defined by two diagonal corners.
     *
     * @return List of randomly generated obstacles.
     */
    public List<IObstacle> createWorldObstacles() {
        // Generate some example obstacles (random positions for demonstration)
        this.obstacles.add(new SquareObstacle(new Position(0, 15), new Position(5, 10)));
        this.obstacles.add(new SquareObstacle(new Position(10, 50), new Position(15, 45)));
        this.obstacles.add(new SquareObstacle(new Position(60, 60), new Position(55, 55)));
        this.obstacles.add(new SquareObstacle(new Position(110, 50), new Position(115, 45)));
        return this.obstacles;
    }
}
