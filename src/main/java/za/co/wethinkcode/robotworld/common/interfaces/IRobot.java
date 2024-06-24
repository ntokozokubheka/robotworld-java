package za.co.wethinkcode.robotworld.common.interfaces;

import za.co.wethinkcode.robotworld.common.position.Position;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld.Direction;


public interface IRobot {

    // Getters and setters for shieldStrength
    int getShieldStrength();
    void setShieldStrength(int shieldStrength);
    int getFireDistance();
    void setFireDistance(int fireDistance);
    public void setShots(int shots);
    public int getShots();
    public void setMaxShieldStrength(int maxShieldStrength);
    // Getters and setters for maxShots
    int getMaxShots();
    void setMaxShots(int maxShots);

    // Getter and setter for world
    IWorld getWorld();
    void setWorld(IWorld world);

    // Getter for status
    String getStatus();
    void setStatus(String status);

    // Getter and setter for currentDirection
    Direction getCurrentDirection();
    void setDirection(Direction direction);

    // Getter and setter for name
    String getName();
    void setName(String name);

    // Getter and setter for position
    Position getPosition();
    void setPosition(Position newPosition);

    // Method to handle commands
    boolean handleCommand(ICommand command);

    public boolean blocksPath(Position startPos, Position endPos);
    boolean blocksPosition(Position position);

    public boolean  isRobotInPath();

    public void setWorldPosition(Position newPosition);

    String toString();

    int getMaxShieldStrength();

}