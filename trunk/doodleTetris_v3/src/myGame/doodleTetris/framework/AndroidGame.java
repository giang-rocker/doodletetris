package myGame.doodleTetris.framework;


import myGame.doodleTetris.Asset;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public abstract class AndroidGame extends Activity implements Game {
	public static int count =1;
	public static int map =1;
	public static int currentSumLines = 0;
	public static int preSumLines = 0;
	
	
	float scaleX, scaleY;
	
	AndroidFastRenderView renderView;
	protected Screen  screen;
	WakeLock wakelock;
	AndroidGraphics graphics;
	SingleTouch touchEvent;
	KeyPress keyPressEvent;
	AssetManager assetManager;
// doi tuong sound voi context game
	Sound sound ;
	// class soun
	//Duong set variable
	public Context context;
	//
	@Override 
    public void onCreate(Bundle savedInstanceState) { 
		Log.d("myTag", "start");
        super.onCreate(savedInstanceState); 
   
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN); 
 
      //  boolean isLandscape = getResources().getConfiguration().orientation == 
    //    		Configuration.ORIENTATION_LANDSCAPE; 
        int frameBufferWidth = 480;
        int frameBufferHeight = 800;
        
        
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
         
        scaleX =  getWindowManager().getDefaultDisplay().getWidth()/(float)frameBufferWidth; 
        scaleY =    getWindowManager().getDefaultDisplay().getHeight()/ (float)frameBufferHeight; 
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        renderView = new AndroidFastRenderView(this, frameBuffer,  scaleX, scaleY); 
        
        touchEvent = new SingleTouch(renderView,scaleX, scaleY);
        keyPressEvent = new KeyPress(renderView);
       
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC); 
        
        screen = getStartScreen();
     
        setContentView(renderView); 
        PowerManager powerManager = (PowerManager) 
        getSystemService(Context.POWER_SERVICE); 
        wakelock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame"); 

	//Duong set variable context
    	context=this;
    	assetManager = getAssets();
    	Log.d("end","end");
    } 
	
	//Duong get context game
	public Context getContext(){
		return context;
	}
	//
	@Override
	public void onResume(){
		super.onResume();
		wakelock.acquire();
		screen.resume();
		renderView.resume();
	
	}	
	
	
	@Override
	public void onPause () {
		super.onPause();
		wakelock.release();
		renderView.pause();
		screen.pause();
		if (isFinishing() ) {screen.dispose(); }
	
		}

	public void onDestroy() {
	super.onDestroy();
    System.runFinalizersOnExit(true);
    System.exit(0);
	}
	
	public void setScreen(Screen screen) {
		if (screen ==null)
			throw new IllegalAccessError("Screen must be not null");
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;

	}

	
	
	public AndroidGraphics getGraphics () {
		return this.graphics;
	}
	
	public SingleTouch getTouchEvent() {
		// TODO Auto-generated method stub
		return touchEvent;
		}
	
	public KeyPress getKeyEvent() {
		// TODO Auto-generated method stub
		return keyPressEvent;
	}
	
	public Screen getCurrentScreen() {
		return screen;
	}
	
	public Screen getStartScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	  public AssetManager getAsset () {
		return assetManager;
	  }
}
