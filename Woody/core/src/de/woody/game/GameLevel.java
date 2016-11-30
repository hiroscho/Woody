package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameLevel implements Screen {
	final WoodyGame game;

	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	boolean leftMove;
	boolean rightMove;
	int level;

	public GameLevel(final WoodyGame gam, int level) {
		this.game = gam;
		this.level = level;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();
		tiledMap = new TmxMapLoader().load("maps/test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 64f);
		
		
//		Gdx.input.setInputProcessor(new InputAdapter() {
//			@Override
//			public boolean keyDown(int keycode) {
//				switch (keycode) {
//				case Keys.LEFT:
//					setLeftMove(true);
//					break;
//				case Keys.RIGHT:
//					setRightMove(true);
//					break;
//				}
//				return true;
//			}
//
//			@Override
//			public boolean keyUp(int keycode) {
//				switch (keycode) {
//				case Keys.LEFT:
//					setLeftMove(false);
//					break;
//				case Keys.RIGHT:
//					setRightMove(false);
//					break;
//				}
//				return true;
//			}
//		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
//		updateMotion();
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

//	public void updateMotion() {
//		if (leftMove) {
//			camera.translate(-16 * Gdx.graphics.getDeltaTime(), 0);
//		}
//		if (rightMove) {
//			camera.translate(16 * Gdx.graphics.getDeltaTime(), 0);
//		}
//	}
//
//	public void setLeftMove(boolean t) {
//		if (rightMove && t)
//			rightMove = false;
//		leftMove = t;
//	}
//
//	public void setRightMove(boolean t) {
//		if (leftMove && t)
//			leftMove = false;
//		rightMove = t;
//	}

}