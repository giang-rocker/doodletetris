package myGame.doodleTetris.framework;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Image  {
    public float x,y;
    public  Bitmap bitmap;
	
    public Image (Bitmap bit,float x, float y) {
    	this.bitmap = bit;
    	this.x=x ; this.y=y;
    	
    }
    
    public void setPosition (float x, float y) {
    	this.x=x ; this.y=y;
    }
    
    public boolean isTouch (SingleTouch event) {
    	if (event!=null){
    	float ex = event.getX();
		float ey =  event.getY();
    	
    	if (ex>=x && ex<=x+bitmap.getWidth()&& ey>=y  && ey<=y+bitmap.getHeight() && event.isTouch())
    	return true;
    	else 
    	return false;
    	}
    	else return false;
    	
	}
	public void draw () {
		Canvas canvas = new Canvas();
		canvas.drawBitmap(bitmap, x, y, null);
		
	}
}
