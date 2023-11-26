package com.codea2z.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class ExcelToXml {

	public Map<String,Object> convertExcelToXml(InputStream file, String fileName)
			throws IOException, ParserConfigurationException, TransformerException {
		// Create a new Workbook object.
		Map<String, Object> map = new HashMap<>();

		try {

			// Workbook workbook = WorkbookFactory.create(new File("excel_file.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			// Get the first sheet.
			Sheet sheet = workbook.getSheetAt(0);

			// Create a new DocumentBuilder object.
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			// Create a new Document object.
			Document document = documentBuilder.newDocument();

			// Create the root element.
			Element rootElement = document.createElement("root");
			document.appendChild(rootElement);

			// Iterate over the rows in the sheet.
			for (Row row : sheet) {
				// Create a new element for the row.
				Element rowElement = document.createElement("row");
				rootElement.appendChild(rowElement);

				// Iterate over the cells in the row.
				for (Cell cell : row) {
					// Create a new element for the cell.
					Element cellElement = document.createElement("cell");
					rowElement.appendChild(cellElement);

					// Set the value of the cell element.
					cellElement.setTextContent(cell.toString());
				}
			}

			// Create a Transformer object.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			// Create a DOMSource object.
			DOMSource domSource = new DOMSource(document);

			// Create a StreamResult object.
			StreamResult streamResult = new StreamResult(new File(
					//"D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/"+fileName+".xml"
					"D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/Converted_Rows_Wise_Xml_File.xml"
					));

			// Transform the DOMSource to the StreamResult.
			transformer.transform(domSource, streamResult);

			// Close the workbook.
			// workbook.close();

			map.put("MSG", "SUCCESS");

		} catch (Exception e) {
			e.printStackTrace();
			map.put("MSG", "ERROR");

		}

		return map;
	}
}
