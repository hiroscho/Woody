package de.woody.game;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.woody.game.screens.GameScreen;

/**
 * Lifesystem:
 * <ul>
 * <li> if player falls down a cliff he looses ALL hearts
 * <li> if player gets hit he looses ONE heart
 * <li> whenever woody looses all hearts he gets respawned at a respawn position (or the start if he hasn't passed one yet) AND he looses ONE life (hearts are currently being refilled in GameScreen)
 * <li> if his life count gets below 1 he is respawned at the start of the level (or go to levelselect?)
 * </ul>
 */
public class Lifesystem {

	private boolean invulnerable;
	private int hearts;
	private int life;
	private boolean isAlive = true;

	public Lifesystem(int hearts) {
		this(hearts, 0);
	}

	public Lifesystem(int hearts, int life) {
		this.hearts = hearts;
		this.life = life;
	}

	public int getHearts() {
		return hearts;
	}

	public int getLife() {
		return life;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public void setLife(int newLife) {
		life = newLife;
		GameScreen.getInstance().getUI().updateLifeImage(life);
	}

	// TODO: temp, waiting for respawn
	public void setHearts(int newHearts) {
		hearts = newHearts;
		GameScreen.getInstance().getUI().updateHeartsImage(hearts);
	}

	public void setIsAlive(boolean bool) {
		isAlive = bool;
	}

	/**
	 * DO NOT use for player.
	 * 
	 * @param damage
	 *            amount of damage
	 * @return remaining hearts
	 */
	public int damageEntity(int damage) {
		return hearts -= damage;
	}

	/**
	 * Damage player by damage-amount, also make him temporarily invulnerable.
	 * 
	 * @param damage
	 *            amount of damage done
	 * @return the amount of hearts the player has afterwards
	 */
	public int damagePlayer(int damage) {
		if (!invulnerable) {
			if (hearts != 1) {
				invulnerable = true;
				startCooldown();
			}
			hearts -= damage;
			GameScreen.getInstance().getUI().updateHeartsImage(hearts);
			GameScreen.getInstance().getHurtSound().play(WoodyGame.getGame().VOLUME);
		}
		return hearts;
	}

	public void startCooldown() {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				invulnerable = false;
			}
		}, 2.0F);
	}

	/**
	 * Once the player is below the map he dies.
	 * 
	 * @param player
	 */
	public void checkAltitude(Player player) {
		if (player.position.y + Player.HEIGHT < 0) {
			hearts = 0;
			GameScreen.getInstance().getUI().updateHeartsImage(hearts);
		}
	}

	/**
	 * If the player doesn't have enough hearts he dies and loses a life.
	 */
	public void checkAlive() {
		if (hearts < 1) {
			setIsAlive(false);
			life -= 1;
			GameScreen.getInstance().getUI().updateLifeImage(life);
		}
	}
}
