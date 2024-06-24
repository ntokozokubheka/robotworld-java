package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

/**
 * Command to launch a robot with specified shield strength and maximum shots.
 */
public class LaunchCommand extends Command {

    private int shieldStrength;
    private int maxShots;

    /**
     * Constructs a LaunchCommand object with the specified arguments.
     *
     * @param arg  The shield strength of the robot to launch.
     * @param arg1 The maximum shots the robot can fire.
     * @param arg2 Unused in this implementation.
     */
    public LaunchCommand(String arg, String arg1, String arg2) {
        super("launch");
        this.shieldStrength = Integer.parseInt(arg1);
        this.maxShots = Integer.parseInt(arg2);
    }

    /**
     * Executes the launch command by initializing the specified robot with given shield strength and maximum shots.
     * Sets the initial status of the robot to "NORMAL".
     *
     * @param target The robot to be launched.
     * @return A CommandResponse object containing the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        target.setShieldStrength(this.shieldStrength);
        target.setMaxShots(this.maxShots);
        target.setMaxShieldStrength(this.shieldStrength);
        target.setShots(this.maxShots);
        target.setStatus("NORMAL");

        return new CommandResponse(this, target.getPosition());
    }
}
