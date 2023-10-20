package com.example.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;


@SpringBootApplication
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
