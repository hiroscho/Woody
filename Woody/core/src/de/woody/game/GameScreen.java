package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class GameScreen implements Screen {

	private final WoodyGame game;

	// map and camera
	private final TiledMap map;
	private final OrthographicCamera camera;
	private final OrthogonalTiledMapRenderer renderer;

	private final Player player;

	private final int level;
	private int checkpoint;

	public GameScreen(final WoodyGame game, final int level) {
		this.game = game;
		this.level = level;

		// playable character
		player = new Player();
		player.texture = new Texture("textures/Woddy.png");

		// convert WIDTH and HEIGHT to tiles
		Player.WIDTH = game.unitScale * player.texture.getWidth();
		Player.HEIGHT = game.unitScale * player.texture.getHeight();

		// set players start position
		player.position.set(Level.getCurrentSpawn(level, checkpoint));

		// load the corresponding map, set the unit scale
		map = new TmxMapLoader().load("maps/level" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, game.unitScale);
		Level.layer = (TiledMapTileLayer) map.getLayers().get(game.collisionLayer);

		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.xTiles, game.yTiles);
		camera.update();
	}

	@Override
	public void render(float delta) {

		// background color
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// checks input and collision then moves the player
		player.move(delta);

		// have the camera follow the character, x-axis only
		camera.position.x = player.position.x;
		camera.update();

		// set the renderer view based on what the camera sees and render it
		renderer.setView(camera);
		renderer.render();

		// render the player
		player.render(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		// when the screen is shown
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	public TiledMap getMap() {
		return map;
	}

	public WoodyGame getGameInstance() {
		return game;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}
}