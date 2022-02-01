package lib;


public class ImageComponent{
	private int minX;
	private int minY;
	private int maxX;
	private int maxY;
	private int insidePixelX;
	private int insidePixelY;


	public ImageComponent(int minX,int minY,int maxX,int maxY,int insidePixelX,int insidePixelY){
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.insidePixelY= insidePixelY;
		this.insidePixelX=insidePixelX;
	}

	public int getMinX(){
		return this.minX;
	}

	public int getMinY(){
		return this.minY;
	}

	public int getMaxX(){
		return this.maxX;
	}

	public int getMaxY(){
		return this.maxY;
	}

	public int getInsidePixelX(){
		return this.insidePixelX;
	}

	public int getInsidePixelY(){
		return this.insidePixelY;
	}

	public void setMinX(int X){
		this.minX = X;
	}

	public void setMinY(int Y){
		this.minY = Y;
	}

	public void setMaxX(int X){
		this.maxX = X;
	}

	public void setMaxY(int Y){
		this.maxY = Y;
	}

	public void setInsidePixelX(int X){
		this.insidePixelX = X;
	}

	public void setInsidePixelY(int Y){
		this.insidePixelY = Y;
	}

	@Override
	public String toString(){
		return "("+this.minX+","+this.minY+","+this.maxX+","+this.maxY+")";
	}


}