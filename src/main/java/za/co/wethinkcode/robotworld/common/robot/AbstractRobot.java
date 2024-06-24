package za.co.wethinkcode.robotworld.common.robot;

import za.co.wethinkcode.robotworld.common.interfaces.ICommand;
import za.co.wethinkcode.robotworld.common.interfaces.IRobot;
import za.co.wethinkcode.robotworld.common.interfaces.IWorld;
import za.co.wethinkcode.robotworld.common.position.Position;

public class AbstractRobot implements IRobot, Cloneable  {

    private IWorld world;

    private int SIZE =1 ;
    private final Position TOP_LEFT = new Position(-200, 100);
    private final Position BOTTOM_RIGHT = new Position(100, -200);

    public static final Position CENTRE = new Position(0, 0);

    private Position position;
    private IWorld.Direction currentDirection;
    private String status;
    private String name;

    private int shieldStrength;

    private int maxShots;
    private int maxShieldStrength;

    private int shots;
    private int fireDistance;

    public AbstractRobot(String name) {
        this.name = name;
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = IWorld.Direction.UP;
    }

    public void setShots(int shots) {
        this.shots = Math.max(shots, 0);
    }



    public void setMaxShieldStrength(int maxShieldStrength) {
        this.maxShieldStrength = maxShieldStrength;
    }

    @Override
    public int getFireDistance() {
        return fireDistance;
    }

    @Override
    public void setFireDistance(int fireDistance) {
        this.fireDistance = fireDistance;
    }
    public int getMaxShieldStrength() {
        return maxShieldStrength;
    }

    public int getShots() {
        return shots;
    }

    // Getter for shieldS
    public int getShieldStrength() {
        return shieldStrength;
    }

    // Setter for shieldStrength
    public void setShieldStrength(int shieldStrength) {
        this.shieldStrength = shieldStrength;
    }

    // Getter for maxShots
    public int getMaxShots() {
        return maxShots;
    }

    // Setter for maxShots
    public void setMaxShots(int maxShots) {
        this.maxShots = maxShots;
    }

    public void setWorld(IWorld world) {
        this.world = world;
    }

    public IWorld getWorld() {
        return world;
    }

    public String getStatus() {
        return this.status;
    }

    public IWorld.Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void setDirection(IWorld.Direction direction) {

        this.currentDirection = direction;
    }

    @Override
    public boolean handleCommand(ICommand command) {

        return command.execute(this).getExecutionStatus();
    }

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

    public boolean blocksPosition(Position position) {
        int bottomLeftX = this.position.getX();
        int bottomLeftY = this.position.getY();

        return position.getX() >= bottomLeftX &&
                position.getX() < bottomLeftX + this.SIZE &&
                position.getY() >= bottomLeftY &&
                position.getY() < bottomLeftY + this.SIZE;
    }

    public boolean  isRobotInPath(){
        for (IRobot robot : this.world.getAllRobots()){

            if(this.blocksPath(this.getPosition() ,robot.getPosition())){
                return true;
            }

        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + this.position.getX() + "," + this.position.getY() + "] "
                + this.name + "> " + this.status;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String robotName) {
        this.name = robotName;
    }

    public void setPosition(Position newPosition) {

        this.position = newPosition;
    }

    public void setWorldPosition(Position newPosition){
        this.world.setPosition(newPosition);
    }

}