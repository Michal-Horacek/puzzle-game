package com.game2.game.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.game2.game.core.model.MoveHistory;
import com.game2.game.entity.*;
import com.game2.game.misc.*;
import com.game2.gui.controller.SoundPlayer;
import com.game2.gui.controller.SoundType;
import javafx.scene.control.Alert;

public class GameController {
	
	private GameModel model;
	private SoundPlayer soundPlayer;

	public GameController(GameModel model) {
		this.model = model;
		soundPlayer = new SoundPlayer();
//		soundPlayer.loadGameSounds();
		soundPlayer.setSoundOn(false);
	}

	public void movePlayer(Direction direction) {

		// dead player
		if(!model.getActivePlayer().isAlive())
			return;

		GameEntity onPlaceEntity = getOnPlaceEntity(model.getActivePlayer());
		GameEntity destinationEntity = getDestinationEntity(model.getActivePlayer(), direction);

		// step from laser wrong direction
		if(onPlaceEntity != null) {

			// teleport
			if(onPlaceEntity.getEntityType() == EntityType.TELEPORT && direction == Direction.STAY) {
				teleport((E_Teleport)onPlaceEntity);
				return;
			}

			// laser
			if(onPlaceEntity.getEntityType() == EntityType.LASER) {
				if(!DirectionConverter.sameLine(((E_Laser)onPlaceEntity).getDirection(),direction)) {
					return;
				}
				else {
					// see onLeaveSquare() function
				}
			}
		}

		// empty
		if(destinationEntity == null) {
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// wall
		if(destinationEntity.getEntityType() == EntityType.WALL) {
			// do nothing
			return;
		}

		// teleport
		if(destinationEntity.getEntityType() == EntityType.TELEPORT) {

			if(!entityOccupied(destinationEntity)) {
				moveEntity(model.getActivePlayer(), direction);
				model.getRunInfo().increaseTime();
				return;
			}
			return;
		}

		// dynamite
		if(destinationEntity.getEntityType() == EntityType.DYNAMITE) {
			if(getDestinationEntity(destinationEntity, direction) == null) {
				moveEntity(destinationEntity, direction);
				moveEntity(model.getActivePlayer(), direction);
				model.getRunInfo().increaseTime();
				return;
			} else {
				if (getDestinationEntity(destinationEntity, direction).getEntityType() == EntityType.WOOD) {
					soundPlayer.playSound(SoundType.EXPLODE2);
					destroyEntity(getDestinationEntity(destinationEntity, direction));
					destroyEntity(destinationEntity);
					moveEntity(model.getActivePlayer(), direction);
					model.getRunInfo().increaseTime();
					return;
				}
			}
		}

		// finish
		if(destinationEntity.getEntityType() == EntityType.FINISH
				&& destinationEntity.isPassable()) {
			finishGame();
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// laser
		if(destinationEntity.getEntityType() == EntityType.LASER) {
			if(!DirectionConverter.sameLine(((E_Laser)destinationEntity).getDirection(), direction)) // wrong direction
				return;
			if(!(destinationEntity).isPassable())
				return;

			soundPlayer.playSound(SoundType.LASER);
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// lock
		if(destinationEntity.getEntityType() == EntityType.LOCK) {
			if(model.getRunInfo().getKeysTaken() > 0) {
				soundPlayer.playSound(SoundType.LOCK);
				destroyEntity(destinationEntity);
				model.getRunInfo().removeKey();
				moveEntity(model.getActivePlayer(), direction);
				model.getRunInfo().increaseTime();
			}
		}

		// stone
		if(destinationEntity.getEntityType() == EntityType.STONE) {
			if(model.getRunInfo().getDrillsTaken() > 0) {
				soundPlayer.playSound(SoundType.STONE);
				destroyEntity(destinationEntity);
				model.getRunInfo().removeDrill();
				moveEntity(model.getActivePlayer(), direction);
				model.getRunInfo().increaseTime();
				return;
			}
		}

		// star
		if(destinationEntity.getEntityType() == EntityType.STAR) {
			soundPlayer.playSound(SoundType.PICKUP);
			destroyEntity(destinationEntity);
			model.getRunInfo().addStar();
			checkAllStarsTaken();
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// key
		if(destinationEntity.getEntityType() == EntityType.KEY) {
			soundPlayer.playSound(SoundType.PICKUP);
			destroyEntity(destinationEntity);
			model.getRunInfo().addKey();
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// drill
		if(destinationEntity.getEntityType() == EntityType.DRILL) {
			soundPlayer.playSound(SoundType.PICKUP);
			destroyEntity(destinationEntity);
			model.getRunInfo().addDrill();
			moveEntity(model.getActivePlayer(), direction);
			model.getRunInfo().increaseTime();
			return;
		}

		// trigger
		if(destinationEntity.getEntityType() == EntityType.TRIGGER) {
			if(!entityOccupied(destinationEntity)) {
				soundPlayer.playSound(SoundType.ACTBUTTON);
				moveEntity(model.getActivePlayer(), direction);
				pushTrigger((E_Trigger) destinationEntity);
				model.getRunInfo().increaseTime();
			}
		}

		// triggerWall
		if(destinationEntity.getEntityType() == EntityType.TRIGGERWALL) {
			if(!destinationEntity.isVisible()) {
				moveEntity(model.getActivePlayer(), direction);
			}
		}

		// wood, bomb
		if(destinationEntity.getEntityType() == EntityType.WOOD
				|| destinationEntity.getEntityType() == EntityType.BOMB) {
			if(getDestinationEntity(destinationEntity, direction) == null) {
				moveEntity(destinationEntity, direction);
				moveEntity(model.getActivePlayer(), direction);
				model.getRunInfo().increaseTime();
				return;
			}
		}

		// rest - E_Wall, E_Stop
		// ...
	}

	public void undoMove() {

		if(model.getRunInfo().getTime() == 0) {
			return;
		}
		while(model.getMoveHistory().getLastTime() == model.getRunInfo().getTime() - 1) {
			model.getMoveHistory().revertAction();
		}
		model.getRunInfo().decreaseTime();
	}

	@SuppressWarnings("unchecked")
	public void switchPlayer() {

		List<E_Player> playersList = (List<E_Player>)(List<?>)model
				.getEntityStack()
				.getStack()
				.stream()
				.filter(p -> p.getEntityType() == EntityType.PLAYER)
				.filter(p -> p.isAlive())
				.collect(Collectors.toList());

		if(playersList.size() == 0)
			return;

		int index = (playersList.indexOf(model.getActivePlayer()) + 1) % playersList.size();
		model.setActivePlayer(playersList.get(index));
	}

	public void startGame() {
		//
	}

	public void setSoundOn(boolean state) {
		soundPlayer.setSoundOn(state);
	}

	// false if touching border
	private boolean checkBorders(GameEntity entity, Direction direction) {

		int posX = entity.getPosition2D().getX() + DirectionConverter.getX(direction);
		int posY = entity.getPosition2D().getY() + DirectionConverter.getY(direction);

		if(posX < 0 || posX >= model.getMapInfo().getWidth() || posY < 0 || posY >= model.getMapInfo().getHeight())
			return true;
		return false;
	}

	private void moveEntity(GameEntity entity, Direction direction) {

		onLeaveSquare(entity.getPosition2D());
		moveEntity(entity, new Point2D(
				entity.getPosition2D().getX() + DirectionConverter.getX(direction),
				entity.getPosition2D().getY() + DirectionConverter.getY(direction)
		));
	}

	private void moveEntity(GameEntity entity, Point2D point2D) {
		int moveX = point2D.getX() - entity.getPosition2D().getX();
		int moveY = point2D.getY() - entity.getPosition2D().getY();
		entity.setPosition2D(point2D);
		if(moveX != 0) {
			model.getMoveHistory().addAction(
					model.getRunInfo().getTime(),
					entity,
					MoveHistory.ActionType.ADD_X,
					moveX
			);
		}
		if(moveY != 0) {
			model.getMoveHistory().addAction(
					model.getRunInfo().getTime(),
					entity,
					MoveHistory.ActionType.ADD_Y,
					moveY
			);
		}
	}

	// lock laser, release trigger
	private void onLeaveSquare(Point2D position2D) {

		Iterator<GameEntity> itr;

		// lock laser
		itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.LASER
					&& destinationEntity.getPosition2D().getX() == position2D.getX()
					&& destinationEntity.getPosition2D().getY() == position2D.getY()) {
				E_Laser laser = (E_Laser) destinationEntity;
				laser.setPassable(false);
				laser.setSkin(laser.getSkin()+1);
				model.getMoveHistory().addAction(
						model.getRunInfo().getTime(),
						destinationEntity,
						MoveHistory.ActionType.SET_PASSABLE,
						false);
				model.getMoveHistory().addAction(
						model.getRunInfo().getTime(),
						destinationEntity,
						MoveHistory.ActionType.ADD_SKIN,
						1
				);
				break;
			}
		}

		// BEGIN release trigger
		int colorIndex = -1;

		// get trigger color
		itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.TRIGGER
					&& destinationEntity.getPosition2D().getX() == position2D.getX()
					&& destinationEntity.getPosition2D().getY() == position2D.getY()) {
				colorIndex = ((E_Trigger)destinationEntity).getColorIndex();
				break;
			}
		}

		if(colorIndex == -1)
			return;

		// release triggerWalls of the color
		itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.TRIGGERWALL) {
				if(((E_TriggerWall)destinationEntity).getColorIndex() == colorIndex) {
					destinationEntity.setVisible(true);
					model.getMoveHistory().addAction(
							model.getRunInfo().getTime(),
							destinationEntity,
							MoveHistory.ActionType.SET_VISIBLE,
							true
					);
				}
			}
		}
		// END release trigger

	}

	private GameEntity getDestinationEntity(GameEntity entity, Direction direction) {

		if(checkBorders(entity, direction))
			return new E_Wall(new Point2D(0, 0)); // simulating borders

		if(!model.getMapInfo().isFreeMove() && !isDestFloor(entity, direction)) {
			return new E_Wall(new Point2D(0, 0)); // simulating borders, if there is no floor
		}

		int destX = entity.getPosition2D().getX() + DirectionConverter.getX(direction);
		int destY = entity.getPosition2D().getY() + DirectionConverter.getY(direction);

		List<GameEntity> list = model
				.getEntityStack()
				.getStack()
				.stream()
				.filter(p -> p.getLayerType() != LayerType.BOTTOM)
				.filter(p -> p.getEntityType() != EntityType.DECORATION)
				.filter(p -> p.isAlive())
				.filter(p -> p.isVisible())
				.filter(p -> p.getPosition2D().getX() == destX)
				.filter(p -> p.getPosition2D().getY() == destY)
				.collect(Collectors.toList());

		return list.isEmpty() ? null : list.get(0);
	}

	private GameEntity getOnPlaceEntity(GameEntity entity) {

		int entityX = entity.getPosition2D().getX();
		int entityY = entity.getPosition2D().getY();

		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getLayerType() != LayerType.MIDDLE)
				continue;
			if (destinationEntity.getEntityType() == EntityType.DECORATION)
				continue;
			if (!destinationEntity.isAlive())
				continue;
			if(!destinationEntity.isVisible())
				continue;

			int destX = destinationEntity.getPosition2D().getX();
			int destY = destinationEntity.getPosition2D().getY();
			if(entityX == destX && entityY == destY)
				return destinationEntity;
		}

		return null;
	}

	private void teleport(E_Teleport teleport) {

		int index = 0;
		List<E_Teleport> teleportList = new ArrayList<>();
		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity entity = itr.next();
			if(entity.getEntityType() == EntityType.TELEPORT) {
				if(((E_Teleport)entity).getColorIndex() != teleport.getColorIndex())
					continue;
				if(!entityOccupied(entity)) {
					teleportList.add((E_Teleport) entity);
				}
				if(entity == teleport) {
					index = teleportList.indexOf(entity);
				}
			}
		}
		if(teleportList.size() < 2)
			return;

		index = (++index)%teleportList.size();
		soundPlayer.playSound(SoundType.TELEPORT);
		moveEntity(model.getActivePlayer(), teleportList.get(index).getPosition2D());
		model.getRunInfo().increaseTime();
	}

	private boolean entityOccupied(GameEntity entity) {

		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.PLAYER) {
				if(entity.getPosition2D().getX() != destinationEntity.getPosition2D().getX()
						|| entity.getPosition2D().getY() != destinationEntity.getPosition2D().getY())
					continue;
				if(destinationEntity == model.getActivePlayer())
					return false;

				// teleport is occupied by another player
				return true;
			}

		}
		return false;
	}

	private void pushTrigger(E_Trigger trigger) {
		hideTriggerWalls(trigger.getColorIndex());
		explodeBombs(trigger.getColorIndex());
	}

	private void hideTriggerWalls(int colorIndex) {

		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.TRIGGERWALL) {
				if(((E_TriggerWall) destinationEntity).getColorIndex() == colorIndex) {
					destinationEntity.setVisible(false);
					model.getMoveHistory().addAction(
							model.getRunInfo().getTime(),
							destinationEntity,
							MoveHistory.ActionType.SET_VISIBLE,
							false
					);
				}
			}
		}
	}

	private void explodeBombs(int colorIndex) {

		List<GameEntity> toDestroy = new ArrayList<>();

		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if (destinationEntity.getEntityType() == EntityType.BOMB) {
				if(((E_Bomb) destinationEntity).getColorIndex() == colorIndex
						&& destinationEntity.isAlive() == true
						&& destinationEntity.isVisible() == true) {
					soundPlayer.playSound(SoundType.EXPLODE1);
					destroyEntity(destinationEntity);
					destroySurroundings(destinationEntity.getPosition2D());
				}
			}
		}
	}

	private void destroySurroundings(Point2D localization2D) {

		Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
		while(itr.hasNext()) {
			GameEntity destinationEntity = itr.next();
			if(destinationEntity.getLayerType() == LayerType.BOTTOM)
				continue;
			if(destinationEntity.getEntityType() == EntityType.DECORATION
					|| destinationEntity.getEntityType() == EntityType.STOP)
				continue;
			if(Math.abs(destinationEntity.getPosition2D().getX()-localization2D.getX()) <= 1
					&& Math.abs(destinationEntity.getPosition2D().getY()-localization2D.getY()) <= 1) {
				if(destinationEntity.getEntityType() == EntityType.BOMB
						&& destinationEntity.isAlive() && destinationEntity.isVisible()) {
					destroyEntity(destinationEntity);
					destroySurroundings(destinationEntity.getPosition2D());
				} else {
					destroyEntity(destinationEntity);
				}

			}
		}
	}

	private boolean isDestFloor(GameEntity entity, Direction direction) {

		for(GameEntity destinationEntity : model.getEntityStack().getStack()) {
			if(destinationEntity.getLayerType() != LayerType.BOTTOM)
				continue;
			int destX = entity.getPosition2D().getX() + DirectionConverter.getX(direction);
			int destY = entity.getPosition2D().getY() + DirectionConverter.getY(direction);
			if(destinationEntity.getPosition2D().getX() == destX
					&& destinationEntity.getPosition2D().getY() == destY) {
				return true;
			}
		}
		return false;
	}

	private void finishGame() {

		if(!model.getRunInfo().isFinished()) {
			model.getRunInfo().setFinished(true);
			System.out.println("Game finished!");
//			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Puzzle solved !");
//			alert.show();
		}
	}

	private void destroyEntity(GameEntity entity) {
		entity.setVisible(false);
		entity.setAlive(false);
		model.getMoveHistory().addAction(
				model.getRunInfo().getTime(),
				entity,
				MoveHistory.ActionType.SET_ALIVE,
				false
		);
		model.getMoveHistory().addAction(
				model.getRunInfo().getTime(),
				entity,
				MoveHistory.ActionType.SET_VISIBLE,
				false
		);
	}

	private void checkAllStarsTaken() {
		if(model.getRunInfo().getStarsTaken() == model.getMapInfo().getStarsCount()) {
			Iterator<GameEntity> itr = model.getEntityStack().getStack().iterator();
			while(itr.hasNext()) {
				GameEntity entity = (GameEntity) itr.next();
				if (entity.getEntityType() == EntityType.FINISH) {
					entity.setPassable(true);
					entity.setSkin(entity.getSkin() + 1);
					model.getMoveHistory().addAction(
							model.getRunInfo().getTime(),
							entity,
							MoveHistory.ActionType.SET_PASSABLE,
							true
					);
					model.getMoveHistory().addAction(
							model.getRunInfo().getTime(),
							entity,
							MoveHistory.ActionType.ADD_SKIN,
							1
					);
				}
			}
		}
	}
}
