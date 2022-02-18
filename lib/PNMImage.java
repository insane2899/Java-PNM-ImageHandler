package lib;

import java.io.IOException;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;




public class PNMImage{

	private int height;
	private int width;
	private int maxValue;
	private int numColors;
	private int[][][] pixels;
	private String originalFormat;
	private static final String PBM_ASCII = "P1";
	private static final String PBM_RAW = "P4";
	private static final String PGM_ASCII = "P2";
	private static final String PGM_RAW = "P5";
	private static final String PPM_ASCII = "P3";
	private static final String PPM_RAW = "P6";
	private byte[] lineSeparator;


	protected PNMImage(String filename){
		readImage(filename);
	}

	private PNMImage(){
	}

	protected PNMImage(PNMImage img){
		this.height = img.getHeight();
		this.width = img.getWidth();
		this.maxValue = img.getMaxValue();
		this.originalFormat = img.getOriginalFormat();
		this.numColors = img.getNumColors();
		this.pixels = new int[height][width][numColors];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				for(int k=0;k<numColors;k++){
					this.pixels[i][j][k]=img.getPixel(i,j,k);
				}
			}
		}
	}

	protected PNMImage(int height,int width,String format,int fillColor){
		this.originalFormat = format;
		this.height = height;
		this.width = width;
		switch(this.originalFormat){
		case PBM_ASCII:
		case PBM_RAW:
			this.numColors = 1;
			this.maxValue = 1;
			break;
		case PGM_ASCII:
		case PGM_RAW:
			this.numColors = 1;
			this.maxValue = 255;
			break;
		case PPM_ASCII:
		case PPM_RAW:
			this.numColors = 3;
			this.maxValue = 255;
			break;
		}
		this.pixels = new int[this.height][this.width][this.numColors];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				for(int k=0;k<numColors;k++){
					this.pixels[i][j][k]=fillColor;
				}
			}
		}
	}

	public int getNumColors(){
		return this.numColors;
	}

	public String getOriginalFormat(){
		return this.originalFormat;
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

	//Two types of writing:
	//One: Writing ASCII in binary, i.e writing '50' as binary(ascii(5))+binary(ascii(0)) i.e <0x35><0x30>
	//Two: Writing '50' entirely as a binary byte i.e 0x16.
	// The readInteger(DataInputStream) reads the first type of binary. Used to decode header as well as data inside file in case of ASCII
	// The DataInputStream.readUnsignedByte() reads the second type of binary.

	private void readImage(String fileName){
		try{
			String ls = System.getProperty("line.separator");
			lineSeparator = ls.getBytes();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			FileInputStream in = new FileInputStream(new File(fileName));
			byte[] buffer = new byte[4096];
			int read = 0;
			do{
				read = in.read(buffer);
				if(read!=-1){
					bout.write(buffer,0,read);
				}
			}while(read!=-1);
			ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
			DataInputStream stream = new DataInputStream(bin);
			readHeader(stream);
			switch(this.originalFormat){
			case PBM_RAW:
				for(int i=0;i<this.height;i++){
					outer:for(int j=0;j<this.width;j+=8){
						int x = stream.readUnsignedByte();
						for(int w = 7;w>=0;w--){
							if(j+7-w==this.width){
								break outer;
							}
							this.pixels[i][j+7-w][0] = (x&(1<<w))>0?1:0;
						}
					}
				}
				break;
			case PBM_ASCII:
				for(int i=0;i<this.height;i++){
					for(int j=0;j<this.width;j++){
						this.pixels[i][j][0]=readInteger(stream);
					}
				}
				break;
			case PGM_RAW:
			case PPM_RAW:
				for(int i=0;i<this.height;i++){
					for(int j=0;j<this.width;j++){
						for(int k=0;k<this.numColors;k++){
							this.pixels[i][j][k]= stream.readUnsignedByte();
						}
					}
				}
				break;
			case PGM_ASCII:
			case PPM_ASCII:
				for(int i=0;i<this.height;i++){
					for(int j=0;j<this.width;j++){
						for(int k=0;k<this.numColors;k++){
							this.pixels[i][j][k]= readInteger(stream);
						}
					}
				}
				break;
			}
			stream.close();
			bout.close();
			bin.close();
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void readHeader(DataInputStream stream)throws IOException{
		byte[] form = new byte[2];
		stream.read(form);
		this.originalFormat = (char)form[0]+""+(char)form[1];
		this.width = readInteger(stream);
		this.height = readInteger(stream);
		switch(this.originalFormat){
		case PBM_RAW:
		case PBM_ASCII:
			this.numColors = 1;
			this.maxValue = 1;
			break;
		case PGM_RAW:
		case PGM_ASCII:
			this.numColors = 1;
			this.maxValue = readInteger(stream);
			break;
		case PPM_ASCII:
		case PPM_RAW:
			this.numColors = 3;
			this.maxValue = readInteger(stream);
			break;
		}
		this.pixels = new int[this.height][this.width][this.numColors];
	}

	private int readInteger(DataInputStream stream)throws IOException{
		int ret = 0;
		boolean foundDigit = false;
		byte[] bytes = new byte[1];
		int b;
		while((b=stream.read(bytes))!=-1){
			char ch = (char)bytes[0];
			if(Character.isDigit(ch)){
				ret = ret*10+Character.digit(ch,10);
				foundDigit = true;
			}
			else{
				if(ch=='#'){
					int length = lineSeparator.length;
					outer: while((b=stream.read(bytes))!=-1){
						for(int i=0;i<length;i++){
							if(bytes[0]==lineSeparator[i]){
								break outer;
							}
						}
					}
					if(b==-1){
						break;
					}
				}
				if(foundDigit){
					break;
				}
			}
		}
		return ret;
	}

	protected void writeImage(String fileName,String outputFormat){
		try{
			switch(outputFormat){
			case PBM_RAW:
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
				out.writeBytes(outputFormat);
				out.writeBytes("\n");
				out.writeBytes(this.width+" "+this.height+"\n");
				for(int i=0;i<this.height;i++){
					int count=7,b=0;
					for(int j=0;j<this.width;j++){
						b |= this.pixels[i][j][0] << count;
						count--;
						if(count==-1){
							count=7;
							out.writeByte(b);
							b=0;
						}
					}
					if(count<7){
						out.writeByte(b);
					}
				}
				out.close();
				break;
			case PGM_RAW:
			case PPM_RAW:
				DataOutputStream out1 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))));
				out1.writeBytes(outputFormat);
				out1.writeBytes("\n");
				out1.writeBytes(this.width+" "+this.height+"\n");
				out1.writeBytes(this.maxValue+"\n");
				for(int i=0;i<this.height;i++){
					for(int j=0;j<this.width;j++){
						for(int k=0;k<this.numColors;k++){
							out1.writeByte(this.pixels[i][j][k]);
						}
					}
				}
				out1.close();
				break;
			case PBM_ASCII:
			case PGM_ASCII:
			case PPM_ASCII:
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
				bw.write(outputFormat+"\n"+this.width+" "+this.height+"\n"+this.maxValue+"\n");
				for(int i=0;i<this.height;i++){
					for(int j=0;j<this.width;j++){
						for(int k=0;k<this.numColors;k++){
							bw.write(this.pixels[i][j][k]+" ");
						}
					}
					bw.write("\n");
				}
				bw.close();
				break;
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	
}