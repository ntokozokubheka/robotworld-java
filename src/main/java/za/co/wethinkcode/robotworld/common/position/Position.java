package za.co.wethinkcode.robotworld.common.position;

/**
 * The Position class represents a coordinate in a 2D space with x and y values.
 * It provides methods to access the coordinates, check equality, determine if
 * a position is within a given rectangular boundary, and generate a string
 * representation of the position.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a Position with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this position.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this position.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two positions are considered equal if they have the same x and y coordinates.
     *
     * @param o the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    /**
     * Checks if this position is within a rectangular boundary defined by
     * the top-left and bottom-right positions.
     *
     * @param topLeft the top-left corner of the rectangular boundary
     * @param bottomRight the bottom-right corner of the rectangular boundary
     * @return true if this position is within the boundary; false otherwise
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    /**
     * Returns a string representation of the position in the format [x,y].
     *
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "[" + this.getX() + "," + this.getY() + "]";
    }
}
