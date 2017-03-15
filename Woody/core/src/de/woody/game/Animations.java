package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

import de.woody.game.Player.State;

public class Animations extends Lifesystem {

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

	public Texture sheetLife;
	public TextureRegion heartsZero;
	public TextureRegion heartsOne;
	public TextureRegion heartsTwo;
	public TextureRegion heartsThree;
	public TextureRegion textureRegionPlaceholder;
	public Image imageHearts;

	public Texture sheetLives;
	public TextureRegion livesZero;
	public TextureRegion livesOne;
	public TextureRegion livesTwo;

	public float stateTime;

	public Animations() {
		stateTime = 0;
		sheetRun = new Texture(Gdx.files.internal("textures/loadingsheet.png"));
		currentState = Player.state;
		previousState = Player.state;
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

		// From here on down will be all Textures used for the Lifesystem UI and
		// be possibly replaced to the standart UI class once there is one
		// (start already Zamy!!)

		sheetLife = new Texture(Gdx.files.internal("textures/sheetLife.png"));
		heartsZero = new TextureRegion(sheetLife, 0, 0, 52, 16);
		heartsOne = new TextureRegion(sheetLife, 52, 0, 52, 16);
		heartsTwo = new TextureRegion(sheetLife, 2 * 52, 0, 52, 16);
		heartsThree = new TextureRegion(sheetLife, 3 * 52, 0, 52, 16);

		sheetLives = new Texture(Gdx.files.internal("textures/sheetLives.png"));
		livesZero = new TextureRegion(sheetLives, 0, 0, 18, 18);
		livesOne = new TextureRegion(sheetLives, 18, 0, 18, 18);
		livesTwo = new TextureRegion(sheetLives, 36, 0, 18, 18);
	}

	public TextureRegion getFrame() {

		currentState = Player.state;
		TextureRegion region;
		if (!Lifesystem.invulnerable) {
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
			if (currentState == Player.state.Walking && (stateTime >= 1))
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
			if (currentState == Player.state.Walking && (stateTime >= 1))
				stateTime = 0;
			return region;
		}
	}

	public void dispose() {
		sheetRun.dispose();
		sheetLife.dispose();
		sheetLives.dispose();
	}
}
