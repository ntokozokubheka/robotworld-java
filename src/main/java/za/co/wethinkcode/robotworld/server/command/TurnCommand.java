package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import java.util.Objects;

/**
 * Represents a command to turn a robot in the specified direction.
 */
public class TurnCommand extends Command {

    /**
     * Constructor for the TurnCommand class.
     * Initializes the command name to "turn" and sets the direction argument.
     * @param argument The direction in which to turn the robot ("left" or "right").
     */
    public TurnCommand(String argument) {
        super("turn", argument);
    }

    /**
     * Executes the turn command to change the direction of the executing robot.
     * @param target The robot executing the command.
     * @return A CommandResponse indicating the success of the turn operation.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        // Save the current direction of the world
        target.getWorld().setDirection(target.getCurrentDirection());

        // Check if the argument is "right"
        if (Objects.equals(this.getArgument(), "right")) {
            // Update the direction of the world based on the current direction
            switch (target.getWorld().getCurrentDirection()) {
                case UP:
                    target.getWorld().setDirection(IWorld.Direction.RIGHT);
                    break;
                case DOWN:
                    target.getWorld().setDirection(IWorld.Direction.LEFT);
                    break;
                case LEFT:
                    target.getWorld().setDirection(IWorld.Direction.UP);
                    break;
                case RIGHT:
                    target.getWorld().setDirection(IWorld.Direction.DOWN);
                    break;
            }
            // Set the direction of the executing robot to the new direction
            target.setDirection(target.getWorld().getCurrentDirection());
            // Update the status of the executing robot
            target.setStatus("Turned right.");
        } else {
            // Update the direction of the world based on the current direction
            switch (target.getWorld().getCurrentDirection()) {
                case UP:
                    target.getWorld().setDirection(IWorld.Direction.LEFT);
                    break;
                case DOWN:
                    target.getWorld().setDirection(IWorld.Direction.RIGHT);
                    break;
                case LEFT:
                    target.getWorld().setDirection(IWorld.Direction.DOWN);
                    break;
                case RIGHT:
                    target.getWorld().setDirection(IWorld.Direction.UP);
                    break;
            }
            // Set the direction of the executing robot to the new direction
            target.setDirection(target.getWorld().getCurrentDirection());
            // Update the status of the executing robot
            target.setStatus("Turned left.");
        }
        // Create a CommandResponse indicating the success of the turn operation
        return new CommandResponse(this, target.getPosition());
    }
}
