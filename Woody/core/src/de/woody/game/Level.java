package de.woody.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
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

	public static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	private static Array<Rectangle> tiles = new Array<Rectangle>();
	public static Array<TiledMapTileLayer> layers = new Array<TiledMapTileLayer>();

	/**
	 * Returns the current spawn for the level and number of reached
	 * checkpoints.
	 * 
	 * @param level
	 *            current level
	 * @param checkpoint
	 *            last reached checkpoint
	 * @return fitting spawnpoint
	 */
	public static Vector2 getCurrentSpawn(int level, int checkpoint) {
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
	public static void addCollisionLayer(String name, TiledMap map) {
		layers.add((TiledMapTileLayer) map.getLayers().get(name));
	}

	/**
	 * Create Door-objects out of all the MapObject's add them to an Array and return it.
	 * 
	 * @param mapObjects  objects from which properties are read to create the doors
	 * @return  an array with all the created doors
	 */
	public static Array<Door> createDoors(MapObjects mapObjects) {
		Array<Door> doors = new Array<Door>();

		Iterator<MapObject> it = mapObjects.iterator();
		
		int tpX = 0;
		int tpY = 0;
		float x, y, width, height;
		
		while (it.hasNext()) {
			MapObject object = it.next();
			
			// get the properties of the object and save some of them in variables
			MapProperties properties = object.getProperties();
			x = properties.get("x", Float.class) * WoodyGame.UNIT_SCALE;
			y = properties.get("y", Float.class) * WoodyGame.UNIT_SCALE;
			width = properties.get("width", Float.class) * WoodyGame.UNIT_SCALE;
			height = properties.get("height", Float.class) * WoodyGame.UNIT_SCALE;
			
			// try getting the custom properties tpX/tpY, if they are missing someone fucked up and it's defaulting
			try {
				tpX = properties.get("tpX", Integer.class);
				tpY = properties.get("tpY", Integer.class);
			} catch(Exception e) {
				System.err.println("Missing tpX/tpY in " + object.getName() + "! Defaulting to (0, 0)!");
			}

			//create the door and add it to the array
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
	public static Array<Rectangle> getTiles(int startX, int startY, int endX, int endY) {
		rectPool.freeAll(tiles);
		tiles.clear();

		for (TiledMapTileLayer layer : layers) {
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

	public static Array<Sprite> registerCoins(MapLayer objectLayer, Array<Sprite> toBeFilled) {
		MapObjects objects = objectLayer.getObjects();
		Texture test = new Texture(Gdx.files.internal("textures/test.png"));
		TextureRegion coinTest = new TextureRegion(test, 0, 0, 64, 64);
		Iterator<MapObject> it = objects.iterator();
		while (it.hasNext()) {
			MapObject object = it.next();
			if (object.getName().startsWith("coin")) {
				MapProperties properties = object.getProperties();
				float x = properties.get("x", Float.class) * WoodyGame.UNIT_SCALE;
				float y = properties.get("y", Float.class) * WoodyGame.UNIT_SCALE;
				float width = properties.get("width", Float.class) * WoodyGame.UNIT_SCALE;
				float height = properties.get("height", Float.class) * WoodyGame.UNIT_SCALE;

				Sprite coin = new Sprite(coinTest);
				coin.setBounds(x, y, width, height);
				toBeFilled.add(coin);
			}
		}
		return toBeFilled;
	}

	public static MapLayer getLayer(TiledMap map, String name) {
		return map.getLayers().get(name);
	}

	public static MapObjects filterObjects(MapObjects objects, String prefix) {
		for (MapObject obj : objects) {
			if (!obj.getName().startsWith(prefix)) {
				objects.remove(obj);
			}
		}
		return objects;
	}

}
