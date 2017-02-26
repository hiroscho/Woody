package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Player.State;

public class Animations extends Player{

	public static State currentState;
	public static State previousState;
	public static Animation woodyRun;
	public static TextureRegion woodyStand;
	public static TextureRegion woodyJump;
	public static TextureRegion woodyFall;
	public static Texture sheetRun;
	
	public static Texture sheetLife;
	public static TextureRegion heartsZero;
	public static TextureRegion heartsOne;
	public static TextureRegion heartsTwo;
	public static TextureRegion heartsThree;
	public static TextureRegion textureRegionPlaceholder;
	public static Image imageHearts;
	
	public static Texture sheetLives;
	public static TextureRegion livesZero;
	public static TextureRegion livesOne;
	public static TextureRegion livesTwo;
	
	public static void loadAnimations()
	{
		sheetRun = new Texture(Gdx.files.internal("textures/loadingsheet.png"));
		currentState = Player.state;
		previousState = Player.state;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 5; i++)
			frames.add(new TextureRegion(sheetRun, i * 64, 0, 64, 94));
		woodyRun = new Animation(0.25f, frames);
		
		woodyJump = new TextureRegion(sheetRun, 5*64, 0, 64, 94);
		woodyFall = new TextureRegion(sheetRun, 6*64, 0, 64, 94);
		
		woodyStand = new TextureRegion(sheetRun, 0, 0, 64, 94);
		
		//From here on down will be all Textures used for the Lifesystem UI and be possibly replaced to the standart UI class once there is one (start already Zamy!!)
		
		sheetLife = new Texture(Gdx.files.internal("textures/sheetLife.png"));
		heartsZero = new TextureRegion(sheetLife, 0, 0, 52, 16);
		heartsOne = new TextureRegion(sheetLife, 52, 0, 52, 16);
		heartsTwo = new TextureRegion(sheetLife, 2*52, 0, 52, 16);
		heartsThree = new TextureRegion(sheetLife, 3*52, 0, 52, 16);
		
		sheetLives = new Texture(Gdx.files.internal("textures/sheetLives.png"));
		livesZero = new TextureRegion(sheetLives, 0, 0, 18, 18);
		livesOne = new TextureRegion(sheetLives, 18, 0, 18, 18);
		livesTwo = new TextureRegion(sheetLives, 36, 0, 18, 18);
	}
	
	public static TextureRegion getFrame(float dt)
	{
		
		currentState = state;
		TextureRegion region;
		
		switch(currentState)
		{
		case Walking:
			region = woodyRun.getKeyFrame(stateTime);
			break;
			
		case Jumping:
			region = woodyJump;
			break;
			
		case Falling:
			region = woodyFall;
			break;
			
			default:
				region = woodyStand;
		}
		
		stateTime = currentState == previousState ? stateTime + Gdx.graphics.getDeltaTime() : 0;
		previousState = currentState;
		if (currentState == state.Walking && (stateTime >= 1))
			stateTime = 0;
		return region;	
	}	
	
	public void dispose()
	{
		sheetRun.dispose();
		sheetLife.dispose();
		sheetLives.dispose();
	}
}
