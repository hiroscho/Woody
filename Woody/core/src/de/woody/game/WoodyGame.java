package de.woody.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

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
	public static final String[] collisionLayers = new String[] { "Collidable Tiles"};

	/** gravity constant **/
	public static final float GRAVITY = -0.5f;
	
	/** tile id and their corresponding name, change it in TileNames.xml **/
	public static HashMap<Integer, String> idNames = new HashMap<Integer, String>();

	@Override
	public void create() {
		loadIdNames();

		this.setScreen(new GameScreen(this, 1));
	}

    private void loadIdNames() {
        XmlReader xml = new XmlReader();
        try {
            FileHandle file = new FileHandle(new File("TileNames.xml"));
            XmlReader.Element ele = xml.parse(file);
            String[] ids;
            String name;

            for (int i = 0; i < ele.getChildCount(); i++) {
                ids = ele.getChild(i).getAttribute("id").split(",");
                name = ele.getChild(i).getAttribute("name");
                for(String s : ids) {
                    s = s.trim();
                    idNames.put(Integer.parseInt(s), name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Has to be used with care, forced typecast on screen
	 * 
	 * @return
	 */
	public GameScreen getGameScreen() {
		return ((GameScreen) this.getScreen());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}

}