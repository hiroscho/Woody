package de.woody.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import de.woody.game.WoodyGame;
import de.woody.game.screens.GameScreen;

public class Walker extends Entity {

	private int restrX1, restrX2;
	private boolean walkLeft = false;
	private Vector2 velocity;

	public Walker(int hearts, Texture tex, int id, int x1, int x2, float x, float y, float width, float height) {
		super(hearts, tex, id, x, y, width, height);
		setRestriction(x1, x2);

		velocity = new Vector2(2.5F, 0);
	}

	public int getLeftRestriction() {
		return restrX1 - 1;
	}

	public int getRightRestriction() {
		return restrX2;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * 
	 * @param x1
	 *            left side
	 * @param x2
	 *            right side
	 */
	public void setRestriction(int x1, int x2) {
		restrX1 = (int) getBody().getX() - x1;
		restrX2 = (int) getBody().getX() + 1 + x2;
	}

	@Override
	public void move(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;
		System.out.println(getBody().getX());
		float leftEnd = GameScreen.getInstance().cameraBottomLeft().x;
		if ((restrX1 > leftEnd && restrX1 < leftEnd + WoodyGame.xTiles)
				|| (restrX2 > leftEnd && restrX2 < leftEnd + WoodyGame.xTiles)) {

			float moveAmount = velocity.scl(delta).x;

			if (walkLeft) {
				if (getBody().getX() > getLeftRestriction()) {
					getBody().setPosition(getBody().getX() - moveAmount, getBody().getY());
				} else {
					walkLeft = false;
					getBody().flip(true, false);
					getBody().setPosition(getBody().getX() + moveAmount, getBody().getY());
				}
			} else {
				if (getBody().getX() + getBody().getWidth() < getRightRestriction()) {
					getBody().setPosition(getBody().getX() + moveAmount, getBody().getY());
				} else {
					walkLeft = true;
					getBody().flip(true, false);
					getBody().setPosition(getBody().getX() - moveAmount, getBody().getY());
				}
			}
			velocity.scl(1 / delta);
		}
	}

	@Override
	public void handleHit() {
		damageEntity(1);
		if (getHearts() < 1) {
			GameScreen.getInstance().levelData.getEnemies().removeValue(this, false);
		}
	}

	@Override
	public void render(Batch batch) {
		getBody().draw(batch);
	}
}
