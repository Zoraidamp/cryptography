import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;


public class MainRabinMiller {	
public BigInteger[] input = new BigInteger[3];

public int readFile() throws IOException{
	int howManyLines =0;
	String file = "wejscie.txt", line = null;
	
	FileReader fileReader = new FileReader(file);
    BufferedReader bufferedReader = new BufferedReader(fileReader);
 
    while((line = bufferedReader.readLine()) != null) {	
    	input[howManyLines] = new BigInteger(line.toString());
    	howManyLines++;
   }    
    
   bufferedReader.close();  
   return howManyLines;
}
	
public static void main(String[] args) throws IOException {
	MainRabinMiller mainRM = new MainRabinMiller();
	int lines = mainRM.readFile();
	for(int i=0; i<lines; i++){
		System.out.println(mainRM.input[i]);
	}	
	
}

}
