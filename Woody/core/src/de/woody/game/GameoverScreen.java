package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameoverScreen extends WoodyGame implements Screen {

	WoodyGame game;
	private Batch batch;


	private final int REPLAY_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int REPLAY_BUTTON_HEIGHT = Gdx.graphics.getHeight() /3;
	private final int MENU_BUTTON_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	private Texture gameOverBanner = new Texture("textures/Gameoverscreen.png");
	private Texture ReplayButtonun = new Texture("textures/Replay_un.png");
	private Texture ReplayButtonak = new Texture("textures/Replay_ak.png");
	private Texture MenuButtonun = new Texture("textures/Menu_un.png");
	private Texture MenuButtonak = new Texture("textures/Menu_ak.png");

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
		int y = (Gdx.graphics.getHeight() /2 - REPLAY_BUTTON_HEIGHT/2);
		int v = Gdx.graphics.getHeight() /(4-(1/2)) + REPLAY_BUTTON_HEIGHT/10;
		if(Gdx.input.getX() < x + REPLAY_BUTTON_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < y + REPLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(ReplayButtonak, x, v, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
			if(Gdx.input.isTouched()){
				this.dispose();
				game.gamescreen();
			}
		}else{
			batch.draw(ReplayButtonun, x, v, REPLAY_BUTTON_WIDTH, REPLAY_BUTTON_HEIGHT);
		}
		//MENU
		int a = Gdx.graphics.getWidth() / 2 - MENU_BUTTON_WIDTH / 2;
		int b = (Gdx.graphics.getWidth() / 2 - MENU_BUTTON_WIDTH / 2);
		int c = Gdx.graphics.getHeight() /100 - MENU_BUTTON_HEIGHT/8;
		if(Gdx.input.getX() < a + MENU_BUTTON_WIDTH && Gdx.input.getX() > a && Gdx.graphics.getHeight() - Gdx.input.getY() < c + MENU_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > c){
			batch.draw(MenuButtonak, b, c, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.menuscreen();
			}
		}else{
			batch.draw(MenuButtonun, b, c, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
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
