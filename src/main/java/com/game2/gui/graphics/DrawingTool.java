package com.game2.gui.graphics;

import com.game2.game.core.Game;
import com.game2.game.entity.E_Laser;
import com.game2.game.misc.GameEntity;
import com.game2.game.misc.EntityType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class DrawingTool {

	private GraphicsContext gc;
	private Game game;
	private int squareSize;
	private SkinStack skinStack;

	public DrawingTool(GraphicsContext gc, Game game, int squareSize) {
		this.gc = gc;
		this.game = game;
		this.squareSize = squareSize;
		skinStack = new SkinStack();
		loadSkins();
	}

	public void drawSimpleBackground(Color color) {

		gc.setFill(color);
		gc.fillRect(0, 0,game.getGameViewer().getWidth()*squareSize, game.getGameViewer().getHeight()*squareSize);
	}

	public void drawGridBackground() {

		for(int x = 0; x < game.getGameViewer().getWidth(); x++) {
			for(int y = 0; y < game.getGameViewer().getHeight(); y++) {

				gc.setStroke(Color.GRAY);
				gc.strokeRect(x * squareSize, y * squareSize, squareSize, squareSize);

				gc.setFill(Color.WHITE);
				gc.fillRect(x * squareSize + 1, y * squareSize + 1, squareSize - 2, squareSize - 2);
			}
		}
	}

	public void drawImageBackground(Image image) {

	}

	public void drawSimpleEntities() {

		List<GameEntity> entityList = game.getGameViewer().getEntityStack().getStack();
		entityList = entityList
				.stream()
				.filter(p -> p.isAlive())
				.filter(p -> p.isVisible())
				.collect(Collectors.toList());
		ListIterator<GameEntity> li = entityList.listIterator(entityList.size());

		while(li.hasPrevious()) {

			GameEntity entity = li.previous();
			switch(entity.getEntityType()) {

				case FLOOR:
					gc.setFill(Color.LIGHTGREEN); break;
				case DECORATION:
					gc.setFill(Color.LIGHTSKYBLUE); break;
				case FINISH:
					gc.setFill(Color.MAGENTA); break;
				case LASER:
					if(((E_Laser)entity).isPassable()) {
						gc.setFill(Color.DARKCYAN);
					}
					else {
						gc.setFill(Color.DARKBLUE);
					}
					break;
				case TELEPORT:
					gc.setFill(Color.BURLYWOOD); break;
				case TRIGGER:
					gc.setFill(Color.CHOCOLATE); break;
				case PLAYER:
					if(entity == game.getGameViewer().getActivePlayer()) {
						gc.setFill(Color.CYAN);
					} else {
						gc.setFill(Color.BLUE);
					}
					break;
				case WOOD:
					gc.setFill(Color.ORANGE); break;
				case WALL:
					gc.setFill(Color.RED); break;
				case DYNAMITE:
					gc.setFill(Color.GOLD); break;
				case STAR:
					gc.setFill(Color.PINK); break;
				case KEY:
					gc.setFill(Color.LIME); break;
				case LOCK:
					gc.setFill(Color.GREEN); break;
				case DRILL:
					gc.setFill(Color.SANDYBROWN); break;
				case STONE:
					gc.setFill(Color.BROWN); break;
				case TRIGGERWALL:
					gc.setFill(Color.CRIMSON); break;
				case BOMB:
					gc.setFill(Color.BLACK); break;
				default:
					gc.setFill(Color.GRAY); break;
			}

			int offset;
			switch(entity.getLayerType()) {
				case BOTTOM:
					offset = 1; break;
				case MIDDLE:
					offset = 3; break;
				case TOP:
					offset = 5; break;
				default:
					offset = 1; break;
			}

			gc.fillRect(entity.getPosition2D().getX() * squareSize + offset,
					entity.getPosition2D().getY() * squareSize + offset,
					squareSize - offset * 2, squareSize - offset * 2);
		}
	}

	public void drawImageEntities() {

		List<GameEntity> entityList = game.getGameViewer().getEntityStack().getStack();
		entityList = entityList
				.stream()
				.filter(p -> p.isAlive())
				.filter(p -> p.isVisible())
				.collect(Collectors.toList());
		ListIterator<GameEntity> li = entityList.listIterator(entityList.size());

		while(li.hasPrevious()) {

			GameEntity entity = li.previous();

			if (!entity.isAlive()) continue;
			if (!entity.isVisible()) continue;
			if(entity.getEntityType() == EntityType.STOP) continue;

			drawEntity(entity);
		}
	}

	public void drawEntity(GameEntity entity) {

		Image image = skinStack.getImage(entity.getEntityType(), entity.getSkin());
		gc.drawImage(image, entity.getPosition2D().getX() * squareSize, entity.getPosition2D().getY() * squareSize, squareSize, squareSize);
	}

	private void loadSkins() {

		try {
			File levelDir = null;
			levelDir = new File(getClass().getClassLoader().getResource("images/items/").toURI());
			File[] fileList = levelDir.listFiles();
			for(File file : fileList) {
				String fileName = file.getName();
				EntityType type = resolveType(fileName);
				int skin = Integer.parseInt(fileName.substring(2,5));
				Image image = null;
				try {
					image = new Image(file.toURI().toURL().toExternalForm());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				SkinItem item = new SkinItem(type, skin, image);
				skinStack.getStack().add(item);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private EntityType resolveType(String s) {

		String entityType = s.substring(0, 2);

		if(entityType.equals("AA")) {
			return EntityType.PLAYER;
		} else if(entityType.equals("AB")) {
			return EntityType.FINISH;
		} else if(entityType.equals("AC")) {
			return EntityType.STAR;
		} else if(entityType.equals("AD")) {
			return EntityType.WALL;
		} else if(entityType.equals("AE")) {
			return EntityType.FLOOR;
		} else if(entityType.equals("AF")) {
			return EntityType.LASER;
		} else if(entityType.equals("AG")) {
			return EntityType.WOOD;
		} else if(entityType.equals("AH")) {
			return EntityType.DYNAMITE;
		} else if(entityType.equals("AI")) {
			return EntityType.LOCK;
		} else if(entityType.equals("AJ")) {
			return EntityType.KEY;
		} else if(entityType.equals("AK")) {
			return EntityType.STONE;
		} else if(entityType.equals("AL")) {
			return EntityType.DRILL;
		} else if(entityType.equals("AM")) {
			return EntityType.TRIGGER;
		} else if(entityType.equals("AN")) {
			return EntityType.BOMB;
		} else if(entityType.equals("AO")) {
			return EntityType.TRIGGERWALL;
		}else if(entityType.equals("AP")) {
			return EntityType.TELEPORT;
		} else if(entityType.equals("ZA")) {
			return EntityType.STOP;
		} else if(entityType.equals("ZB")) {
			return EntityType.DECORATION;
		}

		return null;
	}

	public SkinStack getSkinStack() {
		return skinStack;
	}
}