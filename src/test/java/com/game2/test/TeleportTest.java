package com.game2.test;

import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_Teleport;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 4.10.2017.
 */
public class TeleportTest implements Test {

    public void run() {

        int width = 6;
        int height = 6;

        Game game = new Game();
        EntityStack entityStack = new EntityStack();
        E_Player activePlayer = new E_Player(new Point2D(0, 1));
        entityStack.add(activePlayer);

        entityStack.add(new E_Teleport(new Point2D(1, 1), 0));
        entityStack.add(new E_Teleport(new Point2D(2, 1), 0));
        entityStack.add(new E_Teleport(new Point2D(3, 1), 0));

        entityStack.add(new E_Player(new Point2D(2, 2)));
        entityStack.add(new E_Teleport(new Point2D(1, 2), 1));
        entityStack.add(new E_Teleport(new Point2D(2, 2), 1));

        game.loadDefinedMap(width, height, entityStack, activePlayer);
        game.start();

        game.movePlayer(Direction.RIGHT);
        game.movePlayer(Direction.STAY);
        game.movePlayer(Direction.STAY);
        game.movePlayer(Direction.STAY);

        game.movePlayer(Direction.DOWN);
        game.movePlayer(Direction.STAY); // the next teleport is occupied

        if(activePlayer.getPosition2D().getX() == 1
                && activePlayer.getPosition2D().getY() == 2
                && game.getGameViewer().getTime() == 5) {
            System.out.println("Teleport test OK.");
        }
        else {
            System.out.println("Teleport test FAIL.");
        }

        int time = game.getGameViewer().getTime();
        for(int i=0; i<time; i++) {
            game.undoMove();
        }
        if(activePlayer.getPosition2D().getX() == 0
                && activePlayer.getPosition2D().getY() == 1
                && game.getGameViewer().getTime() == 0) {
            System.out.println("Undo - Teleport test OK.");
        }
        else {
            System.out.println("Undo - Teleport test FAIL.");
        }
    }
}
