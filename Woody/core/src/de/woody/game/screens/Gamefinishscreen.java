package de.woody.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.woody.game.WoodyGame;

public class Gamefinishscreen implements Screen{

	private static final Gamefinishscreen gamefinishScreen = new Gamefinishscreen();
	
	//Batch & current prior Lvl
	private Batch batch;
	private int priorLevel;
	
	private AssetManager asMa = WoodyGame.getGame().manager;
	
	//Textures
	private Texture Background;
	private Texture Nextlvl;
	private Texture Replay;
	private Texture Menu;
	
	//Konstruktor
	private Gamefinishscreen(){}
	//Instance
	public static Gamefinishscreen getInstance(int level){	
		gamefinishScreen.batch = new SpriteBatch();
		gamefinishScreen.priorLevel = level;
		return gamefinishScreen;
	}
	
	//Texturesizes
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	private final int B_width = Gdx.graphics.getWidth() / 3;
	private final int B_height = Gdx.graphics.getHeight() /3;
	
	
	@Override
	public void show() {
		asMa.load("textures/Finishscreenbackground.png", Texture.class);
		asMa.load("textures/Nextlvl.png", Texture.class);
		asMa.load("textures/Replay_un.png", Texture.class);
		asMa.load("textures/Menu_un.png", Texture.class);
		
		while(!asMa.update()){
			asMa.update();
		}
		Background = asMa.get("textures/Finishscreenbackground.png", Texture.class);
		Nextlvl = asMa.get("textures/Nextlvl.png", Texture.class);
		Replay = asMa.get("textures/Replay_un.png", Texture.class);
		Menu = asMa.get("textures/Menu_un.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.7f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Background
		batch.draw(Background, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		//Next Level
		float x = ((Gdx.graphics.getWidth() - B_width) / 2) * 0.9f;
		float y = Gdx.graphics.getHeight() / 2 + B_height /6;
		if (Gdx.input.getX() < x + B_width && Gdx.input.getX() > x && Gdx.graphics.getHeight() - Gdx.input.getY() < y + B_height && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(Nextlvl, x, y, B_width, B_height);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(priorLevel + 1));
			}
		}else{
			batch.draw(Nextlvl, x, y, B_width, B_height);
		}
		
		//Replay
		float a = ((Gdx.graphics.getWidth() - B_width) / 2) * 0.9f;
		float b = Gdx.graphics.getHeight() / (4 - (1 / 2)) + B_height / 10;
		if (Gdx.input.getX() < a + B_width && Gdx.input.getX() > a && Gdx.graphics.getHeight() - Gdx.input.getY() < b + B_height && Gdx.graphics.getHeight() - Gdx.input.getY() > b){
			batch.draw(Replay, a, b, B_width, B_height);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(priorLevel));
			}
		}else{
			batch.draw(Replay, a, b, B_width, B_height);
		}
		
		//Menu
		float d = ((Gdx.graphics.getWidth() - B_width) / 2) * 0.9f;
		float c = Gdx.graphics.getHeight() / 100 - B_height / 8;
		if (Gdx.input.getX() < d + B_width && Gdx.input.getX() > d && Gdx.graphics.getHeight() - Gdx.input.getY() < c + B_height && Gdx.graphics.getHeight() - Gdx.input.getY() > c){
			batch.draw(Menu, d, c, B_width, B_height);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(MainMenueScreen.getInstance());
				return;
			}
		}else{
			batch.draw(Menu, d, c, B_width, B_height);
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
		asMa.clear();
		dispose();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
}

