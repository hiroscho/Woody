package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsScreen implements Screen{
	
	private Batch batch;
	WoodyGame game;
	
	Texture Background = new Texture("textures/Settings_un.png");
	Texture Backgroundp = new Texture("textures/SettingsBackground.png");
	Texture Backun = new Texture("textures/Menu_un.png");
	Texture Backak = new Texture("textures/Menu_ak.png");
	
	
	private static final int BACKGROUND_WIDTH = 300;
	private static final int BACKGROUND_HEIGHT = 150;
	private static final int BACK_WIDTH = Gdx.graphics.getWidth() / 3;
	private static final int BACK_HEIGHT = Gdx.graphics.getHeight() / 3;
	private static final int BACKGROUNDP_WIDTH = Gdx.graphics.getWidth();
	private static final int BACKGROUNDP_HEIGHT = Gdx.graphics.getHeight();
	
	public SettingsScreen(WoodyGame game){
		this.game = game;
		batch = new SpriteBatch();
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		batch.draw(Backgroundp, 0, 0, BACKGROUNDP_WIDTH, BACKGROUNDP_HEIGHT);
		int l = (Gdx.graphics.getWidth() /2 - BACKGROUND_WIDTH/2);
		batch.draw(Background, l, 350, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		int n = Gdx.graphics.getWidth() / 2 - BACK_WIDTH / 2;
		int x = (Gdx.graphics.getWidth() /2 - BACK_WIDTH/2);
		int y = Gdx.graphics.getHeight() /100 - BACK_HEIGHT/8;
		if(Gdx.input.getX() < n + BACK_WIDTH && Gdx.input.getX() > n && Gdx.graphics.getHeight() - Gdx.input.getY() < y + BACK_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(Backak, x, y, BACK_WIDTH, BACK_HEIGHT);
			if(Gdx.input.justTouched()){
				this.dispose();
				game.menuscreen();
			}
		}else{
			batch.draw(Backun, x, y, BACK_WIDTH, BACK_HEIGHT);
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
