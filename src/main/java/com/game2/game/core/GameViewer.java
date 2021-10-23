package com.game2.game.core;

import com.game2.game.entity.E_Player;
import com.game2.game.misc.EntityStack;

/**
 * Created by horacekm on 2.10.2017.
 */
public class GameViewer {

    private GameModel model;

    public GameViewer(GameModel model) {
        this.model = model;
    }

    public int getWidth() {
        return model.getMapInfo().getWidth();
    }

    public int getHeight() {
        return model.getMapInfo().getHeight();
    }

    public int getStarsCount() {
        return model.getMapInfo().getStarsCount();
    }

    public int getStarsTaken() {
        return model.getRunInfo().getStarsTaken();
    }

    public int getDrillsTaken() {
        return model.getRunInfo().getDrillsTaken();
    }

    public int getKeysTaken() {
        return model.getRunInfo().getKeysTaken();
    }

    public EntityStack getEntityStack() {
        return model.getEntityStack();
    }

    public E_Player getActivePlayer() {
        return model.getActivePlayer();
    }

    public int getTime() {
        return model.getRunInfo().getTime();
    }

    public boolean isGameFinished() {
        return model.getRunInfo().isFinished();
    }
}
