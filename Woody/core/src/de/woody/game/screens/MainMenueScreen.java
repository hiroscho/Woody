package de.woody.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.woody.game.WoodyGame;

public class MainMenueScreen implements Screen {
	
	private static final MainMenueScreen mainMenueScreen = new MainMenueScreen();

//	public final int WIDTH = 800;
//	public final int HEIGHT = 480;
	private Batch batch;

	// Textures sizes
	private final int PLAY_BUTTON_WIDTH = Gdx.graphics.getWidth() / 2;
	private final int PLAY_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int SETTINGS_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int SETTINGS_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int CLOSE_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int CLOSE_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	// Textures
	private Texture playButton;
	private Texture playButtonak;
	private Texture settingsButton;
	private Texture settingsButtonak;
	private Texture closeButton;
	private Texture closeButtonak;
	private Texture background;
	
	private Music menueMusic;
	
	private AssetManager asMa = WoodyGame.getGame().manager;

	private MainMenueScreen() {}
	
	public static MainMenueScreen getInstance() {
		mainMenueScreen.batch = new SpriteBatch();
		return mainMenueScreen;
	}

	@Override
	public void show() {
		asMa.load("textures/Play_un.png", Texture.class);
		asMa.load("textures/Play_ak.png", Texture.class);
		asMa.load("textures/Settings_un.png", Texture.class);
		asMa.load("textures/Settings_ak.png", Texture.class);
		asMa.load("textures/Exit_un.png", Texture.class);
		asMa.load("textures/Exit_ak.png", Texture.class);
		asMa.load("textures/Mainscreenbackground3.png", Texture.class);
		asMa.load("audio/mainTheme.mp3", Music.class);
		
		while(!asMa.update()) {
			asMa.update();
		}

		playButton = asMa.get("textures/Play_un.png", Texture.class);
		playButtonak = asMa.get("textures/Play_ak.png", Texture.class);
		settingsButton = asMa.get("textures/Settings_un.png", Texture.class);
		settingsButtonak = asMa.get("textures/Settings_ak.png", Texture.class);
		closeButton = asMa.get("textures/Exit_un.png", Texture.class);
		closeButtonak = asMa.get("textures/Exit_ak.png", Texture.class);
		background = asMa.get("textures/Mainscreenbackground3.png", Texture.class);
		
		//Background Music
		menueMusic = asMa.get("audio/mainTheme.mp3", Music.class);
		menueMusic.setLooping(true);
		menueMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		// Background
		batch.draw(background, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		// PLAY 120/270/320
		int x = (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH) / 2;
		int v = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
		int y = Gdx.graphics.getHeight() / 2 + PLAY_BUTTON_HEIGHT / 3;
		if (Gdx.input.getX() < v + PLAY_BUTTON_WIDTH && Gdx.input.getX() > v
				&& Gdx.graphics.getHeight() - Gdx.input.getY() < y + PLAY_BUTTON_HEIGHT
				&& Gdx.graphics.getHeight() - Gdx.input.getY() > y) {
			batch.draw(playButtonak, x, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				WoodyGame.getGame().setScreen(GameScreen.getInstance(1));
				return;
			}
		} else {
			batch.draw(playButton, x, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}
		// SETTINGS 165/130/170
		int z = Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2;
		int u = Gdx.graphics.getHeight() / (4 - (1 / 2)) + SETTINGS_BUTTON_HEIGHT / 10;
		int w = (Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2);
		if (Gdx.input.getX() < z + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > z
				&& Gdx.graphics.getHeight() - Gdx.input.getY() < u + SETTINGS_BUTTON_HEIGHT
				&& Gdx.graphics.getHeight() - Gdx.input.getY() > u) {
			batch.draw(settingsButtonak, w, u, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				WoodyGame.getGame().setScreen(SettingsScreen.getInstance());
				return;
			}
		} else {
			batch.draw(settingsButton, w, u, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
		}
		// EXIT 165/-10/0
		int n = Gdx.graphics.getWidth() / 2 - CLOSE_BUTTON_WIDTH / 2;
		int m = Gdx.graphics.getHeight() / 100 - CLOSE_BUTTON_HEIGHT / 8;
		int l = (Gdx.graphics.getWidth() / 2 - CLOSE_BUTTON_WIDTH / 2);
		if (Gdx.input.getX() < n + CLOSE_BUTTON_WIDTH && Gdx.input.getX() > n
				&& Gdx.graphics.getHeight() - Gdx.input.getY() < m + CLOSE_BUTTON_HEIGHT
				&& Gdx.graphics.getHeight() - Gdx.input.getY() > m) {
			batch.draw(closeButtonak, l, m, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
			if (Gdx.input.justTouched()) {
				Gdx.app.exit();
			}
		} else {
			batch.draw(closeButton, l, m, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
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
