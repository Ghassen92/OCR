package com.sifast.tess4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TesseractUtil {

	public static final String TESSDATA_PATH;
    private static final  Logger LOGGER = LoggerFactory.getLogger(TesseractUtil.class);
    static{
		TESSDATA_PATH=getPath();
    	LOGGER.info(TESSDATA_PATH);
	}
	private TesseractUtil(){
		
	}
	public static String cvrt(String path){
		String output="";
		File imageFile = new File(path);
		@SuppressWarnings("deprecation")
 		Tesseract instance = Tesseract.getInstance(); 
		instance.setDatapath(TESSDATA_PATH);
		 
		try {
		 
		output = instance.doOCR(imageFile);
		} catch (TesseractException e) {
			LOGGER.error("error: ",e );
		}
  		return output;
		}
		
	public static void main(String[]a){
		System.out.println(cvrt("C:\\Users\\Ghassen\\Pictures\\test1.png"));
 	}
	
 	private static String getPath(){
		String path="";
		Properties prop = new Properties();
		InputStream input = null;
		try {

			//input = new FileInputStream("src/main/resources/config.properties");
			input = TesseractUtil.class	.getResourceAsStream("/config.properties");

			prop.load(input);

 			path=prop.getProperty("TESSDATA_PATH");
			 

		} catch (IOException ex) {
			LOGGER.error("error",ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LOGGER.error("error",e);
				}
			}
		}
		return path;

	  }
	
	 
}
