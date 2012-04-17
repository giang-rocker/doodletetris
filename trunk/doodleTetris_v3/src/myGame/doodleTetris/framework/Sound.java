package myGame.doodleTetris.framework;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
/*
public class Sound {
	public static int soundID;
	public static SoundPool sound;
	public static int resSoundID;
	
	public static void setResSoundID(int ID){
		resSoundID = ID;
	}
	public static int getResSoundID(){
		return resSoundID;
	}
	public static void loadSound(Context context){
		sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		soundID = sound.load(context, resSoundID, 0);
	}
	public static void startSound(){
		sound.play(soundID, 1, 1, 0, 0, 1);
	}
	public static void unloadSound(){
		sound.unload(soundID);
	}
}
*/
public class Sound  {
	public static float appVolume = 1;
	public int soundID;
	public SoundPool sound;
	public int resSoundID;
	Context context;
	

	
	public void play(){
		sound.play(soundID,appVolume,appVolume,0,0,1);
	}
	public void play(float volume){
		sound.play(soundID,volume,volume,0,0,1);
	}
	
	
	public Sound (Context context, int resSoundID) {
		this.context = context;
		this.sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		this.soundID = sound.load(context,resSoundID,1 );
	}
	
	
	public void dispose(){
		sound.unload(soundID);
	}
	
}

