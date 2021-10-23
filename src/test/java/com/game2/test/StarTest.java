package com.game2.test;

import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_Star;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 1.10.2017.
 */
public class StarTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Star star = new E_Star(new Point2D(1, 1));
        entityStack.add(star);

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT);

        if(!star.isAlive() && !star.isVisible()
                && game.getGameViewer().getStarsTaken() == 1) {
            System.out.println("Star test OK.");
        }
        else {
            System.out.println("Star test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && star.isAlive() && star.isVisible()
                && game.getGameViewer().getStarsTaken() == 0) {
            System.out.println("Undo - Star test OK.");
        }
        else {
            System.out.println("Undo - Star test FAIL.");
        }
    }
}
