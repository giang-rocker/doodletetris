package myGame.doodleTetris;

import java.io.IOException;

import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.SingleTouch;
import android.content.res.AssetManager;


public class AutoPlayScreen extends Screen {
	
	void setup () {
		Asset.btn_genetic.setPosition(264, 96);
		Asset.btn_hillClimbing.setPosition(24, 96);
	}
	
	ChromosomeInfo[] listChromosome;

	// star of list at beginning
	int startY = 0;
	int startX = 0;
	
	int moveY =0;
	String minus,second;
	String list[]= new String[1];
	// them doi tuong assetmanager
	AssetManager assetManager ;
	
	// them doi tuong Status MAp
	StatusMap statusMap;
	
	public AutoPlayScreen(Game game) {
		super(game);
		setup () ;
		// TODO Auto-generated constructor stub
		// Giang - bo static
		assetManager = game.getContext().getAssets();
		//Duong cap nhat set select level
		screenName ="selectLevel";
		try {
			list= assetManager.list("Chromosome");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listChromosome = new ChromosomeInfo[list.length];
		
		ReadChromosome streamChr = new ReadChromosome(game);
		
		//Random r = new Random();
			for (int i=0;i<list.length;i++) 
			{
			streamChr.accessFile("Chromosome"+Integer.toString(i+1));
			listChromosome[i] = streamChr.setChromosome();
			}
		startY = 96;
	}

	int currentScollY, startScrollY=-1;
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		SingleTouch touchEvent = game.getTouchEvent();
		
		// button click
		
		if (Asset.btn_hillClimbing.isTouchDown(touchEvent))
			game.setScreen(new HillClimbingScreen(game));
		
		if (Asset.btn_genetic.isTouchDown(touchEvent))
			game.setScreen(new EvolutionScreen(game));
		
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
		if(id!=-1) game.setScreen(new TestAutoPlay(game, listChromosome[id]));
	}
		

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		AndroidGraphics g = game.getGraphics();
		
		g.drawImage(Asset.bg_selectLevel_main);
		drawSlide();
		g.drawImage(Asset.bg_autoPlay);
		
		g.drawImage(Asset.btn_genetic);
		g.drawImage(Asset.btn_hillClimbing);
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
		int n = listChromosome.length;
		for (int i=0;i<n;i++) {
			
			float X = startX;
			float Y = 120 + startY + (height_unit+space_line)*i +moveY;
		
			listChromosome[i].setPosition((int)X, (int)Y);
			g.drawImage(Asset.btn_chromosomeSlide.getBitmap(),(int)X,(int)Y)	;
		
			if (listChromosome[i].typeChromosome==0)
				g.drawImage(Asset.str_genetic.bitmap,(int)X,(int)Y)	;
			if (listChromosome[i].typeChromosome==1)
				g.drawImage(Asset.str_hillClimbing.bitmap,(int)X,(int)Y)	;
		
			// draw id
		drawStringNumber(Integer.toString(i+1), (int)X+24+12, (int)Y+64);
		// draw lines
		drawStringNumber(Integer.toString(listChromosome[i].fitnessValue), (int)X+336,(int) Y+72);
		
		}
	}
	public int checkListLevelTouch (){
		SingleTouch touchEvent = game.getTouchEvent();
		int n = listChromosome.length;
		if (moveY==0){
		for (int i=0;i<n;i++) {
			if (listChromosome[i].isTouch(touchEvent)){
				if (touchEvent.isStillTouch(150))
					if (listChromosome[i].isTouch(touchEvent)) return i;
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
