package com.game2.game.entity;

import com.game2.game.misc.EntityType;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.LayerType;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 3.10.2017.
 */
public class E_Teleport extends GameEntity {

    private int colorIndex;

    public E_Teleport(Point2D location2d, int colorIndex) {
        super(
                location2d, // position on map
                EntityType.TELEPORT, // entity type
                LayerType.MIDDLE, // layer type
                0, // skin
                true, // alive
                true, // visible
                true // passable
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
