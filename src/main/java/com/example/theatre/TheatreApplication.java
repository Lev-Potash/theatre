package com.example.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;


@SpringBootApplication
@ComponentScan({"com.example.theatre.configuration", "com.example.theatre.controller",
		"com.example.theatre.entity", "com.example.theatre.property", "com.example.theatre.repository",
		"com.example.theatre.rest_client", "com.example.theatre.rest_controller",
		"com.example.theatre.rest_service", "com.example.theatre.service" })
public class TheatreApplication {


	public static void main(String[] args) throws IOException {
//		new MyGsonHttpMessageConverter();
//		KeyStoreUtil.setKeyStoreParams();
//		KeyStoreUtil.setTrustStoreParams();
//		setUpTrustStoreForApplication();
		SpringApplication.run(TheatreApplication.class, args);
	}

	private static void setUpTrustStoreForApplication() throws IOException {
		File file = new File("src/main/resources/truststore/mykeys.p12");
		System.out.println("File "+ file.getAbsolutePath());
		System.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
	}
}
