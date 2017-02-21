package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Sami, Otto, Stefan
 *
 *Lifesystem: 	- if player falls down a cliff he looses a ALL hearts
 *			   	- if player gets hit he looses ONE heart
 *				- whenever woody looses all hearts he gets respawned at a respawn position (or the start if there are none / he hasn't passed one yet)
 *					AND he looses ONE life (hearts need to be refilled)
 *				- if his life count gets below 1 => state.DEAD and he is always respawned at the start of the level
 */
public class Lifesystem extends Player implements Screen{
	
	public static int hearts = 3;				//if health = 0 -> State.Dead and a life gets deducted. Furthermore Woody gets respawned and hearts refilled
	public static int life = 2;				//if life < 1 -> State.Dead and Woody starts at the start of the level
	public int oldLife = life;			//used to check if Woody has lost a life (method isLifeLost() maybe necessary for respawn)

	/**
	 * 
	 * @param health
	 * @param life
	 */
	public Lifesystem(int health, int life){
		this.hearts = hearts;
		this.life = life;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getHearts(){
		return hearts;
	}
	/**
	 * 
	 * @param newHealth
	 */
	public void setHearts(int newHearts){
		hearts = newHearts;
	}
	
	public int getLife()
	{
		return life;
	}
	
	public int setLife(int newLife)
	{
		life = newLife;
		return life;
	}
	
	public void setStateDead()
	{
		if (getHearts() < 1)
			state = State.Dead;
	}
	
	public boolean isLifeLost()
	{
		if(oldLife > getLife())
			{
				oldLife = getLife();
				return true;
			}
		else
			return false;
	}
	
	public static void checkAltitude()
	{
		if (position.y + Player.HEIGHT < 0)
		{
			hearts = hearts - 3;
		}
	}
	
	public static void checkAlive()
	{
		if(hearts < 1)
		{
			state = State.Dead;
			life = life -1;
		}
	}
	
	// Screen methods for the game over Screen (implemented)
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
