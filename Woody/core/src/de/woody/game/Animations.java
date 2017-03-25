package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Player.State;
import de.woody.game.screens.WoodyGame;

public class Animations {

	public State currentState;
	public State previousState;
	public Animation woodyRun;
	public TextureRegion woodyStand;
	public TextureRegion woodyJump;
	public TextureRegion woodyFall;
	public Texture sheetRun;

	public Animation woodyRuninv;
	public TextureRegion woodyStandinv;
	public TextureRegion woodyJumpinv;
	public TextureRegion woodyFallinv;

	public float stateTime = 0;

	private AssetManager asMa = WoodyGame.getGame().manager;

	public Animations() {
		sheetRun = asMa.get("textures/sheetRun.png", Texture.class);

		// RUN not damaged
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for (int i = 1; i < 5; i++)
			frames.add(new TextureRegion(sheetRun, i * 64, 0, 64, 94));
		// Not damaged
		woodyRun = new Animation(0.25f, frames);
		woodyJump = new TextureRegion(sheetRun, 5 * 64, 0, 64, 94);
		woodyFall = new TextureRegion(sheetRun, 6 * 64, 0, 64, 94);
		woodyStand = new TextureRegion(sheetRun, 0, 0, 64, 94);

		// damaged
		frames.clear();
		for (int j = 7; j < 11; j++)
			frames.add(new TextureRegion(sheetRun, j * 64, 0, 64, 94));
		woodyRuninv = new Animation(0.25f, frames);
		woodyJumpinv = new TextureRegion(sheetRun, 12 * 64, 0, 64, 94);
		woodyFallinv = new TextureRegion(sheetRun, 13 * 64, 0, 64, 94);
		woodyStandinv = new TextureRegion(sheetRun, 7 * 64, 0, 64, 94);
	}

	public TextureRegion getPlayerFrame(Player player) {

		currentState = player.state;
		TextureRegion region;
		if (!player.life.isInvulnerable()) {
			switch (currentState) {
			case Walking:
				region = woodyRun.getKeyFrame(stateTime);
				break;

			case Jumping:
				region = woodyJump;
				break;

			case Falling:
				region = woodyFall;
				break;

			default:
				region = woodyStand;
			}

			stateTime = currentState == previousState ? stateTime + Gdx.graphics.getDeltaTime() : 0;
			previousState = currentState;
			if (currentState == Player.State.Walking && (stateTime >= 1))
				stateTime = 0;
			return region;

		} else {
			switch (currentState) {
			case Walking:
				region = woodyRuninv.getKeyFrame(stateTime);
				break;

			case Jumping:
				region = woodyJumpinv;
				break;

			case Falling:
				region = woodyFallinv;
				break;

			default:
				region = woodyStandinv;
			}

			stateTime = currentState == previousState ? stateTime + Gdx.graphics.getDeltaTime() : 0;
			previousState = currentState;
			if (currentState == Player.State.Walking && (stateTime >= 1))
				stateTime = 0;
			return region;
		}

	}
}
