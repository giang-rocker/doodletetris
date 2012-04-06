package myGame.doodleTetris.framework;



import android.util.Log;

public abstract class Screen  { 
	
	public String screenName;

	protected final Game game; 
 
  
	  public Screen(Game game) {
		  this.game = game;
		  Log.d("Screen", "start");
		}
  
	public abstract void update(float deltaTime); 
 
    public abstract void present(float deltaTime); 
 
    public abstract void pause(); 
 
    public abstract void resume(); 
 
    public abstract void dispose(); 
} 