import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class Euklides {
	
	
	public int[][] readFile() throws IOException{
		int i=0,rows = 1;
		String file = "uklad.txt", line = null;
		
		FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null) {
        	rows++;
       }   
		
        int[][] numbers = new int[rows][2];
        line = null;
        fileReader = new FileReader(file);
        bufferedReader = new BufferedReader(fileReader);
        while((line = bufferedReader.readLine()) != null) {
        	
        	//System.out.println("*"+line.substring(line.indexOf(' ')+1));
        	numbers[i][0] = Integer.parseInt( line.substring(0, line.indexOf(' ')) );
        	numbers[i][1] = Integer.parseInt( line.substring(line.indexOf(' ')+1) );
        	i++;
       }    
        
       bufferedReader.close();  
       return numbers;
	}
	
	
	public int nwd(int a, int b){
		int pom;
		
		while (b != 0){
			pom = b;
			b = a % b;
			a = pom;
		}
		return a;
	}
	
	
	public int[] sth(int a, int b){
		//nwd(a, b) = r = 1 = b*y + a*x
		int pomR, pomY, pomX;
		int pom = b/a;
		int prevR = a;
		int prevY = 0;
		int prevX = 1;
		int r = b - pom*a;
		int y = 1;
		int x = (-1)*pom;
		//System.out.println(x+" "+y+"->"+(x*a+y*b)+"r "+r);
		while (r>1){
			pom = prevR/r;
			
			pomR = r;
			r = prevR - pom*r;
			prevR = pomR;
			
			pomY = y;
			y = prevY - pom*y;
			prevY = pomY;
			
			pomX = x;
			x = prevX - pom*x;
			prevX = pomX;
			
			//System.out.println(x+" "+y+"->"+(x*a+y*b)+"r "+r);
		
		}
		//System.out.println(x+" "+y+"->"+(x*a+y*b)+" "+a+" "+b);
		int[] results = {x, y};
		return results;
	}
	
	public int[][] pom(int[][] results){
		return results;
	}
	
	public int chineseRest(int a, int aMod, int b, int bMod){
		int[] tabE = sth(aMod, bMod);
		//System.out.println("**"+tabE[0]+" "+tabE[1]+"->"+aMod+"->"+bMod );
		int x = (tabE[0]*aMod*b + tabE[1]*bMod*a + 10*aMod*bMod)%(aMod*bMod);
		//if (x<0) x+= (aMod*bMod);
		//System.out.println(x+" mod "+(tabE[0]*aMod*b + tabE[1]*bMod*a) );
		
		return x;
	}

	public static void main(String[] args) throws IOException {
		Euklides eu = new Euklides();
		//System.out.println( eu.nwd(7, 4) );
		int[][] numbers = eu.readFile();
		int pom = 1;
		//System.out.println(numbers[0][1]);
		
		for(int i=0; i<numbers.length-1; i++){
			if (numbers[i][0]<=0 || numbers[i][1]<=0){
				System.out.println("Zle dane, liczby musza byc ieksze od 0! Koniec programu");
				return;
			}
		}
		
		for(int i=0; i< numbers.length-2; i++){
			pom *= numbers[i][1];
			if ( eu.nwd(pom, numbers[i+1][1]) > 1 ){
				System.out.println("Zle dane, liczby modulo nie sa wzgledem siebie pierwsze! Koniec programu");
				return;
			}
		}
		
		int pomMod = numbers[0][1], x=numbers[0][0];
		for(int i=1; i< numbers.length-1; i++){
			x = eu.chineseRest(x, pomMod, numbers[i][0], numbers[i][1]);
			pomMod *= numbers[i][1]; 
			
		}
		//eu.extendedEuklides(84, 15);
		//eu.chineseRest(2, 4, 6, 7);
		
		
        // Assume default encoding.
        FileWriter fileWriter =
            new FileWriter("crt.txt");

        // Always wrap FileWriter in BufferedWriter.
        BufferedWriter bufferedWriter =
            new BufferedWriter(fileWriter);

        // Note that write() does not automatically
        // append a newline character.
        bufferedWriter.write(x+" "+pomMod);


        // Always close files.
        bufferedWriter.close();
		System.out.println("rozwiazanie: "+x+" "+pomMod);
	}

}
