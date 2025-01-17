package myGame.doodleTetris;

import myGame.doodleTetris.framework.AndroidGame;
import myGame.doodleTetris.framework.Screen;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View.OnTouchListener;



public class Game  extends AndroidGame{
	public static OnTouchListener renderTouch ;
	@Override
	public Screen getStartScreen () {
		Log.d("getStartScreen", "LoadingScreen");
		return new LoadingScreen (this);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			if (getCurrentScreen().screenName != "MainMenu")
				this.setScreen(new MainMenu(this));
			else {finish();}
	return true;
	}
	
}
