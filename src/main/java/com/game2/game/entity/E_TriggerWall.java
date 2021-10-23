package com.game2.game.entity;

import com.game2.game.misc.EntityType;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.LayerType;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class E_TriggerWall extends GameEntity {

    private int colorIndex;

    public E_TriggerWall(Point2D location2d, int colorIndex) {
        super(
                location2d, // position on map
                EntityType.TRIGGERWALL, // entity type
                LayerType.TOP, // layer type
                0, // skin
                true, // alive
                true, // visible
                false // passable
        );

        setColorIndex(colorIndex); // 0-11
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }
}
