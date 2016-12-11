package de.woody.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Buttons extends WoodyGame
{
   Viewport viewport;
   Stage stage;
   static boolean jumpPressed;
   static boolean attackPressed;
   static boolean leftPressed;
static boolean rightPressed;
   OrthographicCamera cam;
   int sizeVariable = 90;
   
   public Buttons(){
       cam = new OrthographicCamera();
       viewport = new FitViewport(1280, 768, cam);
       stage = new Stage(viewport);
       Gdx.input.setInputProcessor(stage);
       
       Table table = new Table();
       table.left().bottom();
       
       Image jumpImg = new Image(new Texture("textures/ButtonJump.png"));
       jumpImg.setSize(sizeVariable, sizeVariable);
       jumpImg.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               jumpPressed = true;
               return true;
           }
           
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               attackPressed = false;
           }
       });
       
       Image rightImg = new Image(new Texture("textures/ButtonRight.png"));
       rightImg.setSize(sizeVariable, sizeVariable);
       rightImg.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               rightPressed = true;
               return true;
           }
           
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               rightPressed = false;

           }
       });
       
       Image leftImg = new Image(new Texture("textures/ButtonLeft.png"));
       leftImg.setSize(sizeVariable, sizeVariable);
       leftImg.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               leftPressed = true;
               return true;
           }
           
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               leftPressed = false;
           }
       });
       
       Image attackImg = new Image(new Texture("textures/ButtonFight.png"));
       attackImg.setSize(sizeVariable, sizeVariable);
       attackImg.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               attackPressed = true;
               return true;
           }
           
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button){
               attackPressed = false;
           }
       });
       
//       table.add(jumpImg).size(jumpImg.getWidth(), jumpImg.getHeight());
//       table.row().pad(5,5,5,5);
//       table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
//       table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
//       table.row().padBottom(5);
//       table.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight());

       table.add();
       table.add(jumpImg).size(jumpImg.getWidth(), jumpImg.getHeight());
       table.add();
       
       table.row().pad(5,5,5,5);
       
       table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
       table.add();
       table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
       
       table.row().padBottom(5);
       
       table.add();
       table.add(attackImg).size(attackImg.getWidth(), attackImg.getHeight());
       table.add();
       
       stage.addActor(table);
   }
       
   public void draw(){
       stage.draw();
   }
   
   public boolean isjumpPressed(){
       return jumpPressed;
   }
   public boolean isLeftPressed(){
       return leftPressed;
   }
   public boolean isRightPressed(){
       return rightPressed;
   }
   public boolean isattackPressed(){
       return attackPressed;
   }
   
   public void resize(int width, int height){
       viewport.update(width, height);
   }
} 