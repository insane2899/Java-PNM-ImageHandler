package Assignment3;

import lib.*;

public class MainProg2{
	public static void main(String[] args) throws Exception{
		StructureElement elem = new StructureElement(1,30);
		PNMImage img = ImageUtil.readImage("Dataset/corpus1.pbm");
		img = ImageUtil.closeImage(img,new StructureElement(3,3));
		PNMImage output = removeHeadlines(img,elem);
		ImageUtil.writeImage(img,"Assignment3/OutputImages/HeadlinesRemoved.pbm","P4");
		ImageUtil.writeImage(output,"Assignment3/OutputImages/ExtractedHeadlines.pbm","P4");
		img = ImageUtil.readImage("Dataset/corpus1.pbm");
		output = ImageUtil.extractOutline(img,new StructureElement(3,3));
		ImageUtil.writeImage(output,"Assignment3/OutputImages/ExtractedOutline.pbm","P4");
	}

	private static PNMImage removeHeadlines(PNMImage img,StructureElement elem){
		PNMImage output = ImageUtil.getEmptyImage(img.getHeight(),img.getWidth(),"P4",0);
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				boolean hasBackground = false;
				outer: for(int k=0;k<elem.getHeight();k++){
					for(int l=0;l<elem.getWidth();l++){
						if(img.getPixel(i+k,j+l)==0){
							hasBackground=true;
							break outer;
						}
					}
				}
				if(!hasBackground){
					for(int k=0;k<elem.getHeight();k++){
						for(int l=0;l<elem.getWidth();l++){
							output.setPixel(i+k,j+l,1);
						}
					}
				}
			}
		}
		for(int i=0;i<img.getHeight();i++){
			for(int j=0;j<img.getWidth();j++){
				if(output.getPixel(i,j)==1){
					img.setPixel(i,j,0);
				}
			}
		}
		return output;
	}
}