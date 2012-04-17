package myGame.doodleTetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import myGame.doodleTetris.framework.AndroidGame;
import android.os.Environment;
import android.util.Log;

public class Population {
	public static int numOfChromosome = 40;
	public static int numOfGeneration = 10;
	public static float ProbCross  = 1.0f;
	public static float ProbMutation  = 0.01f;
	
	int generation =0 ;// the he quan the
	int maxFitnessValue; // fitnessValue of bestChromosome
	int bestChromosome; // id
	int sumOfFitnessValue; // tong gia tri thich nghi
	
	// population
	Chromosome chromosomes[] ;
	
	public Population () {
		chromosomes = new  Chromosome[numOfChromosome];
		maxFitnessValue=-1;
		bestChromosome=-1;
		sumOfFitnessValue = 0;
		generation =0 ;
	}
	/*
	// sao chep quan the
	public Population (Population p_temp) {
		
		for (int i =0;i<numOfChromosome;i++)
			this.chromosomes[i] = new Chromosome(p_temp.chromosomes[i]);
	
		maxFitnessValue=p_temp.maxFitnessValue;
		bestChromosome=p_temp.bestChromosome;
		generation = p_temp.generation;
	}
	*/
	
	public void generatePopulation () {
		for (int i =0;i<numOfChromosome;i++)
			chromosomes[i] = Chromosome.generateChromosome();
		maxFitnessValue =-1;
		bestChromosome =-1;
		sumOfFitnessValue = -1;
		generation =0 ;
	}
	
	public void findBestFitness () {
		maxFitnessValue =-1;
		bestChromosome =-1;
		for (int i =0;i<numOfChromosome;i++){
			if (chromosomes[i].fitnessValue > maxFitnessValue)
			{bestChromosome = i; maxFitnessValue = chromosomes[i].fitnessValue;}
		}
	}
	
	public int getSumOfFitnessValue () {
		maxFitnessValue =-1;
		bestChromosome =-1;
		sumOfFitnessValue =0;
		for (int i =0;i<numOfChromosome;i++){
			sumOfFitnessValue += chromosomes[i].fitnessValue;
		}
		return sumOfFitnessValue;
	}
	
	public void setPecentFit () {
		if (sumOfFitnessValue==0) sumOfFitnessValue = 1;
		for (int i =0;i<numOfChromosome;i++)
			chromosomes[i].pecentFit = ((chromosomes[i].fitnessValue * 100)/sumOfFitnessValue);
	}
	
	// tai tao, chon loc
	public void Reproduction () {
		Random ra = new Random ();
		int position, ballPosition;
		
		Population p_temp = new Population();
		
		int[] circle = new int[100];
		// tạo bánh xe
		position =0;
		int len =0;
		for (int i =0;i<numOfChromosome;i++){
			len =  (chromosomes[i].pecentFit);
			// dat len banh xe
			for (int t=0; t<len;t++)
				circle[position++] = i;
		}
		// dien vao cho trong tren banh xe
		while (position<100) circle[position++] = bestChromosome; // dien them = con tot nhat
		
		// lua chon
		for (int i =0;i<numOfChromosome;i++){
			ballPosition = ra.nextInt(100);
		//	Log.d("Ball Position", ""+ballPosition + "| " +circle[ballPosition] +"-" + chromosomes[ circle[ballPosition]].gen[0] );
			p_temp.chromosomes[i] = new Chromosome();
			p_temp.chromosomes[i].copy( chromosomes[circle[ballPosition]]);
			
		}
		// gan lai.
		
		p_temp.maxFitnessValue = this.maxFitnessValue;
		p_temp.bestChromosome = this.bestChromosome;
		
		// thay the
		for (int i =0;i<numOfChromosome;i++)
			this.chromosomes[i].copy(p_temp.chromosomes[i]);
				
	}
	
	// toan tu ghep Crossover // ti le 100% 
	public void CrossOver (Chromosome a, Chromosome b) {
		// hai con moi
		Chromosome newA, newB;
		newA = new Chromosome(); newB = new Chromosome();
		
		int len =Chromosome.numOfGen ;
		// vi tri lai ghep
		int position = new Random().nextInt(len);
		
		// sao chep doan dau // nguyen ban A - B
		for (int i=0;i<position;i++){
			newA.gen[i] = a.gen[i];
			newB.gen[i] = b.gen[i];
		}
		
		// sao chep doan sau // trao doi A - B
		for (int i=position;i<len;i++){
			newA.gen[i] = b.gen[i];
			newB.gen[i] = a.gen[i];
		}
		
		// thay doi bo me
		b.copy(newB);
		a.copy(newA);
	}
	
	// tien hoa 
	public void Evolution () {
		// ghi log the he hien tai
		logPopulation(generation, "Original");
		
		// tai san xuat the he hien tai.
		this.Reproduction();
		logPopulation(generation, "Reproduction");
		
		// mang kiem tra
		boolean check [] = new boolean [numOfChromosome];
		// reset mang 
		for (int i=0;i<numOfChromosome;i++) check [i] = false;
		
		Random ra = new Random(); // ngau nhien
		int id_a,id_b=0; // hai ca the lai ghep
		
		// lai ghep  100% cac ca the cua quan the tao ra quan the moi
		for (int i =0; i<numOfChromosome/2;i++) {
			
			id_a = ra.nextInt(numOfChromosome);
			while (check[id_a]  )id_a = ra.nextInt(numOfChromosome);
			id_b = ra.nextInt(numOfChromosome);
			while (check[id_b]) id_b = ra.nextInt(numOfChromosome);
			
			// kiem tra lan nua cho chac an
			if (!check[id_a] && !check[id_b]) CrossOver(chromosomes[id_a], chromosomes[id_b]); 
			
			check[id_a]= true; check[id_b] = true;
		}
		
		logPopulation(generation, "CrossOver");
		
		// thay doi thong so the he
		generation++; // tang the he tiep theo
		
	}
	
	// name : REPRODUCTION - CROSSOVER
	public void logPopulation (int generation, String name){
		File root = Environment.getExternalStorageDirectory();
		String path = root+"/DoodleTetris/";
		boolean exists = (new File(path).exists());
		if (!exists) {new File(path).mkdirs();}
		
		File mapInfo = new File(path+ "Generation_"+ Integer.toString(generation)+"_"+name + "_Log.txt" );
		if (!mapInfo.exists()){
		try {
			mapInfo.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileWriter info = null ;
		try {
			info = new FileWriter(mapInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter outer = new BufferedWriter(info);
		// Write log entries to file
		
		try {
				outer.write(" ==========GENERATION  "+ Integer.toString(generation) +" STATISTIC =========== :\n");
			
				String ChromosomeStat= "";
				// ghi thong so tung Chromosome
				
				for (int i =0;i<numOfChromosome;i++) {
					ChromosomeStat += " Chromosome " +Integer.toString(i) +":" ;
					// thong so gen
					for (int k =0; k<Chromosome.numOfGen;k++)
						ChromosomeStat +=  Integer.toString( chromosomes[i].gen[k])+ " | "  ;
					
					// thong so
					ChromosomeStat +="\n FITNESS VALUE :" +  Integer.toString( chromosomes[i].fitnessValue) ;
					ChromosomeStat +=" ||PERCENT FIT :" +  Integer.toString( chromosomes[i].pecentFit) ;
					ChromosomeStat += "\n";
					outer.write(ChromosomeStat);
					ChromosomeStat= "";
				}
				outer.write("-- RESULT --\n");
			outer.write("Max Fitness Value : " +Integer.toString (maxFitnessValue) +"\n");
			outer.write("Best Chromosome : " +Integer.toString (bestChromosome) +"\n");
			outer.write("Sum of  Fitness Value : " +Integer.toString (sumOfFitnessValue) +"\n");
			
			outer.write(" ========== END GENERATION  "+ Integer.toString(generation) +" STATISTIC =========== :\n");
			
			outer.write("\n\n NOTE GEN \n " +
					"1/ The Height of the TALLEST column // chieu cao cao nhat\n" +
					"2/ Hole Count // so lo  \n" +
					"3/ Lines cleared  // so dong duoc xoa\n" +
					"4/ max different height // chenh lech cot cao nhat va thap nhat \n" +
					"5/ Deepest hole // do sau cua lo thap nhat \n" +
					"6/ total dept of holes // tong chieu sau cua cac lo  \n" +
					"7/ Horizontal Roughness // do nham be mat ngang \n" +
					"8/ Sum of Height // tong chieu cao \n");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Cannot create files");
		}
		
		try {
			outer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			Log.d("exst", "exst");
			
		}
	}
}
