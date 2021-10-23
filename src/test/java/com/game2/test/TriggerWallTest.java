package com.game2.test;

import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_TriggerWall;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class TriggerWallTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        entityStack.add(new E_TriggerWall(new Point2D(1, 1), 0));

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT); // triggerWall block
        game.movePlayer(Direction.UP);
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.DOWN); // triggerWall block
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.LEFT); // triggerWall block
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.LEFT);
        game.movePlayer(Direction.UP); // triggerWall block

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 2
                && game.getGameViewer().getTime() == 6) {
            System.out.println("TriggerWall test OK.");
        }
        else {
            System.out.println("TriggerWall test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0) {
            System.out.println("Undo - TriggerWall test OK.");
        }
        else {
            System.out.println("Undo - TriggerWall test FAIL.");
        }
    }
}
