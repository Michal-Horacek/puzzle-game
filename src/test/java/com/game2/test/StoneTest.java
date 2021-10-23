package com.game2.test;

import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 3.10.2017.
 */
public class StoneTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Drill drill = new E_Drill(new Point2D(2, 1));
        entityStack.add(drill);

        E_Stone stone = new E_Stone(new Point2D(1, 1));
        entityStack.add(stone);

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT); // lock block
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.UP); // pick up a drill
        game.movePlayer(Direction.LEFT); // destroy stone

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 1
                && !drill.isAlive() && !drill.isVisible()
                && !stone.isAlive() && !stone.isVisible()
                && game.getGameViewer().getTime() == 5
                ) {
            System.out.println("Stone test OK.");
        }
        else {
            System.out.println("Stone test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && stone.isAlive()
                && stone.isVisible()
                && drill.isAlive()
                && drill.isVisible()
                && game.getGameViewer().getDrillsTaken() == 0) {
            System.out.println("Undo - Stone test OK.");
        }
        else {
            System.out.println("Undo - Stone test FAIL.");
        }
    }
}
