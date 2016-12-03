package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class GameScreen implements Screen {
	private final WoodyGame game;
	private final TiledMap map;
	private final OrthographicCamera camera;
	private final OrthogonalTiledMapRenderer renderer;
	
	private final Woody woody;
	private final float unitScale = WoodyGame.unitScale;
	
	private final int level;
	
	private Texture woodyTexture;
	private int checkpoint;

	public GameScreen(final WoodyGame game, final int level) {
		this.game = game;
		this.level = level;

		// our playable character
		woody = new Woody();
		woodyTexture = new Texture("textures/Woddy.png");

		// convert WIDTH and HEIGHT to tiles
		Woody.WIDTH = unitScale * woodyTexture.getWidth();
		Woody.HEIGHT = unitScale * woodyTexture.getHeight();

		// set woody's start position
		woody.position.set(Level.getCurrentSpawn(level, checkpoint));

		// load the corresponding map, set the unit scale
		map = new TmxMapLoader().load("maps/level" + level + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);

		// create an orthographic camera, show (xTiles)x(yTiles) of the map
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WoodyGame.xTiles, WoodyGame.yTiles);
		camera.update();
	}

	@Override
	public void render(float delta) {

		// background color
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// have the camera follow the character, x-axis only
		camera.position.x = woody.position.x;
		camera.update();

		// set the renderer view based on what the camera sees and render it
		renderer.setView(camera);
		renderer.render();

		Batch b = renderer.getBatch();

		checkInput();
		
		b.begin();
		b.draw(woodyTexture, woody.position.x, woody.position.y, Woody.WIDTH, Woody.HEIGHT);
		b.end();

	}
	
	private void checkInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			woody.position.add(-0.5f, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			woody.position.add(0.5f, 0);
		}
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
}