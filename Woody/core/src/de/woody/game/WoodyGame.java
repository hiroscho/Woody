package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Array;

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

	/** names of the collision layers **/
	public static final String[] collisionLayers = new String[]{"Collidable Tiles", "Destructable"};
	
	/** gravity constant **/
	public static final float GRAVITY = -0.5f;

	@Override
	public void create() {
		
		this.setScreen(new GameScreen(this, 1));
	}
	
	/**
	 * Has to be used with care, forced typecast on screen
	 * 
	 * @return
	 */
	public GameScreen getGameScreen() {
		return ((GameScreen)this.getScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}

}