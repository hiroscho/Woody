package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;

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

	@Override
	public void create() {
		this.setScreen(new MainMenueScreen(this));
		//GameScreen(this, 1) für Plebs
	}
	public void gamescreen(){
		this.setScreen(new GameScreen(this, 1));
	}
	public void deathscreen(){
		this.setScreen(new GameoverScreen(this));
	}
	public void menuscreen(){
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
}