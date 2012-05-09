package myGame.doodleTetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Screen;

public class HighScoreScreen extends Screen {
	Canvas canvas;
	HighScore highscore;
	int startY = 160;
	int startX = 0;
	int moveY =0;
	String minus,second;
	public void showList(){
		startY = startY + moveY*10;
		AndroidGraphics g = game.getGraphics();
		int height_unit=96;
		int space_line=12;		
		for (int i=0;i<5;i++) {
			float X = startX;
			float Y = startY + (height_unit+space_line)*i +moveY;
		
			g.drawImage(Asset.slide_hiscore.getBitmap(),(int)X,(int)Y)	;
		// draw ID
		drawStringNumber((i+1)+"", (int)X+12+24+12, (int)Y+64);
		
		drawStringNumber(highscore.data[0][i].trim(), (int)X+100+24, (int)Y+64+3);
		// draw scoreRecord
		drawStringNumber(highscore.data[1][i].trim(),(int) X+360 +24,(int) Y+64+3);
		// draw timeRecord
		drawTime(Integer.parseInt(highscore.data[2][i].trim()),(int)X+260-24, (int)Y+64+3);
		}
	}
	public void drawNumber(Character c, int x, int y){
		
		AndroidGraphics g = game.getGraphics();
			g.drawImage( Asset.number[(int)c-48].bitmap,x,y);
		}

void drawStringNumber (String s, int startX, int startY) {
		
		int len  = s.length();
		int UNIT_CHAR = 24;
		for (int i=0;i<len;i++){
			drawNumber(s.charAt(i),startX+UNIT_CHAR*i,startY);
		}
}
		void drawTime (int time, int StartX, int StartY) {
			int realTime = time/40;
			String o = "0";
			
			if (realTime/60>9) o = ""; else o = "0";
			minus  = o +  realTime/60;
			
			if (realTime%60>9) o = ""; else o = "0";
			second = o + realTime%60;
			
			drawStringNumber (minus,StartX,StartY);
			drawStringNumber (":",StartX+48,StartY);
			drawStringNumber (second,StartX+44+12,StartY);
		}
	public HighScoreScreen(Game game) {
		super(game);
		highscore = new HighScore(game.getContext());
		highscore.loadHighScore();
		highscore.sort();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		AndroidGraphics g = game.getGraphics();
		g.drawImage(Asset.bg_highScore);
		showList();
		//g.drawImage(Asset.bg_selectLevel_nav);
		
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
	

}
