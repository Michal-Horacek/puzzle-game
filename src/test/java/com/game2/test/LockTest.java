package com.game2.test;

import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 3.10.2017.
 */
public class LockTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Lock lock1 = new E_Lock(new Point2D(1, 1));
        entityStack.add(lock1);
        E_Lock lock2 = new E_Lock(new Point2D(2, 1));
        entityStack.add(lock2);

        E_Key key = new E_Key(new Point2D(1, 2));
        entityStack.add(key);

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT); // lock block
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.RIGHT); // pick up a key
        game.movePlayer(Direction.UP); // open lock
        game.movePlayer(Direction.RIGHT); // lock block

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 1
                && !lock1.isAlive() && !lock1.isVisible()
                && lock2.isAlive() && lock2.isVisible()
                && !key.isAlive() && !key.isVisible()
                && game.getGameViewer().getTime() == 3
                ) {
            System.out.println("Lock test OK.");
        }
        else {
            System.out.println("Lock test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }
        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && lock1.isAlive()
                && lock1.isVisible()
                && key.isAlive()
                && key.isVisible()
                && game.getGameViewer().getKeysTaken() == 0) {
            System.out.println("Undo - Lock test OK.");
        }
        else {
            System.out.println("Undo - Lock test FAIL.");
        }
    }
}
