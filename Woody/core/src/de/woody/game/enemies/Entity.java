package de.woody.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Lifesystem;
import de.woody.game.Player;
import de.woody.game.Projectile;
import de.woody.game.screens.GameScreen;

public abstract class Entity {
	private Array<Projectile> projectiles;
	private float projWidth, projHeight, projLifetime;
	private Vector2 projVelocity;
	
	private Lifesystem life;
	private Sprite body;
	private final int ID;

	public Entity(int hearts, Texture tex, int id, float x, float y, float width, float height) {
		life = new Lifesystem(hearts);
		body = new Sprite(tex);
		body.setBounds(x, y, width, height);
		ID = id;
		projectiles = new Array<Projectile>();
	}

	public int getID() {
		return ID;
	}

	public Sprite getBody() {
		return body;
	}

	public int getHearts() {
		return life.getHearts();
	}
	
	public float getProjectileWidth() {
		return projWidth;
	}
	
	public float getProjectileHeight() {
		return projHeight;
	}
	
	public float getProjectileLifetime() {
		return projLifetime;
	}
	
	public Vector2 getProjectileVelocity() {
		return projVelocity;
	}
	
	public Array<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setBody(Sprite sprite) {
		body = sprite;
	}

	public void changeTexture(Texture tex) {
		body.setTexture(tex);
	}
	
	public void setProjectileProperties(float width, float height, Vector2 velocity, float lifetime) {
		projWidth = width;
		projHeight = height;
		projVelocity = velocity;
		projLifetime = lifetime;
	}

	public int damageEntity(int dmg) {
		return life.damageEntity(dmg);
	}

	public boolean checkCollision(Player player) {
		Rectangle playerRect = player.getPlayerRec();
		if (playerRect.overlaps(body.getBoundingRectangle())) {
			GameScreen.getInstance().levelData.rectPool.free(playerRect);
			return true;
		}
		GameScreen.getInstance().levelData.rectPool.free(playerRect);
		return false;
	}

	public void checkHit(Rectangle rect) {
		if (rect.overlaps(body.getBoundingRectangle())) {
			handleHit();
		}
		GameScreen.getInstance().levelData.rectPool.free(rect);
		return;
	}

	public abstract void render(Batch batch);

	public abstract void move(float delta);
	
	public abstract void handleHit();
}
