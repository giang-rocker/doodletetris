package myGame.doodleTetris;

import java.util.Random;

public class Chromosome {
	public static int numOfGen = 8;
	public static int RangeofGenValue = 10;// gia tri trong khoang [-5,5];
	int gen[]; // gia tri trong khoang [-5,5];
	int fitnessValue;
	int pecentFit ; // * %;
	// gen
	
	public Chromosome (){
		gen = new int[numOfGen];
		for (int i =0; i<numOfGen;i++) gen [i]=0;
		fitnessValue = 0;
		pecentFit = 0;
	}
	
	public void copy (Chromosome c) {
		for (int i =0; i<numOfGen;i++) gen [i]=c.gen[i];
		fitnessValue = c.fitnessValue;
		pecentFit = c.pecentFit;
	}
	
	public static Chromosome generateChromosome () {
		Chromosome s = new Chromosome();
		Random ra = new Random();
		int randomW, sign;
		for (int i=0; i<numOfGen;i++) {
		 randomW = ra.nextInt(RangeofGenValue+1);
		if (ra.nextBoolean())  sign = -1; else sign =1; 
		s.gen[i] = randomW*sign;
		}
		s.fitnessValue = 0;
		s.pecentFit =0;
		return s;
	}
	
	public void setfitnessValue (int f) {
		this.fitnessValue =f;
	}
}
