package de.woody.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Some simple functionality
 * 
 * PLANNED TO CHANGE SPAWNPOINT FINDING THROUGH TILED LAYER
 */
public class Level {

	public Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	private Array<Rectangle> tiles = new Array<Rectangle>();
	public Array<TiledMapTileLayer> collisionTileLayers = new Array<TiledMapTileLayer>();

	/**TODO: SPAWNSYSTEM
	 * Returns the current spawn for the level and number of reached
	 * checkpoints.
	 * 
	 * @param level
	 *            current level
	 * @param checkpoint
	 *            last reached checkpoint
	 * @return fitting spawnpoint
	 */
	public Vector2 getCurrentSpawn(int level, int checkpoint) {
		switch (level) {
		case 1:
			switch (checkpoint) {
			case 0:
				return new Vector2(10, 1);
			}

		default:
			return new Vector2(0, 0);
		}
	}

	/**
	 * Adds the name-layer to be checked for collisions.
	 * 
	 * @param name
	 *            the name of the layer
	 * @param map
	 *            the map with the layers
	 */
	public void addCollisionLayer(String name, TiledMap map) {
		collisionTileLayers.add(getTileLayer(map, name));
	}

	/**
	 * Create Door-objects out of all the MapObject's add them to an Array and
	 * return it.
	 * 
	 * @param mapObjects
	 *            objects from which properties are read to create the doors
	 * @return an array with all the created doors
	 */
	public Array<Door> createDoors(Array<MapObject> mapObjects) {
		Array<Door> doors = new Array<Door>();

		int tpX = 0;
		int tpY = 0;
		float x, y, width, height;

		for (MapObject object : mapObjects) {

			// get the properties of the object and save some of them in
			// variables
			MapProperties properties = object.getProperties();
			x = properties.get("x", Float.class) * WoodyGame.UNIT_SCALE;
			y = properties.get("y", Float.class) * WoodyGame.UNIT_SCALE;
			width = properties.get("width", Float.class) * WoodyGame.UNIT_SCALE;
			height = properties.get("height", Float.class) * WoodyGame.UNIT_SCALE;

			// try getting the custom properties tpX/tpY, if they are missing
			// someone fucked up and it's defaulting
			try {
				tpX = properties.get("tpX", Integer.class);
				tpY = properties.get("tpY", Integer.class);
			} catch (Exception e) {
				System.err.println("Missing tpX/tpY in " + object.getName() + "! Defaulting to (0, 0)!");
			}

			// create the door and add it to the array
			doors.add(new Door(x, y, width, height, tpX, tpY));
		}
		return doors;
	}

	/**
	 * Fills "tiles"-Array with all the tiles of the collisionLayer between the
	 * start and end point.
	 * 
	 * @param startX
	 *            x-coordinate of the first point
	 * @param startY
	 *            y-coordinate of the first point
	 * @param endX
	 *            x-coordinate of the second point
	 * @param endY
	 *            y-coordinate of the second point
	 */
	public Array<Rectangle> getTiles(int startX, int startY, int endX, int endY) {
		rectPool.freeAll(tiles);
		tiles.clear();

		for (TiledMapTileLayer layer : collisionTileLayers) {
			for (int y = startY; y <= endY; y++) {
				for (int x = startX; x <= endX; x++) {
					Cell cell = layer.getCell(x, y);
					if (cell != null) {
						Rectangle rect = rectPool.obtain();
						rect.set(x, y, 1, 1);
						tiles.add(rect);
					}
				}
			}
		}
		return tiles;
	}

	/**
	 * Returns the name layer of map.
	 * 
	 * @param map
	 *            map with layer
	 * @param name
	 *            name of the layer
	 * @return the layer
	 */
	public static TiledMapTileLayer getTileLayer(TiledMap map, String name) {
		return (TiledMapTileLayer) map.getLayers().get(name);
	}

	/**
	 * Get a filtered Array of MapObject's
	 * 
	 * @param objects
	 *            collection with all objects
	 * @param prefix
	 *            filter requirement
	 * @return an Array with MapObject's fitting the given prefix
	 */
	public static Array<MapObject> filterObjects(MapObjects objects, String prefix) {
		Array<MapObject> filteredObjects = new Array<MapObject>();

		for (MapObject obj : objects) {
			if (obj.getName().startsWith(prefix)) {
				filteredObjects.add(obj);
			}
		}
		return filteredObjects;
	}

}
