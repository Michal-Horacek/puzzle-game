package com.game2.game.core.model;

import com.game2.game.misc.EntityType;
import com.game2.game.misc.GameEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horacekm on 7.12.2017.
 */
public class MoveHistory {

    private List<HistoryItem> queue;
    private RunInfo runInfo;

    public enum ActionType {
        ADD_X,
        ADD_Y,
        ADD_SKIN,
        SET_ALIVE,
        SET_VISIBLE,
        SET_PASSABLE
    }

    public MoveHistory(RunInfo runInfo) {
        queue = new ArrayList<>();
        this.runInfo = runInfo;
    }

    public void addAction(int time, GameEntity entity, ActionType actionType, Object value) {

        if(time < getLastTime() || value == null || entity == null || actionType == null) {
            return;
        }

        switch (actionType) {
            case ADD_X:
            case ADD_Y:
            case ADD_SKIN:
                if(!(value instanceof Integer) || (int)value == 0) {
                    return;
                }
                break;
            case SET_ALIVE:
            case SET_PASSABLE:
            case SET_VISIBLE:
                if(!(value instanceof Boolean)) {
                    return;
                }
                break;
            default:
                break;
        }

        HistoryItem newItem = new HistoryItem(time, entity, actionType, value);
        queue.add(newItem);
    }

    public void revertAction() {

        if(queue.isEmpty()) {
            return;
        }
        HistoryItem item = queue.get(queue.size() - 1);

        switch (item.getActionType()) {
            case ADD_X:
                item.getGameEntity().getPosition2D().addX(-(int)item.getValue());
                break;
            case ADD_Y:
                item.getGameEntity().getPosition2D().addY(-(int)item.getValue());
                break;
            case ADD_SKIN:
                item.getGameEntity().setSkin(item.getGameEntity().getSkin() - (int)item.getValue());
                break;
            case SET_ALIVE:
                item.getGameEntity().setAlive(!(Boolean) item.getValue());
                revertRunInfo(item.getGameEntity().getEntityType());
                break;
            case SET_PASSABLE:
                item.getGameEntity().setPassable(!(Boolean) item.getValue());
                break;
            case SET_VISIBLE:
                item.getGameEntity().setVisible(!(Boolean) item.getValue());
                break;
            default:
                break;
        }

        queue.remove(item);
    }

    public int getLastTime() {

        if(queue.isEmpty()) {
            return -1;
        }
        return queue.get(queue.size() - 1).getTime();
    }

    private class HistoryItem {

        private int time;
        private GameEntity gameEntity;
        private ActionType actionType;
        private Object value;

        public HistoryItem(int time, GameEntity gameEntity, ActionType actionType, Object value) {
            this.time = time;
            this.gameEntity = gameEntity;
            this.actionType = actionType;
            this.value = value;
        }

        public int getTime() {
            return time;
        }

        public GameEntity getGameEntity() {
            return gameEntity;
        }

        public ActionType getActionType() {
            return actionType;
        }

        public Object getValue() {
            return value;
        }
    }

    private void revertRunInfo(EntityType entityType) {
        switch(entityType) {
            case STAR:
                runInfo.removeStar();
                break;
            case STONE:
                runInfo.addDrill();
                break;
            case DRILL:
                runInfo.removeDrill();
                break;
            case LOCK:
                runInfo.addKey();
                break;
            case KEY:
                runInfo.removeKey();
                break;
            default:
                break;
        }
    }
}
