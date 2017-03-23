package com.sifast.rest;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class Application extends ResourceConfig{

	public Application(){
		register(MultiPartFeature.class);
		register(Rest.class);
	}

}
