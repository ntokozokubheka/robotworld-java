package za.co.wethinkcode.robotworld.server.command;

import java.util.concurrent.TimeUnit;  // Import for TimeUnit

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

/**
 * Command to reload the shots of a robot to its maximum capacity.
 */
public class ReloadCommand extends Command {

    /**
     * Constructs a ReloadCommand object.
     * The command name is set to "reload".
     */
    public ReloadCommand() {
        super("reload");
    }

    /**
     * Executes the reload command by setting the robot's shots to its maximum capacity
     * and simulating a reload time.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object indicating the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        // Reset the robot's shots to its maximum capacity
        target.setShots(target.getMaxShots());

        try {
            // Simulate reload time based on the world's reload time setting
            long reloadTime = Math.round(target.getWorld().getReloadTime());
            TimeUnit.SECONDS.sleep(reloadTime);  // Sleep for the calculated reload time
        } catch (InterruptedException e) {
            e.printStackTrace();  // Print stack trace if interrupted
        }

        // Set status indicating reload completion
        target.setStatus("RELOAD");
        return new CommandResponse(this, target.getPosition());  // Return CommandResponse indicating successful execution
    }
}
