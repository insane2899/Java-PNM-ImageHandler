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
		if(!img.getFormat().equals("P2")){
			throw new IllegalArgumentException("Image must be grayscale");
		}
		Histogram hs = new Histogram(img);
		int totalPixels = 0;
		for(int i=0;i<256;i++){
			totalPixels += hs.getHistogramLevel(i);
		}
		int[] newGrayScale = new int[256];
		int curr = 0;
		for(int i=0;i<256;i++){
			curr+=hs.getHistogramLevel(i);
			newGrayScale[i]=Math.round((((float)curr)*255)/totalPixels);
		}
		PNMImage output = new PNMImage("P2",img.getHeight(),img.getWidth());
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				output.setPixel(i,j,newGrayScale[img.getPixel(i,j)]);
			}
		}
		return output;
	}

}