package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch.Config;

public class MainMenueScreen implements Screen{
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	private Batch batch;
	WoodyGame game;
	//Textures
	Texture PlayButton = new Texture("textures/Play_un.png");
	Texture PlayButtonak = new Texture("textures/Play_ak.png");
	Texture SettingsButton = new Texture("textures/Settings_un.png");
	Texture SettingsButtonak = new Texture("textures/Settings_ak.png");
	Texture CloseButton = new Texture("textures/Exit_un.png");
	Texture CloseButtonak = new Texture("textures/Exit_ak.png");
	Texture Background = new Texture("textures/Mainscreenbackground3.png");
	//Textures sizes
	private static final int PLAY_BUTTON_WIDTH = 400;
	private static final int PLAY_BUTTON_HEIGHT = 200;
	private static final int SETTINGS_BUTTON_WIDTH = 300;
	private static final int SETTINGS_BUTTON_HEIGHT = 150;
	private static final int CLOSE_BUTTON_WIDTH = 300;
	private static final int CLOSE_BUTTON_HEIGHT = 150;
	private static final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private static final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	public MainMenueScreen(WoodyGame game){
		this.game = game;
		batch = new SpriteBatch();
	}
	
	
	public MainMenueScreen() {
		
	}


	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		//Background
		batch.draw(Background, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		//PLAY 120/270/320
		int v = (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH)/2;
		int x = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
		int y = Gdx.graphics.getHeight() / 2 + PLAY_BUTTON_HEIGHT /6;
		if(Gdx.input.getX() < x + PLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < y + PLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(PlayButtonak,  v, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.gamescreen();
			}
		}else{
			batch.draw(PlayButton, v, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}
		//SETTINGS 165/130/170
		int z = Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2;
		int u = Gdx.graphics.getHeight() /(4-(1/2)) + SETTINGS_BUTTON_HEIGHT/8;
		int w = (Gdx.graphics.getWidth() /2 - SETTINGS_BUTTON_WIDTH/2);
		if(Gdx.input.getX() < z + SETTINGS_BUTTON_WIDTH && Gdx.input.getX() > z && Gdx.graphics.getHeight() - Gdx.input.getY() < u + SETTINGS_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > u){
			batch.draw(SettingsButtonak, w, u, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.settingscreen();
			}
		}else{
			batch.draw(SettingsButton, w, u, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
		}
		//EXIT	165/-10/0
		int n = Gdx.graphics.getWidth() / 2 - CLOSE_BUTTON_WIDTH / 2;
		int m = Gdx.graphics.getHeight() /50 + CLOSE_BUTTON_HEIGHT/40;
		int l = (Gdx.graphics.getWidth() /2 - CLOSE_BUTTON_WIDTH/2);
		if(Gdx.input.getX() < n + CLOSE_BUTTON_WIDTH && Gdx.input.getX() > n && Gdx.graphics.getHeight() - Gdx.input.getY() < m + CLOSE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > m){
			batch.draw(CloseButtonak, l, m, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				Gdx.app.exit();
			}
		}else{
			batch.draw(CloseButton, l, m, CLOSE_BUTTON_WIDTH, CLOSE_BUTTON_HEIGHT);
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
	}

	@Override
	public void dispose() {
	}

}
