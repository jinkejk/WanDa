package com.wanda.service.impl.test;

import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wanda.beans.Drawing;
import com.wanda.service.DrawingService;

public class DrawingServiceImplTest {
	
	private static DrawingService drawingService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			drawingService = (DrawingService) applicationContext.getBean("drawingService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDrawingById() {
//		System.out.println(drawingService.getDrawingById(1));
	}

	@Test
	public void testGetDrawingByStringId() {
//		System.out.println(drawingService.getDrawingByStringId("D20170124S00003"));
    }

	@Test
	public void testAddDrawing() {
		for(int i =1; i<33; i++){			
			Drawing drawing = new Drawing();
			drawing.setStringId("D20170124S100" + i);
//			drawing.setDrawingName("test drawing");
			drawing.setUploadDate(new Date());
//			drawing.setRemark("...");
//			drawing.setSecurityLevel(new SecurityLevel(3));
//			drawing.setAuthor(new User(32));
//			drawing.setAlterDate(new Date());
//			drawing.setLastAlter(new User(33));
			
			drawingService.addDrawing(drawing);
		}
	}

	@Test
	public void testDeleteDrawingById() {
//		drawingService.deleteDrawingById(5);
	}

	@Test
	public void testDeleteDrawingByStringId() {
//		drawingService.deleteDrawingByStringId("D20170124S00006");;
	}

	@Test
	public void testDeleteDrawing() {
//		Drawing drawing = new Drawing();
//		drawing.setDrawingId(1);
//		drawingService.deleteDrawing(drawing);
	}
	
	@Test
	public void testgetDrawingsByPage(){
//		System.out.println(drawingService.getDrawingsByPage(8, 2));
	}

	@Test
	public void testgetTotalDrawingNum(){
//		System.out.println(drawingService.getTotalDrawingNum());
	}
	
	@Test
	public void testgetTotalDrawingNumByContent(){
		System.out.println(drawingService.getDrawingByFieldId("8997957"));
	}
	
	@Test
	public void testgetDrawingByContentByPage(){
		List<Drawing> drawings = drawingService.getDrawingByContentByPage("刚 test 呵呵", 8, 1);
		
		for(Drawing drawing: drawings){
			System.out.println(drawing);
		}
	}
	
	@Test
	public void testcreateUploadDrawingStringId(){
//		System.out.println(drawingService.createUploadDrawingStringId());
	}
	
	@Test
	public void testdeleteEmptyDrawing(){
//		drawingService.deleteEmptyDrawing();
	}
	
	@Test
	public void testdeleteFile(){
//		System.out.println(UtilCommon.createImgName());
	}
	
	@Test
	public void testQR(){
//		try {
//			QRcodeHandler.qrCodeEncode("D20170209S00014",new File("F:\\java web\\10.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(QRcodeHandler.qrCodeDecode(new File("F:\\java web\\10.jpg")));
	}
	
	@Test
	public void testgetDrawingByImgName(){
		System.out.println(drawingService.getDrawingByImgName("2017_02_11_02_27_51_616"));
	}
}
