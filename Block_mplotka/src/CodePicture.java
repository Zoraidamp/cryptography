import java.io.IOException;


public class CodePicture {

	public static void main(String[] args) throws IOException {
		ECB ecb = new ECB();
		ecb.codeECB();
		CBC cbc = new CBC();
		cbc.codeCBC();

	}

}
