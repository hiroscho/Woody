package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Sami
 *
 *Lifesystem: 	- if player falls down a cliff, he dies
 *			   	- if player gets hit he loses a life (will be added later)
 */
public class Lifesystem extends Player implements Screen{
	//life 
	
//	private int health = 3;		//if health = 0 -> dead
//	private boolean status = true;		// is true until falls down (or gets hit 3 times by enemies (in future))
	
	//Gameoverbanner size
//	private static final int GAMEOVERBANNER_WITH = 800;
//	private static final int GAMEOVERBANNER_HEIGHT = 600;
//	private Viewport viewport;
//	private Stage stage;
	
	
//	Texture gameOverBanner;
//	WoodyGame game;

	/**
	 * 
	 * @param health
	 * @param status
	 */
//	public Lifesystem(WoodyGame game, int health, boolean status){
//		this.health = health;
//		this.status = status;
//		
//		this.game = game;
//		viewport = new FitViewport(Player.WIDTH, Player.HEIGHT, new OrthographicCamera());
//		stage = new Stage(viewport, ((WoodyGame) game).batch);
		
		//load Banner ---> following in render method(from implemented Screen)
//		gameOverBanner = new Texture("Gameoverscreen.png");
//	}
	
	//Getters and Setters
	/**
	 * 
	 * @return
	 */
//	public int getHealth(){
//		return health;
//	}
	/**
	 * 
	 * @param updhealth
	 */
//	public void setHealth(int updhealth){
//		health = updhealth;
//	}
	/**
	 * 
	 * @return
	 */
//	public boolean isPlayerAlive(){
//		return status;
//	}
	/**
	 * sets player status = dead
	 */
//	public void setPlayerDead(){
//		status = false;
//	}
	
	//Dead or allive method
	/**
	 * sets the player dead or alive
	 */
//	public void isaliveornot(){
//		if(position.y < 0){
//			this.setPlayerDead();
//			System.out.println("DIEDD");
//			Gdx.app.log("PLAYER", "DIED");
//			if(this.isPlayerAlive() == false){
//				
//			}
//		}else{
//			status = true;
//		}
//	}
//	
	
	
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
