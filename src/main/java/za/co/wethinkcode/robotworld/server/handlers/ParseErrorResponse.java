package za.co.wethinkcode.robotworld.server.handlers;

/**
 * Represents an error response indicating failure to parse arguments.
 */
public class ParseErrorResponse extends ErrorResponse {

    /**
     * Constructs a ParseErrorResponse object with a default error message.
     */
    public ParseErrorResponse() {
        super("Could not parse arguments");
    }
}

