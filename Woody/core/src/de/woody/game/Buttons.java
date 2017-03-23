package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Buttons extends WoodyGame {
	private Stage stage;
	private TextureRegion buttonTextureRegion;
	private TextureRegionDrawable buttonTexRegionDrawable;
	private ImageButton button;
	private Viewport viewport;
	private Array<Button> allButtons = new Array<Button>();
	public Image image;
	private Array<Image> heartsImages = new Array<Image>();
	private Array<Image> lifeImages = new Array<Image>();

	public Buttons() {
		// create a new viewport for the ui
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

		// set up a stage for the ui
		stage = new Stage(viewport);
	}

	public Button addButton(Texture tex, String name) {
		return addButton(tex, name, 0, 0, 90, 90);
	}

	public Button addButton(Texture tex, String name, float xPos, float yPos) {
		return addButton(tex, name, xPos, yPos, 90, 90);
	}

	public Button addButton(Texture tex, String name, float xPos, float yPos, float xSize, float ySize) {
		// making an annoying drawable...
		buttonTextureRegion = new TextureRegion(tex);
		buttonTexRegionDrawable = new TextureRegionDrawable(buttonTextureRegion);

		// set the button up
		button = new ImageButton(buttonTexRegionDrawable);
		button.setName(name);
		button.setSize(xSize, ySize);
		button.setPosition(xPos, yPos);

		// add the button to the list of all buttons
		allButtons.add(button);

		// add the button to the stage to perform rendering and take input
		stage.addActor(button);

		return button;
	}

	public Image addHeartsImage(TextureRegion tex, int amount, float xPos, float yPos, float xSize, float ySize,
			float scale) {
		image = new Image(tex);
		image.setName(amount + "");
		image.setPosition(xPos, yPos);
		image.setSize(xSize, ySize);
		image.setScale(scale);
		heartsImages.add(image);
		stage.addActor(image);
		return image;
	}

	public Image addLifeImage(TextureRegion tex, int amount, float xPos, float yPos, float xSize, float ySize,
			float scale) {
		image = new Image(tex);
		image.setName(amount + "");
		image.setPosition(xPos, yPos);
		image.setSize(xSize, ySize);
		image.setScale(scale);
		lifeImages.add(image);
		stage.addActor(image);
		return image;
	}

	public Stage getStage() {
		return stage;
	}

	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public Array<Button> checkAllButtons() {
		Array<Button> pressedButtons = new Array<Button>();
		for (Button but : allButtons) {
			if (but.isPressed())
				pressedButtons.add(but);
		}
		return pressedButtons;
	}

	public void checkCorrectHeartsImage(Player player) {
		// magic number 3...maxHearts may be interesting
		for (int i = 0; i <= 3; i++) {
			if (player.life.getHearts() == i) {
				for (Image img : heartsImages) {
					if (!img.getName().equals(i + "")) {
						img.setVisible(false);
					} else {
						img.setVisible(true);
					}
				}
			}
		}
	}

	public void checkCorrectLifeImage(Player player) {
		// magic number 2...maxLifes may be interesting
		for (int i = 0; i <= 2; i++) {
			if (player.life.getLife() == i) {
				for (Image img : lifeImages) {
					if (!img.getName().equals(i + "")) {
						img.setVisible(false);
					} else {
						img.setVisible(true);
					}
				}
			}
		}
	}
}