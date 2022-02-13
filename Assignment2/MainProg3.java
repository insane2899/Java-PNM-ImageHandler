package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg3{
	public static void main(String[] args){
		PNMImage img = ImageUtil.readImage("Dataset/LowContrast3.pgm");
		Histogram hs = new Histogram(img);
		PNMImage img2 = hs.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img2,"Assignment2/Matching/Histogram.pbm","P1");
		System.out.println("Done");
		PNMImage img5 = ImageUtil.enhanceContrast(img);
		ImageUtil.writeImage(img5,"Assignment2/Matching/EnhancedImage.pgm","P5");
		Histogram hs3 = new Histogram(img5);
		PNMImage img6 = hs3.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img6,"Assignment2/Matching/EnhancedImageHistogram.pbm","P1");
		PNMImage img7 = ImageUtil.readImage("Dataset/LowContrast2.pgm");
		PNMImage img8 = ImageUtil.matchHistogram(hs3,img7);
		ImageUtil.writeImage(img8,"Assignment2/Matching/MatchedImage.pgm","P2");
		Histogram hs4 = new Histogram(img8);
		PNMImage img9 = hs4.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img9,"Assignment2/Matching/MatchImageHistogram.pbm","P4");
	}
}