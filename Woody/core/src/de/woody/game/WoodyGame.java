package de.woody.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WoodyGame extends Game {
	public static WoodyGame game;
	public SpriteBatch batch;
	
	private CategoryBitsManager categoryBitsManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		categoryBitsManager = new CategoryBitsManager();
		this.setScreen(new GameLevel(this, 1));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
	
	public CategoryBitsManager getCategoryBitsManager() {
		return categoryBitsManager;
}
}