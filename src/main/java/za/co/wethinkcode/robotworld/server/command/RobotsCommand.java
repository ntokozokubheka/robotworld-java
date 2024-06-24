package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

import java.util.List;

/**
 * Represents a command for retrieving the list of robots in the world.
 */
public class RobotsCommand extends Command {

    /**
     * Constructor for the RobotsCommand class.
     * Initializes the command name to "robots".
     */
    public RobotsCommand() {
        super("robots");
    }

    /**
     * Executes the robots command to retrieve the list of robots in the world.
     * @param target The robot executing the command.
     * @return A CommandResponse containing the list of robots and their states.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        // String to store the list of robots and their states
        String robotListString = "";

        // Get the list of robot names and states
        List<String> robotList = target.getWorld().robotsList();

        // Concatenate each robot's name and state to the robotListString
        for (String robotInfo : robotList) {
            robotListString += " {" + robotInfo + "}";
        }

        // Set the status of the executing robot to the robotListString
        target.setStatus(robotListString);

        // Return a CommandResponse indicating the result of the command execution
        return new CommandResponse(this, target.getPosition());
    }
}
