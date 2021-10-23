package com.game2.test;
import com.game2.game.entity.E_Player;
import com.game2.game.entity.E_Wood;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

public class WoodTest implements Test {

	public void run() {

		int width = 6;
		int height = 6;

		Game game = new Game();
		EntityStack entityStack = new EntityStack();
		E_Player activePlayer = new E_Player(new Point2D(0, 1));
		entityStack.add(activePlayer);

		E_Wood wood = new E_Wood(new Point2D(1, 1));
		entityStack.add(wood);
		entityStack.add(new E_Wood(new Point2D(3, 1)));

		game.loadDefinedMap(width, height, entityStack, activePlayer);
		game.start();

		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT); // blocked wood test
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.UP); // border test
		
		if(activePlayer.getPosition2D().getX() == 2
				&& activePlayer.getPosition2D().getY() == 1
				&& wood.getPosition2D().getX() == 2
				&& wood.getPosition2D().getY() == 0
				&& game.getGameViewer().getTime() == 4) {
			System.out.println("Wood test OK.");
		}
		else {
			System.out.println("Wood test FAIL.");
		}

		int time = game.getGameViewer().getTime();
		for(int i=0; i<time; i++) {
			game.undoMove();
		}

		if(activePlayer.getPosition2D().getX() == 0
				&& activePlayer.getPosition2D().getY() == 1
				&& game.getGameViewer().getTime() == 0
				&& wood.getPosition2D().getX() == 1
				&& wood.getPosition2D().getY() == 1) {
			System.out.println("Undo - Wood test OK.");
		}
		else {
			System.out.println("Undo - Wood test FAIL.");
		}
	}

}
