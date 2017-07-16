package com.wanda.util;
import java.io.File;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * office文档转pdf工具类
 * @author jinkejk
 *
 */
public class officeToPdf{
	private File inputFile;// 需要转换的文件 
	private File outputFile;// 输出的文件  

	public officeToPdf(File inputFile, File outputFile) {
		this.inputFile = inputFile;  
		this.outputFile = outputFile;
	}  

	/**
	 * 文档转pdf
	 */
	public String docToPdf() throws Exception{		
		int index = outputFile.getAbsolutePath().lastIndexOf(".");
		String outputName = outputFile.getAbsolutePath().substring(0, index);
		File pdfFile = new File(outputName + ".pdf");

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100); 
		try {
			connection.connect();
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  

			System.out.println("pdf转换中......");
			converter.convert(inputFile, pdfFile);
			System.out.println("pdf转换成功！");
			return pdfFile.getAbsolutePath();
		} catch (Exception cex) {
			System.out.println("pdf转换失败!");
			throw cex; //把异常交给上一层
//			return null;
		} finally {
			try{				
				connection.disconnect(); 
			}catch (Exception e) {
				throw e;
			}
			connection = null;
		} 
	}
	public static void main(String[] args) {
		OpenOfficeServerUtil.start("C:\\Program Files (x86)\\OpenOffice 4\\program\\");
		officeToPdf otp = new officeToPdf(new File("C:\\Users\\Sun\\Desktop\\test\\论文格式.doc"),
				new File("C:\\Users\\Sun\\Desktop\\test\\论文格式.pdf"));
		try {
			otp.docToPdf();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
