package com.game2.test;

import com.game2.game.entity.E_Player;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class MultiplayerTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player player1 = new E_Player(new Point2D(0, 1));
        entityStack.add(player1);

        E_Player player2 = new E_Player(new Point2D(2, 1));
        entityStack.add(player2);

        game.loadDefinedMap(width, height, entityStack, player1);
        game.start();

        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.RIGHT); // player block

        if(player1.getPosition2D().getX() == 1
                && player1.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 1) {
            System.out.println("Multiplayer test OK.");
        }
        else {
            System.out.println("Multiplayer test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }

        if(player1.getPosition2D().getX() == 0
                && player1.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0) {
            System.out.println("Undo - Multiplayer test OK.");
        }
        else {
            System.out.println("Undo - Multiplayer test FAIL.");
        }
    }
}
