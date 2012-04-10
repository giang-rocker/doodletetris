package myGame.doodleTetris.framework;

import android.content.Context;
import android.content.res.AssetManager;

public interface Game  { 
	public SingleTouch getTouchEvent(); 
	public KeyPress getKeyEvent();
//    public FileIO getFileIO(); 
 
    public AndroidGraphics getGraphics(); 
 
    //public Audio getAudio(); 

    public AssetManager getAsset ();
    public void setScreen(Screen screen); 
 
    public Screen getCurrentScreen(); 
 
    public Screen getStartScreen(); 
     
    public Context getContext();
    public int getTaskId ();
}
    