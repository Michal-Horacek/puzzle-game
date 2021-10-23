package com.game2.game.core.model;

/**
 * Created by horacekm on 2.10.2017.
 */
public class RunInfo {

    private int time;
    private int starsTaken;
    private int drillsTaken;
    private int keysTaken;
    private boolean finished;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getTime() {
        return time;
    }

    public int getStarsTaken() {
        return starsTaken;
    }

    public int getDrillsTaken() {
        return drillsTaken;
    }

    public int getKeysTaken() {
        return keysTaken;
    }

    public void increaseTime() {
        time++;
    }

    public void decreaseTime() {
        time--;
    }

    public void addStar() {
        starsTaken++;
    }

    public void removeStar() {
        starsTaken--;
    }

    public void addDrill() {
        drillsTaken++;
    }

    public void removeDrill() {
        drillsTaken--;
    }

    public void addKey() {
        keysTaken++;
    }

    public void removeKey() {
        keysTaken--;
    }
}
