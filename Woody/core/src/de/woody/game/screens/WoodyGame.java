package de.woody.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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
		manager = new AssetManager();
		
		// Create a loader for maps
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	}

	public static WoodyGame getGame() {
		return game;
	}

	@Override
	public void create() {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				System.out.println(Gdx.app.getJavaHeap() + "   " + Gdx.app.getNativeHeap());
			}

		}, 2.0F, 2.0F);
		this.setScreen(SettingsScreen.getInstance());	//MainMenueScreen.getInstance()
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}