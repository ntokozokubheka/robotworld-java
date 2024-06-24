package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.robot.Robot;
import za.co.wethinkcode.robotworld.common.interfaces.*;
import java.util.List;

/**
 * This class represents a command to dump the current state of the robot world.
 */
public class DumpCommand extends Command {

    /**
     * Initializes a new instance of the DumpCommand class.
     */
    public DumpCommand() {
        super("Dump");
    }

    /**
     * Executes the dump command on a specified robot.
     *
     * @param target The robot to dump the state of.
     * @return A response indicating the result of the command execution.
     */
    @Override
    public CommandResponse execute(IRobot target) {

        String myList = "";
        // Get the list of robot names and states
        List<String> robotList = target.getWorld().robotsList();

        // Print each robot's name and state on a new line
        for (String robotInfo : robotList) {
            myList += "{" + robotInfo + "} ";
        }
        for (IObstacle obstacle : target.getWorld().getObstacles()) {

            int x = obstacle.getBottomLeftX();
            int y = obstacle.getBottomLeftY() + obstacle.getSize();

            myList += "{Square obstacle , size  :{" + obstacle.getSize() + "} , top left X :[" + x + "]  top left Y :[" + y + "] } ";
        }
        target.setStatus(myList);

        return new CommandResponse(this, target.getPosition());
    }

}
