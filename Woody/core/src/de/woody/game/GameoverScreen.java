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

	private Viewport viewport;
	private Stage gameoverstate;
	private Game gameover;
	private Batch batch;

	private static final int BANNER_WIDTH = 350;
	private static final int BANNER_HEIGHT = 100;
	Texture gameOverBanner = new Texture("textures/Gameoverscreen.png");

	public GameoverScreen(WoodyGame game) {
		this.gameover = game;
		batch = new SpriteBatch();
	}

	@Override
	public void create() {
		this.setScreen(new GameoverScreen(this));
	}

	// public GameoverScreen(Game game){
	// this.game = game;
	// viewport = new FitViewport(Player.WIDTH, Player.HEIGHT, new
	// OrthographicCamera());
	// gameover = new Stage(viewport, new SpriteBatch());
	// }

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameOverBanner, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
