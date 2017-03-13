package de.woody.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.woody.game.MainMenueScreen;
import de.woody.game.WoodyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MainMenueScreen.WIDTH;
		config.height = MainMenueScreen.HEIGHT;
		config.resizable = true;
		new LwjglApplication(new WoodyGame(), config);
	}
}
