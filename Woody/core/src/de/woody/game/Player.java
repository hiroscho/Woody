package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	public static float WIDTH;
	public static float HEIGHT;

	public static float MAX_VELOCITY = 10f;
	public static float JUMP_VELOCITY = 40f;

	// Deceleration after key release
	public static float DAMPING = 0.87f;

	public enum State {
		Standing, Walking, Jumping, Attacking
	}

	public final Vector2 position = new Vector2();
	public final Vector2 velocity = new Vector2();
	public State state = State.Standing;
	public float stateTime = 0;

	public boolean facesRight = true;
	public boolean grounded = false;

	public Texture texture;

	public void move(float delta) {
		if (delta == 0)
			return;

		if (delta > 0.1f)
			delta = 0.1f;

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			velocity.x = Player.MAX_VELOCITY;
			facesRight = true;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			velocity.x = -Player.MAX_VELOCITY;
			facesRight = false;
		}

		// clamp velocity to max, x-axis only
		velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY);

		// velocity is < 1, set it to 0
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
		}

		// scale to per frame velocity
		velocity.scl(delta);
		
		position.add(checkCollision(delta));
		
		// unscale velocity
		velocity.scl(1/delta);
		
		velocity.x *= DAMPING;

	}

	/**
	 * Check collision in both axis and return the resulting velocity vector.
	 * 
	 * @param delta
	 *            number of frames per second
	 * @return the velocity of the player
	 */
	private Vector2 checkCollision(float delta) {
		// create the bounding box of the player
		Rectangle playerRect = Level.rectPool.obtain();
		playerRect.set(position.x, position.y, WIDTH, HEIGHT);

		int startX, startY, endX, endY;

		// the start-(startX, startY) and end-(endX, endY) point define an area
		// the player can collide against

		// select a x-coordinate left or right from the player and add the
		// current velocity to predict the next position
		// note: we can only potentially collide with 1 tile while moving on the
		// x-axis

		if (velocity.x > 0) {
			startX = endX = (int) (position.x + WIDTH + velocity.x);
		} else {
			startX = endX = (int) (position.x + velocity.x);
		}

		// select the y-coordinates to span the height of our player
		startY = (int) (position.y);
		endY = (int) (position.y + HEIGHT);

		// get the tiles in the potential collision area
		Level.getTiles(startX, startY, endX, endY);

		// if there are any, we'll have a collision
		// set the velocity to the distance between the player and the tile
		if (Level.tiles.size != 0) {
			if (velocity.x > 0) {
				velocity.x = startX - (position.x + WIDTH);
			} else {
				velocity.x = (startX + 1) - position.x;
			}
		}
		// playerRect.x += velocity.x;
		// for (Rectangle tile : Level.tiles) {
		// if (playerRect.overlaps(tile)) {
		// velocity.x = 0;
		// break;
		// }
		// }

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
		Batch batch = screen.getRenderer().getBatch();

		batch.begin();
		if (facesRight) {
			batch.draw(texture, position.x, position.y, WIDTH, HEIGHT);
		} else {
			batch.draw(texture, position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		}
		batch.end();
	}

}
