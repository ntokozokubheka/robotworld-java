package za.co.wethinkcode.robotworld.server;

import za.co.wethinkcode.robotworld.server.command.BackCommand;
import za.co.wethinkcode.robotworld.common.position.Position;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworld.common.robot.Robot;
import za.co.wethinkcode.robotworld.server.command.CommandResponse;
import za.co.wethinkcode.robotworld.server.core.World;
import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackCommandTest {
//
//    private IRobot robot;
//    private IWorld world;
//
//    @BeforeEach
//    public void setUp() {
//        world = new World(10, 10);
//        robot = new Robot("TestRobot", world);
//        robot.setPosition(5, 5);
//        robot.setDirection("NORTH");
//    }
//
//    @Test
//    public void testExecute_Backward_Success() {
//        BackCommand command = new BackCommand("3");
//
//        CommandResponse response = command.execute(robot);
//
//        assertEquals("Moved back by 3 steps.", robot.getStatus());
//        assertEquals(5, response.getNewPosition().getX());
//        assertEquals(2, response.getNewPosition().getY());
//    }
//
//    @Test
//    public void testExecute_Backward_Obstructed() {
//        // Set up an obstruction at the target position
//        world.setObstruction(5, 2);
//
//        BackCommand command = new BackCommand("3");
//
//        CommandResponse response = command.execute(robot);
//
//        assertEquals("Obstructed", robot.getStatus());
//        assertEquals(5, response.getNewPosition().getX());
//        assertEquals(5, response.getNewPosition().getY()); // Should stay at original position due to obstruction
//    }
//
//    @Test
//    public void testExecute_Backward_ObstructedByRobot() {
//        // Place another robot at the target position
//        IRobot anotherRobot = new Robot("AnotherRobot", world);
//        anotherRobot.setPosition(5, 2);
//
//        BackCommand command = new BackCommand("3");
//
//        CommandResponse response = command.execute(robot);
//
//        assertEquals("Obstructed by a bot", robot.getStatus());
//        assertEquals(5, response.getNewPosition().getX());
//        assertEquals(5, response.getNewPosition().getY()); // Should stay at original position due to obstruction by robot
//    }
//
//    @Test
//    public void testExecute_Backward_OutsideSafeZone() {
//        // Move robot to an edge position (0, 5)
//        robot.setPosition(0, 5);
//
//        BackCommand command = new BackCommand("1");
//
//        CommandResponse response = command.execute(robot);
//
//        assertEquals("Sorry, I cannot go outside my safe zone.", robot.getStatus());
//        assertEquals(0, response.getNewPosition().getX());
//        assertEquals(5, response.getNewPosition().getY()); // Should stay at original position due to safe zone restriction
//    }
}

