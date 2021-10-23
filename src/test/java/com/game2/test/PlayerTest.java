package com.game2.test;
import com.game2.game.entity.E_Player;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

public class PlayerTest implements Test {

	public void run() {

		int width = 6;
		int height = 6;

		Game game = new Game();
		EntityStack entityStack = new EntityStack();
		E_Player activePlayer = new E_Player(new Point2D(0, 0));
		entityStack.add(activePlayer);

		game.loadDefinedMap(width, height, entityStack, activePlayer);
		game.start();
		
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.LEFT);
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.UP); // border test
		
		if(activePlayer.getPosition2D().getX() == 1
				&& activePlayer.getPosition2D().getY() == 0
				&& game.getGameViewer().getTime() == 5) {
			System.out.println("Player test OK.");
		}
		else {
			System.out.println("Player test FAIL.");
		}

		int time = game.getGameViewer().getTime();
		for(int i=0; i<time; i++) {
			game.undoMove();
		}

		if(activePlayer.getPosition2D().getX() == 0
				&& activePlayer.getPosition2D().getY() == 0
				&& game.getGameViewer().getTime() == 0) {
			System.out.println("Undo - Player test OK.");
		}
		else {
			System.out.println("Undo - Player test FAIL.");
		}
	}
}
