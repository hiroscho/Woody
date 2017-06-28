package de.woody.game.screens;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Animations;
import de.woody.game.Level;
import de.woody.game.Player;
import de.woody.game.UI;
import de.woody.game.WoodyGame;
import de.woody.game.enemies.Entity;

public class GameScreen implements Screen {
	private static final GameScreen gameScreen = new GameScreen();

	// Variables to change the scale of the hearts / lives image conveniently
	private float scaleHearts = 2;
	private float scaleLives = 2;

	private TiledMap map;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private Player player;
	private Animations playerAnimationHandler;
	// nr of the level
	private int level;
	private Vector2 checkpoint;
	private Rectangle endpoint;
	private UI controller;
	public Level levelData;
	private AssetManager asMa = WoodyGame.getGame().manager;
	private boolean debug = false;
	private ShapeRenderer debugRenderer;
	private TiledMapTileLayer collidableTiles;
	private TiledMapTileLayer nonCollidableTiles;
	// Sound Variables
	private Sound coinSound;
	private Array<Button> pressedButtons;
	private Sound jumpSound;
	private Sound punchSound;
	private Sound doorSound;
	private Sound hurtSound;
	private Sound lavaSound;
	private Sound powerupSound;
	private Sound snowSlideSound;
	// private Music
	private Music levelMusic;

	BitmapFont coinsFont;

	// Test x6.8
	float sclx = 4.3f, scly = 4.3f, px = 6.5f, py = 7.45f;

	private GameScreen() {
	}

	public static GameScreen getInstance() {
		return gameScreen;
	}

	/**
	 * Get the instance of GameScreen, changing the level.
	 * 
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

		// Enemytetures
		asMa.load("textures/Turret.png", Texture.class);
		asMa.load("textures/Raptor.png", Texture.class);
		asMa.load("textures/Rabbit.png", Texture.class);

		// Animations
		asMa.load("textures/sheetRun.png", Texture.class);
		asMa.load("textures/sheetAttack.png", Texture.class);

		// Projectiletexture
		asMa.load("textures/projectile.png", Texture.class);

		// Entitytextures
		// Nothing here yet

		// load Sounds
		asMa.load("audio/coin.wav", Sound.class);
		asMa.load("audio/jump.wav", Sound.class);
		asMa.load("audio/punch.wav", Sound.class);
		asMa.load("audio/door.wav", Sound.class);
		asMa.load("audio/hurt.wav", Sound.class);
		asMa.load("audio/lava.mp3", Sound.class);
		asMa.load("audio/snowSlide.mp3", Sound.class);
		asMa.load("audio/powerup.wav", Sound.class);
		asMa.load("audio/level" + level + ".mp3", Music.class);

		// Font
		asMa.load("Fonts/V3.fnt", BitmapFont.class);

		while (!asMa.update()) {
			asMa.update();
		}

		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WoodyGame.getGame().xTiles, WoodyGame.getGame().yTiles);
		camera.update();

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

		map = asMa.get("maps/level" + level + ".tmx"); // Jeweils richtiges
														// Level Laden
		renderer = new OrthogonalTiledMapRenderer(map, WoodyGame.getGame().UNIT_SCALE);
		levelData = new Level();
		collidableTiles = Level.getTileLayer(map, "Collidable Tiles");
		nonCollidableTiles = Level.getTileLayer(map, "non Collidable");

		// playable character
		player = new Player(levelData.getCurrentSpawn());
		// load the textureRegions for animations
		playerAnimationHandler = new Animations();

		// assign sound
		coinSound = asMa.get("audio/coin.wav", Sound.class);
		jumpSound = asMa.get("audio/jump.wav", Sound.class);
		punchSound = asMa.get("audio/punch.wav", Sound.class);
		doorSound = asMa.get("audio/door.wav", Sound.class);
		hurtSound = asMa.get("audio/hurt.wav", Sound.class);
		lavaSound = asMa.get("audio/lava.mp3", Sound.class);
		powerupSound = asMa.get("audio/powerup.wav", Sound.class);
		snowSlideSound = asMa.get("audio/snowSlide.mp3", Sound.class);
		levelMusic = asMa.get("audio/level" + level + ".mp3");

		// Font
		coinsFont = asMa.get("Fonts/V3.fnt", BitmapFont.class);

		// call once for correct init, lifesystem does the remaining calls
		getUI().updateHeartsImage(player.life.getHearts());
		getUI().updateLifeImage(player.life.getLife());

		// play level1 Music

		levelMusic.setLooping(true);
		if (WoodyGame.getGame().VOLUME > 0.0f) {
			levelMusic.setVolume(WoodyGame.getGame().VOLUME);
			levelMusic.play();
		}

	}

	@Override
	public void render(float delta) {
		// background color
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// get the touched/pressed button
		pressedButtons = controller.checkAllButtons();

		// checks input, sets velocity
		if (pressedButtons.size != 0) {
			for (Button but : pressedButtons) {
				player.setInputVelocity(but);
			}
		} else {
			player.setKeyboardVelocity();
		}

		player.checkBlocks();
		// checks collision then moves the player
		player.move(delta);

		// check and update the checkpoint
		for (Rectangle rec : levelData.getCheckpoints()) {
			if (player.getPlayerRec().overlaps(rec)) {
				if (rec.getX() > checkpoint.x) {
					setCheckpoint(new Vector2(rec.getX(), rec.getY()));
				}
			}
		}

		// check if player reached the end
		if (endpoint.overlaps(player.getPlayerRec())) {
			// TODO: Levelende screen
			WoodyGame.getGame().setScreen(Gamefinishscreen.getInstance(level));
			return;
		}

		// move entities and check for player collision
		for (Entity e : levelData.getEnemies()) {
			e.move(delta);
			if (e.checkCollision(player)) {
				player.life.damagePlayer(1);
			}
		}

		checkGameInput();

		// set the camera borders
		setCamera().update();

		// set the renderer view based on what the camera sees and render it
		renderer.setView(camera);
		renderer.render();

		// Render, move and check collision for enemies
		renderer.getBatch().begin();
		for (Entity e : levelData.getEnemies()) {
			e.render(renderer.getBatch());
		}

		// GlyphLayout scoreLayout = new GlyphLayout(myCoins, "" +
		// player.getCoinAmount());

		// CharSequence scorelay = "" + score;
		// myCoins.draw(renderer.getBatch(), scoreLayout,9,12);

		// SpriteBatch batch;
		// batch = new SpriteBatch();

		// GameScreen.getInstance().getbatch.begin();

		// myCoins.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		// myCoins.setColor(0.5f, 0.5f, 0.7f, 0.7f);
		// myCoins.draw(batch , yourScoreName, 15, 50);

		// batch = new SpriteBatch();
		// myCoins = new BitmapFont();
		// CharSequence str = "Hello World!";
		// myCoins.setColor(0.9f, 0.9f, 0.2f, 0.2f);
		// myCoins.draw(renderer.getBatch(), str, 10, 10);

		// render the player
		player.render();

		renderer.getBatch().end();

		player.life.checkAltitude(player);
		player.life.checkAlive();
		if (!player.life.isAlive()) {
			player.position.set(levelData.getCurrentSpawn());
			camera.position.x = player.position.x;
			if (camera.position.x < WoodyGame.getGame().xTiles / 2) {
				camera.position.x = WoodyGame.getGame().xTiles / 2;
			}
			if (player.life.getLife() >= 0) {
				player.life.setHearts(3);
				player.life.setIsAlive(true);
			} else {
				WoodyGame.getGame().setScreen(GameoverScreen.getInstance(level));
				return;
			}
		}

		// Perform ui logic
		controller.getStage().act(Gdx.graphics.getDeltaTime());

		// check coin Amount
		if (player.getCoinAmount() < 10) {
			sclx = 4.3f;
			scly = 4.3f;
			px = 6.8f;
			py = 7.45f;
		}

		if ((player.getCoinAmount() < 100) && (player.getCoinAmount() > 9)) {
			sclx = 4.3f;
			scly = 4.3f;
			px = 6.41f;
			py = 7.45f;
		}

		if ((player.getCoinAmount() < 1000) && (player.getCoinAmount() > 99)) {
			sclx = 3.5f;
			scly = 4f;
			px = 6.22f;
			py = 7.45f;
		}

		if ((player.getCoinAmount() < 10000) && (player.getCoinAmount() > 999)) {
			sclx = 2.66f;
			scly = 4.0f;
			px = 6.22f;
			py = 7.45f;
		}
		if ((player.getCoinAmount() < 100000) && (player.getCoinAmount() > 9999)) {
			sclx = 2.0f;
			scly = 4.0f;
			px = 6.284f;
			py = 7.45f;
		}

		if ((player.getCoinAmount() < 1000000) && (player.getCoinAmount() > 99999)) {
			sclx = 1.5f;
			scly = 4.0f;
			px = 6.4f;
			py = 7.45f;
		}

		// Draw the ui
		controller.getStage().draw();
		if (sclx != 0f && scly != 0f) {
			coinsFont.getData().setScale(sclx, scly);
		}
		controller.getStage().getBatch().begin();
		coinsFont.draw(controller.getStage().getBatch(), Integer.toString(player.getCoinAmount()),
				px / WoodyGame.getGame().UNIT_SCALE, py / WoodyGame.getGame().UNIT_SCALE,
				1.0f / WoodyGame.getGame().UNIT_SCALE, Align.left, true);
		controller.getStage().getBatch().end();

		// render debug rectangles
		if (debug) {
			renderDebug();
		}
		// Swipe-Check
		LevelSelect.getInstance().checking();
	}

	private OrthographicCamera setCamera() {
		Vector2 vec = cameraBottomLeft();

		if (player.position.x < vec.x + 3)
			camera.position.x += player.position.x - (vec.x + 3);

		if (player.position.x > vec.x + WoodyGame.getGame().xTiles / 2)
			camera.position.x = player.position.x;

		// dont show the area left from the start
		if (player.position.x < 3)
			camera.position.x = WoodyGame.getGame().xTiles / 2;

		return camera;
	}

	public Vector2 cameraBottomLeft() {
		return new Vector2(camera.position.x - (WoodyGame.getGame().xTiles / 2),
				camera.position.y - (WoodyGame.getGame().yTiles / 2));
	}

	@Override
	public void resize(int width, int height) {
		controller.resize(width, height);
	}

	@Override
	public void hide() {
		asMa.clear();
		// dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		// reload all textures and their references....a shitload of work, just
		// don't pause the game if we present it
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

	public Vector2 getCheckpoint() {
		return checkpoint;
	}

	public Rectangle getEndpoint() {
		return endpoint;
	}

	public Animations getPlayerAnimationHandler() {
		return playerAnimationHandler;
	}

	public Player getPlayer() {
		return player;
	}

	public TiledMapTileLayer getCollidableTiles() {
		return collidableTiles;
	}

	public TiledMapTileLayer getNonCollidableTiles() {
		return nonCollidableTiles;
	}

	public Sound getCoinSound() {
		return coinSound;
	}

	public Sound getJumpSound() {
		return jumpSound;
	}

	public Sound getPunchSound() {
		return punchSound;
	}

	public Sound getDoorSound() {
		return doorSound;
	}

	public Sound getHurtSound() {
		return hurtSound;
	}

	public Sound getLavaSound() {
		return lavaSound;
	}

	public Sound getPowerupSound() {
		return powerupSound;
	}

	public Sound getSnowSlideSound() {
		return snowSlideSound;
	}

	public Array<Button> getPressedButtons() {
		return pressedButtons;
	}

	public void setCheckpoint(Vector2 vec) {
		checkpoint = vec;
	}

	public void setEndpoint(Rectangle rec) {
		endpoint = rec;
	}

	private void renderDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);

		debugRenderer.setColor(Color.YELLOW);
		TiledMapTileLayer layer = levelData.getCollidable();
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
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			try {
				BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("sclx: ");
				sclx = Float.parseFloat(r.readLine());
				System.out.println("scly: ");
				scly = Float.parseFloat(r.readLine());
				System.out.println("x: ");
				px = Float.parseFloat(r.readLine());
				System.out.println("y: ");
				py = Float.parseFloat(r.readLine());
			} catch (Exception e) {
			}
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