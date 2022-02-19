package Assignment1;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg2{
	public static void main(String[] args)throws IOException{
		PNMImage img = ImageUtil.readImage("Dataset/image2.ppm");
		ImageUtil.writeImage(img,"Assignment1/color.raw.pnm","P6");
		img =  ImageUtil.readImage("Dataset/maple_input.pgm");
		ImageUtil.writeImage(img,"Assignment1/maple_input.pgm","P2");
	}
}