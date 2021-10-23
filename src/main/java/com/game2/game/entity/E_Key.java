package com.game2.game.entity;

import com.game2.game.misc.EntityType;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.LayerType;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 2.10.2017.
 */
public class E_Key extends GameEntity {

    public E_Key(Point2D location2d) {
        super(
                location2d, // position on map
                EntityType.KEY, // entity type
                LayerType.TOP, // layer type
                0, // skin
                true, // alive
                true, // visible
                false // passable
        );
    }
}
