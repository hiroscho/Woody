package de.woody.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Respawn extends Rectangle {
	
	private Vector2 checkPoint;
	
	
	public Respawn(float x, float y, float width, float height, Vector2 checkPoint) {
		super(x, y, width, height);
		setCheckPoint(checkPoint);
	
	}
	
	public Respawn(float x, float y, float width, float height, int tpX, int tpY) {
		super(x, y, width, height);
		setCheckPoint(tpX, tpY);
	}
	
	public Vector2 getCheckPoint() {
		return checkPoint;
	}

	/**
	 * Set the point where the player is transported to.
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public void setCheckPoint(int x, int y) {
		setCheckPoint(new Vector2(x, y));
	}

	/**
	 * Set the point where the player is transported to.
	 * 
	 * @param CheckPoint
	 */
	public void setCheckPoint(Vector2 checkPoint) {
		this.checkPoint = checkPoint;
	}

}

	
	
	
