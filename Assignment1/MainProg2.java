package Assignment1;

import java.util.*;
import java.io.*;
import java.lang.*;
import lib.*;

public class MainProg2{
	public static void main(String[] args)throws IOException{
		PNMImage img = new PNMImage("Dataset/image2.ppm");
		img.writeImage("Assignment1/color.pnm");
	}
}