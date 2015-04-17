import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ECB {
//------ global values
public int widthCrypto = 35;
public int heightCrypto = 25;
public String input = "plain.bmp";
public String output = "ecb_crypto.bmp";
//in key only small letters
String key = "tellmesomething";
	
//----	

	public ECB(){	}
	
	public ECB(int cryptoX, int cryptoY, String inputF, String outputF){
		widthCrypto = cryptoX; 
		heightCrypto = cryptoY;
		input = inputF;
		output = outputF;
	}
	
	public int[][] oneBlockECB(int [][] imgTab, int blockX, int blockY, int sizeX, int sizeY){
		int pomKey = 0, keyAt;
		for (int y = 0; y<heightCrypto; y++){
			for (int x=0; x<widthCrypto; x++){
				if (pomKey >= key.length()) pomKey = 0;
				keyAt = key.charAt(pomKey)-97;
				//if(y<sizeY && x<sizeX) System.out.println("(x,y):("+(blockX+x)+", "+(blockY+y)+")");
				
				if(y<sizeY && x<sizeX) imgTab[blockX+x][blockY+y]=(imgTab[blockX+x][blockY+y] + keyAt)%2;
				pomKey++;

			}
		}
		
		return imgTab;
	}

	public int[][] codeECBTab(int [][] imgTab, int howManyW, int howManyH, int extraX, int extraY){
		int pom=0, keyAt;
		System.out.println("**w:"+howManyW+", **h:"+howManyH);
		System.out.println("&extraX:"+extraX+", &extraY:"+extraY);
		for(int y=0; y<howManyH; y++){
			for(int x=0; x<howManyW; x++){
				imgTab = oneBlockECB(imgTab, x*widthCrypto, y*heightCrypto, widthCrypto, heightCrypto);
			}
		}	

		if (extraX > 0){
			for(int y=0; y<howManyH; y++){
				imgTab = oneBlockECB(imgTab, howManyW*widthCrypto, y*heightCrypto, extraX, heightCrypto);
			}
		}
		
		if (extraY > 0){
			for(int x=0; x<howManyW; x++){
				imgTab = oneBlockECB(imgTab, x*widthCrypto, howManyH*heightCrypto, widthCrypto, extraY);
			}
		}
		
		if (extraX > 0){
				imgTab = oneBlockECB(imgTab, howManyW*widthCrypto, howManyH*heightCrypto, extraX, extraY);
		}
		return imgTab;
	}
	
	public void codeECB() throws IOException{		
		BufferedImage bimg = ImageIO.read(new File(input));
		System.out.println("w: "+bimg.getWidth()+" h: "+bimg.getHeight());
		int[][] imageTab=  new int[bimg.getWidth()][ bimg.getHeight()];

		for(int i=0; i<bimg.getWidth(); i++){
			for(int j=0; j<bimg.getHeight(); j++){
				if (bimg.getRGB(i, j) == Color.BLACK.getRGB()){
					imageTab[i][j]=1;		
				}
				else imageTab[i][j]=0;
				//System.out.print(imageTab[i][j]);
			}	
			//System.out.println();
		}	

		int howManyW = bimg.getWidth()/widthCrypto, howManyH = bimg.getHeight()/heightCrypto;
		int extraX = bimg.getWidth()%widthCrypto, extraY = bimg.getHeight()%heightCrypto;
		imageTab = codeECBTab(imageTab, howManyW, howManyH, extraX, extraY);
		for(int i=0; i<bimg.getHeight(); i++){
			for(int j=0; j<bimg.getWidth(); j++){	
				//System.out.print(imageTab[j][i]);
				if (imageTab[j][i]==1) bimg.setRGB(j, i, Color.BLACK.getRGB());
				else bimg.setRGB(j, i, Color.WHITE.getRGB());
			}
			//System.out.println();
		}	

		ImageIO.write(bimg, "BMP", new File(output));
	}
	

}
