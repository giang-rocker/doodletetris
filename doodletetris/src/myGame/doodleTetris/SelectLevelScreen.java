package myGame.doodleTetris;

import java.util.Random;

import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.SingleTouch;


public class SelectLevelScreen extends Screen {

	LevelInfo[] listLevel;

	// star of list at beginning
	int startY = 96;
	int startX = 0;
	
	int moveY =0;
	
	public SelectLevelScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		listLevel = new LevelInfo  [100] ;
		Random r = new Random();
		for (int i=0;i<listLevel.length;i++) 
			{
			
			listLevel[i] = new LevelInfo(i,r.nextInt(9999),r.nextInt(9999),r.nextInt(3),true);
			}
		startY = 96;
	}

	int currentScollY, startScrollY=-1;
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		SingleTouch touchEvent = game.getTouchEvent();
		
		// back to menu
		
		//scroll
		if (touchEvent.isMove()){
		currentScollY = (int) touchEvent.getY();
		if (startScrollY==-1) startScrollY = currentScollY;
		moveY = currentScollY - startScrollY;
		
		
		if (Math.abs(moveY) > 20){
				startScrollY = (int) touchEvent.getY();
		}
		if (moveY>0) moveY = 1; else if (moveY<0) moveY = -1;
		
		}
		else moveY =0;
		int id = checkListLevelTouch();
		if(id!=-1) game.setScreen(new ArcadeGameScreen(game, id));
	}
		

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		AndroidGraphics g = game.getGraphics();
		
		g.drawImage(Asset.bg_selectLevel_main);
		drawSlide();
		g.drawImage(Asset.bg_selectLevel_nav);
		
		
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	public void drawSlide (){
	
		startY = startY + moveY*10;
		AndroidGraphics g = game.getGraphics();
		
		int height_unit=72;
		int space_line=12;
		int n = listLevel.length;
		for (int i=0;i<n;i++) {
			
			float X = startX;
			float Y = startY + (height_unit+space_line)*i +moveY;
			listLevel[i].setPosition((int)X,(int)Y)	;
		g.drawImage(Asset.slide_level.getBitmap(),(int)X,(int)Y)	;
		// draw ID
		drawStringNumber(Integer.toString(listLevel[i].id), (int)X+24+12, (int)Y+64);
		// draw scoreRecord
		drawStringNumber(Integer.toString(listLevel[i].scoreRecord), (int)X+120,(int) Y+72);
		// draw timeRecord
		drawStringNumber(Integer.toString((int)listLevel[i].timeRecord),(int) X+240, (int)Y+72);
		//draw start. chua xulu
		drawStar( listLevel[i].rate ,(int)X+342,(int)Y+60+12);
		}
		
	
	}
	public int checkListLevelTouch (){
		SingleTouch touchEvent = game.getTouchEvent();
		int n = listLevel.length;
		if (moveY==0){
		for (int i=0;i<n;i++) {
			if (listLevel[i].isTouch(touchEvent)){
				if (touchEvent.isStillTouch(150))
					if (listLevel[i].isTouch(touchEvent)) return i;
		}
		}
		}
		return -1;
	
	}
		void drawStringNumber (String s, int startX, int startY) {
		
		int len  = s.length();
		int UNIT_CHAR = 24;
		for (int i=0;i<len;i++){
			drawNumber(s.charAt(i),startX+UNIT_CHAR*i,startY);
		}
	}
		void drawStar (int rate, int startX, int startY) {
			AndroidGraphics g = game.getGraphics();
			int len  = 3;
			int UNIT_CHAR = 36;
			for (int i=0;i<len;i++){
				if (i<rate)
				g.drawImage(Asset.icon_star.bitmap,startX+UNIT_CHAR*i,startY);
				else 
					g.drawImage(Asset.icon_star_dis.bitmap,startX+UNIT_CHAR*i,startY);
			}
	}
		
		// ve4 ky tu so
		public void drawNumber(Character c, int x, int y){
			
			AndroidGraphics g = game.getGraphics();
				g.drawImage( Asset.number[(int)c-48].bitmap,x,y);
			}
		
}
