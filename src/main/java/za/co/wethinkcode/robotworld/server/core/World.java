package za.co.wethinkcode.robotworld.server.core;

import za.co.wethinkcode.robotworld.common.interfaces.IObstacle;
import za.co.wethinkcode.robotworld.common.world.AbstractWorld;

import java.util.List;

/**
 * Represents the world in which robots operate, extending the AbstractWorld class.
 * This class initializes the world with given obstacles and inherits functionality
 * for managing robots' positions, directions, and interactions within the world.
 */
public class World extends AbstractWorld {

    /**
     * Constructs a new World instance with the specified obstacles.
     *
     * @param obstacles The list of obstacles to populate the world with.
     */
    public World(List<IObstacle> obstacles) {
        super(obstacles);
    }

}
