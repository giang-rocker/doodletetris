package myGame.doodleTetris.framework;


import myGame.doodleTetris.Asset;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable{
	
		AndroidGame game;
		Bitmap framebuffer;
		Thread renderThread =null;
		SurfaceHolder holder;
		volatile boolean running =false;
		float scaleX, scaleY ;
		public AndroidFastRenderView(AndroidGame game,Bitmap framebuffer, float scaleX, float scaleY) {
			super(game);
		  	this.game = game;
			this.framebuffer = framebuffer;
			holder = getHolder();
			this.scaleX = scaleX;
			this.scaleY = scaleY;
		//	Log.d("AndroidFastRenderView", "start");
		}
		

		public void resume () {
			running =true;
			renderThread = new Thread(this);
			renderThread.start();
			
		}
		
		public void run() {
			long startTime = System.nanoTime();
			Rect rect= new Rect();
			
			while (running) {
				if (!holder.getSurface().isValid())
					continue;
				
				float deltaTime = (System.nanoTime()-startTime)/1000000000.0f;
				startTime = System.nanoTime();
				
				game.getCurrentScreen().update(deltaTime);
				game.getCurrentScreen().present(deltaTime);
				
				Matrix m = new Matrix();
				m.setScale(game.scaleX, game.scaleY);
				Canvas canvas = holder.lockCanvas();
				canvas.getClipBounds(rect);
				canvas.drawBitmap(framebuffer, m,null);
				holder.unlockCanvasAndPost(canvas);
				
			}
			
		}
		

		public void pause() {
			running = false;
			/*
			while (true) {
				try {
					renderThread.join();
				} catch (Exception e) {
					// TODO: handle exception
				}
			
			}
			*/		
			
		}
		
	public void draw (Bitmap t_framebuffer){
		Rect rect= new Rect();
		
			if (!holder.getSurface().isValid())
			return;
			Matrix m = new Matrix();
			m.setScale(game.scaleX, game.scaleY);
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(rect);
			canvas.drawBitmap(t_framebuffer, m,null);
			holder.unlockCanvasAndPost(canvas);
			
			
	}
}