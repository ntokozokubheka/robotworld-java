package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;

/**
 * Represents a response indicating a firing action.
 */
public class FireResponse extends Response {
    private String message;

    /**
     * Constructs a FireResponse object with the given message, data, and state.
     * @param message The message to be included in the response.
     * @param data The data to be included in the response.
     * @param state The state to be included in the response.
     */
    public FireResponse(String message, JsonObject data, JsonObject state) {
        super("OK");
        this.message = message;
        setData(data);
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
     * Converts the FireResponse object to a JSON representation.
     * @return A JsonObject representing the FireResponse.
     */
    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("result", getResult());
        json.add("data", getData());
        json.add("state", getState());
        return json;
    }
}
