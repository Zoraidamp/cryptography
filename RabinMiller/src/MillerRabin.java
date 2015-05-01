import java.math.BigInteger;


public class MillerRabin {
public BigInteger number;
	
	public MillerRabin(BigInteger nr){
		number = nr;
	}
	
	public BigInteger[] count(){
		BigInteger multi = number.subtract(BigInteger.ONE);
		BigInteger power = BigInteger.ZERO;
		BigInteger pom = multi.mod( BigInteger.ONE.add(BigInteger.ONE));
		while (pom.compareTo(BigInteger.ZERO)==0){
			power = power.add(BigInteger.ONE);
			multi = multi.divide(BigInteger.ONE.add(BigInteger.ONE));
			pom = multi.mod( BigInteger.ONE.add(BigInteger.ONE));
		}
		BigInteger[] results= {multi, power};
		//System.out.println(multi+"*"+"2^"+power);
		return results;
	}
	
	
	public BigInteger chooseA(BigInteger a){
		BigInteger[] results = count();
		BigInteger x = a.modPow(results[1], number);
		return x;
	}
	
	public boolean testRabin(int howMany){
		BigInteger[] results = count();
		BigInteger a = BigInteger.valueOf(2);
		int i=0;
		BigInteger x = chooseA(a);
		while(i<howMany && (x.compareTo(BigInteger.ONE)==0 || x.compareTo(number.subtract(BigInteger.ONE))==0) ){
			a.add(BigInteger.ONE);
			x = chooseA(a);
		}
		BigInteger j=BigInteger.ONE;
		while (j.compareTo(results[0])==-1 && x.compareTo(number.subtract(BigInteger.ONE))!=0 ){
			x = x.modPow(BigInteger.valueOf(2), number);
			if (x.comp) return false;
		}
	}
	
	public static void main(String[] args) {
		BigInteger pom = BigInteger.valueOf(9);
		MillerRabin rabin = new MillerRabin(pom);
		//System.out.println(fermat.test());
		rabin.count();		
	}

}
