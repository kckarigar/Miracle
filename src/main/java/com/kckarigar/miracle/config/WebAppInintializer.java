/**
 *
 */
package com.kckarigar.miracle.config;

import javax.servlet.Registration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * WebAppInitializer is the WebApplicationInitilializer implementing class for
 * web setting up on web application start up (a replacement of web.xml
 * configuration). At startup, server looks for WebApplicationInitilializer
 * implementing class.
 *
 * @author Kailash Karigar
 *
 */
public class WebAppInintializer implements WebApplicationInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
	 * .ServletContext)
	 */
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ctx.setServletContext(servletContext);
		Dynamic dynamic = servletContext.addServlet("dispatcher",
				new DispatcherServlet(ctx));
		((ServletRegistration) dynamic).addMapping("/");
		((javax.servlet.ServletRegistration.Dynamic) dynamic)
				.setLoadOnStartup(1);
	}
}
