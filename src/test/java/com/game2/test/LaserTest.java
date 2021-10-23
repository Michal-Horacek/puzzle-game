package com.game2.test;

import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

/**
 * Created by horacekm on 3.10.2017.
 */
public class LaserTest implements Test {

	public void run() {

		int width = 6;
		int height = 6;
		Game game = new Game();
		EntityStack entityStack = new EntityStack();
		E_Player activePlayer = new E_Player(new Point2D(0, 1));
		entityStack.add(activePlayer);

		E_Laser laserHor = new E_Laser(new Point2D(1, 1), Direction.DOWN);
		entityStack.add(laserHor);

		E_Laser laserVer = new E_Laser(new Point2D(3, 3), Direction.RIGHT);
		entityStack.add(laserVer);

		E_Wood wood = new E_Wood(new Point2D(2, 3));
		entityStack.add(wood);

		game.loadDefinedMap(width, height, entityStack, activePlayer);
		game.start();

		// horizontal laser test
		game.movePlayer(Direction.RIGHT); // laser bad side opened
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.DOWN); // laser good side opened
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.UP); // laser good side closed

		// vertical laser test + wood
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.RIGHT); // wood blocked
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.UP); // laser bad side opened
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.LEFT); // laser good side opened
		game.movePlayer(Direction.LEFT); // leave laser, move wood
		game.movePlayer(Direction.RIGHT); // laser good side closed

		if(activePlayer.getPosition2D().getX() == 2
				&& activePlayer.getPosition2D().getY() == 3
				&& !laserHor.isPassable()
				&& laserHor.getSkin() == 1
				&& !laserVer.isPassable()
				&& laserVer.getSkin() == 1
				&& game.getGameViewer().getTime() == 12
				) {
			System.out.println("Laser test OK.");
		}
		else {
			System.out.println("Laser test FAIL.");
		}

		int time = game.getGameViewer().getTime();
		for(int i=0; i<time; i++) {
			game.undoMove();
		}

		if(activePlayer.getPosition2D().getX() == 0
				&& activePlayer.getPosition2D().getY() == 1
				&& game.getGameViewer().getTime() == 0
				&& wood.getPosition2D().getX() == 2
				&& wood.getPosition2D().getY() == 3
				&& laserHor.isPassable()
				&& laserHor.getSkin() == 0
				&& laserVer.isPassable()
				&& laserVer.getSkin() == 0) {
			System.out.println("Undo - Laser test OK.");
		}
		else {
			System.out.println("Undo - Laser test FAIL.");
		}
	}
}
