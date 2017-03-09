package de.woody.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.woody.game.Player.State;

public class GameScreen implements Screen {

	private final WoodyGame game;
	
	//Variables to change the scale of the hearts / lives image conveniently
	private final float scaleHearts = 2;
	private final float scaleLives = 2;
	
	// map and camera
	public static TiledMap map;
	private final OrthographicCamera camera;
	private final OrthogonalTiledMapRenderer renderer;

	private final Player player;

	private final int level;
	private int checkpoint;
	private int counterU;

	private boolean debug = false;
	private ShapeRenderer debugRenderer;
	
	public Image imageTest;

	public final Buttons controller = new Buttons();
	
	private Animations playerAnimationHandler;

	public GameScreen(final WoodyGame game, final int level) {

		
		// (TiledMapTileLayer) map.getLayers().get(game.collisionLayer);
		

		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.xTiles, game.yTiles);
		camera.update();

		// kinda optional and kinda not, allows the setting of positions of UI
		// elements in world coordinates
		Vector3 uiPos;

		Button button;

		// Create the buttons based on a texture, give them identifier names and
		// set their screen position and size
		uiPos = camera.project(new Vector3(18f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonJump.png"), "Jump", uiPos.x, uiPos.y, 64, 64);

		uiPos = camera.project(new Vector3(0.1f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonLeft.png"), "Left", uiPos.x, uiPos.y);

		uiPos = camera.project(new Vector3(3f, 0.25f, 0));
		controller.addButton(new Texture("textures/ButtonRight.png"), "Right", uiPos.x, uiPos.y);

		// controller.addButton(new
		// Texture("textures/ButtonFight.png")).setName("Fight");

		// Start taking input from the ui
		Gdx.input.setInputProcessor(controller.getStage());

		// load the textures for animations
		playerAnimationHandler = new Animations();
		
		uiPos = camera.project(new Vector3(16.75f, 11f, 0));
		controller.addImage(playerAnimationHandler.heartsZero, "imageZeroHearts", uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addImage(playerAnimationHandler.heartsOne, "imageOneHeart", uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addImage(playerAnimationHandler.heartsTwo, "imageTwoHearts", uiPos.x, uiPos.y, 52, 16, scaleHearts);
		controller.addImage(playerAnimationHandler.heartsThree, "imageThreeHearts", uiPos.x, uiPos.y, 52, 16, scaleHearts);
		
		uiPos = camera.project(new Vector3(15f, 11f, 0));
		controller.addImage(playerAnimationHandler.livesZero, "imageLifeZero", uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addImage(playerAnimationHandler.livesOne, "imageLifeOne", uiPos.x, uiPos.y, 18, 18, scaleLives);
		controller.addImage(playerAnimationHandler.livesTwo, "imageLifeTwo", uiPos.x, uiPos.y, 18, 18, scaleLives);
		
		this.game = game;
		this.level = level;

		// playable character
		player = new Player(game, new Texture("textures/Woddy.png"), Level.getCurrentSpawn(level, checkpoint));

		// load the corresponding map, set the unit scale
		map = new TmxMapLoader().load("maps/level" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, WoodyGame.UNIT_SCALE);
		// register all layers that have collision
		for (String name : WoodyGame.collisionLayers) {
			Level.addCollisionLayer(name, this);
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
			Button pressedButton = controller.checkAllButtons();
			System.out.println(Level.layers.get(0).getCell(0, 0).getTile().getId());

			// checks input, sets velocity
			if (pressedButton != null) {
				player.setInputVelocity(pressedButton);
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

			Lifesystem.checkAltitude(player);
			Lifesystem.checkAlive();
			if (Player.state == State.Dead) {
				player.position.set(Level.getCurrentSpawn(level, checkpoint));
				if (Lifesystem.getLife() >= 1)
					Lifesystem.setHearts(3); // TEMPORÄR!!!!!!!!!!!!!
				else
					Lifesystem.setHearts(0);
			}
			
			controller.checkCorrectHeartsImage();
			controller.checkCorrectLifeImage();
			player.checkPlayerAboveBlock();
			if (pressedButton != null) {
				player.checkPlayerInBlock(pressedButton);
			}
			player.checkSliding();
			
			// render the player
			player.render(this);

			// Perform ui logic
			controller.getStage().act(Gdx.graphics.getDeltaTime());
			// Draw the ui
			controller.getStage().draw();

			// render debug rectangles
			if (debug)
				renderDebug();
		}
	}

	private OrthographicCamera setCamera() {
		Vector2 vec = cameraBottomLeft();

		if (player.position.x < vec.x + 3)
			camera.position.x += player.position.x - (vec.x + 3);

		if (player.position.x > vec.x + game.xTiles / 2)
			camera.position.x = player.position.x;

		// dont show the area left from the start
		if (player.position.x < 3)
			camera.position.x = game.xTiles / 2;

		return camera;
	}

	private Vector2 cameraBottomLeft() {
		return new Vector2(camera.position.x - (game.xTiles / 2), camera.position.y - (game.yTiles / 2));
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
	
	public int getLevel() {
		return level;
	}
	
	public int getCheckpoint() {
		return checkpoint;
	}
	
	public Animations getPlayerAnimationHandler() {
		return playerAnimationHandler;
	}

	private void renderDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);

		debugRenderer.setColor(Color.YELLOW);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(game.collisionLayers[0]);
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

	private void checkGameInput() {
		if (Gdx.input.isKeyJustPressed(Keys.B))
			debug = !debug;
		if (Gdx.input.isKeyJustPressed(Keys.L))
			Lifesystem.hearts = Lifesystem.changeHearts(Lifesystem.hearts -1);
		if(Gdx.input.isKeyJustPressed(Keys.R))
		{
			if(Lifesystem.getLife() >= 1)
				Lifesystem.life = Lifesystem.setLife(2);
			else
				Lifesystem.life = Lifesystem.setLife(2);
			
			if(Lifesystem.hearts < 3)
				Lifesystem.setHearts(3);
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.U))															//Disable Button UI
		{
			counterU++;
			if(counterU%2 == 1)
			{
				for(Actor actor : controller.getStage().getActors())
				{
					actor.setVisible(false);
				}
			}
			else
				for(Actor actor : controller.getStage().getActors())
				{
					actor.setVisible(true);
				}
		}
	}
}