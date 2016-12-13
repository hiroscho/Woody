package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

	private final WoodyGame game;

	// map and camera
	private final TiledMap map;
	private final OrthographicCamera camera;
	private final OrthogonalTiledMapRenderer renderer;

	private final Player player;

	private final int level;
	private int checkpoint;

	private float frame = 0;

	private boolean debug = false;
	private ShapeRenderer debugRenderer;

	public final Buttons controller = new Buttons();

	public GameScreen(final WoodyGame game, final int level) {
		
		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, game.xTiles, game.yTiles);
		camera.update();
		
		// kinda optional and kinda not, allows the setting of positions of UI elements in world coordinates
		Vector3 uiPos; 

		// Create the buttons based on a texture, give them identifier names and
		// set their screen position
		uiPos = camera.project(new Vector3(3, 3, 0)); 
		controller.addButton(new Texture("textures/ButtonJump.png"), "Jump").setPosition(uiPos.x, uiPos.y);
		
		uiPos = camera.project(new Vector3(1.5f, 1.5f, 0)); 
		controller.addButton(new Texture("textures/ButtonLeft.png"), "Left").setPosition(uiPos.x, uiPos.y);
		
		uiPos = camera.project(new Vector3(3, 1.5f, 0)); 
		controller.addButton(new Texture("textures/ButtonRight.png"), "Right").setPosition(uiPos.x, uiPos.y);

		// controller.addButton(new
		// Texture("textures/ButtonFight.png")).setName("Fight");

		// Start taking input from the ui
		Gdx.input.setInputProcessor(controller.getStage());

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

			// checks input, sets velocity
			if (pressedButton != null) {
				player.setInputVelocity(pressedButton);
			}
			player.setKeyboardVelocity();

			// checks collision then moves the player
			player.move(delta);

			// currently only for debug mode
			checkGameInput();

			// have the camera follow the character, x-axis only
			if (!(player.position.x < game.xTiles / 2))
				camera.position.x = player.position.x;
			else
				camera.position.x = game.xTiles / 2;

			camera.update();

			// set the renderer view based on what the camera sees and render it
			renderer.setView(camera);
			renderer.render();

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

	@Override
	public void resize(int width, int height) {
		controller.getStage().getViewport().update(width, height, true);
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

	private void renderDebug() {
		debugRenderer.setProjectionMatrix(camera.combined);
		debugRenderer.begin(ShapeType.Line);

		debugRenderer.setColor(Color.RED);
		debugRenderer.rect(player.position.x, player.position.y, Player.WIDTH, Player.HEIGHT);

		debugRenderer.setColor(Color.YELLOW);
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(game.collisionLayer);
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
	}
}