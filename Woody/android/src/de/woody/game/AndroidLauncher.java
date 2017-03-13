package de.woody.game;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.woody.game.WoodyGame;

public class AndroidLauncher extends AndroidApplication {
	private Viewport viewport;
	private Camera camera;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		camera = new PerspectiveCamera();
		viewport = new FitViewport(800,480,camera);
		Gdx.graphics.setResizable(true);
		initialize(new WoodyGame(), config);
	}
}
