package de.woody.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsScreen implements Screen{
	private static final SettingsScreen settingsScreen = new SettingsScreen();
	
	private Batch batch;
	
	private Texture background;
	private Texture backgroundp;
	private Texture menuun;
	private Texture menuak;
	
	
	private final int BACKGROUND_WIDTH = 300;
	private final int BACKGROUND_HEIGHT = 150;
	private final int BACK_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int BACK_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUNDP_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUNDP_HEIGHT = Gdx.graphics.getHeight();
	
	private AssetManager asMa = WoodyGame.getGame().manager;
	
	private SettingsScreen(){}
	
	public static SettingsScreen getInstance() {
		settingsScreen.batch = new SpriteBatch();
		return settingsScreen;
	}
	
	
	@Override
	public void show() {
		asMa.load("textures/Settings_un.png", Texture.class);
		asMa.load("textures/SettingsBackground.png", Texture.class);
		asMa.load("textures/Menu_un.png", Texture.class);
		asMa.load("textures/Menu_ak.png", Texture.class);
		
		while(!asMa.update()) {
			asMa.update();
		}
		
		background = asMa.get("textures/Settings_un.png", Texture.class);
		backgroundp = asMa.get("textures/SettingsBackground.png", Texture.class);
		menuun = asMa.get("textures/Menu_un.png", Texture.class);
		menuak = asMa.get("textures/Menu_ak.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.draw(backgroundp, 0, 0, BACKGROUNDP_WIDTH, BACKGROUNDP_HEIGHT);
		int l = (Gdx.graphics.getWidth() /2 - BACKGROUND_WIDTH/2);
		batch.draw(background, l, 350, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		int n = Gdx.graphics.getWidth() / 2 - BACK_WIDTH / 2;
		int x = (Gdx.graphics.getWidth() /2 - BACK_WIDTH/2);
		int y = Gdx.graphics.getHeight() /100 - BACK_HEIGHT/8;
		if(Gdx.input.getX() < n + BACK_WIDTH && Gdx.input.getX() > n && Gdx.graphics.getHeight() - Gdx.input.getY() < y + BACK_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(menuak, x, y, BACK_WIDTH, BACK_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(MainMenueScreen.getInstance());
				return;
			}
		}else{
			batch.draw(menuun, x, y, BACK_WIDTH, BACK_HEIGHT);
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
