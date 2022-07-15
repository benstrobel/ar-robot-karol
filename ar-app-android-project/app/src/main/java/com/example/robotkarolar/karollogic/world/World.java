package com.example.robotkarolar.karollogic.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Triple;

public class World {

    private final Tile[][] tiles = new Tile[10][10];
    private final List<Entity> entities = new ArrayList<>();
    private Entity selectedEntity;

    public World() {
        for(int x = 0; x < tiles.length; x++) {
            for(int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = new Tile(x,y);
            }
        }
    }

    public Entity addEntity(int x, int y){
        if(tiles[x][y].getEntity() != null) return null;
        Entity entity = new Entity();
        entity.updatePosition(tiles[x][y]);
        tiles[x][y].setEntity(entity);
        entities.add(entity);
        return entity;
    }

    public Triple<Integer, Integer, Integer> step() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return null;
        selectedEntity.getTile().setEntity(null);
        selectedEntity.updatePosition(facingTile);
        facingTile.setEntity(selectedEntity);
        return new Triple<>(facingTile.getX(), facingTile.getY(), facingTile.getBlocks().size());
    }

    public Triple<Integer, Integer, Integer> placeDiamond() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return null;
        facingTile.placeBlock(Block.WATER);
        return new Triple<>(facingTile.getX(), facingTile.getY(), facingTile.getBlocks().size()-1);
    }

    public Triple<Integer, Integer, Integer> placeStone() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return null;
        facingTile.placeBlock(Block.STONE);
        return new Triple<>(facingTile.getX(), facingTile.getY(), facingTile.getBlocks().size()-1);
    }

    public Triple<Integer, Integer, Integer> placeGrass() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return null;
        facingTile.placeBlock(Block.GRASS);
        return new Triple<>(facingTile.getX(), facingTile.getY(), facingTile.getBlocks().size()-1);
    }

    public Triple<Integer, Integer, Integer> lift() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return null;
        if(facingTile.getBlocks().isEmpty()) return null;
        facingTile.liftBlock();
        return new Triple<>(facingTile.getX(), facingTile.getY(), facingTile.getBlocks().size());
    }

    public boolean isNorth() {
        return selectedEntity.getFacing() == Direction.NORTH;
    }

    public boolean isEast() {
        return selectedEntity.getFacing() == Direction.EAST;
    }

    public boolean isSouth() {
        return selectedEntity.getFacing() == Direction.SOUTH;
    }

    public boolean isWest() {
        return selectedEntity.getFacing() == Direction.WEST;
    }

    public boolean isBorder() {
        return getFacingTile(selectedEntity) == null;
    }

    public boolean isBlock() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return false;
        return !facingTile.getBlocks().isEmpty();
    }

    public void leftTurn() {
        selectedEntity.leftTurn();
    }

    public void rightTurn() {
        selectedEntity.rightTurn();
    }

    private Map.Entry<Integer, Integer> getFacingTileCoords(Entity entity) {
        int currentX = entity.getTile().getX();
        int currentY = entity.getTile().getY();

        int deltaX = 0, deltaY = 0;

        switch (entity.getFacing()) {
            case EAST: { deltaX = 1; break; }
            case NORTH: { deltaY = 1; break; }
            case WEST: { deltaX = -1; break; }
            case SOUTH: { deltaY = -1; break; }
        }

        int facingX = currentX+deltaX, facingY = currentY+deltaY;

        if(tiles.length <= facingX || facingX < 0 ||tiles[0].length <= facingY || facingY < 0) return null;
        return Map.entry(facingX, facingY);
    }

    private Tile getFacingTile(Entity entity) {
        Map.Entry<Integer, Integer> facingTileCoords = getFacingTileCoords(entity);
        if(facingTileCoords == null) return null;
        return tiles[facingTileCoords.getKey()][facingTileCoords.getValue()];
    }

    public void setSelectedEntity(Entity selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
    }

    public int getYSize() {
        return tiles.length;
    }

    public int getXSize() {
        if(tiles.length == 0) return 0;
        return tiles[0].length;
    }

    public void printWorld() {
        for(int y = tiles.length-1; y >= 0; y--) {
            System.out.println(" " + repeat("-", tiles.length*3-1));
            for(int x = 0; x < tiles[0].length; x++) {

                char entityRepresentation = ' ';
                if(tiles[x][y].getEntity() != null) {
                    switch (tiles[x][y].getEntity().getFacing()) {
                        case EAST: { entityRepresentation = '→'; break; }
                        case SOUTH: { entityRepresentation = '↓'; break; }
                        case WEST: { entityRepresentation = '←'; break; }
                        case NORTH: { entityRepresentation = '↑'; break; }
                    }
                }

                System.out.print("|" + entityRepresentation + (tiles[x][y].getBlocks().size() > 0?("" + tiles[x][y].getBlocks().size()):" "));
            }
            System.out.println("|");
        }
        System.out.println(" " + repeat("-", tiles.length*3-1));
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    private String repeat(String str, int times) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int x = 0; x < times; x++) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    public boolean compareWith(World otherWorld) {
        if(tiles.length != otherWorld.tiles.length || tiles[0].length != otherWorld.tiles[0].length) return false;
        for(int y = 0; y < tiles[0].length; y++) {
            for(int x = 0; x < tiles.length; x++) {
                Tile tile = tiles[x][y];
                Tile otherTile = otherWorld.tiles[x][y];
                if(!tile.getGround().equals(otherTile.getGround())) return false;
                if(tile.getBlocks().size() != otherTile.getBlocks().size()) return false;
                // Only check if blocks exist, not what kind of block
                /*Block[] blocks = tile.getBlocks().toArray(new Block[] {});
                Block[] otherBlocks = otherTile.getBlocks().toArray(new Block[] {});
                for(int i = 0; i < blocks.length; i++) {
                    if(blocks[i] != otherBlocks[i]){
                        return false;
                    }
                }*/
            }
        }
        return true;
    }
}
