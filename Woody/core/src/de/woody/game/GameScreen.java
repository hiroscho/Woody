package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	
	private static final GameScreen gameScreen = new GameScreen();

	// Variables to change the scale of the hearts / lives image conveniently
	private float scaleHearts = 2;
	private float scaleLives = 2;

	// map and camera
	private TiledMap map;
	private final OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;

	// player
	private Player player;
	private Animations playerAnimationHandler;

	// nr of the level
	private int level;
	// current checkpoint (could be changed to a vector and used directly)
	private int checkpoint;
	
	// UI
	private UI controller;



	// level Data TODO: Put all of it into Level
	public Level levelData;

	private AssetManager asMa = WoodyGame.getGame().manager;

	private boolean debug = false;
	private ShapeRenderer debugRenderer;

	private Sound coinSound;
	
	private GameScreen() {
		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WoodyGame.xTiles, WoodyGame.yTiles);
		camera.update();
	}
	
	public static GameScreen getInstance() {
		return gameScreen;
	}

	/**
	 * Get the instance of GameScreen, changing the level.
	 * @param level
	 * @return
	 */
	public static GameScreen getInstance(int level) {
		gameScreen.level = level;
		gameScreen.debugRenderer = new ShapeRenderer();
		return gameScreen;
	}

	@Override
	public void show() {
		// UI
		asMa.load("textures/ButtonJump.png", Texture.class);
		asMa.load("textures/ButtonLeft.png", Texture.class);
		asMa.load("textures/ButtonRight.png", Texture.class);
		asMa.load("textures/ButtonFight.png", Texture.class);
		asMa.load("textures/sheetHearts.png", Texture.class);
		asMa.load("textures/sheetLives.png", Texture.class);

		// Playertexture
		asMa.load("textures/Woddy.png", Texture.class);

		// Animations
		asMa.load("textures/sheetRun.png", Texture.class);
		
		// Sound Test
		asMa.load("audio/coin.wav", Sound.class);

		while (!asMa.update()) {
			asMa.update();
		}

		controller = new UI();
		
		// kinda optional and kinda not, allows the setting of positions of UI
		// elements in world coordinates
		Vector3 uiPos;

		// Create the buttons based on a texture, give them identifier names and
		// set their screen position and size
		uiPos = camera.project(new Vector3(18f, 0.25f, 0));
		controller.addButton(asMa.get("textures/ButtonJump.png", Texture.class), "Jump", uiPos.x, uiPos.y, 64, 64);

		uiPos = camera.project(new Vector3(0.1f, 0.25f, 0));
		controller.addButton(asMa.get("textures/ButtonLeft.png", Texture.class), "Left", uiPos.x, uiPos.y);

		uiPos = camera.project(new Vector3(3f, 0.25f, 0));
		controller.addButton(asMa.get("textures/ButtonRight.png", Texture.class), "Right", uiPos.x, uiPos.y);

		uiPos = camera.project(new Vector3(15f, 0.4f, 0));
		controller.addButton(asMa.get("textures/ButtonFight.png", Texture.class), "Fight", uiPos.x, uiPos.y);

		// Start taking input from the ui
		Gdx.input.setInputProcessor(controller.getStage());

		uiPos = camera.project(new Vector3(16.75f, 11f, 0));
		controller.addHeartsImage(controller.heartsZero, 0, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(controller.heartsOne, 1, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(controller.heartsTwo, 2, uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addHeartsImage(controller.heartsThree, 3, uiPos.x, uiPos.y, 52, 16, scaleHearts);

		uiPos = camera.project(new Vector3(15f, 11f, 0));
		controller.addLifeImage(controller.livesZero, 0, uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addLifeImage(controller.livesOne, 1, uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addLifeImage(controller.livesTwo, 2, uiPos.x, uiPos.y, 18, 18, scaleLives);

		// load the corresponding map, set the unit scale
		asMa.load("maps/level" + level + ".tmx", TiledMap.class);
		while (!asMa.isLoaded("maps/level" + level + ".tmx")) {
			asMa.update();
		}
		map = asMa.get("maps/level" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, WoodyGame.UNIT_SCALE);
		levelData = new Level();
		
		// playable character
		player = new Player(levelData.getCurrentSpawn());
		// load the textureRegions for animations
		playerAnimationHandler = new Animations();
		
		// Loading the Sounds 
		coinSound = asMa.get("audio/coin.wav", Sound.class);

		// call once for correct init, lifesystem does the remaining calls
		getUI().updateHeartsImage(player.life.getHearts());
		getUI().updateLifeImage(player.life.getLife());
	}

	@Override
	public void render(float delta) {

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
		for (Enemy e : levelData.getEnemies()) {
			e.move();
			e.render(renderer.getBatch());
			if (e.checkCollision(player)) {
				player.life.damagePlayer(1);
			}
		}

		// render the player
		player.render();

		renderer.getBatch().end();

		player.life.checkAltitude(player);
		player.life.checkAlive();
		if (!player.life.isAlive()) {
			player.position.set(levelData.getCurrentSpawn());
			if (player.life.getLife() >= 0) {
				player.life.setHearts(3); // TEMPORÄR!!!!!!!!!!!!!
				player.life.setIsAlive(true);
			} else {
				WoodyGame.getGame().setScreen(GameoverScreen.getInstance(level));
				return;
			}
		}

		// Perform ui logic
		controller.getStage().act(Gdx.graphics.getDeltaTime());
		// Draw the ui
		controller.getStage().draw();

		// render debug rectangles
		if (debug) {
			renderDebug();
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
	public void hide() {
		asMa.clear();
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		renderer.dispose();
		controller.getStage().dispose();
		GameScreen.getInstance().levelData.rectPool.clear();
	}

	public UI getUI() {
		return controller;
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

	public Player getPlayer() {
		return player;
	}
	
	public Sound getCoinSound() {
		return coinSound;
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