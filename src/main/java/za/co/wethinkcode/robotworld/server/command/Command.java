package za.co.wethinkcode.robotworld.server.command;

import za.co.wethinkcode.robotworld.common.interfaces.ICommand;
import za.co.wethinkcode.robotworld.common.interfaces.IRobot;

/**
 * Abstract base class for implementing commands that can be executed on a robot.
 */
public abstract class Command implements ICommand {

    private final String name;
    private String argument;

    /**
     * Constructs a command with a specified name.
     *
     * @param name The name of the command.
     */
    public Command(String name) {
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    /**
     * Constructs a command with a specified name and argument.
     *
     * @param name     The name of the command.
     * @param argument The argument for the command (if any).
     */
    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * Executes the command on the specified robot.
     * This method should be implemented by concrete subclasses.
     *
     * @param target The robot on which to execute the command.
     * @return A CommandResponse object containing the result of the command execution.
     */
    public abstract CommandResponse execute(IRobot target);

    /**
     * Retrieves the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the argument associated with the command.
     *
     * @return The argument of the command.
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Creates a specific command object based on the provided instruction string.
     *
     * @param instruction The instruction string representing the command to create.
     * @return A Command object corresponding to the provided instruction.
     * @throws IllegalArgumentException If the instruction string does not match any supported command.
     */
    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        String commandName = args[0];

        return switch (commandName) {
            case "quit" -> new ShutdownCommand();
            case "help" -> new HelpCommand();
            case "forward" -> createForwardCommand(args);
            case "back" -> createBackCommand(args);
            case "launch" -> new LaunchCommand(args[1], args[2], args[3]);
            case "state" -> new StateCommand();
            case "robots" -> new RobotsCommand();
            case "look" -> new LookCommand();
            case "repair" -> new RepairCommand();
            case "reload" -> new ReloadCommand();
            case "fire" -> new FireCommand();
            case "dump" -> new DumpCommand();
            case "turn" -> new TurnCommand(args[1]);
            case "world" -> new ConfigureWorldCommand(args[1], args[2]);
            default -> throw new IllegalArgumentException("Unsupported command: " + instruction);
        };
    }

    /**
     * Creates a ForwardCommand object based on the provided arguments array.
     *
     * @param args The array of arguments for the forward command.
     * @return A ForwardCommand object created with the specified arguments.
     * @throws IllegalArgumentException If the number of arguments is invalid for the forward command.
     */
    private static Command createForwardCommand(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments for forward command");
        }
        return new ForwardCommand(args[1]);
    }

    /**
     * Creates a BackCommand object based on the provided arguments array.
     *
     * @param args The array of arguments for the back command.
     * @return A BackCommand object created with the specified arguments.
     * @throws IllegalArgumentException If the number of arguments is invalid for the back command.
     */
    private static Command createBackCommand(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments for back command");
        }
        return new BackCommand(args[1]);
    }
}
