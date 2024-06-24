package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;

/**
 * Represents a response containing the current state.
 */
public class StateResponse extends Response {

    /**
     * Constructs a StateResponse object with the given state.
     * @param state The state to be included in the response.
     */
    public StateResponse(JsonObject state) {
        super("OK");
        setState(state);
    }

    /**
     * Converts the StateResponse object to a JSON representation.
     * @return A JsonObject representing the StateResponse.
     */
    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("result", getResult());
        json.add("state", getState());
        return json;
    }
}
