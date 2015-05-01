import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;


public class TestFermata {
public BigInteger number;
	
	public TestFermata(BigInteger nr){
		number = nr;
	}
	
	
	public void printResult(boolean result) throws FileNotFoundException{
		String output ="";
		if (test()) output = "prawdopodobnie pierwsza" ;
		else output ="na pewno z³o¿ona";
		PrintWriter zapis = new PrintWriter("wyjscie.txt");
	    zapis.println(output);
	    zapis.close();
	}
	
	public boolean test(){
		BigInteger two = BigInteger.ONE;
		two = two.add(BigInteger.ONE);
		BigInteger result = two.modPow(number.subtract(BigInteger.ONE), number);
		//System.out.println(result);
		if (result.compareTo(BigInteger.ONE)==0) return true;
		else return false;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		BigInteger pom = BigInteger.valueOf(561);
		TestFermata fermat = new TestFermata(pom);
		//System.out.println(fermat.test());
		fermat.printResult(fermat.test());
	}

}
