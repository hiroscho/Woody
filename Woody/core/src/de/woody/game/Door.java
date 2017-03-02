package de.woody.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Door extends Rectangle {

	private Vector2 teleportPoint;
	
	/**
	 * Create a rectangle with information on where to teleport the player on using the door.
	 * 
	 * @param x  rectangle x-Coordinate
	 * @param y  rectangle y-Coordinate
	 * @param width  rectangle width
	 * @param height  rectangle height
	 * @param teleportPoint
	 */
	public Door(float x, float y, float width, float height, Vector2 teleportPoint) {
		super(x, y, width, height);
		setTeleportPoint(teleportPoint);
	}

	/**
	 * Create a rectangle with information on where to teleport the player on using the door.
	 * 
	 * @param x  rectangle x-Coordinate
	 * @param y  rectangle y-Coordinate
	 * @param width  rectangle width
	 * @param height  rectangle height
	 * @param tpX  teleportPoint x-Coordinate
	 * @param tpY  teleportPoint y-Coordinate
	 */
	public Door(float x, float y, float width, float height, int tpX, int tpY) {
		super(x, y, width, height);
		setTeleportPoint(tpX, tpY);
	}
	
	public Vector2 getTeleportPoint() {
		return teleportPoint;
	}

	/**
	 * Set the point where the player is transported to.
	 * 
	 * @param x
	 *            x-Coordinate
	 * @param y
	 *            y-Coordinate
	 */
	public void setTeleportPoint(int x, int y) {
		setTeleportPoint(new Vector2(x, y));
	}

	/**
	 * Set the point where the player is transported to.
	 * 
	 * @param teleportPoint
	 */
	public void setTeleportPoint(Vector2 teleportPoint) {
		this.teleportPoint = teleportPoint;
	}

}
