package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Represents a request received by the server.
 */
public class Request {
    private String robot;
    private String command;
    private JsonArray arguments;

    /**
     * Constructs a Request object from a JsonObject.
     * @param json The JsonObject containing the request data.
     * @return A Request object constructed from the JsonObject.
     */
    public static Request fromJson(JsonObject json) {
        Request request = new Request();
        request.robot = json.get("robot").getAsString();
        request.command = json.get("command").getAsString();
        request.arguments = json.getAsJsonArray("arguments");
        return request;
    }

    /**
     * Gets the robot associated with the request.
     * @return The robot associated with the request.
     */
    public String getRobot() {
        return robot;
    }

    /**
     * Gets the command associated with the request.
     * @return The command associated with the request.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Gets the arguments associated with the request.
     * @return The arguments associated with the request.
     */
    public JsonArray getArguments() {
        return arguments;
    }
}
