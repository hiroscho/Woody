package de.woody.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameoverScreen implements Screen {
	
	private static final GameoverScreen gameoverScreen = new GameoverScreen();

	private Batch batch;
	private int priorLevel;

	private final int REPLAY_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int REPLAY_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int MENU_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();

	private AssetManager asMa = WoodyGame.getGame().manager;

	private Texture gameOverBanner;
	private Texture replayButtonun;
	private Texture replayButtonak;
	private Texture menuButtonun;
	private Texture menuButtonak;
	
	private GameoverScreen() {}

	public static GameoverScreen getInstance(int level) {
		gameoverScreen.priorLevel = level;
		gameoverScreen.batch = new SpriteBatch();
		return gameoverScreen;
	}

	@Override
	public void show() {
		asMa.load("textures/Gameoverscreen.png", Texture.class);
		asMa.load("textures/Replay_un.png", Texture.class);
		asMa.load("textures/Replay_ak.png", Texture.class);
		asMa.load("textures/Menu_un.png", Texture.class);
		asMa.load("textures/Menu_ak.png", Texture.class);
		
		while(!asMa.update()) {
			asMa.update();
		}

		gameOverBanner = asMa.get("textures/Gameoverscreen.png", Texture.class);
		replayButtonun = asMa.get("textures/Replay_un.png", Texture.class);
		replayButtonak = asMa.get("textures/Replay_ak.png", Texture.class);
		menuButtonun = asMa.get("textures/Menu_un.png", Texture.class);
		menuButtonak = asMa.get("textures/Menu_ak.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5F, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		// Background
		batch.draw(gameOverBanner, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

		// REPLAY
		int x = Gdx.graphics.getWidth() / 2 - REPLAY_BUTTON_WIDTH / 2;
		int y = (Gdx.graphics.getHeight() / 2 - REPLAY_BUTTON_HEIGHT / 2);
		int v = Gdx.graphics.getHeight() / (4 - (1 / 2)) + REPLAY_BUTTON_HEIGHT / 10;
		if (Gdx.input.getX() < x + REPLAY_BUTTON_WIDTH && Gdx.input.getX() > x
				&& Gdx.graphics.getHeight() - Gdx.input.getY() < y + REPLAY_BUTTON_HEIGHT
				&& Gdx.graphics.getHeight() - Gdx.input.getY() > y) {
			batch.draw(replayButtonak, x, v, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				System.gc();
				WoodyGame.getGame().setScreen(GameScreen.getInstance(priorLevel));
				return;
			}
		} else {
			batch.draw(replayButtonun, x, v, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
		}
		// MENU
		int a = Gdx.graphics.getWidth() / 2 - MENU_BUTTON_WIDTH / 2;
		int b = (Gdx.graphics.getWidth() / 2 - MENU_BUTTON_WIDTH / 2);
		int c = Gdx.graphics.getHeight() / 100 - MENU_BUTTON_HEIGHT / 8;
		if (Gdx.input.getX() < a + MENU_BUTTON_WIDTH && Gdx.input.getX() > a
				&& Gdx.graphics.getHeight() - Gdx.input.getY() < c + MENU_BUTTON_HEIGHT
				&& Gdx.graphics.getHeight() - Gdx.input.getY() > c) {
			batch.draw(menuButtonak, b, c, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				System.gc();
				WoodyGame.getGame().setScreen(MainMenueScreen.getInstance());
				return;
			}
		} else {
			batch.draw(menuButtonun, b, c, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		}
		batch.end();

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
		asMa.clear();
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
