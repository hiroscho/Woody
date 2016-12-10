package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class Buttons extends WoodyGame
{
    private Stage stage;
    private Texture textureLeftButton;
    private ImageButton buttonLeft;
    private TextureRegion textureRegionLeft;
    private TextureRegionDrawable textureRegionDrawableLeft;
    private Actor actor;

    @Override
    public void create()
    {
    	textureLeftButton = new Texture(Gdx.files.internal("ButtonLeft.png"));
        textureRegionLeft = new TextureRegion(textureLeftButton);
        textureRegionDrawableLeft = new TextureRegionDrawable(textureRegionLeft);
        buttonLeft = new ImageButton(textureRegionDrawableLeft); //Set the button up

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonLeft); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }
	
    @Override
    public void render()
    {
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw();
    }
    
    
	
	
	
}