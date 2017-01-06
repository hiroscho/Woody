package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Player.State;

public class Animations extends Player{

	public static State currentState;
	public static State previousState;
	public static Animation woodyRun;
	public static TextureRegion woodyStand;
	public static TextureRegion  woodyJump;
	public static Texture sheetRun;
	
	public static void cycleAnimations()
	{
		sheetRun = new Texture(Gdx.files.internal("textures/loadingsheet.png"));
		currentState = Player.state;
		previousState = Player.state;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 1; i < 4; i++)													//frame 2-4 == walking wei�-gr�n-blau
			frames.add(new TextureRegion(sheetRun, i * 64, 0, 64, 128));
		woodyRun = new Animation(0.33f, frames);
		
		woodyJump = new TextureRegion(sheetRun, 256, 0, 64, 128);					//frame 5 == jumping pink
		
		woodyStand = new TextureRegion(sheetRun, 0, 0, 64, 128);					//frame 1 == standing 
		//To do: animations for jumping/attacking etc...
	}
	
	public static TextureRegion getFrame(float dt)
	{
		cycleAnimations();
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
		
	}
}
