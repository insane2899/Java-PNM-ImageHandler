package Test;

import lib.*;


class Test1{
	public static void main(String[] args){
		PNMImage img = ImageUtil.readImage("Dataset/samplePBM.raw.pbm");
		ImageUtil.writeImage(img,"Test/Output/samplePBM.raw.pbm","P4");
	}
}