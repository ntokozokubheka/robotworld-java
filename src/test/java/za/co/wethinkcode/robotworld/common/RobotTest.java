package za.co.wethinkcode.robotworld.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.common.position.Position;
import za.co.wethinkcode.robotworld.common.robot.Robot;
import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    private Robot robot;

    @BeforeEach
    public void setUp() {
        robot = new Robot("TestRobot");
    }

    @Test
    public void testInitialization() {
        assertEquals("TestRobot", robot.getName());
        assertEquals("Ready", robot.getStatus());
        assertEquals(new Position(0, 0), robot.getPosition());
        assertEquals(IWorld.Direction.UP, robot.getCurrentDirection());
    }

    @Test
    public void testGetMaxShots() {
        robot.setMaxShots(10);
        assertEquals(10, robot.getMaxShots());
    }

    @Test
    public void testSettersAndGettersInherited() {
        robot.setShots(5);
        assertEquals(5, robot.getShots());

        robot.setShieldStrength(3);
        assertEquals(3, robot.getShieldStrength());

        robot.setFireDistance(10);
        assertEquals(10, robot.getFireDistance());

        robot.setMaxShieldStrength(4);
        assertEquals(4, robot.getMaxShieldStrength());
    }

    @Test
    public void testBlocksPathInherited() {
        robot.setPosition(new Position(10, 10));

        assertTrue(robot.blocksPath(new Position(9, 10), new Position(11, 10)));
        assertTrue(robot.blocksPath(new Position(10, 9), new Position(10, 11)));
        assertFalse(robot.blocksPath(new Position(11, 11), new Position(12, 12)));
        assertFalse(robot.blocksPath(new Position(9, 9), new Position(8, 8)));
    }
}


