package com.codea2z.handler;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelToXMLColumnWise {

	public Map<String, Object> convertExcelToXmlColumnWise(InputStream file, String fileName)
			throws IOException, ParserConfigurationException, TransformerException {
		// Create a new Workbook object.
		Map<String, Object> map = new HashMap<>();

		try {

			// Workbook workbook = WorkbookFactory.create(new File("excel_file.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get the first sheet.
			Sheet sheet = workbook.getSheetAt(0);

			// Get the number of columns in the sheet.
			int numberOfColumns = sheet.getRow(0).getLastCellNum();

			// Create a new DocumentBuilder object.
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// Create a new Document object.
			Document document = documentBuilder.newDocument();

			// Create the root element.
			Element rootElement = document.createElement("root");
			document.appendChild(rootElement);

			// Iterate over the columns in the sheet.
			for (int i = 0; i < numberOfColumns; i++) {
				// Create a new element for the column.
				String str = sheet.getRow(0).getCell(i).getStringCellValue().trim();
				String columnName = str.replaceAll("\\s", "");
		        //System.out.println(strWithoutSpaces);
				System.out.println("ColumnName : "+columnName);
				Element columnElement = document.createElement(columnName);
				rootElement.appendChild(columnElement);

				// Get the column name.

				// Set the name of the column element.
				//columnElement.setAttribute("name", columnName);

				// Iterate over the rows in the sheet and add the cell values to the column
				// element.
				for (Row row : sheet) {
					// Get the cell value.
					if (row.getCell(i) != null) {
						String cellValue = row.getCell(i).toString();
						//String strRow = cellValue.replaceAll("\\s", "");
						
						// Create a new element for the cell value.
						System.out.println("Cell : "+cellValue);
						
						Element cellElement = document.createElement("cell");
						columnElement.appendChild(cellElement);

						// Set the value of the cell element.
						cellElement.setTextContent(cellValue);
					}
				}
			}

			// Create a Transformer object.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// Create a DOMSource object.
			DOMSource domSource = new DOMSource(document);

			// Create a StreamResult object.
			StreamResult streamResult = new StreamResult(new File(
					// "D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/"+fileName+".xml"
					"D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/Converted_Column_WiseXml_File.xml"
							));

			// Transform the DOMSource to the StreamResult.
			transformer.transform(domSource, streamResult);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("MSG", "ERROR");
		}
		return map;
	}

}
