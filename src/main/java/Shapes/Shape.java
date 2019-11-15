package Shapes;

import Tetris.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape {
    protected List<Block> blocks;
    private List<Block> ghostBlocks;
    private int rotation;

    public Shape() {
        blocks = new ArrayList<Block>();
        rotation = 1;
    }

    public void fall() {
        if (canFall()) {
            blocks.forEach(x -> x.setY(x.getY() + 1));
        }
    }

    public void moveLeft() {
        if (canMoveLeft()) {
            blocks.forEach(x -> x.setX(x.getX() - 1));
        }
    }

    public void moveRight() {
        if (canMoveRight()) {
            blocks.forEach(x -> x.setX(x.getX() + 1));
        }
    }

    public abstract void rotate();

    private boolean canFall() {
        return blocks.stream()
                .allMatch(x ->
                        Game.getTetrion()[x.getX()][x.getY() + 1] == null
                                && x.getY() < Game.getTetrion()[0].length - 1);
    }

    private boolean canMoveLeft() {
        return blocks.stream()
                .allMatch(x ->
                        Game.getTetrion()[x.getX() - 1][x.getY()] == null
                                && x.getX() > 0);
    }

    private boolean canMoveRight() {
        return blocks.stream()
                .allMatch(x ->
                        Game.getTetrion()[x.getX() + 1][x.getY()] == null
                                && x.getX() < Game.getTetrion().length - 1);
    }

    public boolean canRotate(){
        ghostBlocks = blocks;
        rotate();
        if(blocks.stream().anyMatch(x ->
                Game.getTetrion()[x.getX()][x.getY()] != null ||
                x.getX() < 0 ||
                x.getX() > Game.getTetrion().length -1 ||
                x.getY() < 0 ||
                x.getY() > Game.getTetrion()[0].length -1)){
            blocks = ghostBlocks;
            return false;
        }
        else{
            return true;
        }
    }

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
}
