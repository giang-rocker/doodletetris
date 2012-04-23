package myGame.doodleTetris;

import myGame.doodleTetris.framework.SingleTouch;

public class ChromosomeInfo{

	public int id;
	public int fitnessValue;
	public int typeChromosome;
	int[] gen;
	int x, y;
	
	public ChromosomeInfo (int id, int fitnessValue, int typeChromosome) {
		this.id = id;
		this.fitnessValue = fitnessValue;
		this.typeChromosome=typeChromosome;
		gen = new int[Chromosome.numOfGen];
		}
	
	public ChromosomeInfo () {
		this.id = 0;
		this.fitnessValue = 0;
		this.typeChromosome=0;
		gen = new int[Chromosome.numOfGen];
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
