import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class CBC {
	//------ global values
	public int widthCrypto = 35;
	public int heightCrypto = 25;
	public String input = "plain.bmp";
	public String output = "cbc_crypto.bmp";
	//in key only small letters
	String key = "tellmesomething";		
	//----	
	
	public CBC(){ }
	
	public CBC(int cryptoX, int cryptoY, String inputF, String outputF){
		widthCrypto = cryptoX; 
		heightCrypto = cryptoY;
		input = inputF;
		output = outputF;
	}

	public int[][] oneBlockCBC(int [][] imgTab, int blockX, int blockY, int sizeX, int sizeY){
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
	
	public int[][] xorTabs(int[][] pomBlock, int[][] imgTab, int sizeX, int sizeY, int startX, int startY){
	
		
		for (int y = 0; y<sizeY; y++){
			for(int x=0; x<sizeX; x++){
				imgTab[startX+x][startY+y] = imgTab[startX+x][startY+y]^pomBlock[x][y];
				//System.out.print(imgTab[startX+x][startY+y]);
			}
			//System.out.println();
		}
		return imgTab;
	}
	
	public int[][] codeCBCTab(int [][] imgTab, int howManyW, int howManyH, int extraX, int extraY){
		int pom=0, keyAt;
		System.out.println("**w:"+howManyW+", **h:"+howManyH);
		System.out.println("&extraX:"+extraX+", &extraY:"+extraY);
		
		
		Random rand = new Random();
		int[][] pomBlock = new int[widthCrypto][heightCrypto]; 
		//int answer = rand.nextInt(2);
		
		for(int y=0; y<heightCrypto; y++){
			for(int x=0; x<widthCrypto; x++){
				pomBlock[x][y] = rand.nextInt(2); 
			}
		}
		
		//xorTabs(pomBlock, imgTab, widthCrypto, heightCrypto, 0, 0);
		for(int y=0; y<howManyH; y++){
			for(int x=0; x<howManyW; x++){
				imgTab = xorTabs(pomBlock, imgTab, widthCrypto, heightCrypto, x*widthCrypto, y*heightCrypto);
				imgTab = oneBlockCBC(imgTab, x*widthCrypto, y*heightCrypto, widthCrypto, heightCrypto);
				
				for(int y1=0; y1<heightCrypto; y1++){
					for(int x1=0; x1<widthCrypto; x1++){
						pomBlock[x1][y1] = imgTab[x*widthCrypto+x1][y*heightCrypto+y1];
					}
				}
			}
		}	
		int[][] pomBlock2= new int[widthCrypto][heightCrypto];
		for(int y1=0; y1<heightCrypto; y1++){
			for(int x1=0; x1<widthCrypto; x1++){
				pomBlock2[x1][y1] = pomBlock[x1][y1];
			}
		}
		
		if (extraX > 0){
			for(int y=0; y<howManyH; y++){
				imgTab = xorTabs(pomBlock, imgTab, extraX, heightCrypto, howManyW*widthCrypto, y*heightCrypto);
				imgTab = oneBlockCBC(imgTab, howManyW*widthCrypto, y*heightCrypto, extraX, heightCrypto);
				
				for(int y1=0; y1<heightCrypto; y1++){
					for(int x1=0; x1<widthCrypto; x1++){
						if (x1<extraX)pomBlock[x1][y1] = imgTab[howManyW*widthCrypto+x1][y*heightCrypto+y1];
						else pomBlock[x1][y1] = 0;
					}
				}
			}
		}
		
		for(int y1=0; y1<heightCrypto; y1++){
			for(int x1=0; x1<widthCrypto; x1++){
				pomBlock[x1][y1] = pomBlock2[x1][y1];
			}
		}

		if (extraY > 0){
			for(int x=0; x<howManyW; x++){
				imgTab = xorTabs(pomBlock, imgTab, widthCrypto, extraY, x*widthCrypto, howManyH*heightCrypto);
				imgTab = oneBlockCBC(imgTab, x*widthCrypto, howManyH*heightCrypto, widthCrypto, extraY);
				
				for(int y1=0; y1<heightCrypto; y1++){
					for(int x1=0; x1<widthCrypto; x1++){
						if (y1<extraY)pomBlock[x1][y1] = imgTab[x*widthCrypto+x1][howManyH*heightCrypto+y1];
						else pomBlock[x1][y1] = 0;
					}
				}
			}
		}
		
		if (extraX > 0){
			imgTab = xorTabs(pomBlock, imgTab, extraX, extraY, howManyW*widthCrypto, howManyH*heightCrypto);
			imgTab = oneBlockCBC(imgTab, howManyW*widthCrypto, howManyH*heightCrypto, extraX, extraY);
			
			for(int y1=0; y1<heightCrypto; y1++){
				for(int x1=0; x1<widthCrypto; x1++){
					if (y1<extraY && x1<extraX)pomBlock[x1][y1] = imgTab[howManyW*widthCrypto+x1][howManyH*heightCrypto+y1];
					else pomBlock[x1][y1] = 0;
				}
			}
		}
		return imgTab;
	}
	
	
	public void codeCBC() throws IOException{		
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
		
		
		imageTab = codeCBCTab(imageTab, howManyW, howManyH, extraX, extraY);
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
