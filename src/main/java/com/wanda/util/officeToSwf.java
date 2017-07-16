package com.wanda.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * office文档转pdf工具类
 * @author jinkejk
 *
 */
public class officeToSwf extends Thread  {
	private String swfToolsPath;
	private File inputFile;// 需要转换的文件 
	private File outputFile;// 输出的文件  

	public officeToSwf(File inputFile, File outputFile, String swfToolsPath) {
		this.inputFile = inputFile;  
		this.outputFile = outputFile;
		this.swfToolsPath = swfToolsPath;
	}  

	/**
	 * 文档转pdf
	 */
	private String docToPdf() {		
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
			cex.printStackTrace();
			return null;
		} finally {
			connection.disconnect(); 
			connection = null;
		} 
	}

	/**
	 * 利用SWFTools工具将pdf转换成swf，转换完后的swf文件与pdf同名
	 * @param fileDir PDF文件存放路径（包括文件名）
	 * @param exePath 转换器安装路径
	 */

	private void pdfToSwf(String pdfPath){
		Process pro = null;

		File pdfFile = new File(pdfPath);
		if(pdfPath==null || !pdfFile.exists()){
			System.out.println("swf转换失败:pdf不存在！");
			return;
		}
		//命令行命令
		String cmd = swfToolsPath + "pdf2swf.exe " + pdfPath + " -s flashversion=9 -o " + outputFile.getAbsolutePath();

		//Runtime执行后返回创建的进程对象
		try {
			pro = Runtime.getRuntime().exec(cmd);
			//非要读取一遍cmd的输出，要不不会flush生成文件（多线程）
			//再启动两个JAVA线程及时的把被调用进程的输出截获。
			new DoOutput(pro.getInputStream()).start();
			new DoOutput(pro.getErrorStream()).start();

			try {
				//调用waitFor方法，是为了阻塞当前进程，直到cmd执行完
				pro.waitFor();
			} catch (InterruptedException e) {
				System.out.println("swf转换失败：转换异常0！");
				e.printStackTrace();
			}
			if(outputFile.exists())
				System.out.println("swf转换成功！");
			else
				System.out.println("swf转换失败：转换异常1！");
		} catch (IOException e1) {
			System.out.println("swf转换失败:转换异常2！");
			e1.printStackTrace();
		}finally{
			pdfFile.delete();
		}		
	}

	/** 
	 * 由于服务是线程不安全的，所以……需要启动线程
	 */ 
	public void run() {		
		this.pdfToSwf(this.docToPdf());
	}

	/**
	 * 多线程内部类
	 * 读取转换时cmd进程的标准输出流和错误输出流，这样做是因为如果不读取流，进程将死锁
	 * @author iori
	 */
	private static class DoOutput extends Thread {
		public InputStream is;

		//构造方法
		public DoOutput(InputStream is) {
			this.is = is;
		}

		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.is));
			String str = null;
			try {
				//这里并没有对流的内容进行处理，只是读了一遍
				while ((str = br.readLine()) != null);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
