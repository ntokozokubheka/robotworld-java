package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.common.position.Position;

/**
 * Command to execute a firing action from a robot.
 */
public class FireCommand extends Command {

    /**
     * Constructs a FireCommand object.
     * The command name is set to "fire".
     */
    public FireCommand() {
        super("fire");
    }

    /**
     * Executes the fire command on the specified robot.
     * The robot fires in its current direction for a fixed distance of 5 steps.
     * If the fired position matches another robot's position, the fired robot's shield strength is reduced.
     * If the shield strength drops to zero or below, the robot is removed from the world.
     * The firing robot's shot count and status are updated based on the result of the firing action.
     *
     * @param target The robot on which to execute the fire command.
     * @return A CommandResponse object containing the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        IWorld world = target.getWorld();
        Position startPosition = target.getPosition();
        IWorld.Direction direction = target.getCurrentDirection();

        int fireDistance = 5;
        int steps = fireDistance;

        Position newPosition = startPosition;

        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case RIGHT:
                    newPosition = new Position(newPosition.getX() + 1, newPosition.getY());
                    break;
                case UP:
                    newPosition = new Position(newPosition.getX(), newPosition.getY() + 1);
                    break;
                case LEFT:
                    newPosition = new Position(newPosition.getX() - 1, newPosition.getY());
                    break;
                case DOWN:
                    newPosition = new Position(newPosition.getX(), newPosition.getY() - 1);
                    break;
            }

            // Check if the fired position matches another robot's position
            for (IRobot robot : world.getAllRobots()) {
                if (robot.getPosition().equals(newPosition)) {
                    int currentStrength = robot.getShieldStrength();
                    int newShieldStrength = currentStrength - fireDistance;
                    String statusUpdate = steps + "," + robot.getName() + "," + robot.getStatus();

                    if (newShieldStrength <= 0) {
                        world.removeRobot(robot.getName());
                    } else {
                        robot.setShieldStrength(newShieldStrength);
                    }

                    // Update the firing robot's shots count and status
                    target.setShots(target.getShots() - fireDistance);
                    target.setStatus(statusUpdate);
                    return new CommandResponse(this, startPosition);
                }
            }
            fireDistance--;
        }

        // If no target was hit, update the firing robot's status as missed and shots count to 0
        target.setStatus("missed");
        target.setShots(0);
        return new CommandResponse(this, startPosition);
    }
}
