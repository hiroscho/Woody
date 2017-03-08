package de.woody.game;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {
	/** width of the player texture scaled to world coordinates **/
	public static float WIDTH;
	/** height of the player texture scaled to world coordinates **/
	public static float HEIGHT;

	/** maximum velocity's (maybe add a maximum fall velocity?) **/
	public static float standartMaxVelocity = 10f;
	public static float standartJumpVelocity = 15f;
	public static float MAX_VELOCITY = standartMaxVelocity;
	public static float JUMP_VELOCITY = standartJumpVelocity;

	// Deceleration after key release
	public static float DAMPING = 0.87f;

	public enum State {
		Standing, Walking, Jumping, Attacking, Falling, Dead
	}

	/** player position in world coordinates **/
	public Vector2 position = new Vector2();

	/** player velocity in world coordinates per second **/
	public Vector2 velocity = new Vector2();
	public static State state = State.Standing;

	/** time since last jump **/
	public long lastJump = 0;

	/** can player jump in the air **/
	public boolean freeJump;

	public long axeCooldown = System.currentTimeMillis();

	/** is player touching the ground **/
	public boolean grounded = false;

	public boolean hasAxe = true;

	/** is player facing right **/
	public boolean facesRight = true;
	public boolean sliding = false;
	public boolean slidingLeft = false;
	public boolean slidingRight = false;
	public boolean climbing = false;

	public Texture texture;
	
	// easy access game instance
	private WoodyGame game;

	public Player(final WoodyGame game) {
		this(game, null, State.Standing, new Vector2(1, 1), 10f, 15f, 0.87f);
		System.err.println("Warning! No texture!");
	}

	public Player(final WoodyGame game, Texture tex, Vector2 pos) {
		this(game, tex, State.Standing, pos, 10f, 15f, 0.87f);
	}

	public Player(final WoodyGame game, Texture tex, State state, Vector2 pos) {
		this(game, tex, state, pos, 10f, 15f, 0.87f);
	}

	public Player(final WoodyGame game, Texture tex, State state, Vector2 pos, float mVel, float mJump, float damp) {
		texture = tex;
		this.state = state;
		position.set(pos);
		MAX_VELOCITY = mVel;
		JUMP_VELOCITY = mJump;
		DAMPING = damp;
		WIDTH = WoodyGame.UNIT_SCALE * texture.getWidth();
		HEIGHT = WoodyGame.UNIT_SCALE * texture.getHeight();
		this.game = game;
	}

	/**
	 * Sets the velocity depending on input.
	 * 
	 * @param button
	 *            the detected pressedButton
	 */
	public void setInputVelocity(Button button) {

		if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || button.getName().equals("Jump")
				|| Gdx.input.isKeyPressed(Keys.W)) && grounded && !climbing) {
			velocity.y = JUMP_VELOCITY;
			state = State.Jumping;
			grounded = false;
		}

		if ((Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || button.getName().equals("Right")) && !slidingLeft) {
			velocity.x = MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = true;
		}

		if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || button.getName().equals("Left"))  && !slidingRight) {
			velocity.x = -MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = false;
		}
	}

	/**
	 * Intended for testing! Keyboard input only! Sets the velocity depending on
	 * input.
	 */
	public void setKeyboardVelocity() {
		if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) && !climbing) {
			if (grounded) {
				velocity.y = JUMP_VELOCITY;
				state = State.Jumping;
				grounded = false;
				freeJump = true;
			} else {
				if (freeJump && velocity.y < 1) {
					velocity.y = JUMP_VELOCITY;
					state = State.Jumping;
					grounded = false;
					freeJump = false;
				}
			}
		}
		// attack function
		if (Gdx.input.isKeyPressed(Keys.ENTER) && grounded) {
			if(Level.findDoorRect(game.getGameScreen(), position)){
				position.set(new Vector2(Level.getCurrentSpawn(game.getGameScreen().getLevel(), game.getGameScreen().getCheckpoint())));
			}
			
			
			
			if ((axeCooldown + 200) < System.currentTimeMillis()) {

				if (facesRight) {
					int x2 = (int)position.x + 1;
					int y2 = (int)position.y;
					((TiledMapTileLayer) GameScreen.map.getLayers().get("Destructable")).setCell(x2, y2, null);
					((TiledMapTileLayer) GameScreen.map.getLayers().get("Destructable")).setCell(x2, y2+1,null);
					axeCooldown = System.currentTimeMillis();

				} else {
					int x2 = (int)position.x - 1;
					int y2 = (int)position.y;
					((TiledMapTileLayer) GameScreen.map.getLayers().get("Destructable")).setCell(x2, y2,null);
					((TiledMapTileLayer) GameScreen.map.getLayers().get("Destructable")).setCell(x2, y2+1,null);
					axeCooldown = System.currentTimeMillis();
				}

				axeCooldown = System.currentTimeMillis();
			}
		}

		// fly function
		// if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded) {
		// velocity.y = JUMP_VELOCITY;
		// state = State.Jumping;
		// grounded = false;
		// }

		if ((Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) && !slidingLeft)

		{
			velocity.x = MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = true;
		}

		if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && !slidingRight) {
			velocity.x = -MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = false;
		}
	}

	/**
	 * Add gravity, move player, check for collisions.
	 * 
	 * @param delta
	 *            time since the last frame
	 */
	public void move(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;

		// clamp velocity to max, x-axis only
		velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY);
		velocity.y = MathUtils.clamp(velocity.y, -JUMP_VELOCITY, JUMP_VELOCITY);

		// velocity is < 1, set it to 0
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (velocity.y == 0)
				state = State.Standing;
		}

		// apply gravity if player isn't standing or grounded or climbing
		if (!(state == State.Standing) || !grounded && !climbing) {
			velocity.add(0, WoodyGame.GRAVITY);
			grounded = false;
		}

		if (!grounded && (velocity.y < 0) && !climbing) {
			state = State.Falling;
		}

		// scale to frame velocity
		velocity.scl(delta);

		position.add(checkTileCollision(delta));

		// unscale velocity
		velocity.scl(1 / delta);

		velocity.x *= DAMPING;
	}

	/**
	 * Check collision in both axis and return the resulting velocity vector.
	 * 
	 * @param delta
	 *            frames per second
	 * @return the velocity of the player
	 */
	private Vector2 checkTileCollision(float delta) {
		// create the bounding box of the player
		Rectangle playerRect = Level.rectPool.obtain();
		playerRect.set(position.x, position.y, WIDTH-0.1f, HEIGHT);

		// the start-(startX, startY) and end-(endX, endY) point define an area
		// the player can collide against
		int startX, startY, endX, endY;

		// only check if there's movement
		if (velocity.x != 0) {
			// select a x-coordinate left or right from the player and add the
			// current velocity to get the tiles you would step into
			// note: player can only potentially collide with 1 tile while
			// moving on the x-axis (startX = endX)
			if (velocity.x > 0) {
				startX = endX = (int) (position.x + WIDTH + velocity.x);
			} else {
				startX = endX = (int) (position.x + velocity.x);
			}

			// select the y-coordinates to span the height of our player
			startY = (int) (position.y);
			endY = (int) (position.y + HEIGHT);

			// move the playerRect and see if it overlaps with one of the tiles,
			// if it does, handle the collision
			playerRect.x += velocity.x;
			for (Rectangle tile : Level.getTiles(startX, startY, endX, endY)) {
				if (playerRect.overlaps(tile)) {

					// set the players position either directly left or right of
					// the tile
					if (velocity.x > 0) {
						position.x = startX - WIDTH;
					} else {
						position.x = startX + 1;
					}
					velocity.x = 0;
					break;
				}
			}
			// reset the rectangle if you moved it while checking for overlaps
			// (for the following y-collision-check)
			playerRect.x = position.x;
		}

		// only check if there's movement
		if (velocity.y != 0) {
			// select a y-coordinate above or below the player and add the
			// current velocity to get the tiles you would step into
			// note: player can only potentially collide with 1 tile while
			// moving on the y-axis (startY = endY)
			if (velocity.y > 0) {
				startY = endY = (int) (position.y + HEIGHT + velocity.y);
			} else {
				startY = endY = (int) (position.y + velocity.y);
			}

			// select the x-coordinates to span the width of our player
			startX = (int) (position.x);
			endX = (int) (position.x + WIDTH);

			// move the playerRect and see if it overlaps with one of the tiles,
			// if it does, handle the collision
			playerRect.y += velocity.y;
			for (Rectangle tile : Level.getTiles(startX, startY, endX, endY)) {
				if (playerRect.overlaps(tile)) {
					// set the players position either directly above or below
					// the tile
					if (velocity.y > 0) {
						position.y = tile.y - HEIGHT;
						// destroy the tile above
						// TiledMapTileLayer layer = (TiledMapTileLayer)
						// map.getLayers().get("walls");
						// layer.setCell((int) tile.x, (int) tile.y, null);
					} else {
						position.y = tile.y + tile.height;
						// set grounded to true to allow jumping
						grounded = true;
						freeJump = true;
						if (velocity.x != 0)
							state = State.Walking;
						else
							state = State.Standing;
					}
					velocity.y = 0;
					break;
				}
			}
		}
		Level.rectPool.free(playerRect);
		return velocity;
	}
	
	
	public void checkPlayerAboveDamageBlock()
	{
		int x2 = (int)position.x;
		int y2 = (int)position.y - 1;
		if(((((TiledMapTileLayer) GameScreen.map.getLayers().get("Damaging")).getCell(x2, y2)) != null))
		{
			Lifesystem.hearts = Lifesystem.damagePlayer(1);
		}
		
		if(((((TiledMapTileLayer) GameScreen.map.getLayers().get("Slime")).getCell(x2, y2)) != null))
		{
			MAX_VELOCITY = MAX_VELOCITY / 5;
		}
		else
			MAX_VELOCITY = standartMaxVelocity;
		
		if(((((TiledMapTileLayer) GameScreen.map.getLayers().get("Ice")).getCell(x2, y2)) != null))
		{
			DAMPING = 0.975f;
			sliding = true;
		}
		else
		{
			DAMPING = 0.87f;
		}
	}	
	
	public void checkPlayerInBlock(/*Button pressedButton*/)				//button does not work
	{
		int x2 = (int)position.x;
		int y2 = (int)position.y;
		if(((((TiledMapTileLayer) GameScreen.map.getLayers().get("Foliage")).getCell(x2, y2)) != null))
		{
			MAX_VELOCITY = MAX_VELOCITY / 5;
		}
		
		if((((((TiledMapTileLayer) GameScreen.map.getLayers().get("Ladder")).getCell(x2, y2)) != null) && ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) /*|| pressedButton.getName().equals("Jump")*/
				|| Gdx.input.isKeyPressed(Keys.W)))))			//if a ladder is in the background and the jumpkey is pressed do...
		{
			climbing = true;
			velocity.y = 5f;
			velocity.x = 0;
		}
		else if((((((TiledMapTileLayer) GameScreen.map.getLayers().get("Ladder")).getCell(x2, y2)) != null) && !((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) /*|| pressedButton.getName().equals("Jump")*/
				|| Gdx.input.isKeyPressed(Keys.W)))))			//if a ladder is in the background and the jumpkey is not pressed do...
		{
			climbing = true;
			velocity.y = -2.5f;
			velocity.x = 0;
		}
		else
		{
			climbing = false;
		}
	}
	
	public void checkSliding()
	{
		if(velocity.x > 0 && sliding)
		{
			slidingRight = true;
			slidingLeft = false;
		}
		if(velocity.x < 0 && sliding)
		{
			slidingLeft = true;
			slidingRight = false;
		}
		if(velocity.x == 0)
			sliding = false;
		if(!sliding)
		{
			slidingLeft = false;
			slidingRight = false;
		}
	}
	
	 /* 
	 * @param screen
	 *            the active GameScreen
	 */
	public void render(final GameScreen screen) {
		Batch batch = screen.getRenderer().getBatch();
		Animations pAH = screen.getPlayerAnimationHandler();

		batch.begin();
		if (facesRight && (state == State.Standing))
			batch.draw(pAH.getFrame(), position.x, position.y, WIDTH, HEIGHT);
		else if (!facesRight && (state == State.Standing))
			batch.draw(pAH.getFrame(), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		else if (facesRight && (state == State.Walking))
			batch.draw(pAH.getFrame(), position.x, position.y, WIDTH, HEIGHT);
		else if (!facesRight && (state == State.Walking))
			batch.draw(pAH.getFrame(), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		else if (facesRight && (state == State.Jumping))
			batch.draw(pAH.getFrame(), position.x, position.y, WIDTH, HEIGHT);
		else if (!facesRight && (state == State.Jumping))
			batch.draw(pAH.getFrame(), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		else if (facesRight && (state == State.Falling))
			batch.draw(pAH.getFrame(), position.x, position.y, WIDTH, HEIGHT);
		else if (!facesRight && (state == State.Falling))
			batch.draw(pAH.getFrame(), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		else
			batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
		batch.end();
	}
}
