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
	public Texture sheetRun;
	
	public void cycleAnimations()
	{
		sheetRun = new Texture(Gdx.files.internal("textures/loadingsheet.tps"));
		currentState = State.Standing;
		previousState = State.Standing;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 0; i < 9; i++)
			frames.add(new TextureRegion(sheetRun, i * 64, 0, 64, 128));
		woodyRun = new Animation(0.2f, frames);
		
		woodyStand = new TextureRegion(sheetRun, 65, 0, 64, 128);
		//To do: animations for jumping/attacking etc...
		
	}
	
	public static TextureRegion getFrame(float dt)
	{
		currentState = state;
		TextureRegion region;
		
		switch(currentState)
		{
		case Walking:
			region = woodyRun.getKeyFrame(stateTime);
			
//		extra cases für später:
//		case Jumping:
//			region =woodyJump.getKeyFrame(stateTime);
			
			default:
				region = woodyStand;
		}
		
		stateTime = currentState == previousState ? stateTime + dt : 0;
		previousState = currentState;
		return region;	
	}
	
//	@Override
//	public void render(final GameScreen screen)
//	{
//	Batch batch = screen.getRenderer().getBatch();
//
//	batch.begin();
//	if (facesRight && (state == State.Standing))
//		batch.draw(getFrame(stateTime), position.x, position.y, WIDTH, HEIGHT);
//	else if (!facesRight && (state == State.Standing))
//		batch.draw(getFrame(stateTime), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
//	else if (facesRight && (state == State.Walking))
//		batch.draw(getFrame(stateTime), position.x, position.y, WIDTH, HEIGHT);
//	else if (!facesRight && (state == State.Walking))
//		batch.draw(getFrame(stateTime), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
//	else if (facesRight && (state == State.Jumping))
//		batch.draw(getFrame(stateTime), position.x, position.y, WIDTH, HEIGHT);
//	else if (!facesRight && (state == State.Jumping))
//		batch.draw(texture, position.x + WIDTH, position.y, -WIDTH, HEIGHT);
//	else
//		batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
//	batch.end();
//
//	}
	
}
