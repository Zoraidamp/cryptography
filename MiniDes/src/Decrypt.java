import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Decrypt {
	String crypt="";
	String key=""; 
	
	String[][] s0 = new String[][]{
			  {"101", "010", "001", "110", "011", "100", "111", "000"},
			  {"001", "100", "110", "010", "000", "111", "101", "011"}
			};
	
	String[][] s1 = new String[][]{
			  {"100", "000", "110", "101", "111", "001", "011", "010"},
			  {"101", "011", "000", "111", "110", "010", "001", "100"}
			};
	
	
	public void readFiles() throws FileNotFoundException{
		File file = new File("crypto.txt");
	    Scanner in = new Scanner(file);
	    crypt = in.next();
	    in.close();  
	    
	    file = new File("key.txt");
	    in = new Scanner(file);
	    key = in.next();
	    in.close(); 

	    
	    //String pom ="0234";
	    //System.out.println((int)pom.charAt(0)-48);
	    /*String c = "11"; // as binary
	    int decimalValue = Integer.parseInt(c, 2);
	    System.out.println(decimalValue);*/
	    //System.out.println(Integer.toBinaryString(i));
	    
	    /*PrintWriter zapis = new PrintWriter("crypto.txt");
	    zapis.println("1234");
	    zapis.close();
	    StringBuffer*/
	}
	
	public String lookAtS(String positions){
		String position0 = positions.substring(0, 4);
		String position1 = positions.substring(4);
		String wynik="";
		int x0 = (int)position0.charAt(0)-48;
		int y0 =  Integer.parseInt(position0.substring(1), 2);
		
		
		int x1 = (int)position1.charAt(0)-48;
		int y1 =  Integer.parseInt(position1.substring(1), 2);
		
		wynik = s0[x0][y0] + s1[x1][y1];
		//System.out.println("s0s1: "+wynik);
		return wynik;
	}
	
	public String keyPrev(int start){
		String pom="";
		int iPom;
		for(int i=0; i<8; i++){
			iPom = (start+i)%9;
			pom += key.charAt(iPom);
		}
		return pom;
	}
	
	public String eGenerate(String x){
		String wynik ="";
		
		wynik += x.charAt(0);
		wynik += x.charAt(1);
		wynik += x.charAt(3);
		wynik += x.charAt(2);
		wynik += x.charAt(3);
		wynik += x.charAt(2);
		wynik += x.charAt(4);
		wynik += x.charAt(5);
		//System.out.println("E("+x+"):"+wynik);
		return wynik;
	}
	
	public String xorString(String a, String b){
		String wynik="";
		int pom;
		for(int i=0; i< a.length(); i++ ){
			pom = ((int)a.charAt(i)-48) ^ ((int)b.charAt(i)-48);
			//System.out.println("a("+i+"): "+((int)a.charAt(i)-48));
			//System.out.println("b("+i+"): "+((int)b.charAt(i)-48));
			wynik += Integer.toBinaryString(pom);
		}
		return wynik;
	}
	
	public String round(String lr, String keyR){
		String wynik=lr;
		String oldL = wynik.substring(0, 6); 
		String oldR = wynik.substring(6); 
		String l = oldR; ;
		String pomr = xorString(eGenerate(oldR), keyR);
		pomr = xorString(lookAtS( pomr ), oldL);
		String r = pomr; 
		wynik = l + r;
		//System.out.println(wynik+"("+keyR+")");
		return wynik;
	}
	
	public void decrypt() throws FileNotFoundException{
		String wynik = crypt, roundKey;
		//wynik = wynik.substring(6) + wynik.substring(0, 6);
		for(int i=7; i>=0; i--){
			roundKey = keyPrev(i);
			wynik = round(wynik, roundKey);
		}
		String finalW = wynik.substring(6) + wynik.substring(0, 6);
		//System.out.println("final: "+finalW);
		
		PrintWriter zapis = new PrintWriter("decrypto.txt");
	    zapis.println(finalW);
	    zapis.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Decrypt cr = new Decrypt();
		cr.readFiles();
		cr.decrypt();
	}

}
