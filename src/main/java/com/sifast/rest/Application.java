package com.sifast.rest;

 
import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@ApplicationPath("/api")
public class Application extends ResourceConfig{

	static final  Logger LOGGER= LoggerFactory.getLogger(Application.class); 
	public Application(){
		register(MultiPartFeature.class);
		register(Rest.class);
	    String[] packages = {Rest.class.getPackage().getName(), "io.swagger.jaxrs.listing"};
		packages(packages);
		configureSwagger();
	}

	private void configureSwagger() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setTitle("essai swagger");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/ocr-api/api");
		beanConfig.setResourcePackage(Rest.class.getPackage().getName());

		beanConfig.setScan(true);
		beanConfig.setPrettyPrint(true);
		LOGGER.info("class scanned: {}",beanConfig.classes());

	}

}
