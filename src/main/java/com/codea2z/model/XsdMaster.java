package com.codea2z.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XSD_MASTER")
public class XsdMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String xsdName;
	
	private String fileName;
	
	private String excelFileName;
	
	private String excelSheetName;
	
	

	public XsdMaster() {
		
	}

	public XsdMaster(Long id, String xsdName, String fileName, String excelFileName, String excelSheetName) {
		super();
		this.id = id;
		this.xsdName = xsdName;
		this.fileName = fileName;
		this.excelFileName = excelFileName;
		this.excelSheetName = excelSheetName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXsdName() {
		return xsdName;
	}

	public void setXsdName(String xsdName) {
		this.xsdName = xsdName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public String getExcelSheetName() {
		return excelSheetName;
	}

	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}
	
	
	
	

}
