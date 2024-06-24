package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robotworld.server.command.CommandResponse;

/**
 * Represents a response indicating a successful operation.
 */
public class SuccessResponse extends Response {

    /**
     * Constructs a SuccessResponse object with the given command response.
     * @param commandResponse The command response containing the details of the successful operation.
     */
    public SuccessResponse(CommandResponse commandResponse) {
        super("OK");
        setResult("OK");
        JsonObject data = new JsonObject();
        data.addProperty("command", commandResponse.getCurrentCommand().getName());
        data.addProperty("position", commandResponse.getPosition().toString());
        setData(data);

        JsonObject state = new JsonObject();
        state.addProperty("position", commandResponse.getPosition().toString());
        state.addProperty("status", "NORMAL");
        setState(state);
    }

    /**
     * Converts the SuccessResponse object to a JSON representation.
     * @return A JsonObject representing the SuccessResponse.
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
