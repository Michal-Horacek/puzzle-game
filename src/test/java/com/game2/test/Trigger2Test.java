package com.game2.test;

import com.game2.game.core.Game;
import com.game2.game.entity.*;
import com.game2.game.misc.Direction;
import com.game2.game.misc.EntityStack;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 12.12.2017.
 */
public class Trigger2Test implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Trigger trigger = new E_Trigger(new Point2D(1, 1), 0);
        entityStack.add(trigger);
        E_TriggerWall triggerWall = new E_TriggerWall(new Point2D(1, 2), 0);
        entityStack.add(triggerWall);

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        // test triggerWall
        game.movePlayer(Direction.RIGHT); // push trigger
        game.movePlayer(Direction.DOWN); // release trigger

        game.undoMove();

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 1
                && !triggerWall.isVisible()) {
            System.out.println("Undo - Trigger2 test OK.");
        }
        else {
            System.out.println("Undo - Trigger2 test FAIL.");
        }
    }
}
