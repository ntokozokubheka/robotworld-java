package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

/**
 * Represents a command to retrieve and update the state of a robot.
 */
public class StateCommand extends Command {

    /**
     * Constructor for the StateCommand class.
     * Initializes the command name to "state".
     */
    public StateCommand() {
        super("state");
    }

    /**
     * Executes the state command to retrieve and update the state of the executing robot.
     * @param target The robot executing the command.
     * @return A CommandResponse containing the updated state of the robot.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        // Update the status of the executing robot with its current status
        target.setStatus(target.getStatus());

        // Create a CommandResponse containing the updated state of the robot
        CommandResponse response = new CommandResponse(this, target.getPosition());

        // Return the CommandResponse
        return response;
    }
}
