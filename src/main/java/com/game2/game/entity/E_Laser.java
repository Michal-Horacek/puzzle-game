package com.game2.game.entity;

import com.game2.game.misc.*;

/**
 * Created by horacekm on 2.10.2017.
 */
public class E_Laser extends GameEntity {

    private Direction direction;

    public E_Laser(Point2D location2d, Direction direction) {
        super(
                location2d, // position on map
                EntityType.LASER, // entity type
                LayerType.MIDDLE, // layer type
                0, // skin
                true, // alive
                true, // visible
                true // passable
        );
        setDirection(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
