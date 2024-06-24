# Robot World

Welcome to Robot World, a multiplayer game where robots navigate a virtual environment, interact with obstacles, and battle each other. This README provides comprehensive instructions for setting up and running the game.

## Table of Contents
- [How to Start the Client](#how-to-start-the-client)
- [How to Play](#how-to-play)
  - [Server Commands](#server-commands)
  - [Client Commands](#client-commands)
- [Contribution Guidelines](#contribution-guidelines)
- [Testing](#testing)
- [License](#license)
- [Contact](#contact)

## How to Start the Client

To start the client, run the following command in your terminal:

```bash
java -jar target/robot-world-client.jar


How to Play
Server Commands

    quit: Shut down the server.
    robots: List all connected robots.
    dump: Display the current state of the world.

Client Commands

    look: View the robot's surroundings.
    state: Display the robot's current state.
    move [direction]: Move the robot in the specified direction (forward, back, left, right).
    fire: Fire a shot in the current direction.
    reload: Reload the robot's weapon.
    repair: Repair the robot's shields.

Contribution Guidelines
Naming Conventions

    Classes: CamelCase
    Variables: MixedCase with meaningful names
    Methods: Verb-based mixedCase

Indentation

Consistent use of either 2 spaces, 4 spaces, or tabs.
Documentation

Every class and public method must have Javadocs.
Collaboration

Use GitHub Issues for task tracking and team collaboration. Regularly update the board and provide feedback during iteration showcases.
Testing
Unit Tests

Comprehensive tests for both server and client.
Test Coverage

Ensure high test coverage for functional correctness and robustness.
Running Tests

Use Maven to run all tests:

```bash

mvn test
