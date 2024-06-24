package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

/**
 * Command to configure the dimensions of the world in which the robot operates.
 */
public class ConfigureWorldCommand extends Command {

    private int height;
    private int width;

    /**
     * Constructs a ConfigureWorldCommand with the specified height and width.
     *
     * @param height The height of the world to configure.
     * @param width  The width of the world to configure.
     */
    public ConfigureWorldCommand(String height, String width) {
        super("configure-world");
        this.height = Integer.parseInt(height);
        this.width = Integer.parseInt(width);
    }

    /**
     * Executes the configure world command on the specified robot.
     * Sets the dimensions of the world to the specified height and width.
     * Updates the robot's status accordingly.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object containing the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        target.getWorld().setWorldDimensions(this.height, this.width);
        target.setStatus("World has been configured to a height of : " + this.height + " and width of : " + this.width);
        return new CommandResponse(this, target.getPosition());
    }
}
