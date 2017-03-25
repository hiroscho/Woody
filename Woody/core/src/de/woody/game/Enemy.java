package de.woody.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
	public Lifesystem life;
	private Sprite body;
	private final int ID;
	private int restrX1, restrX2;
	private boolean walkLeft = false;

	public Enemy(int hearts, Texture tex, int id, int x1, int x2, float x, float y, float width, float height) {
		life = new Lifesystem(hearts);
		body = new Sprite(tex);
		ID = id;
		body.setBounds(x, y, width, height);
		setRestriction(x1, x2);
	}

	public int getID() {
		return ID;
	}

	public Sprite getBody() {
		return body;
	}

	public int getLeftRestriction() {
		return restrX1 - 1;
	}

	public int getRightRestriction() {
		return restrX2;
	}

	public void setBody(Sprite sprite) {
		body = sprite;
	}

	public void changeTexture(Texture tex) {
		body.setTexture(tex);
	}

	/**
	 * 
	 * @param x1
	 *            left side
	 * @param x2
	 *            right side
	 */
	public void setRestriction(int x1, int x2) {
		restrX1 = (int) body.getX() - x1;
		restrX2 = (int) body.getX() + 1 + x2;
	}

	public void move() {
		if (walkLeft) {
			if (body.getX() > getLeftRestriction()) {
				body.setPosition(body.getX() - 0.05F, body.getY());
			} else {
				walkLeft = false;
				body.flip(true, false);
				body.setPosition(body.getX() + 0.05F, body.getY());
			}
		} else {
			if (body.getX() + body.getWidth() < getRightRestriction()) {
				body.setPosition(body.getX() + 0.05F, body.getY());
			} else {
				walkLeft = true;
				body.flip(true, false);
				body.setPosition(body.getX() - 0.05F, body.getY());
			}
		}
	}

	public boolean checkCollision(Player player) {
		Rectangle playerRect = GameScreen.getInstance().levelData.rectPool.obtain();
		playerRect.set(player.position.x, player.position.y, Player.WIDTH - 0.1f, Player.HEIGHT);
		if(playerRect.overlaps(body.getBoundingRectangle())) {
			GameScreen.getInstance().levelData.rectPool.free(playerRect);
			return true;
		}
		GameScreen.getInstance().levelData.rectPool.free(playerRect);
		return false;
	}

	public void render(Batch batch) {
		body.draw(batch);
	}

	public boolean checkHit(Rectangle rect) {
		if (rect.overlaps(body.getBoundingRectangle())) {
			GameScreen.getInstance().levelData.rectPool.free(rect);
			return true;
		}
		GameScreen.getInstance().levelData.rectPool.free(rect);
		return false;
	}
}
