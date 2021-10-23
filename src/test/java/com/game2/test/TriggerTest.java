package com.game2.test;

import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class TriggerTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player player1 = new E_Player(new Point2D(0, 1));
        entityStack.add(player1);
        E_Player player2 = new E_Player(new Point2D(0, 2));
        entityStack.add(player2);

        E_Trigger trigger1 = new E_Trigger(new Point2D(1, 1), 0);
        entityStack.add(trigger1);
        E_TriggerWall triggerWall = new E_TriggerWall(new Point2D(1, 2), 0);
        entityStack.add(triggerWall);
        E_Trigger trigger2 = new E_Trigger(new Point2D(2, 1), 1);
        entityStack.add(trigger2);
        E_Bomb bomb = new E_Bomb(new Point2D(3, 3), 1);
        entityStack.add(bomb);
        E_Wall wall = new E_Wall(new Point2D(4, 4));
        entityStack.add(wall);

        game.loadDefinedMap(width, height, entityStack, player1);
        game.start();

        // test triggerWall
        game.movePlayer(Direction.RIGHT); // push trigger1
        game.switchPlayer();
        game.movePlayer(Direction.RIGHT); // pass triggerWall
        game.movePlayer(Direction.RIGHT);
        game.switchPlayer();
        game.movePlayer(Direction.RIGHT); // push trigger2, kill player2
        game.switchPlayer(); // player not changed
        game.movePlayer(Direction.LEFT); // push trigger1

        if(!player2.isAlive()
                && !triggerWall.isVisible()
                && !bomb.isAlive()
                && !wall.isAlive()
                && !wall.isVisible()) {
            System.out.println("Trigger test OK.");
        }
        else {
            System.out.println("Trigger test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(player1.getPosition2D().getX() == 0
                && player1.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0
                && player2.isAlive()
                && player2.isVisible()
                && triggerWall.isVisible()
                && bomb.isAlive()
                && bomb.isVisible()
                && wall.isAlive()
                && wall.isVisible()) {
            System.out.println("Undo - Trigger test OK.");
        }
        else {
            System.out.println("Undo - Trigger test FAIL.");
        }
    }
}
