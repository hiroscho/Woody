package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

	// Variables to change the scale of the hearts / lives image conveniently
	private final float scaleHearts = 2;
	private final float scaleLives = 2;

	// map and camera
	private TiledMap map;
	private final OrthographicCamera camera;
	private final OrthogonalTiledMapRenderer renderer;

	private final Player player;

	// nr of the level
	private final int level;
	// current checkpoint (could be changed to a vector and used directly)
	private int checkpoint;

	private boolean debug = false;
	private ShapeRenderer debugRenderer;

	private final Buttons controller = new Buttons();

	private Animations playerAnimationHandler;

	// All doors in the current level
	private Array<Door> doors = new Array<Door>();
	private Array<Enemy> enemies = new Array<Enemy>();

	public Level levelData = new Level();

	public GameScreen(final int level) {

		// (TiledMapTileLayer) map.getLayers().get(game.collisionLayer);

		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WoodyGame.xTiles, WoodyGame.yTiles);
		camera.update();

		// kinda optional and kinda not, allows the setting of positions of UI
		// elements in world coordinates
		Vector3 uiPos;

		// Create the buttons based on a texture, give them identifier names and
		// set their screen position and size
		uiPos = camera.project(new Vector3(18f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonJump.png"), "Jump", uiPos.x, uiPos.y, 64, 64);

		uiPos = camera.project(new Vector3(0.1f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonLeft.png"), "Left", uiPos.x, uiPos.y);

		uiPos = camera.project(new Vector3(3f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonRight.png"), "Right", uiPos.x, uiPos.y);

		uiPos = camera.project(new Vector3(15f, 0.4f, 0));
		controller.addButton(new Texture("textures/ButtonFight.png"), "Fight", uiPos.x, uiPos.y);

		// Start taking input from the ui
		Gdx.input.setInputProcessor(controller.getStage());

		// load the textures for animations
		playerAnimationHandler = new Animations();

		uiPos = camera.project(new Vector3(16.75f, 11f, 0));
		controller.addHeartsImage(playerAnimationHandler.heartsZero, 0, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(playerAnimationHandler.heartsOne, 1, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(playerAnimationHandler.heartsTwo, 2, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(playerAnimationHandler.heartsThree, 3, uiPos.x, uiPos.y, 52, 16, scaleHearts);

		uiPos = camera.project(new Vector3(15f, 11f, 0));
		controller.addLifeImage(playerAnimationHandler.livesZero, 0, uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addLifeImage(playerAnimationHandler.livesOne, 1, uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addLifeImage(playerAnimationHandler.livesTwo, 2, uiPos.x, uiPos.y, 18, 18, scaleLives);

		this.level = level;

		// playable character
		player = new Player(new Texture("textures/Woddy.png"), levelData.getCurrentSpawn(level, checkpoint));

		// load the corresponding map, set the unit scale
		map = new TmxMapLoader().load("maps/level" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, WoodyGame.UNIT_SCALE);
		// register all layers that have collision
		for (String name : WoodyGame.collisionLayers) {
			levelData.addCollisionLayer(name, map);
		}

		// create the doors and save them in an easy access array
		doors = levelData.createDoors(Level.filterObjects(map.getLayers().get("Doors").getObjects(), "door"));

		// Fun with enemies
		Array<MapObject> enemyObjects = Level.filterObjects(map.getLayers().get("Enemy").getObjects(), "Enemy");
		for (MapObject obj : enemyObjects) {
			MapProperties prop = obj.getProperties();
			int id = Integer.parseInt(obj.getName().substring(obj.getName().indexOf(':') + 1));
			int x1 = prop.get("leftRoom", Integer.class);
			int x2 = prop.get("rightRoom", Integer.class);
			int x = (int) (prop.get("x", Float.class) * WoodyGame.UNIT_SCALE);
			int y = (int) (prop.get("y", Float.class) * WoodyGame.UNIT_SCALE);
			Enemy e = new Enemy(1, new Texture("textures/Woddy.png"), id, x1, x2, x, y);
			enemies.add(e);
		}

		debugRenderer = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		if (delta != 0) {

			// background color
			Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			// get the touched/pressed button
			Array<Button> pressedButtons = controller.checkAllButtons();

			// checks input, sets velocity
			if (pressedButtons.size != 0) {
				for (Button but : pressedButtons) {
					player.setInputVelocity(but);
				}
			} else {
				player.setKeyboardVelocity();
			}

			// checks collision then moves the player
			player.move(delta);

			// currently only for debug mode
			checkGameInput();

			// set the camera borders
			setCamera().update();

			// set the renderer view based on what the camera sees and render it
			renderer.setView(camera);
			renderer.render();

			// Render, move and check collision for enemies
			renderer.getBatch().begin();
			for (Enemy e : enemies) {
				e.move();
				e.render(renderer.getBatch());
				if (e.checkCollision(player)) {
					player.life.damagePlayer(1);
				}
			}
			renderer.getBatch().end();

			player.life.checkAltitude(player);
			player.life.checkAlive();
			if (!player.life.isAlive()) {
				player.position.set(levelData.getCurrentSpawn(level, checkpoint));
				if (player.life.getLife() >= 1) {
					player.life.setHearts(3); // TEMPOR�R!!!!!!!!!!!!!
					player.life.setIsAlive(true);
				} else {
					this.dispose();
					WoodyGame.getGame().setScreen(new GameoverScreen(level));
				}
			}

			controller.checkCorrectHeartsImage(player);
			controller.checkCorrectLifeImage(player);

			// render the player
			player.render(this);

			// Perform ui logic
			controller.getStage().act(Gdx.graphics.getDeltaTime());
			// Draw the ui
			controller.getStage().draw();

			// render debug rectangles
			if (debug) {
				renderDebug();
			}

			// check Player invulnerable
			player.life.checkPlayerInvulnerable();
		}
	}

	private OrthographicCamera setCamera() {
		Vector2 vec = cameraBottomLeft();

		if (player.position.x < vec.x + 3)
			camera.position.x += player.position.x - (vec.x + 3);

		if (player.position.x > vec.x + WoodyGame.xTiles / 2)
			camera.position.x = player.position.x;

		// dont show the area left from the start
		if (player.position.x < 3)
			camera.position.x = WoodyGame.xTiles / 2;

		return camera;
	}

	private Vector2 cameraBottomLeft() {
		return new Vector2(camera.position.x - (WoodyGame.xTiles / 2), camera.position.y - (WoodyGame.yTiles / 2));
	}

	@Override
	public void resize(int width, int height) {
		controller.resize(width, height);
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
		// clear the layers out of the hashmap (it will use old level data
		// otherwise)
		doors.clear();
		debugRenderer.dispose();
		map.dispose();
		playerAnimationHandler.dispose();
		renderer.dispose();
		enemies.clear();
	}

	public TiledMap getMap() {
		return map;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public int getLevel() {
		return level;
	}

	public int getCheckpoint() {
		return checkpoint;
	}

	public Animations getPlayerAnimationHandler() {
		return playerAnimationHandler;
	}

	public Array<Door> getDoors() {
		return doors;
	}

	public Array<Enemy> getEnemies() {
		return enemies;
	}

	private void renderDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);

		debugRenderer.setColor(Color.YELLOW);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(WoodyGame.collisionLayers[0]);
		for (int y = 0; y <= layer.getHeight(); y++) {
			for (int x = 0; x <= layer.getWidth(); x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					if (camera.frustum.boundsInFrustum(x + 0.5f, y + 0.5f, 0, 1, 1, 0))
						debugRenderer.rect(x, y, 1, 1);
				}
			}
		}
		debugRenderer.end();
	}

	private int counterU;

	private void checkGameInput() {
		if (Gdx.input.isKeyJustPressed(Keys.B))
			debug = !debug;
		if (Gdx.input.isKeyJustPressed(Keys.L))
			player.life.damagePlayer(1);
		if (Gdx.input.isKeyJustPressed(Keys.R)) {
			if (player.life.getLife() >= 1)
				player.life.setLife(2);
			else
				player.life.setLife(3);
		}

		// Disable Button UI
		if (Gdx.input.isKeyJustPressed(Keys.U)) {
			counterU++;
			if (counterU % 2 == 1) {
				for (Actor actor : controller.getStage().getActors()) {
					actor.setVisible(false);
				}
			} else
				for (Actor actor : controller.getStage().getActors()) {
					actor.setVisible(true);
				}
		}
	}
}