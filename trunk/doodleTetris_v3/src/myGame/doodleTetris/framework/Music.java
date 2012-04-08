package myGame.doodleTetris.framework;

import android.content.Context;
import android.media.MediaPlayer;


public class Music {
	MediaPlayer mediaPlay;
	
	public  Music (Context context, int resid){
		mediaPlay = MediaPlayer.create(context, resid);
		mediaPlay.setVolume(1, 1);
	}
	public  void play(){
		mediaPlay.start();
		mediaPlay.setLooping(true);
	}
	public  void stop(){
		mediaPlay.stop();
		mediaPlay.release();
	}
	public  void pause(){
		mediaPlay.pause();
	}
	
}