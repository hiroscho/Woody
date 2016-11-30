package de.woody.game;

import com.badlogic.gdx.Screen;


public class GameLevel implements Screen {
	    final WoodyGame game;
	    
	    int level;

	    public GameLevel(final WoodyGame gam, int level) {
	        this.game = gam;
	        this.level = level;
	        

	    }

	    @Override
	    public void render(float delta) {
	        
	    }

	    @Override
	    public void resize(int width, int height) {
	    }

	    @Override
	    public void show() {
	        // when the screen is shown
	    }

	    @Override
	    public void hide() {
	    }

	    @Override
	    public void pause() {
	    }

	    @Override
	    public void resume() {
	    }

	    @Override
	    public void dispose() {

	    }

	}
