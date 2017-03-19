package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WoodyGame extends Game {
	public static WoodyGame game;

	/**
	 * scale from px to tiles
	 * 
	 * 1/64f means 1 tile == 64px
	 **/
	public static final float UNIT_SCALE = 1 / 64f;

	/** number of shown tiles on the x-Axis **/
	public final int xTiles = 20;

	/** number of shown tiles on the y-Axis **/
	public final int yTiles = 12;

	/** name of the collision layer **/
	public final String collisionLayer = "Collidable Tiles";

	/** gravity constant **/
	public static final float GRAVITY = -0.5f;
	
	private Viewport viewport;
	private Camera camera;
	@Override
	public void create() {
		this.setScreen(new SettingsScreen(this));
		//GameScreen(this, 1) für Plebs
	}
	public void gamescreen(){
		this.setScreen(new GameScreen(this, 1));
	}
	public void deathscreen(){
		this.setScreen(new GameoverScreen(this));
	}
	public void menuscreen(){
		camera = new PerspectiveCamera();
		viewport = new FitViewport(MainMenueScreen.WIDTH, MainMenueScreen.HEIGHT, camera);
		this.setScreen(new MainMenueScreen(this));
	}
	public void settingscreen(){
		this.setScreen(new SettingsScreen(this));
	}
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
	}
	public void resize(int width, int height){
//		viewport.update(width, height);
//		camera.update();
	}
	
}