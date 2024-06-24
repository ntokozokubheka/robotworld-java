package za.co.wethinkcode.robotworld.client;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworld.client.handlers.RobotClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotClientTest {

    @Test
    public void testSetupWorld() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Prepare input
        String input = "10\n20\n";
        Scanner scanner = new Scanner(input);

        // Prepare output
        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);

        // Prepare mock server response
        String response = "{\"result\":\"OK\"}";
        BufferedReader in = new BufferedReader(new StringReader(response));

        // Call private method using reflection
        Method setupWorldMethod = RobotClient.class.getDeclaredMethod("setupWorld", PrintWriter.class, BufferedReader.class, Scanner.class);
        setupWorldMethod.setAccessible(true);
        setupWorldMethod.invoke(null, out, in, scanner);

        // Verify output
        String expectedOutput = "{\"robot\":\"default\",\"command\":\"world\",\"arguments\":[\"10\",\"20\"]}\n";
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void testInputRobotName() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Prepare input
        String input = "Robo\n";
        Scanner scanner = new Scanner(input);

        // Call private method using reflection
        Method inputRobotNameMethod = RobotClient.class.getDeclaredMethod("inputRobotName", Scanner.class);
        inputRobotNameMethod.setAccessible(true);
        String robotName = (String) inputRobotNameMethod.invoke(null, scanner);

        // Verify output
        assertEquals("Robo", robotName);
    }

    @Test
    public void testInputCommand() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // Prepare input
        String input = "launch\n";
        Scanner scanner = new Scanner(input);

        // Call private method using reflection
        Method inputCommandMethod = RobotClient.class.getDeclaredMethod("inputCommand", Scanner.class);
        inputCommandMethod.setAccessible(true);
        String command = (String) inputCommandMethod.invoke(null, scanner);

        // Verify output
        assertEquals("launch", command);
    }

}
