package za.co.wethinkcode.robotworld.common.interfaces;

import za.co.wethinkcode.robotworld.common.interfaces.ICommand;
import za.co.wethinkcode.robotworld.common.position.Position;

/**
 * Represents a response to a command execution.
 */
public interface ICommandResponse {

    /**
     * Gets the position resulting from the command execution.
     * @return The position resulting from the command execution.
     */
    Position getPosition();

    /**
     * Gets the current command associated with the response.
     * @return The current command associated with the response.
     */
    ICommand getCurrentCommand();

    /**
     * Gets the execution status of the command.
     * @return True if the command execution was successful, false otherwise.
     */
    boolean getExecutionStatus();

    /**
     * Sets the execution status of the command.
     * @param executionStatus The execution status to be set.
     */
    void setExecutionStatus(Boolean executionStatus);
}
