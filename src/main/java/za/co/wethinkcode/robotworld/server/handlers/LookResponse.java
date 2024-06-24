package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;

/**
 * Represents a response indicating the result of a "look" operation.
 */
public class LookResponse extends Response {
    /**
     * Constructs a LookResponse object with the given data and state.
     * @param data The data to be included in the response.
     * @param state The state to be included in the response.
     */
    public LookResponse(JsonObject data, JsonObject state) {
        super("OK");
        setData(data);
        setState(state);
    }

    /**
     * Converts the LookResponse object to a JSON representation.
     * @return A JsonObject representing the LookResponse.
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
