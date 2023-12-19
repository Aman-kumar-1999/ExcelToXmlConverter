package com.codea2z.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codea2z.model.XsdMaster;
import com.codea2z.repo.XsdMasterRepository;

@RestController
@RequestMapping("/xsd")
@CrossOrigin("*")
public class XsdController {
	
	@Autowired
	private XsdMasterRepository xsdMasterRepository;
	
	@PostMapping("/")
	public ResponseEntity<?> createXsd(@RequestBody XsdMaster xsdMaster){
		
		XsdMaster responseXsdMaster = xsdMasterRepository.save(xsdMaster);
		
		return ResponseEntity.ok(responseXsdMaster);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllXsd(){	
		
		return ResponseEntity.ok(xsdMasterRepository.findAll());
	}
	

}
