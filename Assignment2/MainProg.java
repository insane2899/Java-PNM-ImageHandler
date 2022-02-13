package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg{
	public static void main(String[] args){
		PNMImage img = ImageUtil.readImage("Dataset/LowContrast2.pgm");
		Histogram hs = new Histogram(img);
		PNMImage img2 = hs.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img2,"Assignment2/Enhanced/Histogram.pbm","P1");
		System.out.println("Done");
		PNMImage img5 = ImageUtil.enhanceContrast(img);
		ImageUtil.writeImage(img5,"Assignment2/Enhanced/EnhancedImage.pgm","P2");
		Histogram hs3 = new Histogram(img5);
		PNMImage img6 = hs3.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img6,"Assignment2/Enhanced/EnhancedImageHistogram.pbm","P4");




	
		PNMImage img3 = ImageUtil.readImage("Dataset/image2.ppm");
		Histogram hs2 = new Histogram(img3);
		PNMImage img4 = hs2.getHistogramImage(3000,3000,Arrays.asList(0,2));
		ImageUtil.writeImage(img4,"Assignment2/HistogramColorCustom.ppm","P6");
		PNMImage img7 = hs2.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img7,"Assignment2/HistogramColor.ppm","P3");
		System.out.println("Done 2");
	
	}
}