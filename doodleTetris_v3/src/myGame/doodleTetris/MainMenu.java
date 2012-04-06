package myGame.doodleTetris;


import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.Game;
import myGame.doodleTetris.framework.SingleTouch;
import android.util.Log;


public class MainMenu extends Screen  {

	
  void setupButton () {
	Asset.btn_modeAdv.setPosition(290, 360);  
	Asset.btn_modeClassic.setPosition(40, 370);
	
	Asset.btn_setting.setPosition(160, 710);
	Asset.btn_record.setPosition(240, 710);
	Asset.btn_help.setPosition(320, 710);
	Asset.btn_about.setPosition(400, 710);
	Asset.btn_sound.setPosition(20, 730);
	
  	}
	
	public MainMenu (Game game) {
		super(game);
		this.screenName = "MainMenu";
		// TODO Auto-generated constructor stub
		setupButton();
		Log.d("myTag","MainMenu is createed");
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		SingleTouch TouchEvent = game.getTouchEvent();
		
		if ( Asset.btn_modeClassic.isTouch(TouchEvent)   )
			game.setScreen(new ClassicGameScreen(game));
		
		if ( Asset.btn_modeAdv.isTouch(TouchEvent)   )
			game.setScreen(new SelectLevelScreen(game));
			//game.setScreen(new ArcadeGameScreen(game, 1));
		
		
	}

	@Override
	public void present(float deltaTime) {
		AndroidGraphics g = game.getGraphics();
		
		g.drawImage(Asset.bg_main.bitmap, (int )Asset.bg_loading.x,(int ) Asset.bg_loading.y);
		g.drawImage(Asset.btn_modeClassic);
		g.drawImage(Asset.btn_modeAdv);
		g.drawImage(Asset.btn_sound);
		g.drawImage(Asset.btn_setting);
		g.drawImage(Asset.btn_help);
		g.drawImage(Asset.btn_record);
		g.drawImage(Asset.btn_about);
		
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
