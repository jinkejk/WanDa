package com.wanda.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Map;
import org.opencv.core.Mat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码生成器
 * 
 * @blog http://sjsky.iteye.com
 * @author Michael
 */
public class QRcodeHandler {

	/**
	 * 生成二维码
	 * 
	 * @param contents
	 * @param width
	 * @param height
	 * @param imgPath
	 */
	public static void encode(String contents, int width, int height, String imgPath) {
		Map<EncodeHintType, Object> hints = new Hashtable<>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);

			MatrixToImageWriter.writeToStream(bitMatrix, "png",
					new FileOutputStream(imgPath));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析二维码
	 * 
	 * @param imgPath
	 * @param ksize 高斯模糊掩码大小
	 * @return
	 */	
	public static String decode(File img, int imgSize) {
		BufferedImage image = null;
		Result result = null;
		String resultString = null;
		try {
			//缩小原图，加快识别速度
			Mat imgScale = OpenCVUtils.resize(img.getAbsolutePath(), imgSize);
			
			//循环测试不同的高斯模板
			Map<DecodeHintType, Object> hints = new Hashtable<>();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
			
			for(int ksize = 3; ksize <= 23; ksize += 2){
				
				//高斯模糊后二值化
				Mat imgToBinaryAndGauss = OpenCVUtils.imgToBinaryAndGauss(imgScale, ksize);			
				image = OpenCVUtils.Mat2BufferedImage(imgToBinaryAndGauss, ".jpg");
				
				if (image == null) {
					System.out.println("the decode image may be not exit.");
					continue;
				}
				LuminanceSource source = new BufferedImageLuminanceSource(image);  
				BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source)); 
				
				try{
					result =  new QRCodeReader().decode(bitmap, hints);					
				}catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				resultString = result.getText();
				if(resultString != null){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
}