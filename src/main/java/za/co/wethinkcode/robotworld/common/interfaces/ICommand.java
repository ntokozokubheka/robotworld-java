package za.co.wethinkcode.robotworld.common.interfaces;

import za.co.wethinkcode.robotworld.common.robot.Robot;

/**
 * Represents a command that can be executed by a robot.
 */
public interface ICommand {

    /**
     * Gets the name of the command.
     * @return The name of the command.
     */
    String getName();

    /**
     * Gets the argument of the command.
     * @return The argument of the command.
     */
    String getArgument();

    /**
     * Executes the command on the specified target robot.
     * @param target The robot on which the command is to be executed.
     * @return The response to the command execution.
     */
    ICommandResponse execute(IRobot target);
}
