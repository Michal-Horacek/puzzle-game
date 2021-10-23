package com.game2.game.misc;

import java.util.Comparator;

/**
 * Created by horacekm on 20.11.2017.
 */
public class EntityEditorComparator implements Comparator<GameEntity> {

    @Override
    public int compare(GameEntity e1, GameEntity e2) {
        return e1.getPosition2D().getY() - e2.getPosition2D().getY();
    }
}