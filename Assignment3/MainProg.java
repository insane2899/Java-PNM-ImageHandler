package Assignment3;

import lib.*;

public class MainProg{
	public static void main(String[] args){
		PNMImage img = ImageUtil.readImage("Dataset/corpus1.pbm");
		StructureElement elem = new StructureElement(3,3);
		PNMImage dilatedImage = ImageUtil.dilateImage(img,elem);
		ImageUtil.writeImage(dilatedImage,"Assignment3/OutputImages/dilatedImageBitmap.pbm","P4");
		PNMImage erodedImage = ImageUtil.erodeImage(img,elem);
		ImageUtil.writeImage(erodedImage,"Assignment3/OutputImages/erodedImageBitmap.pbm","P1");
		PNMImage openedImage = ImageUtil.openImage(img,elem);
		ImageUtil.writeImage(openedImage,"Assignment3/OutputImages/openedImageBitmap.pbm","P4");
		PNMImage closedImage = ImageUtil.closeImage(img,elem);
		ImageUtil.writeImage(closedImage,"Assignment3/OutputImages/closedImageBitmap.pbm","P4");
		System.out.println("Done");
		elem = new StructureElement(15,15);
		PNMImage img2 = ImageUtil.readImage("Dataset/maple_input.pgm");
		dilatedImage = ImageUtil.dilateImage(img2,elem);
		ImageUtil.writeImage(dilatedImage,"Assignment3/OutputImages/dilatedImageGrayscale.pgm","P5");
		erodedImage = ImageUtil.erodeImage(img2,elem);
		ImageUtil.writeImage(erodedImage,"Assignment3/OutputImages/erodedImageGrayscale.pgm","P2");
		openedImage = ImageUtil.openImage(img2,elem);
		ImageUtil.writeImage(openedImage,"Assignment3/OutputImages/openedImageGrayscale.pgm","P5");
		closedImage = ImageUtil.closeImage(img2,elem);
		ImageUtil.writeImage(closedImage,"Assignment3/OutputImages/closedImageGrayscale.pgm","P5");
		System.out.println("Done2");
	}
}