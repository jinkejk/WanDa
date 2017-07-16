package com.wanda.service.impl.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.wanda.util.OpenCVUtils;

public class OpencvTest {

	@Test
	public void testOne(){
		File file = new File("C:\\Users\\Sun\\Desktop\\DSC_0007_Gass.JPG");
//		System.out.println(file.getAbsolutePath());
//		BufferedImage imageFile = null;
//		try {
//			imageFile = ImageIO.read(file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Mat image = OpenCVUtils.BufferedImage2Mat(imageFile, BufferedImage.TYPE_INT_RGB, CvType.CV_8UC1);
		
		Mat imgToBinaryAndGauss = OpenCVUtils.imgToBinaryAndGauss(file.getAbsolutePath(), 11);

		try {
			ImageIO.write(OpenCVUtils.Mat2BufferedImage(imgToBinaryAndGauss, ".jpg"),
					"jpg", new File("C:\\Users\\Sun\\Desktop\\DSC_0007_tt.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
