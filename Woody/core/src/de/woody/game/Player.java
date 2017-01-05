package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class Player {
	/** width of the player texture scaled to world coordinates **/
	public static float WIDTH;
	/** height of the player texture scaled to world coordinates **/
	public static float HEIGHT;

	/** maximum velocity's (maybe add a maximum fall velocity?) **/
	public static float MAX_VELOCITY = 10f;
	public static float JUMP_VELOCITY = 15f;

	// Deceleration after key release
	public static float DAMPING = 0.87f;

	public enum State {
		Standing, Walking, Jumping, Attacking
	}

	/** player position in world coordinates **/
	public final Vector2 position = new Vector2();

	/** player velocity in world coordinates per second **/
	public final Vector2 velocity = new Vector2();
	public State state = State.Standing;
	public float stateTime = 0;

	/** time since last jump **/
	public long lastJump = 0;

	/** is player touching the ground **/
	public boolean grounded = false;

	/** can player jump again **/
	public boolean freeJump = false;

	/** is player facing right **/
	public boolean facesRight = true;

	public Texture texture;

	public Player() {
		this(null, State.Standing, new Vector2(1, 1), 10f, 15f, 0.87f);
		System.err.println("Warning! No texture!");
	}

	public Player(Texture tex, Vector2 pos) {
		this(tex, State.Standing, pos, 10f, 15f, 0.87f);
	}

	public Player(Texture tex, State state, Vector2 pos) {
		this(tex, state, pos, 10f, 15f, 0.87f);
	}

	public Player(Texture tex, State state, Vector2 pos, float mVel, float mJump, float damp) {
		texture = tex;
		this.state = state;
		position.set(pos);
		MAX_VELOCITY = mVel;
		JUMP_VELOCITY = mJump;
		DAMPING = damp;
		WIDTH = WoodyGame.UNIT_SCALE * texture.getWidth();
		HEIGHT = WoodyGame.UNIT_SCALE * texture.getHeight();
	}
	
	/**
	 * Sets the velocity depending on input.
	 * 
	 * @param button
	 *            the detected pressedButton
	 */
	public void setInputVelocity(Button button) {

		if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || button.getName().equals("Jump")
				|| Gdx.input.isKeyPressed(Keys.W)) && grounded) {
			velocity.y = JUMP_VELOCITY;
			state = State.Jumping;
			grounded = false;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D) || button.getName().equals("Right")) {
			velocity.x = MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = true;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A) || button.getName().equals("Left")) {
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
		if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
				&& grounded) {
			velocity.y = JUMP_VELOCITY;
			state = State.Jumping;
			grounded = false;
			freeJump = true;
			lastJump = System.currentTimeMillis();
		}

		// double Jump
		if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && (System.currentTimeMillis() > (lastJump + 250))
				&& freeJump) {
			velocity.y = JUMP_VELOCITY;
			state = State.Jumping;
			grounded = false;
			freeJump = false;
		}

		// fly function
		// if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded) {
		// velocity.y = JUMP_VELOCITY;
		// state = State.Jumping;
		// grounded = false;
		// }

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			velocity.x = MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = true;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
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

		// velocity is < 1, set it to 0
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			freeJump = false;
			if (state != State.Jumping)
				state = State.Standing;
		}

		// apply gravity if player isn't standing or grounded
		if (!(state == State.Standing) || !grounded) {
			velocity.add(0, WoodyGame.GRAVITY);
			grounded = false;
			state = State.Jumping;
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
		playerRect.set(position.x, position.y, WIDTH, HEIGHT);

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

	/**
	 * Render the player //TODO: depending on state.
	 * 
	 * @param screen
	 *            the active GameScreen
	 */
	public void render(final GameScreen screen) {
//		Batch batch = screen.getRenderer().getBatch();
//
//		batch.begin();
//		if (facesRight && (state == State.Standing)) {
//			batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
//		} 
//
//		else {
//			batch.draw(texture, position.x + WIDTH, position.y, -WIDTH, HEIGHT);
//		}
//		batch.end();
	}

}
