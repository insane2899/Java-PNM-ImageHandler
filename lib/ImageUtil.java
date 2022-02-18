package lib;

import java.util.LinkedList;
import java.util.List;



public class ImageUtil{

	private static final int[] dx = {0,0,1,-1,1,-1,1,-1};
	private static final int[] dy = {1,-1,0,0,1,-1,-1,1};

	static class Limits{
		int xmin,ymin,xmax,ymax;
		public Limits(int xmin,int ymin,int xmax,int ymax){
			this.xmin = xmin;
			this.xmax = xmax;
			this.ymin = ymin;
			this.ymax = ymax;
		}
	}

	public static List<ImageComponent> getImageComponents(PNMImage image,int numNeighbours){
		int label = 1;
		Limits lim = new Limits(0,0,0,0);
		List<ImageComponent> result = new LinkedList<>();
		int[][] imageArray = new int[image.getHeight()][image.getWidth()];
		for(int i=0;i<image.getHeight();i++){
			for(int j=0;j<image.getWidth();j++){
				imageArray[i][j]=image.getPixel(i,j);
			}
		}
		for(int i=0;i<image.getHeight();i++){
			for(int j=0;j<image.getWidth();j++){
				if(imageArray[i][j]==1){
					lim = new Limits(i,j,i,j);
					label++;
					dfs(imageArray,i,j,label,lim,numNeighbours);
					result.add(new ImageComponent(lim.xmin,lim.ymin,lim.xmax,lim.ymax,i,j));
				}
			}
		}
		return result;
	}

	private static void dfs(int[][] image,int xnow,int ynow,int label,Limits lim,int numNeighbours){
		lim.xmin = Math.min(lim.xmin,xnow);
		lim.xmax = Math.max(lim.xmax,xnow);
		lim.ymin = Math.min(lim.ymin,ynow);
		lim.ymax = Math.max(lim.ymax,ynow);
		image[xnow][ynow]=label;
		for(int i=0;i<numNeighbours;i++){
			if(isSafe(image,xnow+dx[i],ynow+dy[i])){
				dfs(image,xnow+dx[i],ynow+dy[i],label,lim,numNeighbours);
			}
		}
	}

	private static boolean isSafe(int[][] image,int x,int y){
		if(x>=0 && y>=0 && x<image.length && y<image[0].length){
			if(image[x][y]==1){
				return true;
			}
		}
		return false;
	}

	public static PNMImage enhanceContrast(PNMImage img)throws IllegalArgumentException{
		if(!img.getOriginalFormat().equals("P2")){
			throw new IllegalArgumentException("Image must be grayscale");
		}
		Histogram hs = new Histogram(img);
		int[] newGrayScale = hs.getCDF();
		PNMImage output = new PNMImage(img.getHeight(),img.getWidth(),"P2",255);
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				output.setPixel(i,j,newGrayScale[img.getPixel(i,j)]);
			}
		}
		return output;
	}

	public static PNMImage matchHistogram(Histogram reference,PNMImage target){
		Histogram targetHS = new Histogram(target);
		int[] refCDF = reference.getCDF();
		int[] tarCDF = targetHS.getCDF();
		int[] tranCDF = new int[256];
		for(int i=0;i<256;i++){
			tranCDF[i] = getClosestMatching(tarCDF[i],refCDF);
		}
		PNMImage output = new PNMImage(target.getHeight(),target.getWidth(),"P2",255);
		for(int i=0;i<target.getHeight();i++){
			for(int j=0;j<target.getWidth();j++){
				output.setPixel(i,j,tranCDF[target.getPixel(i,j)]);
			}
		}
		return output;
	}

	private static int getClosestMatching(int val,int[] array){
		int minDiff = val;
		int ans = 0;
		for(int i=0;i<256;i++){
			int diff = Math.abs(val-array[i]);
			if(diff<minDiff){
				minDiff = diff;
				ans = i;
			}
		}
		return ans;
	}

	public static PNMImage getEmptyImage(int height,int width,String format,int fillColor){
		PNMImage ret = new PNMImage(height,width,format,fillColor);
		return ret;
	}

	public static PNMImage readImage(String fileName){
		return new PNMImage(fileName);
	}

	public static void writeImage(PNMImage img,String fileName,String format){
		img.writeImage(fileName,format);
	}

	public static PNMImage dilateImage(PNMImage input,StructureElement elem){
		PNMImage output = new PNMImage(input);
		switch(input.getOriginalFormat()){
		case "P1":
		case "P4":
			for(int i=0;i<input.getHeight();i++){
				for(int j=0;j<input.getWidth();j++){
					if(input.getPixel(i,j)==0){
						applyDilationBitmap(input,output,new Point(i,j),elem);
					}
				}
			}
			break;
		case "P2":
		case "P5":
		case "P3":
		case "P6":
			for(int i=0;i<input.getHeight();i++){
				for(int j=0;j<input.getWidth();j++){
					for(int k=0;k<input.getNumColors();k++){
						applyDilationGrayScale(input,output,new Point(i,j),elem,k);
					}
				}
			}
			break;
		}
		return output;
	}	

	private static void applyDilationBitmap(PNMImage input,PNMImage output,Point p,StructureElement elem){
		boolean isForegroundPresent = false;
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX() - i;
				int yDist = elem.getCentre().getY() - j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX< output.getHeight() && posY< output.getWidth()){
					if(input.getPixel(posX,posY)==1){
						isForegroundPresent = true;
					}
				}
			}
		}
		if(isForegroundPresent){
			output.setPixel(p.getX(),p.getY(),1);
		}
	}

	private static void applyDilationGrayScale(PNMImage input,PNMImage output,Point p,StructureElement elem,int color){
		int maxPixelDensity = 0;
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX() - i;
				int yDist = elem.getCentre().getY() - j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX< output.getHeight() && posY< output.getWidth()){
					maxPixelDensity = Math.max(maxPixelDensity,input.getPixel(posX,posY,color));
				}
			}
		}
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX() - i;
				int yDist = elem.getCentre().getY() - j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX< output.getHeight() && posY< output.getWidth()){
					output.setPixel(posX,posY,color,maxPixelDensity);
				}
			}
		}
	}

	public static PNMImage erodeImage(PNMImage input,StructureElement elem){
		PNMImage output = new PNMImage(input);
		switch(input.getOriginalFormat()){
		case "P1":
		case "P4":
			for(int i=0;i<input.getHeight();i++){
				for(int j=0;j<input.getWidth();j++){
					if(input.getPixel(i,j)==1){
						applyErosionBitmap(input,output,new Point(i,j),elem);
					}
				}
			}
			break;
		case "P2":
		case "P3":
		case "P5":
		case "P6":
			for(int i=0;i<input.getHeight();i++){
				for(int j=0;j<input.getWidth();j++){
					for(int k=0;k<input.getNumColors();k++){
						applyErosionGrayScale(input,output,new Point(i,j),elem,k);
					}
				}
			}
			break;
		}
		return output;
	}

	private static void applyErosionBitmap(PNMImage input,PNMImage output,Point p,StructureElement elem){
		boolean isBackgroundPresent = false;
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX()-i;
				int yDist = elem.getCentre().getY()-j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX<input.getHeight() && posY<input.getWidth()){
					if(input.getPixel(posX,posY)==0){
						isBackgroundPresent = true;
					}
				}
			}
		}
		if(isBackgroundPresent){
			output.setPixel(p.getX(),p.getY(),0);
		}
	}

	private static void applyErosionGrayScale(PNMImage input,PNMImage output,Point p,StructureElement elem,int color){
		int minPixelDensity = 255;
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX()-i;
				int yDist = elem.getCentre().getY()-j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX<input.getHeight() && posY<input.getWidth()){
					minPixelDensity = Math.min(minPixelDensity,input.getPixel(posX,posY,color));
				}
			}
		}
		for(int i=0;i<elem.getHeight();i++){
			for(int j=0;j<elem.getWidth();j++){
				int xDist = elem.getCentre().getX() - i;
				int yDist = elem.getCentre().getY() - j;
				int posX = p.getX()+xDist;
				int posY = p.getY()+yDist;
				if(posX>=0 && posY>=0 && posX< output.getHeight() && posY< output.getWidth()){
					output.setPixel(posX,posY,color,minPixelDensity);
				}
			}
		}
	}

	public static PNMImage openImage(PNMImage input,StructureElement elem){
		//First erosion then dilation
		PNMImage temp = erodeImage(input,elem);
		PNMImage output = dilateImage(temp,elem);
		return output;
	}

	public static PNMImage closeImage(PNMImage input,StructureElement elem){
		//First dilation then erosion
		PNMImage temp = dilateImage(input,elem);
		PNMImage output = erodeImage(temp,elem);
		return output;
	}
}