package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenueScreen implements Screen{
	
	public final int WIDTH = 800;
	public final int HEIGHT = 480;
	private Batch batch;
	WoodyGame game;
	
	//Textures
	private Texture PlayButton = new Texture("textures/Play_un.png");
	private Texture PlayButtonak = new Texture("textures/Play_ak.png");
	private Texture SettingsButton = new Texture("textures/Settings_un.png");
	private Texture SettingsButtonak = new Texture("textures/Settings_ak.png");
	private Texture CloseButton = new Texture("textures/Exit_un.png");
	private Texture CloseButtonak = new Texture("textures/Exit_ak.png");
	private Texture Background = new Texture("textures/Mainscreenbackground3.png");
	//Textures sizes
	private final int PLAY_BUTTON_WIDTH = Gdx.graphics.getWidth()/2;
	private final int PLAY_BUTTON_HEIGHT = Gdx.graphics.getHeight()/3;
	private final int SETTINGS_BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
	private final int SETTINGS_BUTTON_HEIGHT = Gdx.graphics.getHeight()/3;
	private final int CLOSE_BUTTON_WIDTH = Gdx.graphics.getWidth()/3;
	private final int CLOSE_BUTTON_HEIGHT = Gdx.graphics.getHeight()/3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
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
		int x = (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH)/2;
		int v = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
		int y = Gdx.graphics.getHeight() / 2 + PLAY_BUTTON_HEIGHT /3;
		if(Gdx.input.getX() < v + PLAY_BUTTON_WIDTH && Gdx.input.getX() > v && Gdx.graphics.getHeight() - Gdx.input.getY() < y + PLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(PlayButtonak,  x, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.gamescreen();
			}
		}else{
			batch.draw(PlayButton, x, y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}
		//SETTINGS 165/130/170
		int z = Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2;
		int u = Gdx.graphics.getHeight() /(4-(1/2)) + SETTINGS_BUTTON_HEIGHT/10;
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
		int m = Gdx.graphics.getHeight() /100 - CLOSE_BUTTON_HEIGHT/8;
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
