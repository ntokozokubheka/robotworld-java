package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.position.Position;
import za.co.wethinkcode.robotworld.common.interfaces.*;

/**
 * This class represents a response to a command execution.
 */
public class CommandResponse implements ICommandResponse {

    private final Command currentCommand;
    private final Position position;
    private boolean commandExecutionStatus;

    /**
     * Initializes a new instance of the CommandResponse class.
     *
     * @param command The command that was executed.
     * @param position The position of the robot after the command execution.
     */
    public CommandResponse(Command command, Position position) {
        this.currentCommand = command;
        this.position = position;
        this.commandExecutionStatus = true;
    }

    /**
     * Gets the position of the robot after the command execution.
     *
     * @return The position of the robot.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Gets the command that was executed.
     *
     * @return The command.
     */
    public Command getCurrentCommand() {
        return this.currentCommand;
    }

    /**
     * Gets the status of the command execution.
     *
     * @return The status of the command execution.
     */
    public boolean getExecutionStatus() {
        return this.commandExecutionStatus;
    }

    /**
     * Sets the status of the command execution.
     *
     * @param executionStatus The new status of the command execution.
     */
    public void setExecutionStatus(Boolean executionStatus) {
        this.commandExecutionStatus = executionStatus;
    }
}
