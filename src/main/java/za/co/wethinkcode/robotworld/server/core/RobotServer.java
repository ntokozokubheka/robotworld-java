package za.co.wethinkcode.robotworld.server.core;

import za.co.wethinkcode.robotworld.common.interfaces.IObstacle;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.common.obstacle.SquareObstacle;
import za.co.wethinkcode.robotworld.common.position.Position;
import za.co.wethinkcode.robotworld.server.handlers.RobotHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main server class for the robot world application.
 * Handles incoming client connections and assigns them to RobotHandlers.
 */
public class RobotServer {

    private static final int PORT = 5999;  // Server port number
    private static final int THREAD_POOL_SIZE = 25;  // Number of threads in the thread pool

    /**
     * Main method to start the RobotServer.
     * Creates a thread pool, initializes the world with obstacles,
     * and accepts incoming client connections.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);  // Create a fixed thread pool

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {  // Create a server socket
            System.out.println("Robot server is running on port " + PORT);

            // Initialize the world with randomly generated obstacles
            World world = new World(new WorldObstaclesRandom().createWorldObstacles());

            // Accept incoming client connections indefinitely
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Accept a new client connection
                // Pass the shared World instance to each RobotHandler in the thread pool
                threadPool.execute(new RobotHandler(clientSocket, world));
            }

        } catch (IOException e) {
            e.printStackTrace();  // Print any IO exceptions
        } finally {
            threadPool.shutdown();  // Shut down the thread pool
        }
    }

}
