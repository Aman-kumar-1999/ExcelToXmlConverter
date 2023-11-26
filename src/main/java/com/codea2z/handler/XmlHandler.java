package com.codea2z.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
//import org.yaml.snakeyaml.reader.StreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

//To convert Excel data into JSON in Java, you can use the following code:
//
//Java
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

//import com.codea2z.xmlobject.Billing;
//import com.codea2z.xmlobject.Consignee;
//import com.codea2z.xmlobject.Consignee.Contact;
//import com.codea2z.xmlobject.Consignee.RegistrationNumbers;
//import com.codea2z.xmlobject.Consignee.RegistrationNumbers.RegistrationNumber;
//import com.codea2z.xmlobject.Dutiable;
//import com.codea2z.xmlobject.ExportDeclaration;
//import com.codea2z.xmlobject.ExportDeclaration.OtherCharges;
//import com.codea2z.xmlobject.ExportDeclaration.OtherCharges.OtherCharge;
//import com.codea2z.xmlobject.ObjectFactory;
//import com.codea2z.xmlobject.Request;
//import com.codea2z.xmlobject.Request.MetaData;
//import com.codea2z.xmlobject.Request.ServiceHeader;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
//import java.io.IOException;

//import javax.xml.XMLConstants;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.SchemaOutputResolver;
//import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

@Service
public class XmlHandler {
	
//	@Autowired
//	private Jaxb2Marshaller marshaller;

	public String getDataFromExcel(MultipartFile file) {

		Map<String, Object> map = new HashMap<>();
		try {
			map = Helper.convertExcelToListOfProduct(file.getInputStream());

			List<Object> fileOutput = (List<Object>) map.get("PRODUCTS");

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

		// --------------------------------------------------------------------------------------------------------------
		// RestTemplate restTemplate = new RestTemplate();

		// String response =
		// restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",
		// String.class);
		// System.out.println("Data : " + response);

		// JSONArray jsonArray = new JSONArray(response);
		// JSONObject jsonObject = jsonArray.getJSONObject(0);
		// return jsonObject.toString();

		// JSONObject json = new JSONObject(response);

		// String xmlData = XML.toString(jsonObject);

	}

	public String convertXmlDataToXmlFile() {

		// For Getting data form fake API
		// ---------------------------------------------------------------------------------------------------------------

		RestTemplate restTemplate = new RestTemplate();

		String response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", String.class);
		// System.out.println("Data : " + response);

		JSONArray jsonArray = new JSONArray(response);

		// -----------------------------------------------------------------------------------------------------------------

		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbfactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElement("Hub");
			doc.appendChild(rootElement);

			// rootElement.appendChild(getClient(doc, childListElement, "Mob" ));
			rootElement.appendChild(getClient(doc, jsonArray, "Mob"));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult file = new StreamResult(new File(
					"D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/new_text.xml"));
			// StreamResult conlole = new StreamResult (System.out);
			// transformer.transform(source, conlole);
			transformer.transform(source, file);

		} catch (TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	// private static Node getClient(Document doc, ArrayList<Map<String,Object>>
	// list, String subRootName) {
	private static Node getClient(Document doc, JSONArray list, String subRootName) {

		Element client = doc.createElement(subRootName);

		ArrayList<String> uniqueKey = new ArrayList<>();
		for (int i = 0; i < list.length(); i++) {
			// Element client = doc.createElement(subRootName);

			JSONObject jsonObject = list.getJSONObject(i);

			Iterator<String> keys = jsonObject.keys(); // finding keys form the JSONArray.

			while (keys.hasNext()) {
				String key = keys.next();

				if (!uniqueKey.contains(key)) {
					uniqueKey.add(key);
				}

			}

			// System.out.println("Keys : "+uniqueKey);
			Element item = doc.createElement("items");
			client.appendChild(item);
			for (int j = 0; j < uniqueKey.size(); j++) {

				if (j == 0) {
					item.setAttribute(uniqueKey.get(j), jsonObject.get(uniqueKey.get(j).toString()).toString());
				} else if (j == uniqueKey.size() - 1) {
					item.appendChild(getClientDetails(doc, item, uniqueKey.get(j),
							jsonObject.get(uniqueKey.get(j).toString()).toString()));
				} else {
					item.appendChild(getClientDetails(doc, item, uniqueKey.get(j),
							jsonObject.get(uniqueKey.get(j).toString()).toString()));
				}
			}
			// client.appendChild(getClientDetails(doc, client, uniqueKey.get(j),
			// jsonObject.get(uniqueKey.get(j).toString()).toString()));

			// client.appendChild(getClientDetails(doc, client, "body",
			// jsonObject.getString("body").toString()));
		}

		return client;

	}

	private static Node getClientDetails(Document doc, Element item, String key, String value) {

		Element node = doc.createElement(key);
		node.appendChild(doc.createTextNode(value));

		return node;

	}

	public JsonArray convertExcelDataToJson(InputStream file) {

		String excelFilePath = "D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/excel/Python_Course_Response.xlsx";
		
		JsonArray jsonArray = null;
		// Create a FileInputStream object to read the Excel file
		FileInputStream fis;
		try {
			// fis = new FileInputStream(file);
			// Create a Workbook object to represent the Excel file
			Workbook workbook = new XSSFWorkbook(file);

			// Get the first sheet in the Excel file
			Sheet sheet = workbook.getSheetAt(0);

			// Create a List to store the JSON data
			List<JsonObject> jsonObjects = new ArrayList<>();

			// Iterate over the rows in the Excel sheet
			// for (Row row : sheet) {
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				// Create a JsonObject to represent the row
				JsonObject jsonObject = new JsonObject();
				Row row = sheet.getRow(i);
				// Iterate over the cells in the row
				// for (Cell cell : row) {
				for (int j = 0; i < row.getLastCellNum(); j++) {

					Cell cell = row.getCell(j);

					// Get the column name
					String columnName = String.valueOf(i);

					// Get the cell value
					// Object cellValue = ((Object)

					// Add the cell value to the JsonObject
					jsonObject.addProperty(columnName, cell.toString());
				}

				// Add the JsonObject to the List
				jsonObjects.add(jsonObject);
			}

			// Close the FileInputStream object
			// fis.close();

			// Close the Workbook object
			// workbook.close();

			// Create a Gson object
			Gson gson = new Gson();

			// Convert the List of JsonObjects to a JSONArray
			jsonArray = gson.toJsonTree(jsonObjects).getAsJsonArray();

			// Print the JSONArray
			System.out.println("JSON Array : " + jsonArray);
		} catch (IOException e) {
			// jsonArray.set
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return jsonArray;

	}

	public void mainXml() throws ParserConfigurationException, SAXException, IOException {

		try {
			// Get the XSD file
			File xsdFile = new File("D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/xsd/Shipment_Validation_Request_XSD.xsd");

			// Create a document builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			// Create a document builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XSD file
			Document xsdDocument = builder.parse(xsdFile);

			// Get the root element of the XSD document
			Element xsdRootElement = xsdDocument.getDocumentElement();

			// Create a new XML document
			Document xmlDocument = builder.newDocument();

			// Create the root element of the XML document
			Element xmlRootElement = xmlDocument.createElement(xsdDocument.getDocumentElement().getTagName());
			xmlDocument.appendChild(xmlRootElement);

			// Generate the XML file from the XSD schema
			generateXmlFromXsd(xmlRootElement, xsdRootElement, xmlDocument);

			// Save the XML file
			StreamResult xmlFile = new StreamResult(new File(
					"D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/xml-file.xml"));
			//File xmlFile = new File("D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/static/xml-file.xml");
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(xmlDocument);
			transformer.transform(source, xmlFile);


			

		} catch (TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void generateXmlFromXsd(Element xmlElement, Element xsdElement, Document xmlDocument) {
		for (int i = 0; i < xsdElement.getChildNodes().getLength(); i++) {
			if (xsdElement.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
				//Element xsdChildElement = (Element) xsdElement.getChildNodes().item(i);
				Element xsdChildElement = (Element) xsdElement.getChildNodes().item(i);

				String childElement = xsdChildElement.getTagName();
				int index = childElement.indexOf(":");

				childElement = childElement.substring(index+1);
				// Create a new XML element for the XSD child element
				System.out.println(" Node Name : "+childElement);
			
				
				
				Element xmlChildElement = xmlDocument.createElement(xsdChildElement.getNodeName());
				xmlElement.appendChild(xmlChildElement);

				// Generate the XML file from the XSD schema for the XSD child element
				generateXmlFromXsd(xmlChildElement, xsdChildElement, xmlDocument);
			}
		}
	}
	


}
