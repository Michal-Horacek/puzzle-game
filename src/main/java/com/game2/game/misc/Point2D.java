package com.game2.game.misc;

public class Point2D {
	
	private int x;
	private int y;
	
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}

	public void addX(int x) {
		setX(getX() + x);
	}

	public void addY(int y) {
		setY(getY() + y);
	}
	
	public void addXY(int x, int y) {
		setX(getX() + x);
		setY(getY() + y);
	}

}
