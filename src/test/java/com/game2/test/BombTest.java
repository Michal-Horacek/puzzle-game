package com.game2.test;

import com.game2.game.entity.E_Bomb;
import com.game2.game.entity.E_Player;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class BombTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        E_Bomb bomb = new E_Bomb(new Point2D(1, 1), 0);
        entityStack.add(bomb);
        entityStack.add(new E_Bomb(new Point2D(3, 1), 0));

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.RIGHT); // blocked bomb test
        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.UP);
        game.movePlayer(Direction.UP); // border test

        if(activePlayer.getPosition2D().getX() == 2
                && activePlayer.getPosition2D().getY() == 1
                && bomb.getPosition2D().getX() == 2
                && bomb.getPosition2D().getY() == 0
                && game.getGameViewer().getTime() == 4) {
            System.out.println("Bomb test OK.");
        }
        else {
            System.out.println("Bomb test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && bomb.getPosition2D().getX() == 1
                && bomb.getPosition2D().getY() == 1) {
            System.out.println("Undo - Bomb test OK.");
        }
        else {
            System.out.println("Undo - Bomb test FAIL.");
        }
    }
}
