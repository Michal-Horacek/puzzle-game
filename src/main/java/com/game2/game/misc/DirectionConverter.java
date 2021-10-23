package com.game2.game.misc;

/**
 * Created by horacekm on 2.10.2017.
 */
public class DirectionConverter {

    public static int getX(Direction direction) {

        switch (direction) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            case UP:
                return -0;
            case DOWN:
                return 0;
            case STAY:
                return 0;
        }
        return 0;
    }

    public static int getY(Direction direction) {

        switch (direction) {
            case LEFT:
                return 0;
            case RIGHT:
                return 0;
            case UP:
                return -1;
            case DOWN:
                return 1;
            case STAY:
                return 0;
        }
        return 0;
    }

    public static boolean sameLine(Direction dir1, Direction dir2) {

        if(DirectionConverter.getX(dir1) == DirectionConverter.getX(dir2)) return true; // X = 0, both vertical
        if(DirectionConverter.getY(dir1) == DirectionConverter.getY(dir2)) return true; // Y = 0, both horizontal
        return false;
    }
}
