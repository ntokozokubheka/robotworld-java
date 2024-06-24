package za.co.wethinkcode.robotworld.common.world;

import za.co.wethinkcode.robotworld.common.interfaces.*;

import za.co.wethinkcode.robotworld.common.position.Position;
import  za.co.wethinkcode.robotworld.common.robot.Robot;

import java.util.*;
/**
 * Abstract class representing a world in which robots can navigate and interact.
 * Implements the {@link IWorld} interface.
 */
public abstract class AbstractWorld implements IWorld{
    protected Position currentPosition;
    protected Direction currentDirection;
    private  int visibility =25;
    private List<IObstacle> obstacles;
    private Map<String, IRobot> robots;
    private final int SHEILDSTRENGTH = 15;
    private final  float reloadTime = 30.00F;
    private final  float repairTime = 25.00F;
    private boolean dimensionsSet = false;

    private Position TOP_LEFT = new Position(-100, 200);
    private  Position BOTTOM_RIGHT = new Position(100, -200);

    public AbstractWorld(List<IObstacle> obstacles) {

        this.currentPosition = CENTRE;
        this.currentDirection = Direction.UP;
        this.obstacles = obstacles;
        this.robots = new HashMap<>();

    }



    public boolean removeRobot(String robotName) {
        if (robots.containsKey(robotName)) {
            robots.remove(robotName);
            return true;
        }
        return false;
    }
    public int getSHEILDSTRENGTH() {
        return SHEILDSTRENGTH;
    }

    public Position getTOP_LEFT() {
        return TOP_LEFT;
    }

    public Position getBOTTOM_RIGHT() {
        return BOTTOM_RIGHT;
    }

    public UpdateResponse addRobot(Robot robot) {
        if (robots.containsKey(robot.getName())) {
            return UpdateResponse.FAILED_SAME_ROBOT_NAME;
        }


        int  min_x = getTOP_LEFT().getX();
        int max_x = -getTOP_LEFT().getX();
        int max_y = getTOP_LEFT().getY();
        int min_y = -getTOP_LEFT().getY();

        for (int i = min_x; i <= max_x; i++) {
            for (int j = min_y; j <= max_y; j++) {
                Position newPosition = new Position(i, j);
                if (isPositionFree(newPosition)) {
                    robot.setPosition(newPosition);
                    this.robots.put(robot.getName(), robot);
                    return UpdateResponse.SUCCESS;
                }
            }
        }

        return UpdateResponse.FAILED_NO_SPACE;
    }

    private boolean isPositionFree(Position position) {
        for (IRobot myRobot : getAllRobots()) {
            if (myRobot.blocksPosition(position)) {
                System.out.println("there is a bot here");
                return false;
            }
        }

        for (IObstacle myObstacle : getObstacles()) {
            if (myObstacle.blocksPosition(position)) {
                return false;
            }
        }

        return true;
    }

    public List<IRobot> getAllRobots() {

        return new ArrayList<>(robots.values());

    }

    public void setWorldDimensions(int x, int y) {
        if (!dimensionsSet) {
            int height = x - 1;
            int width = y - 1;

            this.TOP_LEFT = new Position(-width, height);
            this.BOTTOM_RIGHT = new Position(width, -height);

            dimensionsSet = true;
        }
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps) {

        Position newPosition = calculateNewPosition(nrSteps);

        for (IRobot myRobot : getAllRobots()) {
            if (myRobot.blocksPosition(newPosition)) {
                System.out.println("ROBOT BLOCKS POSTIION");
                return UpdateResponse.FAILED_OBSTRUCTED_ROBOT;
            }
        }
        if(!newPosition.isIn(TOP_LEFT, BOTTOM_RIGHT)) {

            return UpdateResponse.FAILED_OUTSIDE_WORLD;
        }
        else if (isNewPositionAllowed(newPosition)) {
            this.currentPosition = newPosition;
            return UpdateResponse.SUCCESS;

        }else{
            return  UpdateResponse.FAILED_OBSTRUCTED;
        }
    }

    @Override
    public void updateDirection(boolean turnRight) {
        this.currentDirection = turnRight ? turnRight() : turnLeft();
    }

    @Override
    public Position getPosition() {
        return this.currentPosition;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public boolean isNewPositionAllowed(Position position) {

        for (IObstacle obstacle : getObstacles()) {
            System.out.println(obstacle.blocksPath(this.currentPosition, position));
            System.out.println(position.isIn(TOP_LEFT, BOTTOM_RIGHT));

            if (obstacle.blocksPath(this.currentPosition, position) &&
                    position.isIn(TOP_LEFT, BOTTOM_RIGHT)){

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isAtEdge() {
        int currentX = this.currentPosition.getX();
        int currentY = this.currentPosition.getY();

        boolean isAtTopEdge = currentY == TOP_LEFT.getY();
        boolean isAtBottomEdge = currentY == BOTTOM_RIGHT.getY();
        boolean isAtLeftEdge = currentX == TOP_LEFT.getX();
        boolean isAtRightEdge = currentX == BOTTOM_RIGHT.getX();

        return isAtTopEdge || isAtBottomEdge || isAtLeftEdge || isAtRightEdge;
    }

    @Override
    public void reset() {
        currentPosition = CENTRE;
        currentDirection = Direction.UP;
        robots.clear();
    }

    @Override
    public List<IObstacle> getObstacles() {
        // Placeholder implementation, you can implement your logic here
        return this.obstacles;
    }

    @Override
    public void showObstacles() {
        // Placeholder implementation, you can implement your logic here
    }
    /**
     * Calculates the new position of the robot based on its current position and direction.
     *
     * @param nrSteps The number of steps to move.
     * @return The calculated new position of the robot.
     */
    private Position calculateNewPosition(int nrSteps) {
        int currentX = this.currentPosition.getX();
        int currentY = this.currentPosition.getY();

        return switch (this.currentDirection) {
            case UP -> new Position(currentX, currentY + nrSteps);
            case DOWN -> new Position(currentX, currentY - nrSteps);
            case LEFT -> new Position(currentX - nrSteps, currentY);
            case RIGHT -> new Position(currentX + nrSteps, currentY);
            default -> throw new IllegalStateException("Unknown direction: " + this.currentDirection);
        };
    }

    private Direction turnRight() {

        return Direction.RIGHT;
    }

    private Direction turnLeft() {

        return Direction.LEFT;
    }

    public void setDirection(Direction direction){

        this.currentDirection = direction;
    }


    public void setPosition(Position newPosition) {

        this.currentPosition = newPosition;
    }

    public int getVisibility() {
        return this.visibility;
    }

    public float getReloadTime() {
        return this.reloadTime;
    }

    public float getRepairTime() {
        return this.repairTime;
    }

    public List<String> robotsList() {
        List<String> robotList = new ArrayList<>();
        for (IRobot robot : robots.values()) {
            String robotInfo = "Name: " + robot.getName() + ", State: " + robot.toString();
            robotList.add(robotInfo);
        }
        return robotList;
    }
}

