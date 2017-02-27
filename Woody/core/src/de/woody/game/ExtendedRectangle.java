package de.woody.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ExtendedRectangle extends Rectangle {

	private String name;
	private Vector2 teleportPoint;

	public ExtendedRectangle(float x, float y, float width, float height, String name) {
		super(x, y, width, height);
		this.name = name;
	}

	/**
	 *  No real use, left it here just in case
	 * @return  the name of the object in tiled
	 */
	public String getName() {
		return name;
	}

	public Vector2 getTeleportPoint() {
		return teleportPoint;
	}

	/**
	 * Currently only used for doors.
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public void setTeleportPoint(float x, float y) {
		teleportPoint = new Vector2(x, y);
	}

	public void setTeleportPoint(Vector2 vec) {
		teleportPoint = vec;
	}

}
