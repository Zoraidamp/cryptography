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
		if (number.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO)==0) return false;
		BigInteger[] results = count();
		BigInteger a = BigInteger.ONE;
		BigInteger x = BigInteger.ONE;
		for(int i=0; i<howMany; i++){
			while(x.compareTo(BigInteger.ONE)==0 || x.compareTo(number.subtract(BigInteger.ONE))==0){
				System.out.println("******x: "+x+" number: "+number+" i: "+i);
				a = a.add(BigInteger.ONE);
				x = chooseA(a);
				System.out.println("______x: "+x+" a: "+a);
			}
			BigInteger j=BigInteger.ONE;
			System.out.println("<<<<j: "+j+" s: "+results[0]);
			while (j.compareTo(results[0])==-1 && x.compareTo(number.subtract(BigInteger.ONE))!=0 ){
				System.out.println("j: "+j+" s: "+results[0]);
				x = x.modPow(BigInteger.valueOf(2), number);
				if (x.compareTo(BigInteger.ONE )==0) return false;
				j=j.add(BigInteger.ONE);
				System.out.println("(@)x: "+x+" s: "+results[0]);
			}
			if (x.compareTo(number.subtract(BigInteger.ONE))!=0) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		BigInteger pom = BigInteger.valueOf(11);
		MillerRabin rabin = new MillerRabin(pom);
		System.out.println("***"+rabin.testRabin(10));
		//rabin.count();		
	}

}
