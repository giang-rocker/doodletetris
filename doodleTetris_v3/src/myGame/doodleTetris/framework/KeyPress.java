package myGame.doodleTetris.framework;


import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class  KeyPress implements OnKeyListener  {

	int keyCode;
	boolean isKeyPressed;
	public KeyPress (View V){
		V.setOnKeyListener(this);
		V.setFocusable(true);
		V.requestFocus();
		keyCode = -50000;
	}
	
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		  Log.d("press", "keypressed");
		if (event.getAction() == KeyEvent.ACTION_DOWN) isKeyPressed = true;
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    {
		        Log.d("Test", "Back button pressed!");
		    }
		    else if(keyCode == KeyEvent.KEYCODE_HOME)
		    {
		        Log.d("Test", "Home button pressed!");
		    }
		    else if(keyCode == KeyEvent.KEYCODE_MENU)
		    {
		        Log.d("Test", "M button pressed!");
		    }
		this.keyCode = keyCode; 
		
		boolean disableEvent = false;

        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK) {

            disableEvent = true;

        }

        return disableEvent;
	}
	
	
 public int getKeyCode () {
	 if (this.keyCode!= KeyEvent.KEYCODE_BACK)
	 return this.keyCode;
	 else return -1;

 }

 public boolean isPressed () {
	return isKeyPressed;

 }


}