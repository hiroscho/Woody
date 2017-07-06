package de.woody.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import de.woody.game.Swipe;
import de.woody.game.WoodyGame;
import de.woody.game.screens.GameScreen;

public class LevelSelect implements Screen{
	
	private static final LevelSelect levelSelect = new LevelSelect();
	private AssetManager asMa = WoodyGame.getGame().manager;
	private Batch batch;
	public float xfirst, xsecond, xthir, xfour, xfive, xsix, xseven, xeight, xnine, xten, xeleven, xtwelve;
	public float xthirteen, xfourteen, xfiveteen;
	
	//Level Stages
	public static int check = 1;
	
	
	private Texture one;
	private Texture two;
	private Texture three;
	private Texture four;
	private Texture five;
	private Texture six;
	private Texture seven;
	private Texture eight;
	private Texture nine;
	private Texture ten;
	private Texture eleven;
	private Texture twelve;
	private Texture thirteen;
	private Texture fourteen;
	private Texture fiveteen;

	private Texture background;

	private Texture menuun;
	private Texture menuak;
	
	//Texturesizes
	private final int LEVELB_WIDTH = Gdx.graphics.getWidth() / 10;
	private final int LEVELB_HEIGHT = Gdx.graphics.getHeight() /10;
	private final int MENU_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int MENU_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
	private LevelSelect(){	}
	
	public static LevelSelect getInstance(){
		levelSelect.batch = new SpriteBatch();
		return levelSelect;
	}
	
	//Screen Methods
	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GestureDetector(new Swipe()));
		
		//Levelnumbers
		asMa.load("textures/eins.png", Texture.class);
		asMa.load("textures/zwei.png", Texture.class);
		asMa.load("textures/drei.png", Texture.class);
		asMa.load("textures/vier.png", Texture.class);
		asMa.load("textures/fuenf.png", Texture.class);
		asMa.load("textures/sechs.png", Texture.class);
		asMa.load("textures/sieben.png", Texture.class);
		asMa.load("textures/acht.png", Texture.class);
		asMa.load("textures/neun.png", Texture.class);
		asMa.load("textures/zehn.png", Texture.class);
		asMa.load("textures/elf.png", Texture.class);
		asMa.load("textures/zwoelf.png", Texture.class);
		asMa.load("textures/dreizehn.png", Texture.class);
		asMa.load("textures/vierzehn.png", Texture.class);
		asMa.load("textures/fuenfzehn.png", Texture.class);
		
		//background
		asMa.load("textures/LevelSelectBackground.png", Texture.class);
		
		//Menubutton
		asMa.load("textures/Menu_un.png", Texture.class);
		asMa.load("textures/Menu_ak.png", Texture.class);
		
		
		while(!asMa.update()) {
			asMa.update();
		}

		one = asMa.get("textures/eins.png", Texture.class);
		two = asMa.get("textures/zwei.png", Texture.class);
		three = asMa.get("textures/drei.png", Texture.class);
		four = asMa.get("textures/vier.png", Texture.class);
		five = asMa.get("textures/fuenf.png", Texture.class);
		six = asMa.get("textures/sechs.png", Texture.class);
		seven = asMa.get("textures/sieben.png", Texture.class);
		eight = asMa.get("textures/acht.png", Texture.class);
		nine = asMa.get("textures/neun.png", Texture.class);
		ten = asMa.get("textures/zehn.png", Texture.class);
		eleven = asMa.get("textures/elf.png", Texture.class);
		twelve = asMa.get("textures/zwoelf.png", Texture.class);
		thirteen = asMa.get("textures/dreizehn.png", Texture.class);
		fourteen = asMa.get("textures/vierzehn.png", Texture.class);
		fiveteen = asMa.get("textures/fuenfzehn.png", Texture.class);
		
		background = asMa.get("textures/LevelSelectBackground.png", Texture.class);
		
		menuun = asMa.get("textures/Menu_un.png", Texture.class);
		menuak = asMa.get("textures/Menu_ak.png", Texture.class);

	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0.7f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//background
		batch.draw(background, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		//Menubutton
		int a = Gdx.graphics.getWidth() / 2 - MENU_WIDTH / 2;
		int b = (Gdx.graphics.getWidth() / 2 - MENU_WIDTH / 2);
		int c = Gdx.graphics.getHeight() / 100 - MENU_HEIGHT / 8;
		
		if (Gdx.input.getX() < a + MENU_WIDTH && Gdx.input.getX() > a && Gdx.graphics.getHeight() - Gdx.input.getY() < c + MENU_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > c) {
			batch.draw(menuak, b, c, MENU_WIDTH, MENU_HEIGHT);
			if (Gdx.input.justTouched()) {
				WoodyGame.getGame().setScreen(MainMenueScreen.getInstance());
				return;
			}
		} else {
			batch.draw(menuun, b, c, MENU_WIDTH, MENU_HEIGHT);
		}
		
		//LEVEL-SELECT
		//x Cordinates
		//1-5
		xfirst = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.40f);
		xsecond = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.70f);
		xthir = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.00f);
		xfour = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.30f);
		xfive = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.60f);
		//6-10
		xsix = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.40f);	//2*
		xseven = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.70f);
		xeight = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.00f);
		xnine = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.30f);
		xten = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.60f); 
		//11-15
		xeleven = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.40f);	//3*
		xtwelve = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 0.70f);
		xthirteen = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.00f);
		xfourteen = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.30f);
		xfiveteen = ((Gdx.graphics.getWidth() / 2 - LEVELB_WIDTH / 2) * 1.60f);
		
		int y = Gdx.graphics.getHeight() / 2 + LEVELB_HEIGHT / 4;

	if(check == 1){
		//ONE
		if(Gdx.input.getX() < xfirst + LEVELB_WIDTH && Gdx.input.getX() > xfirst && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(one, xfirst , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(1));
				return;
			}
		}else{
			batch.draw(one, xfirst, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
	
		//TWO
		if(Gdx.input.getX() < xsecond + LEVELB_WIDTH && Gdx.input.getX() > xsecond && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(two, xsecond , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(2));
				return;
			}
		}else{
			batch.draw(two, xsecond, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//THREE
		if(Gdx.input.getX() < xthir + LEVELB_WIDTH && Gdx.input.getX() > xthir && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(three, xthir , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(3));
				return;
			}
		}else{
			batch.draw(three, xthir, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//FOUR
		if(Gdx.input.getX() < xfour + LEVELB_WIDTH && Gdx.input.getX() > xfour && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(four, xfour , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(4));
				return;
			}
		}else{
			batch.draw(four, xfour, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//FIVE
		if(Gdx.input.getX() < xfive + LEVELB_WIDTH && Gdx.input.getX() > xfive && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(five, xfive , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(5));
				return;
			}
		}else{
			batch.draw(five, xfive, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
	}else if(check == 2){		
		//SIX
		if(Gdx.input.getX() < xsix + LEVELB_WIDTH && Gdx.input.getX() > xsix && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(six, xsix , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(6));
				return;
			}
		}else{
			batch.draw(six, xsix, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
	
		//SEVEN
		if(Gdx.input.getX() < xseven + LEVELB_WIDTH && Gdx.input.getX() > xseven && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(seven, xseven , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(7));
				return;
			}
		}else{
			batch.draw(seven, xseven, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//EIGHT
		if(Gdx.input.getX() < xeight + LEVELB_WIDTH && Gdx.input.getX() > xeight && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(eight, xeight , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(8));
				return;
			}
		}else{
			batch.draw(eight, xeight, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//NINE
		if(Gdx.input.getX() < xnine + LEVELB_WIDTH && Gdx.input.getX() > xnine && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(nine, xnine , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(9));
				return;
			}
		}else{
			batch.draw(nine, xnine, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//TEN
		if(Gdx.input.getX() < xten + LEVELB_WIDTH && Gdx.input.getX() > xten && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(ten, xten , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(9));
				return;
			}
		}else{
			batch.draw(ten, xten, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
	}else if(check == 3){
		//ELEVEN
		if(Gdx.input.getX() < xeleven + LEVELB_WIDTH && Gdx.input.getX() > xeleven && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(eleven, xeleven , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(11));
				return;
			}
		}else{
			batch.draw(eleven, xeleven, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//TWELVE
		if(Gdx.input.getX() < xtwelve + LEVELB_WIDTH && Gdx.input.getX() > xtwelve && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(twelve, xsecond , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(12));
				return;
			}
		}else{
			batch.draw(twelve, xsecond, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//THIRTEEN
		if(Gdx.input.getX() < xthirteen + LEVELB_WIDTH && Gdx.input.getX() > xthirteen && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(thirteen, xthirteen , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(13));
				return;
			}
		}else{
			batch.draw(thirteen, xthirteen, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//FOURTEEN
		if(Gdx.input.getX() < xfourteen + LEVELB_WIDTH && Gdx.input.getX() > xfourteen && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(fourteen, xfourteen , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(14));
				return;
			}
		}else{
			batch.draw(fourteen, xfourteen, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
		//FIVETEEN
		if(Gdx.input.getX() < xfiveteen + LEVELB_WIDTH && Gdx.input.getX() > xfiveteen && Gdx.graphics.getHeight() - Gdx.input.getY() < y + LEVELB_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > y){
			batch.draw(fiveteen, xfiveteen , y, LEVELB_WIDTH, LEVELB_HEIGHT);
			if(Gdx.input.justTouched()){
				WoodyGame.getGame().setScreen(GameScreen.getInstance(15));
				return;
			}
		}else{
			batch.draw(fiveteen, xfiveteen, y, LEVELB_WIDTH, LEVELB_HEIGHT);
		}
	}
		batch.end();	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
		asMa.clear();
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
