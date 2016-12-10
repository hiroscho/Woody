package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Buttons extends WoodyGame{
	
    Stage stage;
    Button button;
//    BitmapFont font;
    Skin skinLeftButton;
//    TextureAtlas buttonAtlas;

    @Override
    public void create() {      
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skinLeftButton = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton("Button1", textButtonStyle);
        stage.addActor(button);
    }
	
	
	
	
	
	
	
}
