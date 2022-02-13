package Assignment1;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg{
	public static void main(String[] args){
		PNMImage img = ImageUtil.readImage("Dataset/corpus1.pbm");
		List<ImageComponent> components = ImageUtil.getImageComponents(img,8);
		showComponents(components);
		PNMImage newImage = removeComponents(components);
		ImageUtil.writeImage(newImage,"Assignment1/output.pbm","P1");
		System.out.println("Done");
	}

	static void showComponents(List<ImageComponent> components){
		PNMImage newImage = ImageUtil.readImage("Dataset/corpus1.pbm");
		for(int i=0;i<components.size();i++){
			for(int x=components.get(i).getMinX();x<=components.get(i).getMaxX();x++){
				newImage.setPixel(x,components.get(i).getMinY(),1);
				newImage.setPixel(x,components.get(i).getMaxY(),1);
			}
			for(int y=components.get(i).getMinY();y<=components.get(i).getMaxY();y++){
				newImage.setPixel(components.get(i).getMinX(),y,1);
				newImage.setPixel(components.get(i).getMaxX(),y,1);
			}
		}
		ImageUtil.writeImage(newImage,"Assignment1/SeparatedComponents.pbm","P1");

	}

	static PNMImage removeComponents(List<ImageComponent> components){
		PNMImage newImage = ImageUtil.readImage("Dataset/corpus1.pbm");
		for(int i=0;i<components.size();i++){
			if(components.get(i).getMaxX()-components.get(i).getMinX()<=10 || components.get(i).getMaxY()-components.get(i).getMinY()<=10){
				//System.out.println("Removed Component");
				for(int x = components.get(i).getMinX();x<=components.get(i).getMaxX();x++){
					for(int y = components.get(i).getMinY();y<=components.get(i).getMaxY();y++){
						newImage.setPixel(x,y,0);
					}
				}
			}
		}
		return newImage;
	}
}