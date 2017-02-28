package de.woody.game;

import java.util.Iterator;

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
	public static Array<ExtendedRectangle> doors = new Array<ExtendedRectangle>();
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
	 * @param name  the name of the layer
	 * @param map  the map with the layers
	 */
	public static void addCollisionLayer(String name, TiledMap map) {
		layers.add((TiledMapTileLayer) map.getLayers().get(name));
	}

	/**
	 * Create all rectangles with the namePrefix from the given layer and add
	 * them to the given array. This implementation is special because all
	 * rectangle objects MUST have the tpX and tpY properties.
	 * 
	 * @param objectLayer  the layer from which objects are read
	 * @param namePrefix  the name Prefix by which objects are differentiated
	 * @param toBeFilled  array in which rectangles are saved
	 * @return  the array after being filled
	 */
	public static Array<ExtendedRectangle> rectanglesFromObjectLayer(MapLayer objectLayer, String namePrefix,
			Array<ExtendedRectangle> toBeFilled) {
		MapObjects objects = objectLayer.getObjects();
		Iterator<MapObject> it = objects.iterator();
		while (it.hasNext()) {
			MapObject object = it.next();
			if (object.getName().startsWith(namePrefix)) {
				MapProperties properties = object.getProperties();
				float x = properties.get("x", Float.class) * WoodyGame.UNIT_SCALE;
				float y = properties.get("y", Float.class) * WoodyGame.UNIT_SCALE;
				float width = properties.get("width", Float.class) * WoodyGame.UNIT_SCALE;
				float height = properties.get("height", Float.class) * WoodyGame.UNIT_SCALE;

				ExtendedRectangle rec = new ExtendedRectangle(x, y, width, height, object.getName());
				try {
					rec.setTeleportPoint(properties.get("tpX", Integer.class), properties.get("tpY", Integer.class));
				} catch (Exception e) {
					System.err.println("The properties tpX/tpY are missing in one or more object!");
				}
				toBeFilled.add(rec);
			}
		}
		return toBeFilled;
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
}
