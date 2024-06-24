package za.co.wethinkcode.robotworld.server.handlers;

class UnsupportedCommandResponse extends ErrorResponse {
    public UnsupportedCommandResponse() {
        super("Unsupported command");
    }
}
