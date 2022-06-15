package world;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return entity;
    }

    public boolean step() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return false;
        selectedEntity.getTile().setEntity(null);
        selectedEntity.updatePosition(facingTile);
        facingTile.setEntity(selectedEntity);
        return true;
    }

    public boolean place() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return false;
        facingTile.placeBlock(Block.RED);
        return true;
    }

    public boolean lift() {
        Tile facingTile = getFacingTile(selectedEntity);
        if (facingTile == null) return false;
        if(facingTile.getBlocks().isEmpty()) return false;
        facingTile.liftBlock();
        return true;
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
            case EAST -> deltaX = 1;
            case NORTH -> deltaY = 1;
            case WEST -> deltaX = -1;
            case SOUTH -> deltaY = -1;
        }

        int facingX = currentX+deltaX, facingY = currentY+deltaY;

        if(tiles.length <= facingX || facingX <= 0 ||tiles[0].length <= facingY || facingY <= 0) return null;
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
}
