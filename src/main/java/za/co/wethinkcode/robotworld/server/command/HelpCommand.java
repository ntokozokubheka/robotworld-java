package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.robot.Robot;
import za.co.wethinkcode.robotworld.common.interfaces.*;

/**
 * This class represents a command to provide information about available commands.
 */
public class HelpCommand extends Command {

    /**
     * Initializes the help command.
     */
    public HelpCommand() {
        super("help");
    }

    /**
     * Executes the help command on the specified target robot.
     *
     * @param target The target robot to execute the command on.
     * @return The response from the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        target.setStatus("I can understand these commands:\n" +
                "OFF  - Shut down robot\n" +
                "HELP - provide information about commands\n" +
                "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'");
        return  new CommandResponse(this, target.getPosition());
    }
}
