package myGame.doodleTetris.framework;

import android.content.Context;
import android.media.MediaPlayer;


public class Music {
	MediaPlayer mediaPlay;
	
	public Music (Context context, int resid){
		mediaPlay = MediaPlayer.create(context, resid);
		mediaPlay.setVolume(1, 1);
	}
	public void setLooping(){
		mediaPlay.setLooping(true);
	}
	public void play(){
		mediaPlay.start();
		mediaPlay.setLooping(true);
	}
	public  void stop(){
		mediaPlay.stop();
	}
	public  void pause(){
		mediaPlay.pause();
	}
	public void offVolume(){
		mediaPlay.setVolume(0, 0);
	}
	public void onVolume(){
		mediaPlay.setVolume(1, 1);
	}
}