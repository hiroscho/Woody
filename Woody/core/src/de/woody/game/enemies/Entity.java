package de.woody.game.enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Lifesystem;
import de.woody.game.Player;
import de.woody.game.Projectile;
import de.woody.game.WoodyGame;
import de.woody.game.screens.GameScreen;

public abstract class Entity {
	private Array<Projectile> projectiles;
	private float projWidth, projHeight, projLifetime, projFrequency;
	private Vector2 projVelocity;
	private Texture projTexture;
	private AssetManager asMa = WoodyGame.getGame().manager;
	private Lifesystem life;
	private Sprite body;
	private Animation animation;
	private final int ID;
	private float stateTime = 0;

	public Entity(int hearts, Texture tex, int id, float x, float y, float width, float height) {
		life = new Lifesystem(hearts);
		body = new Sprite(tex);
		body.setBounds(x, y, width, height);
		ID = id;
		projectiles = new Array<Projectile>();
	}

	public Entity(int hearts, Animation tex, int id, float x, float y, float width, float height) {
		life = new Lifesystem(hearts);
		animation = tex;
		body = new Sprite(tex.getKeyFrame(stateTime));
		body.setBounds(x, y, width, height);
		ID = id;
		projectiles = new Array<Projectile>();
		projVelocity = new Vector2();
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

	public Texture getProjectileTexture() {
		return projTexture;
	}

	public Array<Projectile> getProjectiles() {
		return projectiles;
	}

	public float getProjectileFrequency() {
		return projFrequency;
	}

	public Animation getAnimation() {
		return animation;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setBody(Sprite sprite) {
		body = sprite;
	}

	public void changeRegion(TextureRegion texReg) {
		body.setRegion(texReg);
	}
	
	public void setStateTime(float time) {
		stateTime = time;
	}

	public void setProjectileProperties(float width, float height, Vector2 velocity, float lifetime, String texture,
			float frequency) {
		projWidth = width;
		projHeight = height;
		projVelocity = velocity;
		projLifetime = lifetime;
		projTexture = asMa.get(texture, Texture.class);
		projFrequency = frequency;
	}
	
	public void addProjectileVelocity(Vector2 velocity) {
		projVelocity.add(velocity);
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
