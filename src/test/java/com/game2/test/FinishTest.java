package com.game2.test;

import com.game2.game.entity.E_Finish;
import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_Star;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 1.10.2017.
 */
public class FinishTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Star star1 = new E_Star(new Point2D(0, 0));
        entityStack.add(star1);
        E_Star star2 = new E_Star(new Point2D(0, 2));
        entityStack.add(star2);

        E_Finish finish = new E_Finish(new Point2D(1, 1));
        entityStack.add(finish);

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT); // check finish
        game.movePlayer(Direction.UP);
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.RIGHT); // check finish
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.UP);
        game.movePlayer(Direction.RIGHT); // check finish

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().isGameFinished()
                && game.getGameViewer().getTime() == 5) {
            System.out.println("Finish test OK.");
        }
        else {
            System.out.println("Finish test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && star1.isAlive() && star1.isVisible()
                && star2.isAlive() && star2.isVisible()
                && !finish.isPassable()
                && game.getGameViewer().getStarsTaken() == 0) {
            System.out.println("Undo - Finish test OK.");
        }
        else {
            System.out.println("Undo - Finish test FAIL.");
        }
    }
}
