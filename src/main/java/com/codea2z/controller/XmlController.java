package com.codea2z.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codea2z.handler.CbeXiiHandler;
import com.codea2z.handler.CbeXiiiHandler;
import com.codea2z.handler.DHLClient;
import com.codea2z.handler.Helper;
import com.codea2z.unusedclasses.ExcelToXMLColumnWise;
import com.codea2z.unusedclasses.ExcelToXml;
import com.codea2z.unusedclasses.XmlHandler;


@RestController
@RequestMapping("excel")
@CrossOrigin("*")
public class XmlController {
	
//	@Autowired
//	private XmlHandler xmlHandler;
//	
//	@Autowired
//	private ExcelToXml excelToXml;
//	
//	@Autowired
//	private ExcelToXMLColumnWise excelToXMLColumnWise;
	
	@Autowired
	private DHLClient dhlClient;
	
	@Autowired
	private CbeXiiHandler cbeXiiHandler;
	
	@Autowired
	private CbeXiiiHandler cbeXiiiHandler;
	
	@PostMapping("/demo")
	public ResponseEntity<?> convertExcelDataToXml( @RequestParam("file") MultipartFile file

    ){
        //Map<String,Object> map = new HashMap<>();
        String result = null;
        if (Helper.checkExcelFormat(file)) {
            try {
            	
            	String fileName = file.getOriginalFilename();
            	result = dhlClient.data(file.getInputStream());
            	//map.put("Data", dhlClient.data(file.getInputStream()));
            	
            	//map.put("Object", xmlHandler.convertExcelDataToJson(file.getInputStream()));
            	
            	//map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream(), fileName); // Row wise XMl generator
            	//map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
            	
            	
                
            } catch (Exception e) {
            	result = "Invalid Request";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
                

            }           


        }else {
        	result = "Kindly upload Excel file only";
            //map.put("STATUS","Kindly upload Excel file only");
        }

        return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/cbexii")
	public ResponseEntity<?> convertExcelCbeXii( @RequestParam("file") MultipartFile file

    ){
        //Map<String,Object> map = new HashMap<>();
        String result = null;
        if (Helper.checkExcelFormat(file)) {
            try {
            	
            	String fileName = file.getOriginalFilename();
            	result = cbeXiiHandler.sendExcelData(file.getInputStream());
            	//map.put("Data", dhlClient.data(file.getInputStream()));
            	
            	//map.put("Object", xmlHandler.convertExcelDataToJson(file.getInputStream()));
            	
            	//map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream(), fileName); // Row wise XMl generator
            	//map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
            	
            	
                
            } catch (Exception e) {
            	result = "Invalid Request";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
                

            }           


        }else {
        	result = "Kindly upload Excel file only";
            //map.put("STATUS","Kindly upload Excel file only");
        }

        return ResponseEntity.ok(result);
		
	}
	@PostMapping("/cbexiii")
	public ResponseEntity<?> convertExcelCbeXiii( @RequestParam("file") MultipartFile file
			
			){
		//Map<String,Object> map = new HashMap<>();
		String result = null;
		if (Helper.checkExcelFormat(file)) {
			try {
				
				String fileName = file.getOriginalFilename();
				result = cbeXiiiHandler.sendExcelData(file.getInputStream());
				//map.put("Data", dhlClient.data(file.getInputStream()));
				
				//map.put("Object", xmlHandler.convertExcelDataToJson(file.getInputStream()));
				
				//map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream(), fileName); // Row wise XMl generator
				//map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
				
				
				
			} catch (Exception e) {
				result = "Invalid Request";
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
				
				
			}           
			
			
		}else {
			result = "Kindly upload Excel file only";
			//map.put("STATUS","Kindly upload Excel file only");
		}
		
		return ResponseEntity.ok(result);
		
	}
	
//	@PostMapping("/")
//	public ResponseEntity<?> getExcelData( @RequestParam("file") MultipartFile file
//
//    ){
//        Map<String,Object> map = new HashMap<>();
//        if (Helper.checkExcelFormat(file)) {
//            try {
//            	
//            	String fileName = file.getOriginalFilename();
//            	
//            	map.put("Object", xmlHandler.convertExcelDataToJson(file.getInputStream()));
//            	
//            	//map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream(), fileName); // Row wise XMl generator
//            	//map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
//            	
//            	
//                
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//                
//
//            }           
//
//
//        }else {
//            map.put("STATUS","Kindly upload Excel file only");
//        }
//
//        return ResponseEntity.ok(map);
//		
//	}
//	
//	@PostMapping("/row")
//	public ResponseEntity<?> getExcelDataRow( @RequestParam("file") MultipartFile file
//
//    ){
//        Map<String,Object> map = new HashMap<>();
//        if (Helper.checkExcelFormat(file)) {
//            try {
//            	
//            	String fileName = file.getOriginalFilename();
//            	
//            	//xmlHandler.getDataFromExcel(file);
//            	map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream(), fileName); // Row wise XMl generator
//            	//map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
//            	
//            	
//                
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//                
//
//            }           
//
//
//        }else {
//            map.put("STATUS","Kindly upload Excel file only");
//        }
//
//        return ResponseEntity.ok(map);
//		
//	}
//	
//	@PostMapping("/column")
//	public ResponseEntity<?> getExcelDataColumn( @RequestParam("file") MultipartFile file
//			
//			){
//		Map<String,Object> map = new HashMap<>();
//		if (Helper.checkExcelFormat(file)) {
//			try {
//				
//				String fileName = file.getOriginalFilename();
//				
//				//xmlHandler.getDataFromExcel(file);
//				//map = (Map<String, Object>) excelToXml.convertExcelToXml(file.getInputStream()); // Row wise XMl generator
//				map = (Map<String, Object>) excelToXMLColumnWise.convertExcelToXmlColumnWise(file.getInputStream(), fileName); // Column wise XMl generator
//				
//				
//				
//			} catch (Exception e) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//				
//				
//			}           
//			
//			
//		}else {
//			map.put("STATUS","Kindly upload Excel file only");
//		}
//		
//		return ResponseEntity.ok(map);
//		
//	}
	
	

}
