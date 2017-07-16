package com.wanda.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * 封装一些opencv的函数
 * @author jinkejk
 *
 */
public class OpenCVUtils {

	/**
	 * 先高斯去模糊然后二值化
	 */
	public static Mat imgToBinaryAndGauss(String imgPath, int ksize){
		//加载opencv库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  

		Mat image = Highgui.imread(imgPath);
		
		return imgToBinaryAndGauss(image, ksize);		
	}

	/**
	 * 重载
	 * 先高斯去模糊然后二值化
	 */
	public static Mat imgToBinaryAndGauss(Mat image, int ksize){
		//加载opencv库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  

		//建立图像高斯化存储空间
		Mat gassImg=new Mat(image.rows(),image.cols(),CvType.CV_8UC1);
		/**
		 * src：输入图片，可以使是任意通道数，该函数对通道是独立处理的，但是深度只能是CV_8U, CV_16U, CV_16S, CV_32F or CV_64F. 
				           dst：输出图片，和输入图片相同大小和深度。 
				           ksize：高斯内核大小。ksize.width和ksize.height允许不相同但他们必须是正奇数。或者等于0，由参数sigma的乘机决定。 
				           sigmaX：高斯内核在X方向的标准偏差。 
				           sigmaY：高斯内核在Y方向的标准偏差。如果sigmaY为0，他将和sigmaX的值相同，如果他们都为0，那么他们由ksize.width和ksize.height计算得出。 
				           borderType：用于判断图像边界的模式。
		 **/
		Imgproc.GaussianBlur(image, gassImg, new Size(ksize,ksize), 0, 0, Imgproc.BORDER_DEFAULT);
		//			    Highgui.imwrite("C:\\Users\\Sun\\Desktop\\DSC_0007_Gass.JPG", gassImg);

		//建立灰度图像存储空间
		Mat grayImg = new Mat(image.rows(),image.cols(),CvType.CV_8UC1);
		//彩色图像灰度化
		Imgproc.cvtColor(gassImg, grayImg,Imgproc.COLOR_RGB2GRAY);
		//		Highgui.imwrite("C:\\Users\\Sun\\Desktop\\DSC_0007_GRAY.JPG", dst);

		//建立图像二值化存储空间
		Mat binaryImg=new Mat(image.rows(),image.cols(),CvType.CV_8UC1);
		//图像二值化
		Imgproc.threshold(grayImg,binaryImg,100,255,Imgproc.THRESH_OTSU);
		//图像保存输出
		//		Highgui.imwrite("C:\\Users\\Sun\\Desktop\\DSC_0007_BINARY.JPG", binaryImg);

		return binaryImg;
	}

	/**
	 * 将Mat类型转化成BufferedImage类型
	 * 
	 * @param amatrix Mat对象
	 * @param fileExtension 文件扩展名
	 * @return
	 */
	public static BufferedImage Mat2BufferedImage(Mat mat, String fileExtension) {
		//加载opencv库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		MatOfByte mob = new MatOfByte();

		Highgui.imencode(fileExtension, mat, mob);
		// convert the "matrix of bytes" into a byte array
		byte[] byteArray = mob.toArray();
		BufferedImage bufImage = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			bufImage = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufImage;
	}

	/**
	 * 变化尺寸
	 * 若尺寸小于size，则不变
	 */
	public static Mat resize(String imgPath, int size){
		//加载opencv库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Mat image = Highgui.imread(imgPath);
		//尺寸符合要求，不变
		if(image.rows() <= size || image.cols() <= size){
			return image;
		}else{			
			//等比例缩放
			int max = (image.rows()>image.cols()? image.rows():image.cols());
			double scale = (double)size/max;
			int row = (int)(image.rows() * scale);
			int col = (int)(image.cols() * scale);
			Size dsize = new Size(col, row);

			//建立图像高斯化存储空间
			Mat dst = new Mat(row, col, CvType.CV_8UC1);			
			Imgproc.resize(image, dst, dsize);

			return dst;
		}
	}
	
	/**
	 * 存储图片
	 */
	public static void saveImg(String imgPath, Mat image){
		//加载opencv库
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		Highgui.imwrite(imgPath, image);

	}
}
