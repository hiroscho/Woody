package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * 
 * @author Sami, Otto, Stefan
 *
 *         Lifesystem: - if player falls down a cliff he looses ALL hearts - if
 *         player gets hit he looses ONE heart - whenever woody looses all
 *         hearts he gets respawned at a respawn position (or the start if there
 *         are none / he hasn't passed one yet) AND he looses ONE life (hearts
 *         need to be refilled) - if his life count gets below 1 => state.DEAD
 *         and he is always respawned at the start of the level
 */
public class Lifesystem {
	public static boolean invulnerable; // true for inv false for not
	public static int hearts = 3; // if health = 0 -> State.Dead and a life gets
									// deducted. Furthermore Woody gets
									// respawned and hearts refilled
	public static int life = 2; // if life < 1 -> State.Dead and Woody starts at
								// the start of the level
	public static int oldLife = life; // used to check if Woody has lost a life
										// (method isLifeLost() maybe necessary
										// for respawn)
	public static int currenthearts = hearts;

	public int getHearts() {
		return hearts;
	}

	public static void setHearts(int newHearts) {
		hearts = newHearts;
	}

	public static int changeHearts(int newHearts) { // used to change the number
													// of hearts
		return hearts = newHearts;
	}

	public static void checkPlayerInvulnerable() {
		if (invulnerable) {
			Timer.schedule(new Task() {
				@Override
				public void run() {
					 System.out.println(System.currentTimeMillis());
					invulnerable = false;
					Timer.instance().clear();
				}

			}, 2.0F);
		}
	}

	public void nodamage() {

	}

	public static int damagePlayer(int damage) {
		if (!invulnerable) {
			if(hearts != 1) {
				invulnerable = true;
			}
			return hearts -= damage;
		} else {
			return hearts;
		}
	}

	public static int getLife() {
		return life;
	}

	public static int setLife(int newLife) {
		life = newLife;
		return life;
	}

	private static void setStateDead() {
		Player.state = Player.State.Dead;
	}

	public static void checkAltitude(Player player) {
		if (player.position.y + Player.HEIGHT < 0) {
			hearts = hearts - 3;
		}
	}

	public static void checkAlive() {
		if (hearts < 1) {
			setStateDead();
			life = life - 1;
		}
	}
}
