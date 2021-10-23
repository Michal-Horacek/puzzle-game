package com.game2.game.core;

import com.game2.game.entity.*;
import com.game2.game.misc.Direction;
import com.game2.game.misc.EntityStack;

public final class Game {

	private GameModel model;
	private GameLoader loader;
	private GameController controller;
	private GameViewer viewer;
	
	public Game() {
		model = new GameModel();
		loader = new GameLoader(model);
		controller = new GameController(model);
		viewer = new GameViewer(model);
	}

	public void loadMapFromFile(String filePath) {
		loader.loadFromFile(filePath);
	}

	public void loadDefinedMap(int width, int height, EntityStack entityStack, E_Player activePlayer) {
		loader.loadDefinedMap(width, height, entityStack, activePlayer);
	}

	public void createEmptyMap(int width, int height) {
		loader.createEmptyMap(width, height);
	}

	public void start() {
		controller.startGame();
	}

	public GameViewer getGameViewer() {
		return viewer;
	}
	
	public void movePlayer(Direction direction) {
		controller.movePlayer(direction);
	}

	public void switchPlayer() {
		controller.switchPlayer();
	}

	public void undoMove() {
		controller.undoMove();
	}

	public void setSoundOn(boolean state) {
		controller.setSoundOn(state);
	}
}
