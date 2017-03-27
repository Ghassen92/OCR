package com.sifast.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileExistsException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sifast.tess4j.TesseractUtil;

@Path("/")
@Api(value = "/",tags = "OCR api")
public class Rest {

	static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
	private static final Logger LOGGER = LoggerFactory.getLogger(Rest.class);

	
	
	@ApiImplicitParams(@ApiImplicitParam(
			name="file", paramType="form" , dataType="java.io.File",required=true))
 
	 
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(	value = "Extraire le texte à partir d'un fichier ( .pdf, .png, .jgp) en utilisant la bibliothèque « Tess4j ».",
				  produces=MediaType.TEXT_PLAIN)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "erreur lors de communication avec le serveur")})

	public Response uploadFile(
   				@ApiParam(hidden=true)		 @FormDataParam("file") InputStream uploadedInputStream,
   				@ApiParam(hidden=true)		 @FormDataParam("file") FormDataContentDisposition fileDetail)
			throws FileExistsException {

		LOGGER.info(uploadedInputStream == null ? "null" : "mriguel");
		String uploadedFileLocation = TEMP_DIRECTORY + fileDetail.getFileName();
		writeToFile(uploadedInputStream, uploadedFileLocation);

		LOGGER.info("file saved : {}",uploadedFileLocation);

		String output = TesseractUtil.cvrt(uploadedFileLocation);

		return Response.status(200).entity(output).build();

	}

	private void writeToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
		} catch (IOException e) {
			LOGGER.error("error", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LOGGER.error("error", e);
			}
		}

	}
	
	
 

}
