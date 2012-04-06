package myGame.doodleTetris.framework;


import java.io.InputStream;


import android.content.res.AssetManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;


public class AndroidGraphics  implements Graphics  { 
    AssetManager assets; 
    Bitmap frameBuffer; 
    Canvas canvas; 
    Paint paint; 
    Rect srcRect = new Rect(); 
    Rect dstRect = new Rect(); 
    
    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) { 
    	this.assets = assets; 
        this.frameBuffer = frameBuffer; 
        this.canvas = new Canvas(frameBuffer); 
        this.paint = new Paint(); 
        Log.d("Graphics", "start");
		
    } 

    public void drawImage (Bitmap bitmap, int x, int y) {
    	canvas.drawBitmap(bitmap, x, y, null);
    	
    }
    
    public void drawImage (ImgButton imgButton) {
    	canvas.drawBitmap(imgButton.getBitmap(), imgButton.getX(), imgButton.getY(), null);
    	
    }
    
    public void drawImage (Image image) {
    	canvas.drawBitmap(image.bitmap, image.x, image.y, null);
    	
    }
    
   public Bitmap newBitmap (String filename) {
	   Bitmap bitmap = null;
	   InputStream in =null;
	   try{
		   in = assets.open(filename);
		   bitmap= BitmapFactory.decodeStream(in);
	   }
	   catch (Exception e) {
		// TODO: handle exception
		  
		   Log.d("could not load file","could not load file");
	   }
	   
	 
	   return bitmap;
   }
    
   

   
}