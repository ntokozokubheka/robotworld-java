package za.co.wethinkcode.robotworld.common.obstacle;

import za.co.wethinkcode.robotworld.common.interfaces.IObstacle;
import za.co.wethinkcode.robotworld.common.position.Position;

/**
 * Represents a square obstacle in the robot world.
 */
public class SquareObstacle implements IObstacle {
    private final int bottomLeftX;
    private final int bottomLeftY;
    private static final int SIZE = 5;

    /**
     * Constructs a SquareObstacle object with the given top-left and bottom-right positions.
     * @param topLeftPos The top-left position of the obstacle.
     * @param bottomRightPos The bottom-right position of the obstacle.
     */
    public SquareObstacle(Position topLeftPos, Position bottomRightPos) {
        this.bottomLeftX = topLeftPos.getX();
        this.bottomLeftY = topLeftPos.getY() - SIZE;
    }

    /**
     * Checks if the given position is blocked by the obstacle.
     * @param position The position to be checked.
     * @return True if the position is blocked, false otherwise.
     */
    @Override
    public boolean blocksPosition(Position position) {
        return position.getX() >= this.bottomLeftX &&
                position.getX() < this.bottomLeftX + SIZE &&
                position.getY() >= this.bottomLeftY &&
                position.getY() < this.bottomLeftY + SIZE;
    }

    /**
     * Checks if the path from the start position to the end position is blocked by the obstacle.
     * @param startPos The start position of the path.
     * @param endPos The end position of the path.
     * @return True if the path is blocked, false otherwise.
     */
    @Override
    public boolean blocksPath(Position startPos, Position endPos) {
        int x1 = startPos.getX();
        int y1 = startPos.getY();
        int x2 = endPos.getX();
        int y2 = endPos.getY();

        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);

        int x = x1;
        int y = y1;
        while (x != x2 || y != y2) {
            if (blocksPosition(new Position(x, y))) {
                return true;
            }
            x += dx;
            y += dy;
        }
        return false;
    }

    /**
     * Gets the bottom-left x-coordinate of the obstacle.
     * @return The bottom-left x-coordinate.
     */
    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    /**
     * Gets the bottom-left y-coordinate of the obstacle.
     * @return The bottom-left y-coordinate.
     */
    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    /**
     * Gets the size of the obstacle.
     * @return The size of the obstacle.
     */
    @Override
    public int getSize() {
        return SIZE;
    }
}
