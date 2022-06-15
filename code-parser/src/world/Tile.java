package world;

import java.util.ArrayDeque;
import java.util.Deque;

public class Tile {
    private Ground ground = Ground.FLOOR;
    private final Deque<Block> blocks = new ArrayDeque<>();
    private Entity entity;

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public void placeBlock(Block block) {
        blocks.push(block);
    }

    public Block liftBlock() {
        return blocks.pop();
    }

    public Ground getGround() {
        return ground;
    }

    public Deque<Block> getBlocks() {
        return blocks;
    }
}
