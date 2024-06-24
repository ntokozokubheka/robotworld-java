package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import java.util.concurrent.TimeUnit;

/**
 * Represents a command to repair the shield strength of a robot.
 */
public class RepairCommand extends Command{

    /**
     * Constructs a RepairCommand object.
     */
    public RepairCommand() {
        super("repair");
    }

    /**
     * Executes the repair command to restore the shield strength of the target robot.
     * @param target The target robot on which the command is to be executed.
     * @return The response to the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {
        try {
            long repairTime = Math.round(target.getWorld().getRepairTime());
            TimeUnit.SECONDS.sleep(repairTime); // Sleep for the specified repair time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        target.setShieldStrength(target.getMaxShieldStrength());
        target.setStatus("REPAIR");
        return new CommandResponse(this, target.getPosition());
    }
}
