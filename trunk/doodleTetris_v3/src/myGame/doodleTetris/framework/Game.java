package myGame.doodleTetris.framework;

public interface Game  { 
	public SingleTouch getTouchEvent(); 
	public KeyPress getKeyEvent();
//    public FileIO getFileIO(); 
 
    public AndroidGraphics getGraphics(); 
 
    //public Audio getAudio(); 
 
    public void setScreen(Screen screen); 
 
    public Screen getCurrentScreen(); 
 
    public Screen getStartScreen(); 
}
    