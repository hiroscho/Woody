package de.woody.game;

/**
 *
 *
 *Lifesystem: 	- if player falls down a cliff he looses ALL hearts
 *			   	- if player gets hit he looses ONE heart
 *				- whenever woody looses all hearts he gets respawned at a respawn position (or the start if there are none / he hasn't passed one yet)
 *					AND he looses ONE life (hearts need to be refilled)
 *				- if his life count gets below 1 => state.DEAD and he is always respawned at the start of the level
 */
public class Lifesystem{
	
	private int hearts;				//if health = 0 -> State.Dead and a life gets deducted. Furthermore Woody gets respawned and hearts refilled
	private int life;					//if life < 1 -> State.Dead and Woody starts at the start of the level
	private boolean isAlive = true;
	
	public Lifesystem() {
		this(1, 1);
	}
	
	public Lifesystem(int hearts, int life) {
		this.hearts = hearts;
		this.life = life;
	}
	
	public int getHearts(){
		return hearts;
	}
	
	public int getLife()
	{
		return life;
	}

	public boolean isAlive() {
		return isAlive;
	}
	
	public void setHearts(int newHearts){
		hearts = newHearts;
	}
	
	public void setLife(int newLife)
	{
		life = newLife;
	}
	
	public void setIsAlive(boolean bool) {
		isAlive = bool;
	}
	
	/**
	 * Damage player by damage-amount.
	 * 
	 * @param damage  amount of damage done
	 * @return  the amount of hearts the player has afterwards
	 */
	public int damagePlayer(int damage)
	{
		return hearts -= damage;
	}
	
	/**
	 * Once the player is below the map he dies.
	 * 
	 * @param player
	 */
	public void checkAltitude(Player player)
	{
		if (player.position.y + Player.HEIGHT < 0)
		{
			hearts -= 3;
		}
	}
	
	/**
	 * If the player doesn't have enough hearts he dies and loses a life.
	 */
	public void checkAlive()
	{
		if(hearts < 1)
		{
			setIsAlive(false);
			life -= 1;
		}
	}
}
