package za.co.wethinkcode.robotworld.server.handlers;


import com.google.gson.JsonObject;


/**
 * Represents an error response that can be sent by the server.
 */
public class ErrorResponse extends Response {

    /**
     * Constructs an ErrorResponse object with the given error message.
     * @param message The error message to be included in the response.
     */
    public ErrorResponse(String message) {
        super("ERROR");
        JsonObject data = new JsonObject();
        data.addProperty("message", message);
        setData(data);
    }

    /**
     * Converts the ErrorResponse object to a JSON representation.
     * @return A JsonObject representing the ErrorResponse.
     */
    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("result", getResult());
        json.add("data", getData());
        return json;
    }
}

