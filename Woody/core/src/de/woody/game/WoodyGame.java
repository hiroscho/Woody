package de.woody.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;

public class WoodyGame extends ApplicationAdapter {
	Texture img;
	TiledMap tiledMap;
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;

	@Override
	public void create() {
		float w = 1280;
		float h = 720;  //Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.update();
		tiledMap = new TmxMapLoader().load("tiled.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.LEFT:
					setLeftMove(true);
					break;
				case Keys.RIGHT:
					setRightMove(true);
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case Keys.LEFT:
					setLeftMove(false);
					break;
				case Keys.RIGHT:
					setRightMove(false);
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		updateMotion();
	}

	@Override
	public void dispose() {
	}

	boolean leftMove;
	boolean rightMove;

	public void updateMotion() {
		if (leftMove) {
			camera.translate(-256 * Gdx.graphics.getDeltaTime(),0);
		}
		if (rightMove) {
			camera.translate(256 * Gdx.graphics.getDeltaTime(),0);
		}
	}

	public void setLeftMove(boolean t) {
		if (rightMove && t)
			rightMove = false;
		leftMove = t;
	}

	public void setRightMove(boolean t) {
		if (leftMove && t)
			leftMove = false;
		rightMove = t;
	}
}
