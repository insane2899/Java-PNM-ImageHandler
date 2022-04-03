package SelfTries;

import lib.*;

public class MainProg{
	public static void main(String[] args){
		PNMImage input = ImageUtil.readImage("Dataset/dollar2.pgm");
		PNMImage[] outputs = ImageUtil.bitPlaneSplicing(input);
		for(int i=0;i<outputs.length;i++){
			ImageUtil.writeImage(outputs[i],"SelfTries/OutputImages/"+Integer.toString(i)+".pbm","P4");
		}
		PNMImage outputCustom = ImageUtil.getEmptyImage(input.getHeight(),input.getWidth(),"P5",0);
		for(int x=0;x<input.getHeight();x++){
			for(int y=0;y<input.getWidth();y++){
				for(int i=4;i<8;i++){
					outputCustom.setPixel(x,y,outputCustom.getPixel(x,y)|((outputs[i].getPixel(x,y)==0?1<<i:0)));
				}
			}
		}
		ImageUtil.writeImage(outputCustom,"SelfTries/OutputImages/Custom.pbm","P5");
	}
}