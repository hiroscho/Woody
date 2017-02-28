package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	private Array<Button> allButtons = new Array<Button>();
	public Image image;
	public static Array<Image> allImages = new Array<Image>();

	public Buttons() {
		// create a new viewport for the ui
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

		// set up a stage for the ui
		stage = new Stage(viewport);
	}

	public Button addButton(Texture tex, String name) {
		return addButton(tex, name, 0, 0, 90, 90);
	}

	public Button addButton(Texture tex, String name, float xPos, float yPos) {
		return addButton(tex, name, xPos, yPos, 90, 90);
	}

	public Button addButton(Texture tex, String name, float xPos, float yPos, float xSize, float ySize) {
		// making an annoying drawable...
		buttonTextureRegion = new TextureRegion(tex);
		buttonTexRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);

		// set the button up
		button = new ImageButton(buttonTexRegionDrawable);
		button.setName(name);
		button.setSize(xSize, ySize);
		button.setPosition(xPos, yPos);

		// add the button to the list of all buttons
		allButtons.add(button);

		// add the button to the stage to perform rendering and take input
		stage.addActor(button);

		return button;
	}
	
	public Image addImage(TextureRegion tex, String name, float xPos, float yPos, float xSize, float ySize, float scale)
	{
		image = new Image(tex);
		image.setName(name);
		image.setPosition(xPos, yPos);
		image.setSize(xSize, ySize);
		image.setScale(scale);
		allImages.add(image);
		stage.addActor(image);
		return image;
	}

	public Stage getStage() {
		return stage;
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public Array<Button> checkAllButtons() {
		Array<Button> pressedButtons = new Array<Button>();
		for (Button but : allButtons) {
			if (but.isPressed())
				pressedButtons.add(but);
		}
		return pressedButtons;
	}
	
	public void checkCorrectHeartsImage()
	{

		if(Lifesystem.hearts == 0)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageOneHeart") || actor.getName().equals("imageTwoHearts") || actor.getName().equals("imageThreeHearts"))
					actor.setVisible(false);
				if(actor.getName().equals("imageZeroHearts"))
					actor.setVisible(true);
			}
		}
		
		if(Lifesystem.hearts == 1)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageZeroHearts") || actor.getName().equals("imageTwoHearts") || actor.getName().equals("imageThreeHearts"))
					actor.setVisible(false);
				if(actor.getName().equals("imageOneHeart"))
					actor.setVisible(true);
			}
		}
		
		if(Lifesystem.hearts == 2)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageZeroHearts") || actor.getName().equals("imageOneHeart") || actor.getName().equals("imageThreeHearts"))
					actor.setVisible(false);
				if(actor.getName().equals("imageTwoHearts"))
					actor.setVisible(true);
			}
		}
		
		if(Lifesystem.hearts == 3)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageZeroHearts") || actor.getName().equals("imageOneHeart") || actor.getName().equals("imageTwoHearts"))
					actor.setVisible(false);
				if(actor.getName().equals("imageThreeHearts"))
					actor.setVisible(true);
			}
		}
	}
	
	public void checkCorrectLifeImage()
	{
		if(Lifesystem.life == 0)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageLifeOne") || actor.getName().equals("imageLifeTwo"))
					actor.setVisible(false);
				if(actor.getName().equals("imageLifeZero"))
					actor.setVisible(true);
			}
		}
		
		if(Lifesystem.life == 1)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageLifeZero") || actor.getName().equals("imageLifeTwo"))
					actor.setVisible(false);
				if(actor.getName().equals("imageLifeOne"))
					actor.setVisible(true);
			}
		}
		
		if(Lifesystem.life == 2)
		{
			for(Actor actor : stage.getActors())
			{
				if(actor.getName().equals("imageLifeOne") || actor.getName().equals("imageLifeZero"))
					actor.setVisible(false);
				if(actor.getName().equals("imageLifeTwo"))
					actor.setVisible(true);
			}
		}
	}
}