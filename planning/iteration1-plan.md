Iteration 1: Initial Setup and Core Functionality
Goals:

    Object-Oriented Design
        Implement modular design using interfaces and abstract classes.
        Create concrete implementations for robots and obstacles.

    Robot Functionality
        Implement basic robot actions: movement, direction management, and state handling.

    World Simulation
        Develop a simulated environment with boundaries and obstacles.
        Implement methods for robot interaction within the world.

    Command Handling
        Implement command execution using a command design pattern.
        Ensure robots can execute commands and interact with the world based on commands.

    Testing and Validation
        Conduct unit testing for core functionalities: robot movements, obstacle detection, world updates, and command execution.
        Validate system reliability and robustness through thorough testing.

    Documentation and Readability
        Document classes, methods, and significant code sections using Javadoc comments.
        Ensure code clarity and maintainability through clear documentation.

Tasks Distribution:

    AbstractRobot (Karabo)
        Implement abstract robot functionalities.
        Handle command execution and state management.

    AbstractWorld (Ntokozo)
        Implement abstract world functionalities.
        Manage world boundaries, obstacles, and robot interactions.

    SquareObstacle (Thabiso)
        Implement square obstacle logic.
        Define obstacle blocking mechanisms and interactions with robots.

    Position (Zama)
        Implement position class with coordinates management.
        Ensure position calculations and comparisons are accurate.

    Integration and Testing (Prudence)
        Conduct unit tests for implemented functionalities.
        Verify system behavior under various scenarios.