package com.example.robotkarolar.karollogic.world;

/**
 * Actors on the Board
 */

public class Entity {
    private Direction facing = Direction.NORTH;
    private Tile tile;

    public void updatePosition(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public void leftTurn() {
        switch (facing) {
            case EAST:  { facing = Direction.NORTH; break; }
            case SOUTH: { facing = Direction.EAST; break; }
            case WEST: { facing = Direction.SOUTH; break; }
            case NORTH: { facing = Direction.WEST; break; }
        }
    }

    public void rightTurn() {
        switch (facing) {
            case EAST: { facing = Direction.SOUTH; break; }
            case SOUTH: { facing = Direction.WEST; break; }
            case WEST: { facing = Direction.NORTH; break; }
            case NORTH: { facing = Direction.EAST; break; }
        }
    }

    public Direction getFacing() {
        return facing;
    }
}
