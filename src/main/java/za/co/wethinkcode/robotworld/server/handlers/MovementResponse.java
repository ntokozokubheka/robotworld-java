package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;

/**
 * Represents a response indicating a movement operation.
 */
public class MovementResponse extends Response {
    private String message;

    /**
     * Constructs a MovementResponse object with the given message and state.
     * @param message The message to be included in the response.
     * @param state The state to be included in the response.
     */
    public MovementResponse(String message, JsonObject state) {
        super("OK");
        this.message = message;
        setState(state);
    }

    /**
     * Gets the message associated with the response.
     * @return The message associated with the response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Converts the MovementResponse object to a JSON representation.
     * @return A JsonObject representing the MovementResponse.
     */
    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("result", getResult());
        json.addProperty("message", message);
        json.add("state", getState());
        return json;
    }
}
