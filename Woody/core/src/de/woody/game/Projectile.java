package de.woody.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.woody.game.enemies.Entity;
import de.woody.game.screens.GameScreen;
import de.woody.game.screens.WoodyGame;

public class Projectile {
	private static int projIdGen = 0;

	private Vector2 velocity;
	private Sprite body;
	private Entity owner;
	private final int id;
	private Array<Rectangle> priorTiles;

	public Projectile(Entity owner, Texture tex, float x, float y, float width, float height, Vector2 velocity, float lifetime) {
		this.owner = owner;
		body = new Sprite(tex);
		body.setBounds(x, y, width, height);
		this.velocity = velocity;
		id = projIdGen++;
		setTimedRemoval(lifetime);
	}
	
	public int getId(){
		return id;
	}

	public Sprite getProjectile() {
		return body;
	}

	public void changeTexture(Texture tex) {
		body.setTexture(tex);
	}

	public void render(Batch batch) {
			body.draw(batch);
	}

	public void move(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;

		velocity.scl(delta);
		
		body.setPosition(body.getX() + velocity.x, body.getY() + velocity.y);
		handlePlayerCollision(GameScreen.getInstance().getPlayer());
		handleTileCollision();

		velocity.scl(1 / delta);
	}

	private void handlePlayerCollision(Player player) {
		Rectangle playerRect = player.getPlayerRec();
		if (playerRect.overlaps(body.getBoundingRectangle())) {
			player.life.damagePlayer(1);
			owner.getProjectiles().removeValue(this, false);
		}
		GameScreen.getInstance().levelData.rectPool.free(playerRect);
	}

	private void handleTileCollision() {
		int startX, startY, endX, endY;
		if (velocity.x != 0) {
			if (velocity.x > 0) {
				startX = endX = (int) (body.getX() + body.getWidth() - 0.1F);
			} else {
				startX = endX = (int) (body.getX() + 0.1F);
			}

			startY = (int) (body.getY() + 0.1F);
			endY = (int) (body.getY() + body.getHeight() - 0.1F);
			
			priorTiles = GameScreen.getInstance().levelData.getTiles(priorTiles, startX, startY, endX, endY);

			for (Rectangle tile : priorTiles) {
				if (body.getBoundingRectangle().overlaps(tile)) {
					owner.getProjectiles().removeValue(this, false);
					return;
				}
			}
		}

		if (velocity.y != 0) {
			if (velocity.y > 0) {
				startY = endY = (int) (body.getY() + body.getHeight() - 0.1F);
			} else {
				startY = endY = (int) (body.getY() + 0.1F);
			}

			startX = (int) (body.getX() + 0.1F);
			endX = (int) (body.getX() + body.getWidth() - 0.1F);

			if (startY > WoodyGame.yTiles && startY < 0) {
				owner.getProjectiles().removeValue(this, false);
				return;
			}

			priorTiles = GameScreen.getInstance().levelData.getTiles(priorTiles, startX, startY, endX, endY);
			
			for (Rectangle tile : priorTiles) {
				if (body.getBoundingRectangle().overlaps(tile)) {
					owner.getProjectiles().removeValue(this, false);
					return;
				}
			}
		}
	}
	
	private void setTimedRemoval(float delay) {
		Timer.schedule(new Task() {
			@Override
			public void run() {
				for(Projectile p : owner.getProjectiles()) {
					if(p.getId() == id) {
						owner.getProjectiles().removeValue(p, false);
					}
				}
			}
		}, delay);
	}
}
