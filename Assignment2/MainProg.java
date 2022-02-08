package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg{
	public static void main(String[] args){
		PNMImage img = new PNMImage("Dataset/LowContrast2.pgm");
		Histogram hs = new Histogram(img);
		PNMImage img2 = hs.getHistogramImage(3000,3000);
		img2.writeImage("Assignment2/Enhanced/Histogram.pbm");
		System.out.println("Done");
		PNMImage img5 = ImageUtil.enhanceContrast(img);
		img5.writeImage("Assignment2/Enhanced/EnhancedImage.pgm");
		Histogram hs3 = new Histogram(img5);
		PNMImage img6 = hs3.getHistogramImage(3000,3000);
		img6.writeImage("Assignment2/Enhanced/EnhancedImageHistogram.pbm");




		/*
		PNMImage img3 = new PNMImage("Dataset/image2.ppm");
		Histogram hs2 = new Histogram(img3);
		PNMImage img4 = hs2.getHistogramImage(3000,3000,Arrays.asList(0,2));
		img4.writeImage("Assignment2/HistogramColorCustom.ppm");
		PNMImage img7 = hs2.getHistogramImage(3000,3000);
		img7.writeImage("Assignment2/HistogramColor.ppm");
		System.out.println("Done 2");
		*/
	}
}