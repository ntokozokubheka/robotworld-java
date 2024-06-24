package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;

/**
 * Command to move the robot forward by a specified number of steps.
 */
public class ForwardCommand extends Command {

    /**
     * Constructs a ForwardCommand object with the specified argument.
     *
     * @param argument The number of steps to move forward.
     */
    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    /**
     * Executes the forward command on the specified robot.
     * Moves the robot forward by the number of steps specified in the command argument.
     * Updates the robot's position and status based on the movement result.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object containing the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        int nrSteps = Integer.parseInt(getArgument());
        target.setWorldPosition(target.getPosition());
        target.getWorld().setDirection(target.getCurrentDirection());

        IWorld.UpdateResponse result = null;
        for (int i = 0; i < nrSteps; i++) {
            result = target.getWorld().updatePosition(1);
        }

        if (result == IWorld.UpdateResponse.SUCCESS) {
            target.setPosition(target.getWorld().getPosition());
            target.setStatus("Moved forward by " + nrSteps + " steps.");
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
