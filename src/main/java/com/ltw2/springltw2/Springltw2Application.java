package com.ltw2.springltw2;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Springltw2Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(Springltw2Application.class, args);
	}
	
	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	        // Register resource handler for images
	        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/images/")
	                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	    }

}
