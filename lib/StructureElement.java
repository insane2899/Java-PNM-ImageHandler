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

	public StructureElement(int height,int width,int r,Point centre){
		this.centre = centre;
		this.height = height;
		this.width = width;
		this.structure = new int[height][width];
		int x = r,y = 0;
		if(r>0){
			setPoint(x+centre.getX(),y+centre.getY());
			setPoint(-x+centre.getX(),centre.getY());
			setPoint(centre.getX(),r+centre.getY());
			setPoint(centre.getX(),-r+centre.getY());
		}
		int P = 1-r;
		while(x>y){
			y++;
			if(P<=0){
				P = P + 2*y +1;
			}
			else{
				x--;
				P = P + 2*y - 2*x + 1;
			}
			if(x<y){
				break;
			}
			setPoint(x+centre.getX(),y+centre.getY());
			setPoint(-x+centre.getX(),y+centre.getY());
			setPoint(x+centre.getX(),-y+centre.getY());
			setPoint(-x+centre.getX(),-y+centre.getY());
			if(x!=y){
				setPoint(y+centre.getX(),x+centre.getY());
				setPoint(-y+centre.getX(),x+centre.getY());
				setPoint(y+centre.getX(),-x+centre.getY());
				setPoint(-y+centre.getX(),-x+centre.getY());
			}
		}
		fillInside(centre);
	}

	private void fillInside(Point p){
		this.structure[p.getX()][p.getY()]=1;
		if(p.getX()-1>=0 && this.structure[p.getX()-1][p.getY()]==0){
			fillInside(new Point(p.getX()-1,p.getY()));
		}
		if(p.getX()+1<this.height && this.structure[p.getX()+1][p.getY()]==0){
			fillInside(new Point(p.getX()+1,p.getY()));
		}
		if(p.getY()-1>=0 && this.structure[p.getX()][p.getY()-1]==0){
			fillInside(new Point(p.getX(),p.getY()-1));
		}
		if(p.getY()+1<this.width && this.structure[p.getX()][p.getY()+1]==0){
			fillInside(new Point(p.getX(),p.getY()+1));
		}

	}

	private void setPoint(int x,int y){
		if(x>=0 && y>=0 && x<height && y<width){
			this.structure[x][y]=1;
		}
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