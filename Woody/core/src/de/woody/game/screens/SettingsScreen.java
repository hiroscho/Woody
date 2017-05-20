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
	
	//Textures
	private Texture titel;
	private Texture menuun;
	private Texture menuak;
	private Texture settingsbackground;
	private Texture soundoff;
	private Texture soundon;
	
	//Sound on/off
	private int check = 0;
	
	public void change(){
		if(check == 0){
			check = 1;
		}else{
			check = 0;
		}
	}
	
	//Texture sizes
	private final int TITEL_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int TITEL_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACK_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int BACK_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	private AssetManager asMa = WoodyGame.getGame().manager;
	
	private SettingsScreen(){}
	
	public static SettingsScreen getInstance() {
		settingsScreen.batch = new SpriteBatch();
		return settingsScreen;
	}
	
	
	@Override
	public void show() {
		asMa.load("textures/Settings_un.png", Texture.class);
		asMa.load("textures/Menu_un.png", Texture.class);
		asMa.load("textures/Menu_ak.png", Texture.class);
		asMa.load("textures/Soundbackground.png", Texture.class);
		asMa.load("textures/Soundoff.png", Texture.class);
		asMa.load("textures/Soundon.png", Texture.class);
		
		while(!asMa.update()) {
			asMa.update();
		}
		
		settingsbackground = asMa.get("textures/Soundbackground.png", Texture.class);
		titel = asMa.get("textures/Settings_un.png", Texture.class);
		menuun = asMa.get("textures/Menu_un.png", Texture.class);
		menuak = asMa.get("textures/Menu_ak.png", Texture.class);
		soundoff = asMa.get("textures/Soundoff.png", Texture.class);
		soundon = asMa.get("textures/Soundon.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Background
		batch.draw(settingsbackground,0,0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		
		//TITEL
		float k = 1.70f;
		float l = ((Gdx.graphics.getWidth() /2) - (TITEL_WIDTH/k));
		batch.draw(titel, l, Gdx.graphics.getHeight() / 2 + TITEL_HEIGHT / 3);
		
		//SOUND ON/OFF
		int a = Gdx.graphics.getWidth() / 2 - TITEL_WIDTH / 2;
		int b = (Gdx.graphics.getWidth() / 2 - TITEL_WIDTH/ 2);
		int c = Gdx.graphics.getHeight() / 2 - TITEL_HEIGHT / 2;
		
		if(Gdx.input.getX() < a + BACK_WIDTH && Gdx.input.getX() > a && Gdx.graphics.getHeight() - Gdx.input.getY() < c + BACK_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > c){
			if(Gdx.input.justTouched()){
				change();
			}
		}
		
		switch(check){
		
		case 0:{
			//SOUND-ON FUNKTION PASTE HERE
			batch.draw(soundon, b, c, TITEL_WIDTH, TITEL_HEIGHT);
			break;
		}
		case 1:{
			//SOUND-OFF FUNKTION PASTE HERE
			batch.draw(soundoff, b, c, TITEL_WIDTH, TITEL_HEIGHT);
			break;
		}
		default:{
			batch.draw(soundon, b, c, TITEL_WIDTH, TITEL_HEIGHT);
			break;
		}
		}
				
		
		//MENU
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
