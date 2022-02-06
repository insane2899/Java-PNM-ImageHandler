package lib;

import java.util.List;

public class Histogram{

	static class Point{
		int x,y;
		public Point(int x,int y){
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString(){
			return "("+this.x+","+this.y+")";
		}
	}


	private int[][] histogram;
	private int maxValue;
	private int minValue;
	private int numColors;

	public Histogram(PNMImage image){
		createHistogram(image);
	}

	public Histogram(int numColors){
		this.histogram = new int[256][1];
		this.maxValue = 255;
		this.minValue = 0;
		this.numColors = numColors;
	}

	public int getHistogramLevel(int x){
		return this.histogram[x][0];
	}

	public int getHistogramLevel(int x,int y){
		return this.histogram[x][y];
	}

	private void createHistogram(PNMImage img){
		if(img.getFormat().equals("P1")||img.getFormat().equals("P2")){
			this.histogram = new int[256][1];
			this.numColors = 1;
			this.maxValue = -1;
			this.minValue = 256;
			for(int i=0;i<img.getHeight();i++){
				for(int j=0;j<img.getWidth();j++){
					this.histogram[img.getPixel(i,j)][0]++;
					this.maxValue = Math.max(this.maxValue,img.getPixel(i,j));
					this.minValue = Math.min(this.minValue,img.getPixel(i,j));
				}
			}
		}
		else{
			this.histogram = new int[256][3];
			this.numColors = 3;
			this.maxValue = -1;
			this.minValue = 256;
			for(int i=0;i<img.getHeight();i++){
				for(int j=0;j<img.getWidth();j++){
					for(int k=0;k<numColors;k++){
						this.histogram[img.getPixel(i,j,k)][k]++;
						this.maxValue = Math.max(this.maxValue,img.getPixel(i,j,k));
						this.minValue = Math.min(this.minValue,img.getPixel(i,j,k));
					}
				}
			}
		}
	}

	public PNMImage getHistogramImage(int height,int width){
		try{
			if(numColors==1){
				return getHistogramImageBitmap(height,width);
			}
			else{
				return getHistogramImageColor(height,width);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public PNMImage getHistogramImage(int height,int width,List<Integer> colors){
		try{
			return getHistogramImageColor(height,width,colors);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	private PNMImage getHistogramImageColor(int height,int width)throws Exception{
		check(width,256,"Size small for histogram");
		check(height,200,"Size small for histogram");
		PNMImage img = new PNMImage("P3",height,width);
		int widthHistogram = width - 2*(int)(((double)1/15)*width);
		int yAxis = (width - widthHistogram)/2;
		int xAxis = height - (int)(((double)1/15)*height);
		int axisThickness = 10;
		colorRectangle(img,new Point(xAxis,yAxis),new Point(xAxis+axisThickness,yAxis+widthHistogram),0,3);
		colorRectangle(img,new Point(height-xAxis,yAxis-axisThickness),new Point(xAxis+axisThickness,yAxis),0,3);
		int heightMax = height - 2*(height - xAxis);
		int maxPixelDensity=0;
		for(int i=0;i<256;i++){
			for(int j=0;j<3;j++){
				maxPixelDensity = Math.max(maxPixelDensity,histogram[i][j]);
			}
		}
		double scaleVertical = (double)heightMax/maxPixelDensity;
		double scaleHorizontal = (double)widthHistogram/256;
		for(int j=0;j<3;j++){
			for(int i=0;i<256;i++){
				int yBeg = yAxis + (int)(i*scaleHorizontal);
				int yEnd = yAxis + (int)((i+1)*scaleHorizontal);
				int xBeg = xAxis - (int)(this.histogram[i][j]*scaleVertical);
				int xEnd = xAxis;
				colorRectangle(img,new Point(xBeg,yBeg),new Point(xEnd,yEnd),50,j);
				/*
				for(int k=0;k<3;k++){
					if(k!=j){
						colorRectangle(img,new Point(xBeg,yBeg),new Point(xEnd,yEnd),0,k);
					}
					else{
						colorRectangle(img,new Point(xBeg,yBeg),new Point(xEnd,yEnd),255,k);
					}
				}*/
			}
		}
		return img;

	}

	private PNMImage getHistogramImageColor(int height,int width,List<Integer> colours)throws Exception{
		check(width,256,"Size small for histogram");
		check(height,200,"Size small for histogram");
		PNMImage img = new PNMImage("P3",height,width);
		int widthHistogram = width - 2*(int)(((double)1/15)*width);
		int yAxis = (width - widthHistogram)/2;
		int xAxis = height - (int)(((double)1/15)*height);
		int axisThickness = 10;
		colorRectangle(img,new Point(xAxis,yAxis),new Point(xAxis+axisThickness,yAxis+widthHistogram),0,3);
		colorRectangle(img,new Point(height-xAxis,yAxis-axisThickness),new Point(xAxis+axisThickness,yAxis),0,3);
		int heightMax = height - 2*(height - xAxis);
		int maxPixelDensity=0;
		for(int i=0;i<256;i++){
			for(int j=0;j<3;j++){
				if(!colours.contains(j)){
					maxPixelDensity = Math.max(maxPixelDensity,histogram[i][j]);
				}
			}
		}
		double scaleVertical = (double)heightMax/maxPixelDensity;
		double scaleHorizontal = (double)widthHistogram/256;
		for(int j=0;j<colours.size();j++){
			for(int i=0;i<256;i++){
				int yBeg = yAxis + (int)(i*scaleHorizontal);
				int yEnd = yAxis + (int)((i+1)*scaleHorizontal);
				int xBeg = xAxis - (int)(this.histogram[i][j]*scaleVertical);
				int xEnd = xAxis;
				colorRectangle(img,new Point(xBeg,yBeg),new Point(xEnd,yEnd),50,colours.get(j));
			}
		}
		return img;

	}

	private PNMImage getHistogramImageBitmap(int height,int width)throws Exception{
		check(width,256,"Size small for histogram");
		check(height,200,"Size small for histogram");
		PNMImage img = new PNMImage("P1",height,width);
		int widthHistogram = width - 2*(int)(((double)1/15)*width);
		int yAxis = (width - widthHistogram)/2;
		int xAxis = height - (int)(((double)1/15)*height);
		int axisThickness = 10;
		colorRectangle(img,new Point(xAxis,yAxis),new Point(xAxis+axisThickness,yAxis+widthHistogram),1);
		colorRectangle(img,new Point(height-xAxis,yAxis-axisThickness),new Point(xAxis+axisThickness,yAxis),1);
		int heightMax = height - 2*(height - xAxis);
		int maxPixelDensity=0;
		for(int i=0;i<256;i++){
			maxPixelDensity = Math.max(maxPixelDensity,histogram[i][0]);
		}
		double scaleVertical = (double)heightMax/maxPixelDensity;
		double scaleHorizontal = (double)widthHistogram/256;
		for(int i=0;i<256;i++){
			int yBeg = yAxis + (int)(i*scaleHorizontal);
			int yEnd = yAxis + (int)((i+1)*scaleHorizontal);
			int xBeg = xAxis - (int)(this.histogram[i][0]*scaleVertical);
			int xEnd = xAxis;
			colorRectangle(img,new Point(xBeg,yBeg),new Point(xEnd,yEnd),1);
		}
		return img;
	}

	private void check(int val,int constant,String msg)throws Exception{
		if(val<constant){
			throw new IllegalArgumentException(msg);
		}
	}

	private void colorRectangle(PNMImage img,Point leftTop,Point rightBottom,int value){
		for(int i=leftTop.x;i<=rightBottom.x;i++){
			for(int j=leftTop.y;j<=rightBottom.y;j++){
				img.setPixel(i,j,value);
			}
		}
	}

	private void colorRectangle(PNMImage img,Point leftTop,Point rightBottom,int value,int color){
		if(color==3){
			colorRectangle(img,leftTop,rightBottom,value,0);
			colorRectangle(img,leftTop,rightBottom,value,1);
			colorRectangle(img,leftTop,rightBottom,value,2);
		}
		else{
			for(int i=leftTop.x;i<=rightBottom.x;i++){
				for(int j=leftTop.y;j<=rightBottom.y;j++){
					img.setPixel(i,j,color,value);
				}
			}
		}
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(this.numColors+" "+this.maxValue+" "+this.minValue);
		sb.append("\n");
		for(int i=0;i<histogram[0].length;i++){
			for(int j=0;j<histogram.length;j++){
				sb.append(i+" "+j+"->"+histogram[j][i]);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}