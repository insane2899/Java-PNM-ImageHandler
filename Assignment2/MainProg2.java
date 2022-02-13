package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg2{
	public static void main(String[] args){
		PNMImage img3 = ImageUtil.readImage("Dataset/image2.ppm");
		Histogram hs2 = new Histogram(img3);
		PNMImage img4 = hs2.getHistogramImage(3000,3000);
		ImageUtil.writeImage(img4,"Assignment2/ColorHistogram/HistogramColor.ppm","P3");
		PNMImage img5 = hs2.getHistogramImage(3000,3000,Arrays.asList(0,1));
		ImageUtil.writeImage(img5,"Assignment2/ColorHistogram/HistogramColorCustom.ppm","P6");
		System.out.println("Done");
	}
}
