package com.game2.test;

import com.game2.game.core.Game;
import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_Stop;
import com.game2.game.entity.E_Teleport;
import com.game2.game.entity.E_Wood;
import com.game2.game.misc.Direction;
import com.game2.game.misc.EntityStack;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 22.11.2017.
 */
public class StopTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        entityStack.add(new E_Stop(new Point2D(1, 1)));
        entityStack.add(new E_Wood(new Point2D(1, 2)));

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.UP);

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 3) {
            System.out.println("Stop test OK.");
        }
        else {
            System.out.println("Stop test FAIL.");
        }
    }
}
