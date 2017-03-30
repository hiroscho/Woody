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
import de.woody.game.screens.GameScreen;
import de.woody.game.screens.WoodyGame;

public class LevelSelect implements Screen{
	
	private static final LevelSelect levelSelect = new LevelSelect();
	private AssetManager asMa = WoodyGame.getGame().manager;
	private Batch batch;
	public static float xfirst, xsecond, xthir, xfour, xfive, xsix, xseven, xeight, xnine, xten, xeleven, xtwelve;
	public static float xthirteen, xfourteen, xfiveteen;
	
	//Level Stages
	public static final int MAX = 3;
	public static final int MIN = 1;
	public static int check = 1;
	
	public static boolean checking(int value, int min, int max){
		if(value > max){
			value = max;
			System.out.println("Stay on the right "+value);
			return false;
		}else if(value < min){
			value = min;
			System.out.println("Stay on the left"+ value);
			return false;
		}else{
			return true;
		}
	}
	
	
	//Textures
	//Levelnumbers
	private Texture one = new Texture("textures/eins.png");
	private Texture two = new Texture("textures/zwei.png");
	private Texture three = new Texture("textures/drei.png");
	private Texture four = new Texture("textures/vier.png");
	private Texture five = new Texture("textures/fuenf.png");
	private Texture six = new Texture("textures/sechs.png");
	private Texture seven = new Texture("textures/sieben.png");
	private Texture eight = new Texture("textures/acht.png");
	private Texture nine = new Texture("textures/neun.png");
	private Texture ten = new Texture("textures/zehn.png");
	private Texture eleven = new Texture("textures/elf.png");
	private Texture twelve = new Texture("textures/zwoelf.png");
	private Texture thirteen = new Texture("textures/dreizehn.png");
	private Texture fourteen = new Texture("textures/vierzehn.png");
	private Texture fiveteen = new Texture("textures/fuenfzehn.png");
	//background
	private Texture background = new Texture("textures/LevelSelectBackground.png");
	//Menubutton
	private Texture menuun = new Texture("textures/Menu_un.png");
	private Texture menuak = new Texture("textures/Menu_ak.png");
	//Texturesizes
	private final int LEVELB_WIDTH = Gdx.graphics.getWidth() / 10;
	private final int LEVELB_HEIGHT = Gdx.graphics.getHeight() /10;
	private final int MENU_WIDTH = Gdx.graphics.getWidth() / 3;
	private final int MENU_HEIGHT = Gdx.graphics.getHeight() / 3;
	private final int BACKGROUND_WIDTH = Gdx.graphics.getWidth();
	private final int BACKGROUND_HEIGHT = Gdx.graphics.getHeight();
	
//	public LevelSelect() {
//		batch = new SpriteBatch();
//		Gdx.input.setInputProcessor(new GestureDetector(new Swipe()));
//		return levelSelect;
//	}
	private LevelSelect(){
		
	}
	
	public static LevelSelect getInstance(){
		levelSelect.batch = new SpriteBatch();
		Gdx.input.setInputProcessor(new GestureDetector(new Swipe()));
		return levelSelect;
	}
	
	//Screen Methods
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
		
		//Swipe x changes
//		if(Swipe.flinged == true){
//			//1-5
//			xfirst += (Swipe.rightone -Swipe.leftone);
//			xsecond += (Swipe.rightone -Swipe.leftone);
//			xthir += (Swipe.rightone -Swipe.leftone);
//			xfour += (Swipe.rightone -Swipe.leftone);
//			xfive += (Swipe.rightone -Swipe.leftone);
//			//6-10
//			xsix += (Swipe.rightone -Swipe.leftone);
//			xseven += (Swipe.rightone -Swipe.leftone);
//			xeight += (Swipe.rightone -Swipe.leftone);
//			xnine += (Swipe.rightone -Swipe.leftone);
//			xten += (Swipe.rightone -Swipe.leftone);
//			//11-15
//			xeleven += (Swipe.rightone -Swipe.leftone);
//			xtwelve += (Swipe.rightone -Swipe.leftone);
//			xthirteen +=(Swipe.rightone -Swipe.leftone);
//			xfourteen += (Swipe.rightone -Swipe.leftone);
//			xfiveteen += (Swipe.rightone -Swipe.leftone);
//		}
		//Y cordinate
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		menuun.dispose();
		menuak.dispose();
		one.dispose();
		two.dispose();
		three.dispose();
		four.dispose();
		five.dispose();
		six.dispose();
		seven.dispose();
		eight.dispose();
		nine.dispose();
		ten.dispose();
		eleven.dispose();
		twelve.dispose();
		thirteen.dispose();
		fourteen.dispose();
		fiveteen.dispose();
		
	}

}
