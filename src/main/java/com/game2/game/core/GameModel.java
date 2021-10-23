package com.game2.game.core;

import com.game2.game.core.model.MapInfo;
import com.game2.game.core.model.MoveHistory;
import com.game2.game.core.model.RunInfo;
import com.game2.game.entity.E_Player;
import com.game2.game.misc.EntityStack;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.EntityType;

/**
 * Created by horacekm on 2.10.2017.
 */
public class GameModel {

    private RunInfo runInfo;
    private MapInfo mapInfo;
    private E_Player activePlayer;
    private EntityStack entityStack;
    private MoveHistory moveHistory;

    public GameModel() {
        runInfo = new RunInfo();
        mapInfo = new MapInfo();
        entityStack = new EntityStack();
        moveHistory = new MoveHistory(runInfo);
    }

    public MoveHistory getMoveHistory() {
        return moveHistory;
    }

    public void setEntityStack(EntityStack entityStack) {
        this.entityStack = entityStack;
    }

    public RunInfo getRunInfo() {

        return runInfo;
    }

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public EntityStack getEntityStack() {
        return entityStack;
    }

    public E_Player getActivePlayer() {

        return activePlayer;
    }

    public void setActivePlayer(E_Player activePlayer) {

        this.activePlayer = activePlayer;
        changePlayerSkins();
    }

    private void changePlayerSkins() {

        for(GameEntity entity : entityStack.getStack()) {
            if (entity.getEntityType() == EntityType.PLAYER) {
                E_Player player = (E_Player) entity;
                if (player == getActivePlayer()) {
                    player.setSkin(player.getSkin() - (player.getSkin() - 1) % 2); // always get activePlayer skin n. 1,3,5...
                } else {
                    player.setSkin(player.getSkin() + player.getSkin() % 2); // always get passivePlayer skin n. 2,4,6...
                }
            }
        }
    }
}
