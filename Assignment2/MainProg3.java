package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg3{
	public static void main(String[] args){
		PNMImage img = new PNMImage("Dataset/LowContrast3.pgm");
		Histogram hs = new Histogram(img);
		PNMImage img2 = hs.getHistogramImage(3000,3000);
		img2.writeImage("Assignment2/Matching/Histogram.pbm");
		System.out.println("Done");
		PNMImage img5 = ImageUtil.enhanceContrast(img);
		img5.writeImage("Assignment2/Matching/EnhancedImage.pgm");
		Histogram hs3 = new Histogram(img5);
		PNMImage img6 = hs3.getHistogramImage(3000,3000);
		img6.writeImage("Assignment2/Matching/EnhancedImageHistogram.pbm");
		PNMImage img7 = new PNMImage("Dataset/LowContrast2.pgm");
		PNMImage img8 = ImageUtil.matchHistogram(hs3,img7);
		img8.writeImage("Assignment2/Matching/MatchedImage.pgm");
		Histogram hs4 = new Histogram(img8);
		PNMImage img9 = hs4.getHistogramImage(3000,3000);
		img9.writeImage("Assignment2/Matching/MatchImageHistogram.pbm");
	}
}