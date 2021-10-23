package com.game2.game.misc;

public abstract class GameEntity extends Entity2D {
	
	private final EntityType entityType;
	private final LayerType layerType;
	private int skin;
	private boolean alive;
	private boolean visible;
	private boolean passable;

	public GameEntity(Point2D location2d, EntityType entityType, LayerType layerType, int skin, boolean alive,
					  boolean visible, boolean passable) {
		super(location2d);
		this.entityType = entityType;
		this.layerType = layerType;
		this.skin = skin;
		this.alive = alive;
		this.visible = visible;
		this.passable = passable;

	}

	public EntityType getEntityType() {
		return entityType;
	}
	
	public LayerType getLayerType() {
		return layerType;
	}

	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}

}
