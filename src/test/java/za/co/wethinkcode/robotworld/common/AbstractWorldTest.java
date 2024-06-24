package za.co.wethinkcode.robotworld.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworld.common.position.Position;
import za.co.wethinkcode.robotworld.common.interfaces.*;
import za.co.wethinkcode.robotworld.common.robot.Robot;
import za.co.wethinkcode.robotworld.common.world.AbstractWorld;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldTest {
    private AbstractWorld world;
    private List<IObstacle> obstacles;

    @BeforeEach
    void setUp() {
        obstacles = new ArrayList<>();
        // Create and add mock obstacles if needed
        world = new AbstractWorld(obstacles) {
            @Override
            public List<IObstacle> getObstacles() {
                return obstacles;
            }

            @Override
            public void showObstacles() {
                // Implement if needed
            }
        };
    }

    @Test
    void testAddRobot() {
        Robot robot = new Robot("TestRobot");
        IWorld.UpdateResponse response = world.addRobot(robot);
        assertEquals(IWorld.UpdateResponse.SUCCESS, response, "Robot should be added successfully");

        // Attempt to add the same robot again
        response = world.addRobot(robot);
        assertEquals(IWorld.UpdateResponse.FAILED_SAME_ROBOT_NAME, response, "Should fail due to same robot name");

        // Create another robot and add
        Robot anotherRobot = new Robot("AnotherRobot");
        response = world.addRobot(anotherRobot);
        assertEquals(IWorld.UpdateResponse.SUCCESS, response, "Another robot should be added successfully");
    }

    @Test
    void testRemoveRobot() {
        Robot robot = new Robot("TestRobot");
        world.addRobot(robot);
        assertTrue(world.removeRobot("TestRobot"), "Robot should be removed successfully");
        assertFalse(world.removeRobot("NonExistentRobot"), "Removing a non-existent robot should return false");
    }

    @Test
    void testSetWorldDimensions() {
        world.setWorldDimensions(10, 10);
        assertEquals(new Position(-9, 9), world.getTOP_LEFT(), "Top left corner should be (-9, 9)");
        assertEquals(new Position(9, -9), world.getBOTTOM_RIGHT(), "Bottom right corner should be (9, -9)");
    }

    @Test
    void testIsAtEdge() {
        world.setPosition(new Position(-100, 200)); // Set to top left corner
        assertTrue(world.isAtEdge(), "Position should be at the edge");

        world.setPosition(new Position(0, 0)); // Set to center
        assertFalse(world.isAtEdge(), "Position should not be at the edge");

        world.setPosition(new Position(100, -200)); // Set to bottom right corner
        assertTrue(world.isAtEdge(), "Position should be at the edge");
    }


    @Test
    void testRobotsList() {
        Robot robot1 = new Robot("TestRobot1");
        Robot robot2 = new Robot("TestRobot2");
        world.addRobot(robot1);
        world.addRobot(robot2);

        List<String> robotList = world.robotsList();
        assertTrue(robotList.contains("Name: TestRobot1, State: " + robot1.toString()), "Robot list should contain TestRobot1");
        assertTrue(robotList.contains("Name: TestRobot2, State: " + robot2.toString()), "Robot list should contain TestRobot2");
    }
}

