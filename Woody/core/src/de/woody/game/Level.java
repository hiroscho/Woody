package de.woody.game;

import com.badlogic.gdx.graphics.Texture;
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
 * Contains level data such as enemies, doors, collisionTileLayers.
 * 
 */
public class Level {

	public Pool<Rectangle> rectPool;
	private Array<Rectangle> tiles;
	private Array<Door> doors;
	private Array<Enemy> enemies;
	private Array<TiledMapTileLayer> collisionTileLayers;

	public Level() {
		tiles = new Array<Rectangle>();
		doors = new Array<Door>();
		enemies = new Array<Enemy>();
		collisionTileLayers = new Array<TiledMapTileLayer>();
		rectPool = new Pool<Rectangle>() {
			@Override
			protected Rectangle newObject() {
				return new Rectangle();
			}
		};
		init();
	}
	
	public Array<Enemy> getEnemies() {
		return enemies;
	}
	
	public Array<Door> getDoors() {
		return doors;
	}

	private void init() {
		createDoors(
				Level.filterObjects(GameScreen.getInstance().getMap().getLayers().get("Objects").getObjects(), "door"));
		createEnemies(Level.filterObjects(GameScreen.getInstance().getMap().getLayers().get("Objects").getObjects(),
				"Enemy"));
		createCollisionLayers();
	}
	
	private void createCollisionLayers() {
		for (String name : WoodyGame.collisionLayers) {
			collisionTileLayers.add(getTileLayer(GameScreen.getInstance().getMap(), name));
		}
	}

	/**
	 * TODO: SPAWNSYSTEM
	 * 
	 * @return spawnpoint
	 */
	public Vector2 getCurrentSpawn() {
		return new Vector2(10, 1);
	}

	/**
	 * Read and save the basic properties (x, y, width, height) for further use.
	 * 
	 * @param properties
	 *            the properties of the object
	 * @return an array with x, y, width, height in this order
	 */
	private Array<Float> getBasicProperties(MapProperties properties) {
		Array<Float> basic = new Array<Float>();
		basic.add(properties.get("x", Float.class) * WoodyGame.UNIT_SCALE);
		basic.add(properties.get("y", Float.class) * WoodyGame.UNIT_SCALE);
		basic.add(properties.get("width", Float.class) * WoodyGame.UNIT_SCALE);
		basic.add(properties.get("height", Float.class) * WoodyGame.UNIT_SCALE);
		return basic;
	}

	/**
	 * Create Door-objects out of all the MapObject's add them to an Array and
	 * return it.
	 * 
	 * @param objects
	 *            objects from which properties are read to create the doors
	 * @return an array with all the created doors
	 */
	private void createDoors(Array<MapObject> objects) {
		int tpX = 0;
		int tpY = 0;

		for (MapObject object : objects) {

			// get the properties of the object and save some of them in
			// variables
			MapProperties properties = object.getProperties();
			Array<Float> basic = getBasicProperties(properties);

			// get the custom properties tpX/tpY
			tpX = properties.get("tpX", Integer.class);
			tpY = properties.get("tpY", Integer.class);

			// create the door and add it to the array
			Door door = new Door(basic.get(0), basic.get(1), basic.get(2), basic.get(3), tpX, tpY);
			doors.add(door);
		}
	}

	private void createEnemies(Array<MapObject> objects) {
		for (MapObject obj : objects) {
			MapProperties prop = obj.getProperties();
			int id = Integer.parseInt(obj.getName().substring(obj.getName().indexOf(':') + 1));
			int x1 = prop.get("leftRoom", Integer.class);
			int x2 = prop.get("rightRoom", Integer.class);
			String texture = prop.get("texture", String.class);
			Array<Float> basic = getBasicProperties(prop);
			Enemy e = new Enemy(1, WoodyGame.getGame().manager.get(texture, Texture.class), id, x1, x2, basic.get(0),
					basic.get(1), basic.get(2), basic.get(3));
			enemies.add(e);
		}
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
