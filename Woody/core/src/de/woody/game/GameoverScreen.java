package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameoverScreen extends WoodyGame implements Screen {

//	private Viewport viewport;
//	private Stage gameoverstate;
	
//	private game gameover;
	WoodyGame game;
	private Batch batch;

//	private static final int GAMEOVER_BANNER_WIDTH = 170;
//	private static final int GAMEOVER_BANNER_HEIGHT = 40;
	private static final int REPLAY_BUTTON_WIDTH = 300;
	private static final int REPLAY_BUTTON_HEIGHT = 150;
	private static final int MENU_BUTTON_WIDTH = 300;
	private static final int MENU_BUTTON_HEIGHT = 150;
	private static final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private static final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	Texture gameOverBanner = new Texture("textures/Gameoverscreen.png");
	Texture ReplayButtonun = new Texture("textures/Replay_un.png");
	Texture ReplayButtonak = new Texture("textures/Replay_ak.png");
	Texture MenuButtonun = new Texture("textures/Menu_un.png");
	Texture MenuButtonak = new Texture("textures/Menu_ak.png");

	public GameoverScreen(WoodyGame game) {
		this.game = game;
		batch = new SpriteBatch();
	}

	@Override
	public void create() {
		this.setScreen(new GameoverScreen(this));
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Background
		batch.draw(gameOverBanner, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		//REPLAY
		int x = Gdx.graphics.getWidth() / 2 - REPLAY_BUTTON_WIDTH / 2;
		if(Gdx.input.getX() < x + REPLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < 130 + REPLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > 130){
			batch.draw(ReplayButtonak, 170, 140, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()){
				this.dispose();
				game.gamescreen();
			}
		}else{
			batch.draw(ReplayButtonun, 170, 140, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
		}
		//MENU
		int n = Gdx.graphics.getWidth() / 4 - MENU_BUTTON_WIDTH / 4;
		if(Gdx.input.getX() < n + MENU_BUTTON_WIDTH && Gdx.input.getX() > n && Gdx.graphics.getHeight() - Gdx.input.getY() < -20 + MENU_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > -20){
			batch.draw(MenuButtonak, 165, -10, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.menuscreen();
			}
		}else{
			batch.draw(MenuButtonun, 165, -10, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
		}
		
		
		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
