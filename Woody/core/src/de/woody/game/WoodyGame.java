package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;

public class WoodyGame extends Game {
	private static final WoodyGame game = new WoodyGame();

	/**
	 * scale from px to tiles
	 * 
	 * 1/64f means 1 tile == 64px
	 **/
	public static final float UNIT_SCALE = 1 / 64f;

	/** number of shown tiles on the x-Axis **/
	public static final int xTiles = 20;

	/** number of shown tiles on the y-Axis **/
	public static final int yTiles = 12;

	/** names of the collision layers **/
	public static final String[] collisionLayers = new String[] { "Collidable Tiles", "Destructable" };

	/** gravity constant **/
	public static final float GRAVITY = -0.5f;

	public AssetManager manager;

	private WoodyGame() {
	}

	public static WoodyGame getGame() {
		return game;
	}

	@Override
	public void create() {
		manager = new AssetManager();
		this.setScreen(new MainMenueScreen());
	}

	/**
	 * Life hacks, don't use it if you don't know what you're doing ~Thomas
	 * 
	 * @return
	 */
	public GameScreen getGameScreen() {
		if (this.getScreen() instanceof GameScreen) {
			return (GameScreen) getScreen();
		}
		return null;
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}