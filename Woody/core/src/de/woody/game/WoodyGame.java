package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

public class WoodyGame extends Game {

	public static WoodyGame game;

	/**
	 * Scale from px to tiles 1/64f means 1 tile == 64px
	 **/
	public static final float unitScale = 1 / 64f;

	/** number of shown tiles on the x-Axis **/
	public static final int xTiles = 20;

	/** number of shown tiles on the y-Axis **/
	public static final int yTiles = 15;

	@Override
	public void create() {
		this.setScreen(new GameScreen(this, 1));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
	}
}