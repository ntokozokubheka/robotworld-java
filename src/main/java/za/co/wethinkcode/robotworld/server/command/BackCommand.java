package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.robot.Robot;
import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;

/**
 * Command to move the robot backward a specified number of steps.
 */
public class BackCommand extends Command {

    /**
     * Constructs a new BackCommand object with the specified argument.
     *
     * @param argument The number of steps to move backward.
     */
    public BackCommand(String argument) {
        super("back", argument);
    }

    /**
     * Executes the back command on the specified robot.
     * Moves the robot backward by the number of steps specified in the command argument.
     * Updates the robot's position and status based on the movement result.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object containing the executed command and the robot's new position.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        int nrSteps = Integer.parseInt(getArgument());
        target.setWorldPosition(target.getPosition());
        target.getWorld().setDirection(target.getCurrentDirection());

        IWorld.UpdateResponse result = null;
        for (int i = 0; i < nrSteps; i++) {
            result = target.getWorld().updatePosition(-1);
        }
        if (result == IWorld.UpdateResponse.SUCCESS) {
            target.setPosition(target.getWorld().getPosition());
            target.setStatus("Moved back by " + Math.abs(nrSteps) + " steps."); // Use Math.abs() to display positive steps
        } else if (result == IWorld.UpdateResponse.FAILED_OBSTRUCTED) {
            target.setStatus("Obstructed");
        } else if (result == IWorld.UpdateResponse.FAILED_OBSTRUCTED_ROBOT) {
            target.setStatus("Obstructed by a bot");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return new CommandResponse(this, target.getPosition());
    }
}
