package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.common.interfaces.IObstacle;
import za.co.wethinkcode.robotworld.common.position.Position;

/**
 * Command to scan the surroundings of the robot and report any nearby robots or obstacles.
 */
public class LookCommand extends Command {

    /**
     * Constructs a LookCommand object.
     * The command name is set to "look".
     */
    public LookCommand() {
        super("look");
    }

    /**
     * Executes the look command on the specified robot.
     * Scans in all four cardinal directions for obstacles and other robots within the visibility range.
     * Updates the robot's status with the findings.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object containing the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        StringBuilder status = new StringBuilder("[");
        boolean foundObstacleOrRobot = false;

        // Define directions and corresponding movements
        IWorld.Direction[] directions = {
                IWorld.Direction.UP,
                IWorld.Direction.RIGHT,
                IWorld.Direction.DOWN,
                IWorld.Direction.LEFT
        };

        String[] directionNames = {"North", "East", "South", "West"};

        for (int dirIndex = 0; dirIndex < directions.length; dirIndex++) {
            IWorld.Direction direction = directions[dirIndex];
            String directionName = directionNames[dirIndex];

            // Save original position and direction
            Position originalPosition = target.getPosition();
            IWorld.Direction originalDirection = target.getCurrentDirection();

            int steps = 0;
            boolean shouldBreak = false;

            while (steps <= target.getWorld().getVisibility()) {
                // Calculate the new position based on the current direction
                Position newPosition = calculateNewPosition(originalPosition, direction, steps);

                // Check if the new position is within the world boundaries
                if (!newPosition.isIn(target.getWorld().getTOP_LEFT(), target.getWorld().getBOTTOM_RIGHT())) {
                    break;
                }

                // Check for robots at the new position
                for (IRobot robot : target.getWorld().getAllRobots()) {
                    if (robot.blocksPosition(newPosition) && !robot.equals(target)) {
                        status.append(String.format("[robot, %s, %d]", directionName, steps));
                        foundObstacleOrRobot = true;
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) break;

                // Check for obstacles at the new position
                for (IObstacle obstacle : target.getWorld().getObstacles()) {
                    if (obstacle.blocksPosition(newPosition)) {
                        status.append(String.format("[obstacle, %s, %d]", directionName, steps));
                        foundObstacleOrRobot = true;
                        shouldBreak = true;
                        break;
                    }
                }
                if (shouldBreak) break;

                steps++;
            }

            // Restore original position and direction
            target.setWorldPosition(originalPosition);
            target.getWorld().setDirection(originalDirection);
        }

        // Append final status message
        if (!foundObstacleOrRobot) {
            status.append("No robot or obstacle in sight");
        } else {
            status.deleteCharAt(status.length() - 1);  // Remove trailing comma
        }
        status.append("]");

        // Set the robot's status with the compiled status message
        target.setStatus(status.toString());
        return new CommandResponse(this, target.getPosition());
    }

    /**
     * Calculates the new position based on the current position and direction.
     *
     * @param position The current position of the robot.
     * @param direction The direction in which to move.
     * @param nrSteps The number of steps to move in the specified direction.
     * @return The new position after moving in the specified direction.
     */
    private Position calculateNewPosition(Position position, IWorld.Direction direction, int nrSteps) {
        int currentX = position.getX();
        int currentY = position.getY();

        return switch (direction) {
            case UP -> new Position(currentX, currentY + nrSteps);
            case DOWN -> new Position(currentX, currentY - nrSteps);
            case LEFT -> new Position(currentX - nrSteps, currentY);
            case RIGHT -> new Position(currentX + nrSteps, currentY);
            default -> throw new IllegalStateException("Unknown direction: " + direction);
        };
    }
}
