package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import de.woody.game.screens.MainMenueScreen;

import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.XmlReader;

public class WoodyGame extends Game {
	private static final WoodyGame game = new WoodyGame();

	/**
	 * scale from px to tiles
	 * 
	 * 1/64f means 1 tile == 64px
	 **/
	public final float UNIT_SCALE = 1 / 64f;

	/** number of shown tiles on the x-Axis **/
	public final int xTiles = 20;

	/** number of shown tiles on the y-Axis **/
	public final int yTiles = 12;

	/** gravity constant **/
	public final float GRAVITY = -0.5f;

	/** tile id and their corresponding name, change it in TileNames.xml **/
	public final ArrayMap<Integer, String> idNames = new ArrayMap<Integer, String>();

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
		loadIdNames();
		this.setScreen(MainMenueScreen.getInstance());
	}

	private void loadIdNames() {
		XmlReader xml = new XmlReader();
		try {
			FileHandle file = Gdx.files.internal("xml/TileNames.xml");
			XmlReader.Element ele = xml.parse(file);
			String[] ids;
			String name;

			for (int i = 0; i < ele.getChildCount(); i++) {
				ids = ele.getChild(i).getAttribute("id").split(",");
				name = ele.getChild(i).getAttribute("name");
				for (String s : ids) {
					s = s.trim();
					idNames.put(Integer.parseInt(s), name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}