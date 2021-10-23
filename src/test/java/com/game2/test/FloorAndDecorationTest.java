package com.game2.test;

import com.game2.game.entity.*;
import com.game2.game.misc.EntityStack;
import com.game2.game.core.Game;
import com.game2.game.misc.Direction;
import com.game2.game.misc.Point2D;

public class FloorAndDecorationTest implements Test {
	
	public void run() {

		int width = 6;
		int height = 6;

		Game game = new Game();
		EntityStack entityStack = new EntityStack();
		E_Player activePlayer = new E_Player(new Point2D(0, 1));
		entityStack.add(activePlayer);

		entityStack.add(new E_Floor(new Point2D(1, 1)));
		entityStack.add(new E_Floor(new Point2D(2, 1)));
		entityStack.add(new E_Floor(new Point2D(3, 1)));

		entityStack.add(new E_Decoration(new Point2D(2, 1)));

		E_Wood wood = new E_Wood(new Point2D(1, 1));
		entityStack.add(wood);

		game.loadDefinedMap(width, height, entityStack, activePlayer);
		game.start();

		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT);
		game.movePlayer(Direction.RIGHT);
		
		if(activePlayer.getPosition2D().getX() == 4
				&& activePlayer.getPosition2D().getY() == 1) {
			System.out.println("Floor and Decoration test OK.");
		}
		else {
			System.out.println("Floor and Decoration test FAIL.");
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
			System.out.println("Undo - Floor and Decoration test OK.");
		}
		else {
			System.out.println("Undo - Floor and Decoration test FAIL.");
		}
	}

}
