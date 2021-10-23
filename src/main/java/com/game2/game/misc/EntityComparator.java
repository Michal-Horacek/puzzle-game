package com.game2.game.misc;

import java.util.Comparator;

/**
 * Created by horacekm on 12.10.2017.
 */
public class EntityComparator implements Comparator<GameEntity> {

    @Override
    public int compare(GameEntity e1, GameEntity e2) {
        return e1.getEntityType().ordinal() - e2.getEntityType().ordinal();
    }
}
