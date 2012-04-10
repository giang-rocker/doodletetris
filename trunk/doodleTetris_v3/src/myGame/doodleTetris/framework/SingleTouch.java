package myGame.doodleTetris.framework;


import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class  SingleTouch implements OnTouchListener  {
	public static final int TOUCH_UP=0;
	public static final int TOUCH_DOWN=1;
	public static final int TOUCH_DRAGGED=2;
	
	TouchEvent touchEvent;
	float ScaleX; float ScaleY;
	MotionEvent event;
	boolean isTouch ;
	
	int startScrollY, currentScrollY; 
	
	public SingleTouch (View V,float ScaleX, float ScaleY) {
		V.setOnTouchListener(this);
		touchEvent = null;
		this.ScaleX = ScaleX;
		this.ScaleY = ScaleY;
		event = null;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		this.event = event;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			startScrollY = (int)event.getY();
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			currentScrollY = (int)event.getY();
			startScrollY = currentScrollY;
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
		}
		
		return false;
	}
	
	public void cleanTouchEvent () {
		touchEvent = null;
	}
	
	public float getX () {if (event!=null )return event.getX()/ScaleX; else return 0;}
	
	public float getY () {if (event!=null )return event.getY()/ScaleY; else return 0;}

	public float getMoveX () { return event.getX()/ScaleX; }
	
	public float getMoveY () {return event.getY()/ScaleY;}

	
	public boolean isTouch () {
		if (event!=null)
		if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) return true;
		else return false;
		else return false;
	}
	
	public MotionEvent getTouchEvent () {
		return event;
	}

		public boolean isMove () {if (event.getAction()==MotionEvent.ACTION_MOVE) return true; else return false;}
		
		public boolean isTouchDown () {if (event.getAction()==MotionEvent.ACTION_DOWN) return true; else return false;}
		
		public boolean isStillTouch (int millisecond ) {
				int start ;
				if (event!=null) {
				int duration = millisecond * 1000000;
				start = (int) System.nanoTime();
				
				while ((int) System.nanoTime() - start < duration){
					 if (event.getAction()== MotionEvent.ACTION_UP) {return false; } 
					 }
				return true;
				}
				else return false;
		}
}