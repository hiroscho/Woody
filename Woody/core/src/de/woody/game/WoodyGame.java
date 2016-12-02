package de.woody.game;

import com.badlogic.gdx.Game;

public class WoodyGame extends Game {
	public static WoodyGame game;
	
	@Override
	public void create () {
		this.setScreen(new GameLevel(this, 1));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}