package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import de.woody.game.screens.LevelSelect;

public class Swipe implements GestureListener{
	
	public static boolean flinged = false;
	public static float rightone = 0, leftone = 0; 

	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if(Math.abs(velocityX)>Math.abs(velocityY)){
			if(velocityX>10){	//0
				LevelSelect.check--;
				if(LevelSelect.checking(LevelSelect.check, LevelSelect.MIN, LevelSelect.MAX) == true){
					flinged = false;
					System.out.println("RECHTS STOP STOP");
//					rightone = 0;
//					leftone = 0;
				}else{
					flinged = true;
//            		rightone = Gdx.graphics.getWidth();
//					leftone = 0;
                    System.out.println("RECHTS "+ LevelSelect.check);
                    return true;
				}   
            }else if (velocityX<-10){	//0
            	LevelSelect.check++;
            	if(LevelSelect.checking(LevelSelect.check, LevelSelect.MIN, LevelSelect.MAX) == true){
            		System.out.println("LINKS STOP STOP");
            		flinged = false;
//            		rightone = 0;
//            		leftone = 0;
            	}else{
            		flinged = true;
//            		leftone = Gdx.graphics.getWidth();
//            		rightone = 0;
            		System.out.println("LINKS "+LevelSelect.check);
            		return true;
            	}
            }else{
            	flinged = false;
//            	rightone = 0;
//           	leftone = 0;
            }
		}else{

			// Ignore the input, because we don't care about up/down swipes.
		}
		return false; //true
	}
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
		// TODO Auto-generated method stub
		
	}

}
