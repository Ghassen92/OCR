package com.sifast.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileExistsException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.LoggerFactory;

import com.sifast.tess4j.TesseractUtil;

@Path("/")
public class Rest {

	static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");
	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(Rest.class);

	@GET
	@Produces(APPLICATION_JSON)
	public String getAll() {
		return "hello";
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail)
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
