package lib;


public class StructureElement{
	private int[][] structure;
	private Point centre;
	private int width;
	private int height;

	public StructureElement(int[][] structure,Point centre){
		this.centre = centre;
		this.structure = structure;
		this.height = structure.length;
		this.width = structure[0].length;
	}

	public StructureElement(int height,int width){
		this.height = height;
		this.width = width;
		this.structure = new int[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				this.structure[i][j]=1;
			}
		}
		this.centre = new Point(height/2,width/2);
	}

	public Point getCentre(){
		return this.centre;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}

	public int getPixel(int x,int y){
		return this.structure[x][y];
	}

}