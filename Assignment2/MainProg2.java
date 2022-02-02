package Assignment2;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg2{
	public static void main(String[] args){
		/*
		PNMImage img = new PNMImage("Dataset/baboon.ascii.pgm");
		Histogram hs = new Histogram(img);
		PNMImage img2 = hs.getHistogramImage(500,3000);
		img2.writeImage("Assignment2/HistogramCustom.pbm");*/
		PNMImage img3 = new PNMImage("Dataset/image2.ppm");
		Histogram hs2 = new Histogram(img3);
		PNMImage img4 = hs2.getHistogramImage(3000,3000);
		img4.writeImage("Assignment2/HistogramColor.ppm");
		PNMImage img5 = hs2.getHistogramImage(3000,3000,Arrays.asList(0,1,2));
		img5.writeImage("Assignment2/HistogramColorCustom.ppm");
		System.out.println("Done");
	}
}
