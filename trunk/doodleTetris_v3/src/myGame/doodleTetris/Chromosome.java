package myGame.doodleTetris;

import java.util.Random;

import android.util.Log;

public class Chromosome extends ChromosomeInfo {
	public static int numOfGen = 14;
	public static int RangeofGenValue = 10000000;// gia tri tong khoang [-5,5];
	int gen[]; // gia tri trong khoang [-RangeofGenValue,RangeofGenValue];
	int fitnessValue;
	int pecentFit ; // * % ngan
	// gen
	int genIndex;
	int typeChromosome;
	public Chromosome (){
		gen = new int[numOfGen];
		for (int i =0; i<numOfGen;i++) gen [i]=0;
		fitnessValue = 0;
		pecentFit = 0;
		genIndex=0;
	}
	
	public void copy (Chromosome c) {
		for (int i =0; i<numOfGen;i++) gen [i]=c.gen[i];
		fitnessValue = c.fitnessValue;
		pecentFit = c.pecentFit;
	}
	
	public static Chromosome generateChromosome () {
		Chromosome s = new Chromosome();
		Random ra = new Random();
		int randomW, sign=1;
		for (int i=0; i<numOfGen;i++) {
	 randomW =  ((ra.nextInt() + ra.nextInt())%RangeofGenValue);
	
	//	 Log.d("RANDOM","RANDOM" + randomW);
		if (ra.nextBoolean())  sign = -1; else sign =1; 
	//	 sign = 1;// luon duong
		s.gen[i] = randomW*sign;
		}
		// special gen 2; dương
	//	s.gen[2] =  (((int)System.nanoTime() + ra.nextInt())%RangeofGenValue);
		
		s.fitnessValue = 0;
		s.pecentFit =0;
		s.genIndex=0;
		return s;
	}
	
	public void setfitnessValue (int f) {
		this.fitnessValue =f;
	}
}
