package za.co.wethinkcode.robotworld.client.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RobotClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5999;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Auto-flush enabled
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            setupWorld(out, in, scanner);

            String robotName = inputRobotName(scanner);

            boolean continueLoop = true;
            while (continueLoop) {
                String command = inputCommand(scanner);

                switch (command) {
                    case "launch":
                        sendLaunchRequest(out, in, scanner, robotName);
                        break;
                    case "state":
                        sendStateRequest(out, in, robotName);
                        break;
                    case "robots":
                        sendRobotsRequest(out, in, robotName);
                        break;
                    case "dump":
                        sendDumpRequest(out, in, robotName);
                        break;
                    case "look":
                        sendLookRequest(out, in, robotName);
                        break;
                    case "quit":
                        continueLoop = sendQuitRequest(out, in, robotName);
                        break;
                    case "forward":
                    case "back":
                        sendMoveRequest(out, in, scanner, robotName, command);
                        break;
                    case "turn":
                        sendTurnRequest(out, in, scanner, robotName);
                        break;
                    case "repair":
                        sendRepairRequest(out, in, robotName);
                        break;
                    case "reload":
                        sendReloadRequest(out, in, robotName);
                        break;
                    case "fire":
                        sendFireRequest(out, in, robotName);
                        break;
                    case "help":
                        displayHelp();
                        break;
                    default:
                        System.out.println("Unsupported command.");
                        break;
                }

                if (!continueLoop) {
                    break; // Exit the while loop if continueLoop is false
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void setupWorld(PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {
        int height = promptAndGetInt(scanner, "Enter the height: ");
        int width = promptAndGetInt(scanner, "Enter the width: ");

        String initialRequestJson = String.format("{\"robot\":\"default\",\"command\":\"world\",\"arguments\":[\"%d\",\"%d\"]}", height, width);
        out.println(initialRequestJson);

        String initialResponse = readResponse(in);
        processResponse(initialResponse);
    }

    private static String inputRobotName(Scanner scanner) {
        System.out.print("Enter robot name: ");
        return scanner.nextLine().trim();
    }

    private static String inputCommand(Scanner scanner) {
        System.out.print("Enter command (launch, state, look, forward, back, turn, repair, reload, fire): ");
        return scanner.nextLine().trim();
    }

    private static void sendLaunchRequest(PrintWriter out, BufferedReader in, Scanner scanner, String robotName) throws IOException {
        String kind = promptAndInput(scanner, "Enter kind of robot: ");
        int shieldStrength = promptAndGetInt(scanner, "Enter maximum shield strength: ");
        int maxShots = promptAndGetInt(scanner, "Enter maximum shots: ");

        JSONObject requestJson = createRequestJson(robotName, "launch", kind, shieldStrength, maxShots);
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendStateRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "state");
        out.println(requestJson.toString());
        String response = readResponse(in);
        processResponse(response);
    }

    private static boolean sendQuitRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "quit");
        out.println(requestJson.toString());
        String response = readResponse(in);
        processResponse(response);
        return false;
    }


    private static void sendDumpRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "dump");
        out.println(requestJson.toString());
        String response = readResponse(in);
        processResponse(response);
    }


    private static void sendRobotsRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "robots");
        out.println(requestJson.toString());
        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendLookRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "look");
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendMoveRequest(PrintWriter out, BufferedReader in, Scanner scanner, String robotName, String direction) throws IOException {
        int steps = promptAndGetInt(scanner, "Enter number of steps: ");

        JSONObject requestJson = createRequestJson(robotName, direction, steps);
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendTurnRequest(PrintWriter out, BufferedReader in, Scanner scanner, String robotName) throws IOException {
        String direction = promptAndInput(scanner, "Enter direction to turn (left/right): ");

        JSONObject requestJson = createRequestJson(robotName, "turn", direction);
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendRepairRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "repair");
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendReloadRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "reload");
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static void sendFireRequest(PrintWriter out, BufferedReader in, String robotName) throws IOException {
        JSONObject requestJson = createRequestJson(robotName, "fire");
        out.println(requestJson.toString());

        String response = readResponse(in);
        processResponse(response);
    }

    private static JSONObject createRequestJson(String robotName, String command, Object... arguments) {
        JSONObject requestJson = new JSONObject();
        requestJson.put("robot", robotName);
        requestJson.put("command", command);

        JSONArray argsArray = new JSONArray();
        for (Object arg : arguments) {
            argsArray.put(arg);
        }
        requestJson.put("arguments", argsArray);

        return requestJson;
    }

    private static String readResponse(BufferedReader in) throws IOException {
        return in.readLine();
    }

    private static void processResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println("Processed Response:");
            System.out.println("Result: " + jsonResponse.getString("result"));

            if (jsonResponse.has("data")) {
                JSONObject data = jsonResponse.getJSONObject("data");
                System.out.println("Data:");
                data.keySet().forEach(key -> System.out.println(key + ": " + data.get(key)));
            }

            if (jsonResponse.has("state")) {
                JSONObject state = jsonResponse.getJSONObject("state");
                System.out.println("State:");
                state.keySet().forEach(key -> System.out.println(key + ": " + state.get(key)));
            }
        } catch (JSONException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String promptAndInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int promptAndGetInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static boolean askToContinue(Scanner scanner) {
        System.out.print("Do you want to send another request? (yes/no): ");
        String userResponse = scanner.nextLine().trim().toLowerCase();
        return userResponse.equals("yes");
    }

    private static void displayHelp() {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Command Help:\n");
        helpMessage.append("OFF       - Shut down robot\n");
        helpMessage.append("HELP      - Provide information about commands\n");
        helpMessage.append("FORWARD   - Move forward by specified number of steps, e.g. 'FORWARD 10'\n");
        helpMessage.append("BACK      - Move backward by specified number of steps, e.g. 'BACK 5'\n");
        helpMessage.append("TURN LEFT - Turn left (counter-clockwise)\n");
        helpMessage.append("TURN RIGHT- Turn right (clockwise)\n");
        helpMessage.append("FIRE      - Fire a shot\n");
        helpMessage.append("RELOAD    - Reload the robot's weapon\n");
        helpMessage.append("REPAIR    - Repair the robot\n");
        helpMessage.append("LOOK      - Look in all directions\n");
        helpMessage.append("STATE     - Get the state of the robot\n");
        helpMessage.append("DUMP      - Get the state of all world entities\n");

        System.out.println(helpMessage.toString());
    }
}