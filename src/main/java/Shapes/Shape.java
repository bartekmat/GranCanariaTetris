package Shapes;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    private List<Block> blocks;
    private List<Block> ghostBlocks;
    private int rotation;
    private ImagePattern color;

    public Shape(String colorPath) {
        //WIP for deploy purposes
        InputStream inputStream = this.getClass().getResourceAsStream(colorPath);
        //=======================
        this.color = new ImagePattern(new Image(inputStream));
        this.blocks = new ArrayList<>();
        this.ghostBlocks = new ArrayList<>();
    }

    public abstract void rotate(Block[][] tetrion);
    public abstract boolean canRotate(Block[][] tetrion);

    public ImagePattern getColor() {
        return color;
    }

    public void moveLeft(Block[][] tetrion) {
        if (canMoveLeft(tetrion)) {
            blocks.forEach(block -> block.setX(block.getX() - 1));
        }
    }

    private boolean canMoveLeft(Block[][] tetrion) {
        if (blocks.stream().allMatch(block -> block.getX() > 0)) {
            return blocks.stream()
                    .allMatch(block -> tetrion[block.getX() - 1][block.getY()] == null);
        } else {
            return false;
        }
    }

    public void moveRight(Block[][] tetrion) {
        if (canMoveRight(tetrion)) {
            blocks.forEach(block -> block.setX(block.getX() + 1));
        }
    }

    private boolean canMoveRight(Block[][] tetrion) {
        if (blocks.stream().allMatch(block -> block.getX() < tetrion.length - 1)) {
            return blocks.stream()
                    .allMatch(block -> tetrion[block.getX() + 1][block.getY()] == null);
        } else {
            return false;
        }
    }
    //metody wypierdalają -> robimy pola protected
    public void addBlocks(Block block) {
        this.blocks.add(block);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getRotation() {
        return rotation;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public List<Block> getGhostBlocks() {
        return ghostBlocks;
    }

    public void addGhostBlocks(Block block) {
        this.ghostBlocks.add(block);
    }
    //============================================

    void initGhostBlocks() {
        this.ghostBlocks.clear();
        this.blocks.forEach(block -> addGhostBlocks(new Block(block.getX(), block.getY(), color)));
    }

    private void translateBlockOfGivenShape(List<Block> givenBlocks, int blockIndex, int translateX, int translateY) {
        givenBlocks.get(blockIndex).setX(givenBlocks.get(blockIndex).getX() + translateX);
        givenBlocks.get(blockIndex).setY(givenBlocks.get(blockIndex).getY() + translateY);
    }

    public void translateRealBlockOfGivenShape(int blockIndex, int translateX, int translateY) {
        translateBlockOfGivenShape(blocks, blockIndex, translateX, translateY);
    }

    public void translateGhostBlockOfGivenShape(int blockIndex, int translateX, int translateY) {
        translateBlockOfGivenShape(ghostBlocks, blockIndex, translateX, translateY);
    }
}
