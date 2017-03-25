package de.woody.game.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import de.woody.game.Projectile;
import de.woody.game.screens.GameScreen;

public class Spitter extends Entity {

	private boolean cooldown = true;

	public Spitter(int hearts, Texture tex, int id, float x, float y, float width, float height) {
		super(hearts, tex, id, x, y, width, height);
	}

	@Override
	public void move(float delta) {
		if (delta > 0.1f)
			delta = 0.1f;

		float playerX = GameScreen.getInstance().getPlayer().position.x;
		if ((playerX + 4) > (getBody().getX() + getBody().getWidth()) || (playerX - 4) < getBody().getX()) {
			if (cooldown) {
				Projectile p = new Projectile(this, getBody().getX() + getBody().getWidth() / 2,
						getBody().getY() + getBody().getHeight() / 2, getProjectileWidth(), getProjectileHeight(),
						getProjectileVelocity(), getProjectileLifetime());
				getProjectiles().add(p);
				cooldown(2F);
			}
			for (Projectile p : getProjectiles()) {
				p.move(delta);
			}
		}
	}

	private void cooldown(float time) {
		cooldown = false;
		Timer.schedule(new Task() {
			@Override
			public void run() {
				cooldown = true;
			}
		}, time);
	}

	@Override
	public void handleHit() {
		// do nothing, they are invincible! mwhaha
	}

	public void projectileRemoved(Projectile proj) {
		System.out.println("remove proj");
		getProjectiles().removeValue(proj, false);
	}

	@Override
	public void render(Batch batch) {
		getBody().draw(batch);

		for (Projectile p : getProjectiles()) {
			p.render(batch);
		}
	}

}
