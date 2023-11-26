package com.codea2z;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import com.codea2z.handler.DHLClient;



@SpringBootApplication
public class ExcelToXmlConverterApplication {


	public static void main(String[] args) {
		SpringApplication.run(ExcelToXmlConverterApplication.class, args);
		System.out.println("Project is running >>> .............");
		DHLClient dhlClient = new DHLClient();
		dhlClient.data();		
		//dhlClient.generateDemoExcel();

	}

}
