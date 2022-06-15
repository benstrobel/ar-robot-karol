package world;

public class Entity {
    private Direction facing = Direction.NORTH;
    private Tile tile;
    private int x;
    private int y;

    public void updatePosition(Tile tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public Tile getTile() {
        return tile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void leftTurn() {
        switch (facing) {
            case EAST -> facing = Direction.NORTH;
            case SOUTH -> facing = Direction.EAST;
            case WEST -> facing = Direction.SOUTH;
            case NORTH -> facing = Direction.WEST;
        }
    }

    public void rightTurn() {
        switch (facing) {
            case EAST -> facing = Direction.SOUTH;
            case SOUTH -> facing = Direction.WEST;
            case WEST -> facing = Direction.NORTH;
            case NORTH -> facing = Direction.EAST;
        }
    }

    public Direction getFacing() {
        return facing;
    }
}
