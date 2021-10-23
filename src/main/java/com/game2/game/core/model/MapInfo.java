package com.game2.game.core.model;

/**
 * Created by horacekm on 2.10.2017.
 */
public class MapInfo {

    private int width;
    private int height;
    private int starsCount;
    private boolean freeMove = true; // out of the floor

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public boolean isFreeMove() {
        return freeMove;
    }

    public void setFreeMove(boolean freeMove) {
        this.freeMove = freeMove;
    }
}
