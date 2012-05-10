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
	public static int numOfChromosome = 100; // lage is good
	public static int numOfGeneration = 100;
	public static float ProbCross  = 1.0f;
	public static float ProbMutation  = 0.01f;
	
	
	int generation =0 ;// the he quan the
	int maxFitnessValue; // fitnessValue of bestChromosome
	int bestChromosome; // id
	int sumOfFitnessValue; // tong gia tri thich nghi
	int numOfDiffChromosome =0;
	int numOfMutationChromosome;
	int[] mutationChromosome;
	
	
	// population
	Chromosome chromosomes[] ;
	
	public Population () {
		chromosomes = new  Chromosome[numOfChromosome];
		maxFitnessValue=-1;
		bestChromosome=-1;
		sumOfFitnessValue = 0;
		generation =0 ;
		mutationChromosome = new int [100];
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
	
	// generate full weight
		public void generatePopulation_FULLSPEC () {
			int w = -Chromosome.RangeofGenValue;
			
			for (int i =0;i<numOfChromosome;i++){
				chromosomes[i] = new Chromosome();
				for (int k =0; k <Chromosome.numOfGen;k++){
					if (w == Chromosome.RangeofGenValue+1) w = -Chromosome.RangeofGenValue;
				chromosomes[i].gen[k] = w++;
				}
				
			
			}
			maxFitnessValue =-1;
			bestChromosome =-1;
			sumOfFitnessValue = -1;
			generation =0 ;
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
		if (sumOfFitnessValue==0){
			for (int i =0;i<numOfChromosome;i++){
				chromosomes[i]= Chromosome.generateChromosome();
				chromosomes[i].pecentFit=250;
				}
		return;
		}
		
		
		for (int i =0;i<numOfChromosome;i++)
			chromosomes[i].pecentFit = ((chromosomes[i].fitnessValue * 1000)/sumOfFitnessValue);
	}
	
	// tai tao, chon loc
	public void Reproduction () {
		Random ra = new Random ();
		int position, ballPosition;
		
		Population p_temp = new Population();
		boolean[] isChose = new boolean[numOfChromosome];
		
		for (int i=0;i<numOfChromosome;i++) isChose[i] =false;
		String content ="";
		int[] circle = new int[1000];
		// tạo bánh xe
		position =0;
		int len =0;
		for (int i =0;i<numOfChromosome;i++){
			len =  (chromosomes[i].pecentFit);
			// dat len banh xe
			for (int t=0; t<len;t++){
				circle[position++] = i;
				content+= "" + i;		
			}
		}
		// dien vao cho trong tren banh xe
		while (position<1000){ circle[position++] = bestChromosome;content+= "" + bestChromosome;		} // dien them = con tot nhat }
		
		
		logString(content,"circle");
		
		// lua chon
		for (int i =0;i<numOfChromosome;i++){
			ballPosition = ra.nextInt(1000);
		//	Log.d("Ball Position", ""+ballPosition + "| " +circle[ballPosition] +"-" + chromosomes[ circle[ballPosition]].gen[0] );
			p_temp.chromosomes[i] = new Chromosome();
			p_temp.chromosomes[i].copy( chromosomes[circle[ballPosition]]);
			
			isChose[circle[ballPosition]] = true;
		}
		
		
		
		for (int i=0;i<numOfChromosome;i++) if (isChose[i]) numOfDiffChromosome++;
		
		// gan lai.
		p_temp.maxFitnessValue = this.maxFitnessValue;
		p_temp.bestChromosome = this.bestChromosome;
		
		// thay the
		for (int i =0;i<numOfChromosome;i++)
			this.chromosomes[i].copy(p_temp.chromosomes[i]);

	}
	
	// toan tu ghep Crossover // ti le 100% 
	public boolean CrossOver (Chromosome a, Chromosome b) {
		// hai con moi
		Chromosome newA, newB;
		newA = new Chromosome(); newB = new Chromosome();
		
		int len =Chromosome.numOfGen ;
		// vi tri lai ghep
		int position = new Random().nextInt(len);
		
		boolean f=true;
		// sao chep doan dau // nguyen ban A - B
		for (int i=0;i<position;i++){
			if ( a.gen[i]!=b.gen[i]) f =false;
			newA.gen[i] = a.gen[i];
			newB.gen[i] = b.gen[i];

			
		}
		if (f) return false;
		f = true;
		// sao chep doan sau // trao doi A - B
		for (int i=position;i<len;i++){
			if ( a.gen[i]!=b.gen[i]) f =false;
			newA.gen[i] = b.gen[i];
			newB.gen[i] = a.gen[i];
		}
		
		if (f) return false;
		
		// thay doi bo me
		b.copy(newB);
		a.copy(newA);
		
		return true;
	}
	// Dot bien
	
		public boolean Mutation (Chromosome s) {
			boolean f = false;
			Random r = new Random();
			int ra;
			int sign;
			for (int i =0;i< Chromosome.numOfGen;i++)
			{
				ra =(r.nextInt(1000));
				
			//    Log.d("Mutation", "Mutationed :"+ ra +" -  " + ProbMutation*1000);
				if (Math.abs(ra) <=( ProbMutation*1000)){
					f = true;
					if (r.nextBoolean())sign = 1; else sign = -1;
					s.gen[i]=  ((r.nextInt() + (int) System.nanoTime()) %Chromosome.RangeofGenValue) * sign;
				} 
				
			}
			return f;
		}
	
	// tien hoa 
	public void Evolution () {
		// ghi log the he hien tai
		logPopulation(generation, "Original");
		
		// tai san xuat the he hien tai.
		this.Reproduction();
		logPopulation(generation, "Reproduction");
		
		// tra ve 0 so luong ca the dot bien
		
		numOfMutationChromosome =0;
		// dot bien
		for (int i =0;i<numOfChromosome;i++)
			if (Mutation (chromosomes[i])) {
				mutationChromosome[numOfMutationChromosome++] = i;
			}
		// ghi log dot bien
		logPopulation(generation, "Mutation");
		
		
		numOfDiffChromosome =0;
		// mang kiem tra
		boolean check [] = new boolean [numOfChromosome];
		// reset mang 
		for (int i=0;i<numOfChromosome;i++) check [i] = false;
		
		Random ra = new Random(); // ngau nhien
	//	int id_a,id_b=0; // hai ca the lai ghep
		int chose=0;
		// lai ghep  100% cac ca the cua quan the tao ra quan the moi
		
		// khi ko trao doi dc con nao thi tao moi
		for (int id_a = 0;id_a< numOfChromosome-1;id_a++)
			for (int id_b = id_a+1 ;id_b< numOfChromosome;id_b++)
			{
				// kiem tra lan nua cho chac an	
			if (!check[id_a] && !check[id_b] ) 
				{
				if (CrossOver(chromosomes[id_a], chromosomes[id_b])){
				check[id_a]= true; check[id_b] = true;
				chose+=2;
					}
				}
		}
		// neu toan la anh em, bo sung 1/2 ca the moi
		if (chose==0){
			for (int id_b = 0 ;id_b< numOfChromosome/2;id_b++)
			{
				chromosomes[id_b] = Chromosome.generateChromosome();
			}
			
		}
		int id_a,id_b=0; // hai ca the lai ghep
		String str1="", str2="";
		// xu ly nhung con con lai : trung nhau - anh em
		while (chose<numOfChromosome) {
			id_a = ra.nextInt(numOfChromosome);
			while (check[id_a]  )id_a = ra.nextInt(numOfChromosome);
			// chon ID B tranh ID A
			id_b = ra.nextInt(numOfChromosome);
			while (check[id_b]) id_b = ra.nextInt(numOfChromosome);
			
			// kiem tra lan nua cho chac an	
			if (!check[id_a] && !check[id_b] ) 
				{
				check[id_a] = true; check[id_b] = true;
				// thay the con co gia tri thich nghi thap hon
				for (int i=0;i<Chromosome.numOfGen;i++) {
					str1 += "" + chromosomes[id_a].gen[i]+ " | ";
					str2 += "" + chromosomes[id_b].gen[i]+ " | ";
				}
				str1+="\n";
				str2+="\n";
				if (chromosomes[id_a].fitnessValue> chromosomes[id_b].fitnessValue)
				{
					while (!CrossOver(chromosomes[id_a], chromosomes[id_b]))
						 id_b = ra.nextInt(numOfChromosome);
				}
				else 
				{	
					while (!CrossOver(chromosomes[id_a], chromosomes[id_b]))
					 id_a = ra.nextInt(numOfChromosome);
				}
				chose+=2;
				}
			
		}
		
		// xu ly nhung con anh em
		logString(str1 +"\n"+str2,"CHEC KPARENT");
		logPopulation(generation, "CrossOver");
		
		// thay doi thong so the he
		generation++; // tang the he tiep theo
		
	}
	
	public boolean compareChromosome (Chromosome a, Chromosome b){
		for (int i =0; i<Chromosome.numOfGen;i++)
			if (a.gen[i]!=b.gen[i]) return false;
		return true;
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
						ChromosomeStat +=  Integer.toString( chromosomes[i].gen[k])+ " , "  ;
					ChromosomeStat += ";"  ;
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
			outer.write("num of Difference Chromosome : " +Integer.toString (numOfDiffChromosome) +"\n");
			outer.write(" ========== END GENERATION  "+ Integer.toString(generation) +" STATISTIC =========== :\n");
			
			if (name =="Mutation") {
				outer.write(" ========== MUTATION  "+ Integer.toString(generation) +" STATISTIC =========== :\n");
				for (int i =0;i<numOfMutationChromosome;i++) {
					ChromosomeStat += " Chromosome " +Integer.toString(mutationChromosome[i]) +"\n" ;
						outer.write(ChromosomeStat);
					ChromosomeStat= "";
				}
				
			}
			
			outer.write("\n\n NOTE GEN \n " +
					" BOARD : 10x20, Chromosome : 50 - range : -1000, gen 14 co dot bien10%  "+
					"1/ The Height of the TALLEST column // chieu cao cao nhat\n" +
					"2/ Hole Count // so lo  \n" +
					"3/ Lines cleared  // so dong duoc xoa\n" +
					"4/ max different height // chenh lech cot cao nhat va thap nhat \n" +
					"5/ Deepest hole // do sau cua lo thap nhat \n" +
					"6/ total dept of holes // tong chieu sau cua cac lo  \n" +
					"7/ Horizontal Roughness // do nham be mat ngang \n" +
					"8/ Sum of Height // tong chieu cao \n"+
					"9/ TouchBlock \n"+
					"10/ Ho Sau Nhat \n");
			
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

	// name : REPRODUCTION - CROSSOVER
		public void logString (String content, String fileName){
			File root = Environment.getExternalStorageDirectory();
			String path = root+"/DoodleTetris/";
			boolean exists = (new File(path).exists());
			if (!exists) {new File(path).mkdirs();}
			
			File mapInfo = new File(path+ "Generation_"+ Integer.toString(generation)+"_"+fileName + "_Log.txt" );
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
					outer.write(content);
			
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
