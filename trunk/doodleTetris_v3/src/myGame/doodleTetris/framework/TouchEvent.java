package myGame.doodleTetris.framework;

import android.view.MotionEvent;

public class TouchEvent {
	
	int type;
	float x, y;
	boolean isTouch;
	MotionEvent event;
	
	public TouchEvent (MotionEvent e,float scaleX, float scaleY) {
	event =e;
		
		x = e.getX()/scaleX ; y = e.getY()/scaleY;
	}
	
	public float getX () {
		return this.x;
		
	}
	public float getY () {
		return this.y;
	}
	
	public boolean isTouch () {
		return this.isTouch;
		
	}
	
}
