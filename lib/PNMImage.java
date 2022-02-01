package lib;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.StringTokenizer;

public class PNMImage{

	private String format;
	private int height;
	private int width;
	private int maxValue;
	private int[][][] pixels;
	private BufferedReader br;
	private StringTokenizer st;

	public PNMImage(String filename){
		readImage(filename);
	}

	public PNMImage(){

	}

	public PNMImage(String format,int height,int width){
		this.format = format;
		this.height = height;
		this.width = width;
		this.maxValue = 255;
		if(this.format.equals("P3")){
			this.pixels = new int[height][width][3];
			for(int i=0;i<height;i++){
				for(int j=0;j<width;j++){
					for(int k=0;k<3;k++){
						this.pixels[i][j][k]=255;
					}
				}
			}
		}
		else{
			this.pixels = new int[height][width][1];
			if(this.format.equals("P2")){
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						this.pixels[i][j][0]=255;
					}
				}
			}
		}
	}

	public String getFormat(){
		return this.format;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public void setFormat(String format){
		this.format = format;
	}

	public int getPixel(int X,int Y){
		return getPixel(X,Y,0);
	}

	public int getPixel(int X,int Y,int Z){
		return this.pixels[X][Y][Z];
	}

	public void setPixel(int X,int Y,int Val){
		this.pixels[X][Y][0]=Val;
	}

	public void setPixel(int X,int Y,int Z,int Val){
		this.pixels[X][Y][Z]=Val;
	}

	public int getMaxValue(){
		return this.maxValue;
	}

	public void setMaxValue(int value){
		this.maxValue = value;
	}

	private void readImage(String filename){
		try{
			br = new BufferedReader(new FileReader(filename));
			st = new StringTokenizer(br.readLine());
			format = next();
			this.width = nextInt();
			this.height = nextInt();
			if(format.equals("P3")){
				this.maxValue=nextInt();
				this.pixels = new int[height][width][3];
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						for(int k=0;k<3;k++){
							this.pixels[i][j][k]=nextInt();
						}
					}
				}
			}
			else if(format.equals("P2")){
				this.maxValue = nextInt();
				this.pixels = new int[height][width][1];
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						this.pixels[i][j][0]=nextInt();
					}
				}
			}
			else if(format.equals("P1")){
				this.maxValue = 1;
				this.pixels = new int[height][width][1];
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						this.pixels[i][j][0]=nextInt();
					}
				}
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public void writeImage(String filename){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			bw.write(this.format);
			bw.newLine();
			bw.write(Integer.toString(this.width)+" "+Integer.toString(this.height));
			bw.newLine();
			if(this.format.equals("P3")){
				bw.write(Integer.toString(this.maxValue));
				bw.newLine();
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						for(int k=0;k<3;k++){
							bw.write(Integer.toString(this.pixels[i][j][k])+" ");
						}
					}
					bw.newLine();
				}
			}
			else if(this.format.equals("P2")){
				bw.write(Integer.toString(this.maxValue));
				bw.newLine();
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						bw.write(Integer.toString(this.pixels[i][j][0])+" ");
					}
					bw.newLine();
				}
			}
			else if(this.format.equals("P1")){
				for(int i=0;i<height;i++){
					for(int j=0;j<width;j++){
						bw.write(Integer.toString(this.pixels[i][j][0])+" ");
					}
					bw.newLine();
				}
			}
			bw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private String next(){
		while(st==null || !st.hasMoreElements()){
			try{
				st = new StringTokenizer(br.readLine().trim());
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	private int nextInt(){
		return Integer.parseInt(next());
	}

	
}