package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Buttons extends WoodyGame {
	private Stage stage;
	private TextureRegion buttonTextureRegion;
	private TextureRegionDrawable buttonTexRegionDrawable;
	private ImageButton button;
	private Viewport viewport;
	private boolean jumpPressed;
	private boolean attackPressed;
	private boolean leftPressed;
	private boolean rightPressed;
	private Array<Button> allButtons = new Array<Button>();

	public Buttons() {
		// create a new viewport for the ui
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
		
		// set up a stage for the ui
		stage = new Stage(viewport);

	}
	
	public Button addButton(Texture tex, String name) {
		// making an annoying drawable...
		buttonTextureRegion = new TextureRegion(tex);
		buttonTexRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);
		
		// set the button up
		button = new ImageButton(buttonTexRegionDrawable); 
		button.setName(name);
		
		//add the button to the list of all buttons
		allButtons.add(button);
		
		// add the button to the stage to perform rendering and take input
		stage.addActor(button);
		
		return button;
	}

	public Stage getStage() {
		return stage;
	}

	public void draw() {
		stage.draw();
	}

	public boolean isjumpPressed() {
		return jumpPressed;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}

	public boolean isattackPressed() {
		return attackPressed;
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public Button checkAllButtons() {
		for (Button but : allButtons) {
			if (but.isPressed())
				return but;
		}
		return null;
	}
}