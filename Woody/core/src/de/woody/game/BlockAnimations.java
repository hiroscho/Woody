package de.woody.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import de.woody.game.screens.GameScreen;

public class BlockAnimations {
	
	public Texture sheetAnimation;
	public Animation animVanish;
	
	public float stateTime = 0;

	private AssetManager asMa = WoodyGame.getGame().manager;
	
	public BlockAnimations()
	{
	sheetAnimation = asMa.get("textures/vanishSheet.png", Texture.class);
	Array<TextureRegion> frames = new Array<TextureRegion>();
	frames.clear();
	for(int i = 1; i <= 5; i++)
		frames.add(new TextureRegion(sheetAnimation, i *66, 0,66,66));
	animVanish = new Animation(0.2f, frames);	
	}
	
	public TextureRegion vanishAnimation(int x, int y)
	{
		TextureRegion textureRegion = animVanish.getKeyFrame(0);
	
		for(int i =0; i>5;i++)
		{
			textureRegion = animVanish.getKeyFrame((float) (i*animVanish.getFrameDuration()));
			if(GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().getId() == 27)		
			{
				//GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().setTextureRegion(null);
				GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().setTextureRegion(textureRegion);
				return textureRegion;
			}
		}
		return textureRegion;
	}
	
//	public void blockAnimations(int x, int y, int Id, boolean destroyAfter)
//	{
//		TextureRegion texReg;
//		
//		if(GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().getId() == Id && (GameScreen.getInstance().levelData.getCollidable().getCell(x, y) != null))
//		{
//			int i = 0;
//			Timer.schedule(new Task() {
//				@Override
//				public void run() {
//					Cell cell;
//					while(GameScreen.getInstance().levelData.getCollidable().getCell(x, y) != null)
//					{
//						
//						texReg = animVanish.getKeyFrame((float) (i*animVanish.getFrameDuration()));
//						GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().setTextureRegion(null);
//						GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().setTextureRegion(texReg);
//						i++;
//					}
//				}
//
//			}, 1F);
//		}
//		
//		else if(GameScreen.getInstance().levelData.getCollidable().getCell(x, y).getTile().getId() == Id && (GameScreen.getInstance().levelData.getNonCollidable().getCell(x, y) != null))
//		{
//			int i = 0;
//
//		}
//	}
}
