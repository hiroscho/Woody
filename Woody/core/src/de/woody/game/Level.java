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

import de.woody.game.enemies.Entity;
import de.woody.game.enemies.Spitter;
import de.woody.game.enemies.Walker;
import de.woody.game.screens.GameScreen;

/**
 * Contains level data such as enemies, doors, respawnPoints, layers.
 * 
 */
public class Level {

	public Pool<Rectangle> rectPool;
	private Array<Door> doors;
	private Array<Entity> enemies;
	private TiledMapTileLayer collidable;
	private TiledMapTileLayer nonCollidable;

	public Level() {
		doors = new Array<Door>();
		enemies = new Array<Entity>();
		rectPool = new Pool<Rectangle>() {
			@Override
			protected Rectangle newObject() {
				return new Rectangle();
			}
		};
		collidable = Level.getTileLayer(GameScreen.getInstance().getMap(), "Collidable Tiles");
		nonCollidable = Level.getTileLayer(GameScreen.getInstance().getMap(), "non Collidable");
		init();
	}

	public Array<Entity> getEnemies() {
		return enemies;
	}

	public Array<Door> getDoors() {
		return doors;
	}
	
	public TiledMapTileLayer getCollidable() {
		return collidable;
	}
	
	public TiledMapTileLayer getNonCollidable() {
		return nonCollidable;
	}
	
	private void init() {
		MapObjects objects = GameScreen.getInstance().getMap().getLayers().get("Objects").getObjects();
		createDoors(Level.filterObjects(objects, "door"));
		createWalkers(Level.filterObjects(objects, "Walker"));
		createSpitters(Level.filterObjects(objects, "Spitter"));
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
	 * Create Door-objects out of all the MapObject's add them to the doors
	 * array.
	 * 
	 * @param objects
	 *            objects from which properties are read to create the doors
	 */
	private void createDoors(Array<MapObject> objects) {
		for (MapObject object : objects) {

			// get the properties of the object and save some of them in
			// variables
			MapProperties properties = object.getProperties();
			Array<Float> basic = getBasicProperties(properties);

			// get the custom properties tpX/tpY
			int tpX = properties.get("tpX", 0, Integer.class);
			int tpY = properties.get("tpY", 0, Integer.class);

			// create the door and add it to the array
			Door door = new Door(basic.get(0), basic.get(1), basic.get(2), basic.get(3), tpX, tpY);
			doors.add(door);
		}
	}

	private void createWalkers(Array<MapObject> objects) {
		for (MapObject obj : objects) {
			MapProperties prop = obj.getProperties();
			int id = prop.get("id", Integer.class);
			int x1 = prop.get("leftRoom", 0, Integer.class);
			int x2 = prop.get("rightRoom", 0, Integer.class);
			String texture = prop.get("texture", "textures/Woddy.png", String.class);
			Array<Float> basic = getBasicProperties(prop);
			Walker e = new Walker(1, WoodyGame.getGame().manager.get(texture, Texture.class), id, x1, x2, basic.get(0),
					basic.get(1), basic.get(2), basic.get(3));
			enemies.add(e);
		}
	}

	// TODO: optimize entity only updating when they are in view (gotta think
	// about something for horizontal projectiles)
	private void createSpitters(Array<MapObject> objects) {
		for (MapObject obj : objects) {
			MapProperties prop = obj.getProperties();
			int id = prop.get("id", Integer.class);
			String texture = prop.get("texture", "textures/Woddy.png", String.class);
			Array<Float> basic = getBasicProperties(prop);
			Spitter e = new Spitter(1, WoodyGame.getGame().manager.get(texture, Texture.class), id, basic.get(0),
					basic.get(1), basic.get(2), basic.get(3));

			texture = prop.get("projTexture", "textures/projectile.png", String.class);
			float xVel = prop.get("xVelocity", 0F, Float.class);
			float yVel = prop.get("yVelocity", 0F, Float.class);
			float lifetime = prop.get("projLifetime", 0F, Float.class);
			float projWidth = prop.get("projWidth", 0.5F, Float.class);
			float projHeight = prop.get("projHeight", 0.5F, Float.class);
			float projFrequency = prop.get("projFrequency", 2.0F, Float.class);
			e.setProjectileProperties(projWidth, projHeight, new Vector2(xVel, yVel), lifetime, texture, projFrequency);
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
	public Array<Rectangle> getTiles(Array<Rectangle> tiles, int startX, int startY, int endX, int endY) {
		if (tiles == null) {
			tiles = new Array<Rectangle>();
		}
		rectPool.freeAll(tiles);
		tiles.clear();

		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = collidable.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
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
	
	public static String getTileName(Integer id) {
		if (WoodyGame.idNames.get(id) != null) {
			return WoodyGame.idNames.get(id);
		}
		return "null";
	}
}
