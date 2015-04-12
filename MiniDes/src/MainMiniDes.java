import java.io.IOException;


public class MainMiniDes {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		switch(args[0]){
		case("-e"):
			Crypt cr = new Crypt();
			cr.readFiles();
			cr.crypt();
		break;
		
		case("-d"):
			Decrypt decrypt = new Decrypt();
			decrypt.readFiles();
			decrypt.decrypt();
		break;
	
		case("-a"):
			Analysis an = new Analysis();
			an.readFiles();
			an.crypt();
		break;
		}
	}

}
