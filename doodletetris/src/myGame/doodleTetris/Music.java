package myGame.doodleTetris;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	static MediaPlayer mp;
	public static void setMusic(Context context, int resid){
		mp = MediaPlayer.create(context, resid);
		mp.setVolume(1, 1);
	}
	public static void startMusic(){
		mp.start();
	}
	public static void stopMusic(){
		mp.stop();
	}
	public static void pauseMusic(){
		mp.pause();
	}
}
