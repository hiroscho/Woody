package de.woody.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;

import de.woody.game.enemies.Entity;
import de.woody.game.enemies.Walker;
import de.woody.game.screens.GameScreen;
import de.woody.game.screens.WoodyGame;

import com.badlogic.gdx.maps.tiled.TiledMap;

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
		Standing, Walking, Jumping, Attacking, Falling
	}

	/** player position in world coordinates **/
	public Vector2 position = new Vector2();

	/** player velocity in world coordinates per second **/
	public Vector2 velocity = new Vector2();
	public State state = State.Standing;

	/** time since last jump **/
	public long lastJump = 0;

	/** can player jump in the air **/
	public boolean freeJump;

	/** current coin score **/
	private int coinAmount;

	/** Cooldown for the axe **/
	public long axeCooldown = System.currentTimeMillis();

	/** is player touching the ground **/
	public boolean grounded = false;

	public boolean hasAxe = true;

	/** is player facing right **/
	public boolean facesRight = true;

	public Lifesystem life;
	
	/** Required for freeing the rectangles **/
	private Array<Rectangle> priorTiles;

	public Player() {
		this(State.Standing, new Vector2(1, 1), 10f, 15f, 0.87f);
		System.err.println("Warning! No texture!");
	}

	public Player(Vector2 pos) {
		this(State.Standing, pos, 10f, 15f, 0.87f);
	}

	public Player(State state, Vector2 pos) {
		this(state, pos, 10f, 15f, 0.87f);
	}

	public Player(State state, Vector2 pos, float mVel, float mJump, float damp) {
		this.state = state;
		position.set(pos);
		MAX_VELOCITY = mVel;
		JUMP_VELOCITY = mJump;
		DAMPING = damp;
		WIDTH = 1.0F;
		HEIGHT = 1.46875F;
		life = new Lifesystem(3, 2);
	}

	public int getCoinAmount() {
		return coinAmount;
	}

	private void addCoin() {
		coinAmount++;
	}

	private void setCoinAmount(int x) {
		coinAmount = x;
	}

	/**
	 * Sets the velocity depending on input.
	 * 
	 * @param button
	 *            the detected pressedButton
	 */
	public void setInputVelocity(Button button) {

		if (button.getName().equals("Jump")) {
			handleJump();
		}

		if (button.getName().equals("Right")) {
			handleRight();
		}

		if (button.getName().equals("Left")) {
			handleLeft();
		}
		// do we need to be grounded for use of this stuff?
		if (button.getName().equals("Fight") && grounded) {
			handleFight();
		}

	}

	/**
	 * Intended for testing! Keyboard input only! Sets the velocity depending on
	 * input.
	 */
	public void setKeyboardVelocity() {
		if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
			handleJump();
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
			handleRight();
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
			handleLeft();
		}

		if (Gdx.input.isKeyPressed(Keys.ENTER) && grounded) {
			handleFight();
		}
	}

	private void handleJump() {
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

	private void handleRight() {
		velocity.x = MAX_VELOCITY;
		if (grounded)
			state = State.Walking;
		facesRight = true;
	}

	private void handleLeft() {
		velocity.x = -MAX_VELOCITY;
		if (grounded)
			state = State.Walking;
		facesRight = false;
	}

	private void handleFight() {

		// use doors
		Rectangle playerRect = getPlayerRec();

		Iterator<Door> it = GameScreen.getInstance().levelData.getDoors().iterator();
		while (it.hasNext()) {
			Door rec = it.next();
			if (playerRect.overlaps(rec)) {
				position.set(rec.getTeleportPoint());
			}
		}
		GameScreen.getInstance().levelData.rectPool.free(playerRect);

		if ((axeCooldown + 200) < System.currentTimeMillis()) {

			// hit enemies
			Rectangle area = GameScreen.getInstance().levelData.rectPool.obtain();
			area.set(position.x + WIDTH, position.y, 1.5F, 1.5F);

			for (Entity e : GameScreen.getInstance().levelData.getEnemies()) {
				e.checkHit(area);
			}

			// destroy blocks
			TiledMap map = GameScreen.getInstance().getMap();

			if (facesRight) {
				int x2 = (int) position.x + 1;
				int y2 = (int) position.y;
				Level.getTileLayer(map, "Destructable").setCell(x2, y2, null);
				Level.getTileLayer(map, "Destructable").setCell(x2, y2 + 1, null);
				axeCooldown = System.currentTimeMillis();

			} else {
				int x2 = (int) position.x - 1;
				int y2 = (int) position.y;
				Level.getTileLayer(map, "Destructable").setCell(x2, y2, null);
				Level.getTileLayer(map, "Destructable").setCell(x2, y2 + 1, null);
				axeCooldown = System.currentTimeMillis();
			}

			axeCooldown = System.currentTimeMillis();
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

		// clamp velocities to max
		velocity.x = MathUtils.clamp(velocity.x, -MAX_VELOCITY, MAX_VELOCITY);
		velocity.y = MathUtils.clamp(velocity.y, -JUMP_VELOCITY, JUMP_VELOCITY);

		// velocity is < 1, set it to 0
		if (Math.abs(velocity.x) < 1) {
			velocity.x = 0;
			if (velocity.y == 0)
				state = State.Standing;
		}

		// apply gravity if player isn't standing or grounded
		if (!(state == State.Standing) || !grounded) {
			velocity.add(0, WoodyGame.GRAVITY);
			grounded = false;
		}

		if (!grounded && (velocity.y < 0)) {
			state = State.Falling;
		}

		// scale to frame velocity
		velocity.scl(delta);

		position.add(checkTileCollision());

		deleteNearbyCoinBlocks((int) position.x, (int) position.y);

		// unscale velocity
		velocity.scl(1 / delta);

		velocity.x *= DAMPING;

	}

	public void deleteNearbyCoinBlocks(int x2, int y2) {

		for (int i = x2 - 1; i <= x2 + 1; i++) {
			if (Level.getTileLayer(GameScreen.getInstance().getMap(), "Coins").getCell(i, y2) != null) {
				Level.getTileLayer(GameScreen.getInstance().getMap(), "Coins").setCell(i, y2, null);
				addCoin();
				// System.out.println(getCoinAmount());
			}
		}
	}

	/**
	 * Check collision in both axis and return the resulting velocity vector.
	 * 
	 * @param delta
	 *            frames per second
	 * @return the velocity of the player
	 */
	private Vector2 checkTileCollision() {
		// create the bounding box of the player
		Rectangle playerRect = getPlayerRec();

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
			priorTiles = GameScreen.getInstance().levelData.getTiles(priorTiles, startX, startY, endX, endY);
			for (Rectangle tile : priorTiles) {
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
			priorTiles = GameScreen.getInstance().levelData.getTiles(priorTiles, startX, startY, endX, endY);
			for (Rectangle tile : priorTiles) {
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
		GameScreen.getInstance().levelData.rectPool.free(playerRect);
		return velocity;
	}

	public Rectangle getPlayerRec() {
		Rectangle playerRect = GameScreen.getInstance().levelData.rectPool.obtain();
		playerRect.set(position.x, position.y, WIDTH - 0.1f, HEIGHT);
		return playerRect;
	}

	/**
	 * Render the player depending on state.
	 * 
	 */
	public void render() {
		Batch batch = GameScreen.getInstance().getRenderer().getBatch();
		Animations pAH = GameScreen.getInstance().getPlayerAnimationHandler();

		if (facesRight) {
			batch.draw(pAH.getPlayerFrame(this), position.x, position.y, WIDTH, HEIGHT);
		} else { // faces left
			batch.draw(pAH.getPlayerFrame(this), position.x + WIDTH, position.y, -WIDTH, HEIGHT);
		}
	}
}
