package myGame.doodleTetris.framework;


import android.graphics.Bitmap;

public class ImgButton {

	     int x,y;
	     Bitmap bitmap;
		
	    public ImgButton (Bitmap bit,int  x, int  y) {
	    	this.bitmap = bit;
	    	this.x=x ; this.y=y;
	    	
	    }
	    
	    public void setPosition (int  x, int y) {
	    	this.x=x ; this.y=y;
	    }
	    
	    
	   public int getX () {
		   return this.x;
	   }
	   public int getY () {
		   return this.y;
	   }
	    
	   public Bitmap getBitmap () {
		   return this.bitmap;
	   }
	   
	   public void setBitmap (Bitmap b) {
		    this.bitmap = b;
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

}
