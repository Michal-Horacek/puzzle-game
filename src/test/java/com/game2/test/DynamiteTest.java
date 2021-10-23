package com.game2.test;
import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

public class DynamiteTest implements Test {
	
	public void run() {

		int width = 6;
		int height = 6;
		Game game = new Game();
		EntityStack entityStack = new EntityStack();
		E_Player activePlayer = new E_Player(new Point2D(0, 1));
		entityStack.add(activePlayer);
		
		E_Dynamite dynamite = new E_Dynamite(new Point2D(1, 1));
		entityStack.add(dynamite);
		E_Wall wall = new E_Wall(new Point2D(3, 1));
		entityStack.add(wall);
		
		E_Wood wood = new E_Wood(new Point2D(3, 0));
		entityStack.add(wood);

		game.loadDefinedMap(width, height, entityStack, activePlayer);
		game.start();

		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT); // blocked item test
		game.movePlayer(Direction.DOWN);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.UP); // border test
		game.movePlayer(Direction.LEFT);
		game.movePlayer(Direction.UP);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT); // pass-through test
		
		if(activePlayer.getPosition2D().getX() == 3
				&& activePlayer.getPosition2D().getY() == 0
				&& wood.getPosition2D().getX() == 3
				&& wood.getPosition2D().getY() == 0
				&& dynamite.getPosition2D().getX() == 2
				&& dynamite.getPosition2D().getY() == 0
				&& wood.isAlive() == false
				&& dynamite.isAlive() == false
				&& game.getGameViewer().getTime() == 8) {
			System.out.println("Dynamite test OK.");
		}
		else {
			System.out.println("Dynamite test FAIL.");
		}

		int time = game.getGameViewer().getTime();
		for(int i=0; i<time; i++) {
			game.undoMove();
		}

		if(activePlayer.getPosition2D().getX() == 0
				&& activePlayer.getPosition2D().getY() == 1
				&& game.getGameViewer().getTime() == 0
				&& wood.getPosition2D().getX() == 3
				&& wood.getPosition2D().getY() == 0
				&& dynamite.getPosition2D().getX() == 1
				&& dynamite.getPosition2D().getY() == 1
				&& wood.isAlive() == true
				&& wood.isVisible() == true
				&& dynamite.isAlive() == true
				&& dynamite.isVisible() == true) {
			System.out.println("Undo - Dynamite test OK.");
		}
		else {
			System.out.println("Undo - Dynamite test FAIL.");
		}
	}

}
