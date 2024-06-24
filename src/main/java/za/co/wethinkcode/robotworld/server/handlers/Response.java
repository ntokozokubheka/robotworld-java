package za.co.wethinkcode.robotworld.server.handlers;

import com.google.gson.JsonObject;

/**
 * Abstract class representing a response from a server.
 * This class encapsulates a result string, optional data, and state information.
 */
public abstract class Response {

    private String result;      // Result of the response
    private JsonObject data;    // Optional data associated with the response
    private JsonObject state;   // State information of the response

    /**
     * Constructor to initialize the response with a result string.
     *
     * @param result The result string of the response.
     */
    public Response(String result) {
        this.result = result;
    }

    /**
     * Getter for the result string of the response.
     *
     * @return The result string.
     */
    public String getResult() {
        return result;
    }

    /**
     * Setter for the result string of the response.
     *
     * @param result The result string to set.
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Getter for the optional data associated with the response.
     *
     * @return The JsonObject containing data associated with the response.
     */
    public JsonObject getData() {
        return data;
    }

    /**
     * Setter for the optional data associated with the response.
     *
     * @param data The JsonObject containing data to set.
     */
    public void setData(JsonObject data) {
        this.data = data;
    }

    /**
     * Getter for the state information of the response.
     *
     * @return The JsonObject containing state information.
     */
    public JsonObject getState() {
        return state;
    }

    /**
     * Setter for the state information of the response.
     *
     * @param state The JsonObject containing state information to set.
     */
    public void setState(JsonObject state) {
        this.state = state;
    }

    /**
     * Abstract method to convert the response object to a JsonObject representation.
     *
     * @return The JsonObject representing the response.
     */
    public abstract JsonObject toJson();
}
