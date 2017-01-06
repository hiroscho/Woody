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
	public static Texture sheetRun;
	
	public static void cycleAnimations()
	{
		sheetRun = new Texture(Gdx.files.internal("textures/loadingsheet.png"));
		currentState = Player.state;
		previousState = State.Standing;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 0; i < 7; i++)
			frames.add(new TextureRegion(sheetRun, i * 64, 0, 64, 128));
		woodyRun = new Animation(0.2f, frames);
		
		woodyStand = new TextureRegion(sheetRun, 0, 0, 64, 128);
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
			
//		extra cases für später:
//		case Jumping:
//			region =woodyJump.getKeyFrame(stateTime);
//			break;
			
			default:
				region = woodyStand;
		}
		
		stateTime = currentState == previousState ? stateTime + dt : 0;
		previousState = currentState;
		return region;	
	}	
}
