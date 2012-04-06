package myGame.doodleTetris;

import myGame.doodleTetris.framework.SingleTouch;

public class LevelInfo{

	public int id;
	public float timeRecord;
	public int scoreRecord;
	public int rate;
	public boolean isUnlock;
	int x, y;
	
	public LevelInfo (int id,float timeRecord,	int scoreRecord,int rate,boolean isUnlock) {
		this.id = id;
		this.timeRecord = timeRecord;
		this.scoreRecord=scoreRecord;
		this.rate=rate;
		this.isUnlock=isUnlock;
		
	}
	
	public LevelInfo () {
		this.id = 88;
		this.timeRecord = 8888;
		this.scoreRecord= 8888;
		this.rate=2;
		this.isUnlock=true;
	}
	
	public void setPosition (int x, int y) {
		this.x=x; this.y=y;
		
	}
	
	  public boolean isTouch (SingleTouch event) {
	    	if (event!=null){
	    	float ex = event.getX();
			float ey =  event.getY();
	    	int startX=this.x+24, startY = this.y+48;
	    	int endX=startX+432, endY = startY+72;
	    	
	    	if (ex>=startX && ex<=endX && ey>=startY  && ey<=endY)
	    	return true;
	    	else 
	    	return false;
	    	}
	    	else return false;
	    	
		}
}
