package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;

/**
 * Command to shut down the robot world and disconnect all robots.
 */
public class ShutdownCommand extends Command {

    /**
     * Constructs a ShutdownCommand object.
     * The command name is set to "quit".
     */
    public ShutdownCommand() {
        super("quit");
    }

    /**
     * Executes the shutdown command by disconnecting all robots from the world and setting the world status.
     *
     * @param target The robot on which to execute the command (not directly used in this command).
     * @return A CommandResponse object indicating the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        // Set status for the robot
        target.setStatus("Quitting world and disconnecting all robots.");

        // Get the world instance
        IWorld world = target.getWorld();

        // Remove all robots from the world
        for (IRobot robot : world.getAllRobots()) {
            world.removeRobot(robot.getName());
        }

        // Create a CommandResponse indicating successful execution
        CommandResponse response = new CommandResponse(this, target.getPosition());
        response.setExecutionStatus(true);
        return response;
    }
}
