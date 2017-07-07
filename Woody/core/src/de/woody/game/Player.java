package de.woody.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.woody.game.enemies.Entity;
import de.woody.game.screens.GameScreen;

public class Player {
	/** width of the player texture scaled to world coordinates **/
	public static float WIDTH;
	/** height of the player texture scaled to world coordinates **/
	public static float HEIGHT;

	/** maximum velocity's (maybe add a maximum fall velocity?) **/
	public static float MAX_VELOCITY;
	public static float JUMP_VELOCITY;
	public float jumpMultiplier = 1.25f;
	public float speedMultiplier = 1.5f;

	// Deceleration after key release
	public static float DAMPING = 0.87f;

	public enum State {
		Standing, Walking, Jumping, Attacking, Falling, Climbing, Swimming
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
	public boolean attacking = false;
	public boolean speedActivated = false;
	public boolean jumpActivated = false;

	/** current coin score **/
	private int coinAmount = 0;

	/** Cooldown for fightSound **/
	public long fightCooldown = System.currentTimeMillis();

	/** Cooldown for the jumpSound **/
	public long jumpSoundCooldown = System.currentTimeMillis();

	/** Jump Counter for sound **/
	public int jumpCounter = 0;

	/** is player touching the ground **/
	public boolean grounded = false;

	public boolean hasAxe = true;

	/** is player facing right **/
	public boolean facesRight = true;

	/** is player sliding to the left **/
	public boolean slidingLeft = false;

	/** is player sliding to the right **/
	public boolean slidingRight = false;

	/** Life of the player **/
	public Lifesystem life;

	/** Required for freeing the rectangles **/
	private Array<Rectangle> priorTiles;

	private Vector2 savedPosition = new Vector2();

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
		MAX_VELOCITY = 10F;
		JUMP_VELOCITY = 15F;
	}

	public int getCoinAmount() {
		return coinAmount;
	}

	private void addCoin() {
		coinAmount++;
		GameScreen.getInstance().getCoinSound().play(WoodyGame.getGame().VOLUME);
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
		if (button.getName().equals("Jump") && !(state == State.Climbing)) {
			handleJump();
		}

		if (button.getName().equals("Right") && !slidingLeft) {
			handleRight();
		}

		if (button.getName().equals("Left") && !slidingRight) {
			handleLeft();
		}

		if (button.getName().equals("Fight")) {

			handleFight();
		}

	}

	/**
	 * Intended for testing! Keyboard input only! Sets the velocity depending on
	 * input.
	 */
	public void setKeyboardVelocity() {
		if ((Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
				&& !(state == State.Climbing)) {
			handleJump();
		}

		if ((Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) && !slidingLeft) {
			handleRight();
		}

		if ((Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) && !slidingRight) {
			handleLeft();
		}

		if (Gdx.input.isKeyPressed(Keys.ENTER) && grounded) {
			handleFight();
		}
	}

	private void handleJump() {
		Cell lowerCell = GameScreen.getInstance().levelData.getNonCollidable().getCell((int) (position.x + WIDTH / 2),
				(int) position.y);
		String lower = "";
		if (lowerCell != null && lowerCell.getTile() != null) {
			lower = Level.getTileName(lowerCell.getTile().getId());
		}
		if ((grounded || state == State.Swimming) && !attacking) {

			if (!(lower.equals("water") || lower.equals("lava"))) {
				GameScreen.getInstance().getJumpSound().play(WoodyGame.getGame().VOLUME);
			}
			velocity.y = JUMP_VELOCITY;
			state = State.Jumping;
			grounded = false;
			freeJump = true;
		} else {
			if (freeJump && velocity.y < 1) {
				if (!(lower.equals("water") || lower.equals("lava"))) {
					GameScreen.getInstance().getJumpSound().play(WoodyGame.getGame().VOLUME);
				}
				velocity.y = JUMP_VELOCITY;
				state = State.Jumping;
				grounded = false;
				freeJump = false;
			}
		}
	}

	private void handleRight() {
		if (!attacking) {
			velocity.x = MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = true;
		}
	}

	private void handleLeft() {
		if (!attacking) {
			velocity.x = -MAX_VELOCITY;
			if (grounded)
				state = State.Walking;
			facesRight = false;
		}
	}

	private void handleFight() {
		// use doors
		Rectangle playerRect = getPlayerRec();

		Iterator<Door> it = GameScreen.getInstance().levelData.getDoors().iterator();
		while (it.hasNext()) {
			Door rec = it.next();
			if (playerRect.overlaps(rec)) {
				position.set(rec.getTeleportPoint());
				GameScreen.getInstance().getDoorSound().play(WoodyGame.getGame().VOLUME);
			}
		}
		GameScreen.getInstance().levelData.rectPool.free(playerRect);

		if ((fightCooldown + 700) < System.currentTimeMillis()) {
			attacking = true;

			Timer.schedule(new Task() {
				@Override
				public void run() {
					attacking = false;
				}
			}, 0.6F);

			GameScreen.getInstance().getPunchSound().play(WoodyGame.getGame().VOLUME);
			
			// hit enemies
			Rectangle area = GameScreen.getInstance().levelData.rectPool.obtain();
			Rectangle area2 = GameScreen.getInstance().levelData.rectPool.obtain();
			area.set(position.x + WIDTH, position.y, 1.5F, 1.5F);
			area2.set(position.x - 1.5f, position.y, 1.5F, 1.5F);

			for (Entity e : GameScreen.getInstance().levelData.getEnemies()) {
				e.checkHit(area);
				e.checkHit(area2);
			}

			// destroy blocks
			int x2;
			if (facesRight) {
				x2 = (int) position.x + 1;
			} else {
				x2 = (int) position.x - 1;
			}
			int y2 = (int) position.y;
			Cell cell;
			for (int i = 0; i <= 1; i++) {
				cell = GameScreen.getInstance().levelData.getCollidable().getCell(x2, y2 + i);
				if (cell != null && cell.getTile() != null) {
					if (Level.getTileName(cell.getTile().getId()).equals("destructable")) {
						GameScreen.getInstance().levelData.getCollidable().setCell(x2, y2 + i, null);
					}
				}
			}

			fightCooldown = System.currentTimeMillis();
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
		if (Math.abs(velocity.x) < 1 && !attacking) {
			velocity.x = 0;
			if (velocity.y == 0 && !attacking)
				state = State.Standing;
		}

		// apply gravity if player isn't standing or grounded or climbing or
		// swimming or attacking or...
		if (!(state == State.Standing)
				|| !grounded && !(state == State.Climbing) && !(state == State.Swimming) && !attacking) {
			velocity.add(0, WoodyGame.getGame().GRAVITY);
			grounded = false;
		}

		if ((!grounded && (velocity.y < 0)) && !(state == State.Climbing) && !(state == State.Swimming) && !attacking) {
			state = State.Falling;
		}

		if (attacking)
			state = State.Attacking;

		// scale to frame velocity
		velocity.scl(delta);

		position.add(checkTileCollision());

		deleteNearbyCoinBlocks();

		// unscale velocity
		velocity.scl(1 / delta);

		velocity.x *= DAMPING;

		GameScreen.getInstance().getSnowSlideSound().stop();
	}

	public void deleteNearbyCoinBlocks() {
		int x, y;
		Cell cell;
		for (int i = 0; i <= 1; i++) {
			x = (int) (position.x + WIDTH * i);
			for (float j = 0; j <= 1; j += 0.5) {
				y = (int) (position.y + HEIGHT * j);
				cell = GameScreen.getInstance().getNonCollidableTiles().getCell(x, y);
				if (cell != null && cell.getTile() != null) {
					if (Level.getTileName(cell.getTile().getId()).equals("coin")) {
						GameScreen.getInstance().getNonCollidableTiles().setCell(x, y, null);
						addCoin();
					} else if (Level.getTileName(cell.getTile().getId()).equals("speedBoots")) {
						GameScreen.getInstance().getNonCollidableTiles().setCell(x, y, null);
						MAX_VELOCITY = MAX_VELOCITY *speedMultiplier;
						speedActivated = true;
						Timer.schedule(new Task() {
							@Override
							public void run() {
								speedActivated = false;
							}
						}, 5F);
					} else if (Level.getTileName(cell.getTile().getId()).equals("jumpBoots")) {
						GameScreen.getInstance().getNonCollidableTiles().setCell(x, y, null);
						JUMP_VELOCITY = JUMP_VELOCITY *jumpMultiplier;
						jumpActivated = true;
						Timer.schedule(new Task() {
							@Override
							public void run() {
								jumpActivated = false;
							}
						}, 5F);
					}
				}
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
						if (velocity.x != 0 && !attacking)
							state = State.Walking;
						else if (attacking)
							state = State.Attacking;
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

	public void checkBlocks() {
		int x = (int) (position.x + WIDTH / 2);
		int y = (int) (position.y);
		if (speedActivated && jumpActivated)
			resetParametersBoth();
		else if (speedActivated)
			resetParametersSpeed();
		else if (jumpActivated)
			resetParametersJump();
		else
			resetParameters();
		applyLowerCollidableEffect(x, y);
		// higher priority thus later
		applyNonCollidableEffect(x, y);
	}

	private void resetParameters() {
		MAX_VELOCITY = 10F;
		JUMP_VELOCITY = 15F;
		DAMPING = 0.87F;
		slidingRight = false;
		slidingLeft = false;
	}

	private void resetParametersSpeed() {
		MAX_VELOCITY = 10F * speedMultiplier;
		JUMP_VELOCITY = 15F;
		DAMPING = 0.87F;
		slidingRight = false;
		slidingLeft = false;
	}

	private void resetParametersJump() {
		MAX_VELOCITY = 10F;
		JUMP_VELOCITY = 15F * jumpMultiplier;
		DAMPING = 0.87F;
		slidingRight = false;
		slidingLeft = false;
	}

	private void resetParametersBoth() {
		MAX_VELOCITY = 10F * speedMultiplier;
		JUMP_VELOCITY = 15F * jumpMultiplier;
		DAMPING = 0.87F;
		slidingRight = false;
		slidingLeft = false;
	}

	private void applyNonCollidableEffect(int x, int y) {
		Cell lowerCell = GameScreen.getInstance().levelData.getNonCollidable().getCell(x, y);

		String lower = "";
		if (lowerCell != null && lowerCell.getTile() != null) {
			lower = Level.getTileName(lowerCell.getTile().getId());
		}
		// Foliage
		if (lower.equals("bush")) {
			MAX_VELOCITY = MAX_VELOCITY / 2;
			return;
		}
		boolean pressedFlag = false;
		for (Button but : GameScreen.getInstance().getPressedButtons()) {
			if (but.getName().equals("Jump")) {
				pressedFlag = true;
			}
		}

		// Ladder
		if (lower.equals("ladder")) {
			if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)
					|| pressedFlag) {
				velocity.y = 5f;
			} else {
				velocity.y = -2.5f;
			}
			MAX_VELOCITY = MAX_VELOCITY / 3;
			state = State.Climbing;
			return;
		}

		if (lower.equals("lifeBlock")) {
			life.setHearts(3);
			lowerCell.setTile(null);
			return;
		}

		Cell upperCell = GameScreen.getInstance().levelData.getNonCollidable().getCell(x, y + 1);
		String upper = "";
		if (upperCell != null && upperCell.getTile() != null) {
			upper = Level.getTileName(upperCell.getTile().getId());
		}

		// Lava
		if (lower.equals("lava")) {
			if (upper.equals("lava")) {
				if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)
						|| Gdx.input.isKeyPressed(Keys.W) || pressedFlag) {
					velocity.y = 3F;
				} else {
					velocity.y = -0.5F;
				}
			} else {
				if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)
						|| Gdx.input.isKeyPressed(Keys.W) || pressedFlag) {
					handleJump();
				} else {
					velocity.y = -0.5F;
				}
			}
			life.damagePlayer(1);
			MAX_VELOCITY = 1f;
			state = State.Swimming;
			return;
		}

		// Water
		if (lower.equals("water")) {
			if (upper.equals("water")) {
				if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)
						|| Gdx.input.isKeyPressed(Keys.W) || pressedFlag) {
					velocity.y = 5F;
				} else {
					velocity.y = -2.5F;
				}

			} else {
				if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.isKeyPressed(Keys.UP)
						|| Gdx.input.isKeyPressed(Keys.W) || pressedFlag) {
					handleJump();
				} else {
					velocity.y = -2.5F;
				}
			}
			MAX_VELOCITY = 3f;
			state = State.Swimming;
			return;
		}

	}

	private void applyLowerCollidableEffect(int x, int y) {

		Cell lowerCell = GameScreen.getInstance().levelData.getCollidable().getCell(x, y - 1);

		String lower = "";
		if (lowerCell != null && lowerCell.getTile() != null) {
			lower = Level.getTileName(lowerCell.getTile().getId());
		}

		// Damaging
		if (lower.equals("iceSpikes")) {
			life.damagePlayer(1);
			return;
		}

		// Slime
		if (lower.equals("slimeLayer")) {
			MAX_VELOCITY = 1.5f;
			return;
		}

		// Ice
		if (lower.equals("iceLayer")) {
			GameScreen.getInstance().getSnowSlideSound().play(WoodyGame.getGame().VOLUME);
			if (velocity.x > 0) {
				slidingRight = true;
			}
			if (velocity.x < 0) {
				slidingLeft = true;
			}
			if (velocity.x == 0) {
				slidingLeft = false;
				slidingRight = false;
			}
			DAMPING = 0.975f;
			MAX_VELOCITY = MAX_VELOCITY / 2;
			return;
		}

		if (lowerCell == null) {
			Cell otherCell = new Cell();
			if (x + 1 == (int) (position.x + WIDTH)) {
				// otherCell is right Cell
				otherCell = GameScreen.getInstance().levelData.getCollidable().getCell(x + 1, y - 1);
			}
			if (x - 1 == (int) position.x) {
				// otherCell is left Cell
				otherCell = GameScreen.getInstance().levelData.getCollidable().getCell(x - 1, y - 1);
			}
			if (otherCell != null && otherCell.getTile() != null) {
				lower = Level.getTileName(otherCell.getTile().getId());
			}
		}

		// Vanishing
		if (lower.equals("vanishing")) {
			if (savedPosition.equals(new Vector2((int) position.x, (int) position.y))) {
				return;
			}
			savedPosition = new Vector2((int) position.x, (int) position.y);
			vanish(y);
		}
	}

	private void vanish(final int y) {
		final float x2 = position.x;

		Timer.schedule(new Task() {
			@Override
			public void run() {
				Cell cell;
				for (int i = 0; i <= 1; i++) {
					cell = GameScreen.getInstance().levelData.getCollidable().getCell((int) (x2 + WIDTH * i), y - 1);
					if (cell != null && cell.getTile() != null) {
						if (Level.getTileName(cell.getTile().getId()).equals("vanishing")) {
							GameScreen.getInstance().levelData.getCollidable().setCell((int) (x2 + WIDTH * i), y - 1,
									null);
						}
					}
				}
				if (velocity.y == 0) {
					velocity.add(0, WoodyGame.getGame().GRAVITY);
					state = State.Falling;
				}
			}

		}, 0.5F);
	}

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