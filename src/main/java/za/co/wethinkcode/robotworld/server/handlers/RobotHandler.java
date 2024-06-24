package za.co.wethinkcode.robotworld.server.handlers;

import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.server.command.Command;
import za.co.wethinkcode.robotworld.common.robot.Robot;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import za.co.wethinkcode.robotworld.server.command.StateCommand;
import za.co.wethinkcode.robotworld.server.core.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonArray;
import java.util.Objects;

/**
 * Handles incoming requests from robots and sends appropriate responses.
 * Each instance handles communication with a single robot.
 */
public class RobotHandler implements Runnable {

    private final Socket socket;        // Socket for communication with the robot
    private final World world;          // Shared world instance
    private final Map<String, Robot> robots;  // Map of robots by name
    private boolean hasLaunch;          // Flag indicating if a robot has been launched
    private boolean isWorldSet;         // Flag indicating if world dimensions are set

    /**
     * Constructor for initializing a RobotHandler instance.
     *
     * @param socket The socket for communication with the robot.
     * @param world The shared World instance.
     */
    public RobotHandler(Socket socket, World world) {
        this.socket = socket;
        this.world = world;
        this.robots = new HashMap<>();
    }

    /**
     * Method executed when a thread starts to handle incoming commands from a robot.
     * Reads commands, processes them, and sends back responses.
     */
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JsonObject requestJson = JsonParser.parseString(inputLine).getAsJsonObject();
                Request request = Request.fromJson(requestJson);

                Response response = handleCommandResponse(request);
                out.println(response.toJson().toString());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if the number of arguments in the request matches the expected length for the command.
     *
     * @param commandName The name of the command.
     * @param arguments The arguments provided in the request.
     * @return true if the number of arguments matches the expected length, false otherwise.
     */
    private boolean isValidArgumentLength(String commandName, JsonArray arguments) {
        int expectedLength = -1;

        switch (commandName) {
            case "forward":
            case "back":
            case "turn":
                expectedLength = 1;
                break;

            case "state":
            case "robots":
            case "look":
            case "fire":
            case "dump":
            case "repair":
            case "reload":
            case "quit":
                expectedLength = 0;
                break;

            case "launch":
                expectedLength = 3;
                break;

            case "world":
                expectedLength = 2;
                break;

            default:
                return false;
        }

        return arguments.size() == expectedLength;
    }

    /**
     * Handles the incoming request and generates an appropriate response.
     *
     * @param request The incoming request.
     * @return The response to be sent back to the robot.
     */
    private Response handleCommandResponse(Request request) {
        String simpleCommand = request.getCommand();
        if (!isValidArgumentLength(request.getCommand(), request.getArguments())) {
            return new ErrorResponse("Could not parse arguments");
        }

        // Check if the world dimensions are set
        if (!simpleCommand.equals("world") && !isWorldSet) {
            return new ErrorResponse("World dimensions are not set.");
        }

        // Check if the world dimensions are already set for 'world' command
        if (simpleCommand.equals("world") && isWorldSet) {
            return new ErrorResponse("World dimensions have already been set.");
        }

        // Check if a robot has been launched for non-launch commands
        if (!simpleCommand.equals("launch") && !simpleCommand.equals("world") && !hasLaunch) {
            return new ErrorResponse("Robot has not been launched.");
        }

        // Check if a robot has been launched for 'launch' command
        if (simpleCommand.equals("launch") && hasLaunch) {
            return new ErrorResponse("Robot has already been launched.");
        }

        String fullCommand = buildFullCommand(request);
        Command command = createCommand(fullCommand);
        Robot robot = getOrCreateRobot(request.getRobot());
        robot.setWorld(this.world);

        boolean success = robot.handleCommand(command);

        return switch (request.getCommand()) {
            case "launch" -> handleLaunch(success, request, robot, command, this.world);
            case "forward", "back", "turn" -> handleMovement(success, robot);
            case "state" -> handleState(success, robot);
            case "look" -> handleLook(success, robot);
            case "repair" -> handleRepair(success, robot);
            case "reload" -> handleReload(success, robot);
            case "fire" -> handleFire(success, robot);
            case "robots", "dump", "quit" -> handleRobots(success, robot);
            case "world" -> handleWorld(success, robot);
            default -> new ErrorResponse("Unsupported command");
        };
    }

    /**
     * Retrieves an existing robot from the map by its name, or creates a new one if not found.
     *
     * @param robotName The name of the robot.
     * @return The Robot instance associated with the given name.
     */
    private Robot getOrCreateRobot(String robotName) {
        return robots.getOrDefault(robotName, new Robot(robotName));
    }

    /**
     * Constructs the full command string from the request.
     *
     * @param request The request containing command and arguments.
     * @return The full command string.
     */
    private String buildFullCommand(Request request) {
        String commandName = request.getCommand();
        JsonArray argumentsArray = request.getArguments();

        StringBuilder argumentsBuilder = new StringBuilder();
        for (int i = 0; i < argumentsArray.size(); i++) {
            if (i > 0) {
                argumentsBuilder.append(" ");
            }
            argumentsBuilder.append(argumentsArray.get(i).getAsString());
        }

        return commandName + " " + argumentsBuilder.toString();
    }

    /**
     * Creates a Command instance from the full command string.
     *
     * @param fullCommand The full command string.
     * @return The Command instance created from the command string.
     */
    private Command createCommand(String fullCommand) {
        return Command.create(fullCommand);
    }

    /**
     * Handles the response for the 'launch' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param request The original request object.
     * @param robot The Robot instance associated with the request.
     * @param command The Command instance executed.
     * @param world The World instance.
     * @return The response for the 'launch' command.
     */
    private Response handleLaunch(Boolean success, Request request, Robot robot, Command command, IWorld world) {
        if (request.getArguments().toString().contains("default")) {
            if (!robots.containsKey(request.getRobot())) {
                robot.setName(request.getRobot());
                IWorld.UpdateResponse addRobotResponse = world.addRobot(robot);

                if (addRobotResponse == IWorld.UpdateResponse.FAILED_NO_SPACE) {
                    return new ErrorResponse("There is no space in the world.");
                } else if (addRobotResponse == IWorld.UpdateResponse.FAILED_SAME_ROBOT_NAME) {
                    return new ErrorResponse("Too many of you in this world.");
                }

                robots.put(request.getRobot(), robot);
            }

            JsonObject data = new JsonObject();
            data.addProperty("position", "[" + robot.getPosition().getX() + ", " + robot.getPosition().getY() + "]");
            data.addProperty("max visibility", world.getVisibility() + " steps");
            data.addProperty("max reload", world.getVisibility() + " seconds");
            data.addProperty("max repair", world.getRepairTime() + " seconds");
            data.addProperty("max shields", world.getSHEILDSTRENGTH() + " hits");

            JsonObject state = new JsonObject();
            state.addProperty("position", "[" + robot.getPosition().getX() + ", " + robot.getPosition().getY() + "]");
            state.addProperty("direction", robot.getCurrentDirection().toString());
            state.addProperty("shields", robot.getShieldStrength());
            state.addProperty("shots", robot.getMaxShots() + " shots");
            state.addProperty("status", "NORMAL");

            LaunchResponse launchResponse = new LaunchResponse(data, state);
            this.hasLaunch = true;

            return launchResponse;
        } else {
            return new ErrorResponse("You can only launch the default robot into the world.");
        }
    }

    /**
     * Handles the response for movement commands ('forward', 'back', 'turn').
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for movement commands.
     */
    private Response handleMovement(Boolean success, Robot robot) {
        JsonObject state = new JsonObject();
        state.addProperty("status", robot.getStatus());

        if (success && !robot.getStatus().contains("Obstructed")) {
            return new MovementResponse("Done", state);
        }
        else if (robot.getStatus().contains("Obstructed")) {
            return new MovementResponse("Done", state);
        } else {
            return new ErrorResponse("Error: Could not support command");
        }
    }

    /**
     * Handles the response for the 'look' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'look' command.
     */
    private Response handleLook(Boolean success, Robot robot) {
        JsonArray objectsArray = new JsonArray();
        JsonObject state = new JsonObject();

        if (!robot.getStatus().contains("or")) {
            String[] array = robot.getStatus().split(",");

            JsonObject objJson = new JsonObject();
            objJson.addProperty("direction", robot.getCurrentDirection().toString());
            objJson.addProperty("type", array[0]);
            objJson.addProperty("distance", array[1]);
            objectsArray.add(objJson);

            state.addProperty("status", "Object found");
        } else {
            state.addProperty("status", "No robots or obstacles in sight.");
        }

        JsonObject data = new JsonObject();
        data.add("objects", objectsArray);

        return new LaunchResponse(data, state);
    }

    /**
     * Handles the response for the 'state' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'state' command.
     */
    private Response handleState(Boolean success, Robot robot) {
        JsonObject state = new JsonObject();

        state.addProperty("position", "[" + robot.getPosition().getX() + ", " + robot.getPosition().getY() + "]");
        state.addProperty("direction", robot.getCurrentDirection().toString());
        state.addProperty("shields", robot.getShieldStrength());
        state.addProperty("shots", robot.getShots());
        state.addProperty("status", "NORMAL");

        return new StateResponse(state);
    }

    /**
     * Handles the response for the 'repair' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'repair' command.
     */
    private Response handleRepair(Boolean success, Robot robot) {
        JsonObject data = new JsonObject();
        data.addProperty("message", "Done");

        JsonObject state = new JsonObject();
        state.addProperty("status", robot.getStatus());

        return new LaunchResponse(data, state);
    }

    /**
     * Handles the response for the 'reload' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'reload' command.
     */
    private Response handleReload(Boolean success, Robot robot) {
        JsonObject data = new JsonObject();
        data.addProperty("message", "Done");

        JsonObject state = new JsonObject();
        state.addProperty("status", "RELOAD");

        return new LaunchResponse(data, state);
    }

    /**
     * Handles the response for the 'fire' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'fire' command.
     */
    private Response handleFire(Boolean success, Robot robot) {
        JsonObject data = new JsonObject();
        JsonObject state = new JsonObject();

        if (!robot.getStatus().contains("missed")) {
            String[] parts = robot.getStatus().split(",");

            data.addProperty("message", "Hit");
            data.addProperty("distance", parts[0]);
            data.addProperty("robot", parts[1]);
            data.addProperty("state", "{" + parts[2] + "}");
        } else {
            data.addProperty("message", "Miss");
        }

        state.addProperty("shots", robot.getShots());
        return new LaunchResponse(data, state);
    }

    /**
     * Handles the response for the 'robots', 'dump', and 'quit' commands.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'robots', 'dump', or 'quit' command.
     */
    private Response handleRobots(Boolean success, Robot robot) {
        JsonObject data = new JsonObject();
        data.addProperty("message", "Done");

        JsonObject state = new JsonObject();
        state.addProperty("status", robot.getStatus());

        return new LaunchResponse(data, state);
    }

    /**
     * Handles the response for the 'world' command.
     *
     * @param success Indicates if the command execution was successful.
     * @param robot The Robot instance associated with the command.
     * @return The response for the 'world' command.
     */
    private Response handleWorld(Boolean success, Robot robot) {
        JsonObject data = new JsonObject();
        data.addProperty("message", "Done");

        JsonObject state = new JsonObject();
        state.addProperty("status", robot.getStatus());

        this.isWorldSet = true;
        return new LaunchResponse(data, state);
    }
}
