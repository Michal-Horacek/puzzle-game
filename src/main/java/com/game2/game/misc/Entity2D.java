package com.game2.game.misc;

import com.game2.game.misc.Point2D;

public abstract class Entity2D {

	private Point2D position2D;
	
	public Entity2D(Point2D position2D) {
		this.position2D = position2D;
	}
	
	public Point2D getPosition2D() {
		return position2D;
	}

	public void setPosition2D(Point2D position2D) {
		this.position2D.setX(position2D.getX());
		this.position2D.setY(position2D.getY());
	}
}
