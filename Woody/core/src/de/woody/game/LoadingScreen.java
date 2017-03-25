package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class LoadingScreen implements Screen{
	
	private AssetManager asMa = WoodyGame.getGame().manager;
	private int level;
	
	/**
	 * Used to load all textures in one place with the AssetManager.
	 * 
	 * @param level  the level to be loaded after the textures
	 */
	public LoadingScreen(int level) {
		this.level = level;
		//UI
		asMa.load("textures/ButtonJump.png", Texture.class);
		asMa.load("textures/ButtonLeft.png", Texture.class);
		asMa.load("textures/ButtonRight.png", Texture.class);
		asMa.load("textures/ButtonFight.png", Texture.class);
		asMa.load("textures/sheetHearts.png", Texture.class);
		asMa.load("textures/sheetLives.png", Texture.class);
		
		//Animations
		asMa.load("textures/sheetRun.png", Texture.class);

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(asMa.update()) {
			WoodyGame.getGame().setScreen(new GameScreen(level));
			return;
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void dispose() {
	}

}
