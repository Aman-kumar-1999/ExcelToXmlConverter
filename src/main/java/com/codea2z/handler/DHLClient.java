package com.codea2z.handler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

//import com.codea2z.xmlobject.ShipmentRequest;
//import com.codea2z.xmlobject.ShipmentRequest.Billing;
//import com.codea2z.xmlobject.ShipmentRequest.Consignee;
//import com.codea2z.xmlobject.ShipmentRequest.Consignee.Contact;
//import com.codea2z.xmlobject.ShipmentRequest.Consignee.RegistrationNumbers;
//import com.codea2z.xmlobject.ShipmentRequest.Consignee.RegistrationNumbers.RegistrationNumber;
//import com.codea2z.xmlobject.ShipmentRequest.Dutiable;
//import com.codea2z.xmlobject.ShipmentRequest.ExportDeclaration;
//import com.codea2z.xmlobject.ShipmentRequest.ExportDeclaration.OtherCharges;
//import com.codea2z.xmlobject.ShipmentRequest.ExportDeclaration.OtherCharges.OtherCharge;
//import com.codea2z.xmlobject.ShipmentRequest.Request;
//import com.codea2z.xmlobject.ShipmentRequest.Request.MetaData;
//import com.codea2z.xmlobject.ShipmentRequest.Request.ServiceHeader;

import generated.ShipmentRequest;
import generated.ShipmentRequest.Billing;
import generated.ShipmentRequest.Consignee;
import generated.ShipmentRequest.Consignee.Contact;
import generated.ShipmentRequest.Consignee.RegistrationNumbers;
import generated.ShipmentRequest.Consignee.RegistrationNumbers.RegistrationNumber;
import generated.ShipmentRequest.Dutiable;
import generated.ShipmentRequest.ExportDeclaration;
import generated.ShipmentRequest.ExportDeclaration.CustomsDocuments;
import generated.ShipmentRequest.ExportDeclaration.CustomsDocuments.CustomsDocument;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.CustomsPaperworks;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.CustomsPaperworks.CustomsPaperwork;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.GrossWeight;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.ItemReferences;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.ItemReferences.ItemReference;
import generated.ShipmentRequest.ExportDeclaration.ExportLineItem.Weight;
import generated.ShipmentRequest.ExportDeclaration.InvoiceIndicativeCustomsValues;
import generated.ShipmentRequest.ExportDeclaration.InvoiceReferences;
import generated.ShipmentRequest.ExportDeclaration.InvoiceReferences.InvoiceReference;
import generated.ShipmentRequest.ExportDeclaration.OtherCharges;
import generated.ShipmentRequest.ExportDeclaration.OtherCharges.OtherCharge;
import generated.ShipmentRequest.Reference;
import generated.ShipmentRequest.Request;
import generated.ShipmentRequest.Request.MetaData;
import generated.ShipmentRequest.Request.ServiceHeader;
import generated.ShipmentRequest.ShipmentDetails;
import generated.ShipmentRequest.ShipmentDetails.Pieces;
import generated.ShipmentRequest.ShipmentDetails.Pieces.Piece;
import generated.ShipmentRequest.ShipmentDetails.Pieces.Piece.AdditionalInformation;
import generated.ShipmentRequest.ShipmentDetails.Pieces.Piece.AdditionalInformation.CustomerAdditionalInformation;
import generated.ShipmentRequest.ShipmentDetails.Pieces.Piece.AdditionalInformation.CustomerBarcodes;
import generated.ShipmentRequest.ShipmentDetails.Pieces.Piece.PieceReference;
import generated.ShipmentRequest.Shipper;
import generated.ShipmentRequest.SpecialService;


//import jakarta.xml.bind.JAXBContext;
//import jakarta.xml.bind.JAXBException;
//import jakarta.xml.bind.Marshaller;
//import jakarta.xml.bind.Unmarshaller;
@Service
public class DHLClient {

	public String data(InputStream file) {
	//public RequestEntity<?> data(InputStream file) {
		
		String responseData = null;

		try {
			String str = "9128984478";

			ShipmentRequest shipmentRequest = new ShipmentRequest();
			
			//FileInputStream file = new FileInputStream("D://rough/Excel_To_Xml_Converter/ExcelToXmlConverter/src/main/resources/excel/DHL_XML_Data.xlsx");
	            //Workbook workbook = new XSSFWorkbook();
	            XSSFWorkbook workbook = new XSSFWorkbook(file);
	            // Assuming the data starts from the second row
	            //Sheet sheet = workbook.getSheetAt(1);
	            XSSFSheet sheet = workbook.getSheet("Sheet1");
	            Iterator<Row> rowIterator = sheet.iterator();

	            // Skip the header row
	            rowIterator.next();

	            //List<Person> persons = new ArrayList<>();

	            while (rowIterator.hasNext()) {
	            	
	                Row row = rowIterator.next();
	              //---------------------------------------------------------------------------------------------------------------
					Request request = new Request();
						ServiceHeader serviceHeader = new ServiceHeader();
						//String dateTimeString = "2023-11-18 19:39:00";

				        // Create an instance of DatatypeFactory
				        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

				        // Parse the DateTime string to LocalDateTime
				        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
				        //LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

				        // Convert LocalDateTime to XMLGregorianCalendar
				        XMLGregorianCalendar xmlGregorianCalendar = datatypeFactory.newXMLGregorianCalendar(row.getCell(0).getStringCellValue());

						serviceHeader.setMessageTime(xmlGregorianCalendar);
						serviceHeader.setMessageReference(new BigInteger(String.valueOf(row.getCell(1).getStringCellValue())));
				        serviceHeader.setSiteID(row.getCell(2).getStringCellValue());
						serviceHeader.setPassword(row.getCell(3).getStringCellValue());
					request.setServiceHeader(serviceHeader); // Sequence
															// Sating Service Header

						MetaData metaData = new MetaData();
						metaData.setSoftwareName(row.getCell(4).getStringCellValue());
						metaData.setSoftwareVersion(Float.valueOf((float) row.getCell(5).getNumericCellValue()));
					request.setMetaData(metaData); // Sequence // Sating Meta
				shipmentRequest.setRequest(request); // Sequence // Sating Request

				String regionCode = row.getCell(6).getStringCellValue();
				shipmentRequest.setRegionCode(regionCode); // Sating Region Code
				String languageCode = row.getCell(7).getStringCellValue();
				
				shipmentRequest.setLanguageCode(languageCode); // Sating Language Code
				//------------------------------------------------------------------------------------------------------------------
					Billing billing = new Billing();
					billing.setShipperAccountNumber(row.getCell(8).getStringCellValue());
					billing.setShippingPaymentType(row.getCell(9).getStringCellValue());
					billing.setBillingAccountNumber(row.getCell(10).getStringCellValue());
				shipmentRequest.setBilling(billing); // Sequence // Sating Billing
				//------------------------------------------------------------------------------------------------------------------
					Consignee consignee = new Consignee();
					consignee.setCompanyName(row.getCell(11).getStringCellValue());
					consignee.setAddressLine1(row.getCell(12).getStringCellValue());
					consignee.setAddressLine2(row.getCell(13).getStringCellValue());
					consignee.setAddressLine3(row.getCell(14).getStringCellValue());
					consignee.setCity(row.getCell(15).getStringCellValue());
					consignee.setPostalCode((int) row.getCell(16).getNumericCellValue());
					consignee.setCountryCode(row.getCell(17).getStringCellValue());
					consignee.setCountryName(row.getCell(18).getStringCellValue());

						Contact contact = new Contact();
						contact.setPersonName(row.getCell(19).getStringCellValue());
						contact.setFaxNumber((byte) ((int) row.getCell(20).getNumericCellValue()));
						contact.setEmail(row.getCell(21).getStringCellValue());
						contact.setMobilePhoneNumber((long) row.getCell(22).getNumericCellValue());
						contact.setPhoneNumber((long) row.getCell(23).getNumericCellValue());
						contact.setPhoneExtension(row.getCell(24).getStringCellValue());
					consignee.setContact(contact); // Sequence // Sating Contact into Consignee

					consignee.setStreetName(row.getCell(25).getStringCellValue());
					consignee.setBuildingName(row.getCell(26).getStringCellValue());
					consignee.setStreetNumber(row.getCell(27).getStringCellValue());

					RegistrationNumbers registrationNumbers = new RegistrationNumbers();

						RegistrationNumber registrationNumber = new RegistrationNumber();
						registrationNumber.setNumber((long) row.getCell(28).getNumericCellValue());
						registrationNumber.setNumberTypeCode(row.getCell(29).getStringCellValue());
						registrationNumber.setNumberIssuerCountryCode(row.getCell(30).getStringCellValue());
				

					registrationNumbers.getRegistrationNumber().add(registrationNumber); // Sequence // Sating Registration
																						// Number
					consignee.setRegistrationNumbers(registrationNumbers);
					consignee.setBusinessPartyTypeCode(row.getCell(31).getStringCellValue());
				shipmentRequest.setConsignee(consignee); // Sequence // Sating Consignee with all related object
				
				//----------------------------------------------------------------------------------------------------------------
				
					Dutiable dutiable = new Dutiable();
					dutiable.setDeclaredCurrency(row.getCell(33).getStringCellValue());
					dutiable.setDeclaredValue((Float.valueOf((float) row.getCell(32).getNumericCellValue())));
					dutiable.setShipperEIN(row.getCell(34).getStringCellValue());
					dutiable.setTermsOfTrade(row.getCell(35).getStringCellValue());
				shipmentRequest.setDutiable(dutiable); // Sequence // Sating Dutiable
				//----------------------------------------------------------------------------------------------------------------
				String useDHLInvoice = row.getCell(36).getStringCellValue();
				shipmentRequest.setUseDHLInvoice(useDHLInvoice); // Sating UseDHLINvoice
				String dHLInvoiceLanguageCode = row.getCell(37).getStringCellValue();
				shipmentRequest.setDHLInvoiceLanguageCode(dHLInvoiceLanguageCode); // Sating DHLInvoiceLanguageCode
				String dHLInvoiceType = row.getCell(38).getStringCellValue();
				shipmentRequest.setDHLInvoiceType(dHLInvoiceType); // DHLInvoiceType
				//---------------------------------------------------------------------------------------------------------------
					ExportDeclaration exportDeclaration = new ExportDeclaration();
					exportDeclaration.setInterConsignee(row.getCell(39).getStringCellValue());
					exportDeclaration.setIsPartiesRelation(row.getCell(40).getStringCellValue());
					exportDeclaration.setSignatureName(row.getCell(41).getStringCellValue());
					exportDeclaration.setSignatureTitle(row.getCell(42).getStringCellValue());
					exportDeclaration.setExportReason(row.getCell(43).getStringCellValue());
					exportDeclaration.setExportReasonCode(row.getCell(44).getStringCellValue());
					exportDeclaration.setInvoiceNumber(row.getCell(45).getStringCellValue());
					DatatypeFactory datatypeFactory1 = DatatypeFactory.newInstance();
			        XMLGregorianCalendar xmlGregorianCalendar1 = datatypeFactory1.newXMLGregorianCalendar(row.getCell(46).getStringCellValue());
					exportDeclaration.setInvoiceDate(xmlGregorianCalendar1);
					
					exportDeclaration.setRemarks(row.getCell(47).getStringCellValue());

						OtherCharges otherCharges = new OtherCharges();

							OtherCharge otherCharge = new OtherCharge();
							otherCharge.setOtherChargeCaption(row.getCell(48).getStringCellValue());
							otherCharge.setOtherChargeType(row.getCell(49).getStringCellValue());
							otherCharge.setOtherChargeValue((Float.valueOf((float) row.getCell(50).getNumericCellValue())));
						
						otherCharges.getOtherCharge().add(otherCharge); // Sequence // Set Other Charge

				
					exportDeclaration.setOtherCharges(otherCharges); // Set OtherCharges
					// Cell value must be change
					exportDeclaration.setTermsOfPayment(row.getCell(51).getStringCellValue());
					exportDeclaration.setSignatureImage(row.getCell(52).getStringCellValue());
					exportDeclaration.setReceiverReference(row.getCell(53).getStringCellValue());
					exportDeclaration.setExporterId((int) row.getCell(54).getNumericCellValue());
					exportDeclaration.setExporterCode(row.getCell(55).getStringCellValue());
					exportDeclaration.setPackageMarks(row.getCell(56).getStringCellValue());
					exportDeclaration.setOtherRemarks2(row.getCell(57).getStringCellValue());
					exportDeclaration.setOtherRemarks3(row.getCell(58).getStringCellValue());
					exportDeclaration.setAddDeclText1(row.getCell(59).getStringCellValue());
				//exportDeclaration.sete
				
						ExportLineItem exportLineItem = new ExportLineItem();
						exportLineItem.setLineNumber((byte) ((int) row.getCell(60).getNumericCellValue()));
						exportLineItem.setQuantity((byte) ((int) row.getCell(61).getNumericCellValue()));
						exportLineItem.setQuantityUnit(row.getCell(62).getStringCellValue());
						exportLineItem.setDescription(row.getCell(63).getStringCellValue());
						exportLineItem.setValue((Float.valueOf((float) row.getCell(64).getNumericCellValue())));
						exportLineItem.setCommodityCode(row.getCell(65).getStringCellValue());
				
							Weight weight = new Weight();
							weight.setWeight((Float.valueOf((float) row.getCell(66).getNumericCellValue())));
							weight.setWeightUnit(row.getCell(67).getStringCellValue());
						exportLineItem.setWeight(weight);
				
							GrossWeight grossWeight = new GrossWeight();
							grossWeight.setWeight((Float.valueOf((float) row.getCell(68).getNumericCellValue())));
							grossWeight.setWeightUnit(row.getCell(69).getStringCellValue());
						exportLineItem.setGrossWeight(grossWeight);
				
						exportLineItem.setManufactureCountryCode(row.getCell(70).getStringCellValue());
						exportLineItem.setImportCommodityCode(row.getCell(71).getStringCellValue());
				
							ItemReferences itemReferences = new ItemReferences();
								ItemReference itemReference = new ItemReference();
								itemReference.setItemReferenceType(row.getCell(72).getStringCellValue());
								itemReference.setItemReferenceNumber((long) row.getCell(73).getNumericCellValue());
							itemReferences.getItemReference().add(itemReference);
						exportLineItem.setItemReferences(itemReferences);
				
							CustomsPaperworks customsPaperworks = new CustomsPaperworks();
								CustomsPaperwork customsPaperwork = new CustomsPaperwork();
								customsPaperwork.setCustomsPaperworkID(row.getCell(74).getStringCellValue());
								customsPaperwork.setCustomsPaperworkType(row.getCell(75).getStringCellValue());
							customsPaperworks.setCustomsPaperwork(customsPaperwork);
						exportLineItem.setCustomsPaperworks(customsPaperworks);
				
					exportDeclaration.getExportLineItem().add(exportLineItem);
				
					exportDeclaration.setInvoiceInstructions(row.getCell(76).getStringCellValue());
					exportDeclaration.setPlaceOfIncoterm(row.getCell(77).getStringCellValue());
					exportDeclaration.setShipmentPurpose(row.getCell(78).getStringCellValue());
					exportDeclaration.setDocumentFunction(row.getCell(79).getStringCellValue());
				
						CustomsDocuments customsDocuments = new CustomsDocuments();
							CustomsDocument customsDocument = new CustomsDocument();
							customsDocument.setCustomsDocumentID(row.getCell(80).getStringCellValue());
							customsDocument.setCustomsDocumentType(row.getCell(81).getStringCellValue());
						customsDocuments.setCustomsDocument(customsDocument);
					exportDeclaration.setCustomsDocuments(customsDocuments);
				
					exportDeclaration.setInvoiceTotalNetWeight((Float.valueOf((float) row.getCell(82).getNumericCellValue())));
					exportDeclaration.setInvoiceTotalGrossWeight((Float.valueOf((float) row.getCell(83).getNumericCellValue())));
				
						InvoiceReferences invoiceReferences = new InvoiceReferences();
							InvoiceReference invoiceReference = new InvoiceReference();
							invoiceReference.setInvoiceReferenceType(row.getCell(84).getStringCellValue());
							invoiceReference.setInvoiceReferenceNumber(row.getCell(85).getStringCellValue());
						invoiceReferences.getInvoiceReference().add(invoiceReference);
				
					exportDeclaration.setInvoiceReferences(invoiceReferences);
				
						InvoiceIndicativeCustomsValues indicativeCustomsValues = new InvoiceIndicativeCustomsValues();
						indicativeCustomsValues.setImportCustomsDutyValue((Float.valueOf((float) row.getCell(86).getNumericCellValue())));
						indicativeCustomsValues.setImportTaxesValue((Float.valueOf((float) row.getCell(87).getNumericCellValue())));
					exportDeclaration.setInvoiceIndicativeCustomsValues(indicativeCustomsValues);	
				
				shipmentRequest.setExportDeclaration(exportDeclaration);    // Sating ExportDeclaration
				
				//---------------------------------------------------------------------------------------------------------------
				Reference reference = new Reference();
				reference.setReferenceID(row.getCell(88).getStringCellValue());
				reference.setReferenceType(row.getCell(89).getStringCellValue());
				
				shipmentRequest.setReference(reference);
				//---------------------------------------------------------------------------------------------------------------
					ShipmentDetails shipmentDetails = new ShipmentDetails();
				
						Pieces pieces = new Pieces();
							Piece piece = new Piece();
							piece.setPieceID((byte) ((int) row.getCell(90).getNumericCellValue()));
							piece.setPackageType(row.getCell(91).getStringCellValue());
							piece.setWidth((byte) ((int) row.getCell(92).getNumericCellValue()));
							piece.setHeight((byte) ((int) row.getCell(93).getNumericCellValue()));
							piece.setDepth((byte) ((int) row.getCell(94).getNumericCellValue()));
							piece.setPieceContents(row.getCell(95).getStringCellValue());
								PieceReference pieceReference = new PieceReference();
								pieceReference.setReferenceID(row.getCell(96).getStringCellValue());
								pieceReference.setReferenceType(row.getCell(97).getStringCellValue());			
							piece.setPieceReference(pieceReference);
				
								AdditionalInformation additionalInformation = new AdditionalInformation();
				
									CustomerBarcodes customerBarcodes = new CustomerBarcodes();
									customerBarcodes.setBarcodeContent((int) row.getCell(98).getNumericCellValue());
									customerBarcodes.setBarcodeType((byte) ((int) row.getCell(99).getNumericCellValue()));
									customerBarcodes.setTextBelowBarcode((int) row.getCell(100).getNumericCellValue());			
								additionalInformation.setCustomerBarcodes(customerBarcodes);
				
									CustomerAdditionalInformation customerAdditionalInformation = new CustomerAdditionalInformation();
									customerAdditionalInformation.setCaption(row.getCell(101).getStringCellValue());
									customerAdditionalInformation.setDescription(row.getCell(102).getStringCellValue());			
								additionalInformation.setCustomerAdditionalInformation(customerAdditionalInformation);			
								additionalInformation.setCustomerDescription(row.getCell(103).getStringCellValue());
							
							piece.setAdditionalInformation(additionalInformation);
							piece.setParentPieceIdentificationNumber(row.getCell(104).getStringCellValue());
							piece.setPieceIdentificationNumber(row.getCell(105).getStringCellValue());
							piece.setUseOwnPieceIdentificationNumber(row.getCell(106).getStringCellValue());
							piece.setPieceIdentificationNumberDataIdentifier(row.getCell(107).getStringCellValue());
							
						pieces.getPiece().add(piece);
						
						
					shipmentDetails.setPieces(pieces);
					shipmentDetails.setWeightUnit(row.getCell(108).getStringCellValue());
					shipmentDetails.setGlobalProductCode(row.getCell(109).getStringCellValue());
					shipmentDetails.setLocalProductCode(row.getCell(110).getStringCellValue());
					DatatypeFactory datatypeFactory2 = DatatypeFactory.newInstance();
			        XMLGregorianCalendar xmlGregorianCalendar2 = datatypeFactory1.newXMLGregorianCalendar(row.getCell(111).getStringCellValue());
					shipmentDetails.setDate(xmlGregorianCalendar2);
					shipmentDetails.setContents(row.getCell(112).getStringCellValue());
					shipmentDetails.setDimensionUnit(row.getCell(113).getStringCellValue());
					shipmentDetails.setPackageType(row.getCell(114).getStringCellValue());
					shipmentDetails.setIsDutiable(row.getCell(115).getStringCellValue());
					shipmentDetails.setCurrencyCode(row.getCell(116).getStringCellValue());
					
				shipmentRequest.setShipmentDetails(shipmentDetails);
				//--------------------------------------------------------------------------------------------------------------
					Shipper shipper = new Shipper();
					shipper.setShipperID(row.getCell(117).getStringCellValue());
					shipper.setCompanyName(row.getCell(118).getStringCellValue());
					shipper.setAddressLine1(row.getCell(119).getStringCellValue());
					shipper.setAddressLine2(row.getCell(120).getStringCellValue());
					shipper.setAddressLine3(row.getCell(121).getStringCellValue());
					shipper.setCity(row.getCell(122).getStringCellValue());
					shipper.setDivisionCode(row.getCell(123).getStringCellValue());
					shipper.setPostalCode((int) row.getCell(124).getNumericCellValue());
					shipper.setCountryCode(row.getCell(125).getStringCellValue());
					shipper.setCountryName(row.getCell(126).getStringCellValue());
				
						ShipmentRequest.Shipper.Contact shipperContact = new ShipmentRequest.Shipper.Contact();
						shipperContact.setPersonName(row.getCell(127).getStringCellValue());
						shipperContact.setPhoneNumber(row.getCell(128).getStringCellValue());
						shipperContact.setPhoneExtension((short) row.getCell(129).getNumericCellValue());
						shipperContact.setFaxNumber((long) row.getCell(130).getNumericCellValue());
						shipperContact.setTelex((int) row.getCell(131).getNumericCellValue());
						shipperContact.setEmail(row.getCell(132).getStringCellValue());
						shipperContact.setMobilePhoneNumber((long) row.getCell(133).getNumericCellValue());
				
					shipper.setContact(shipperContact);
					
					shipper.setStreetName(row.getCell(134).getStringCellValue());
					shipper.setBuildingName(row.getCell(135).getStringCellValue());
					shipper.setStreetNumber(row.getCell(136).getStringCellValue());
					
						ShipmentRequest.Shipper.RegistrationNumbers shipperRegistraNumbers = new ShipmentRequest.Shipper.RegistrationNumbers();
							ShipmentRequest.Shipper.RegistrationNumbers.RegistrationNumber shipperRegistraNumber = new ShipmentRequest.Shipper.RegistrationNumbers.RegistrationNumber();
							shipperRegistraNumber.setNumber(row.getCell(137).getStringCellValue());
							shipperRegistraNumber.setNumberTypeCode(row.getCell(138).getStringCellValue());
							shipperRegistraNumber.setNumberIssuerCountryCode(row.getCell(139).getStringCellValue());
						shipperRegistraNumbers.getRegistrationNumber().add(shipperRegistraNumber);
					
					shipper.setRegistrationNumbers(shipperRegistraNumbers);
					shipper.setBusinessPartyTypeCode(row.getCell(140).getStringCellValue());					
					
				shipmentRequest.setShipper(shipper);
				//-------------------------------------------------------------------------------------------------------------
					SpecialService specialService = new SpecialService();
					specialService.setSpecialServiceType(row.getCell(141).getStringCellValue());
				shipmentRequest.setSpecialService(specialService);
				//-------------------------------------------------------------------------------------------------------------
	                
	                
	                
	                ///Person person = new Person();
	                ///person.setName(row.getCell(0).getStringCellValue());
	                ///person.setAge((int) row.getCell(1).getNumericCellValue());

	                ///Address address = new Address();
	                ///address.setStreet(row.getCell(2).getStringCellValue());
	                ///address.setCity(row.getCell(3).getStringCellValue());
	                ///address.setCountry(row.getCell(4).getStringCellValue());

	                ///person.setAddress(address);

	                ///ContactDetails contactDetails = new ContactDetails();
	                ///contactDetails.setEmail(row.getCell(5).getStringCellValue());
	                ///contactDetails.setPhone(row.getCell(6).getStringCellValue());

	                ///person.setContactDetails(contactDetails);

	                ///persons.add(person);
	            }

	            //PersonList personList = new PersonList();
	            //personList.setPersons(persons);

	            // Now you have the data mapped to Java objects
	            //System.out.println("Mapped Java Objects:");
	            

			
			
			
			System.out.println("W ::::::::::::::  " + marshalToXml(shipmentRequest));
			responseData = marshalToXml(shipmentRequest);
			
			//object3 = marshalToXml(shipmentRequest);
			
		} catch (Exception e) {
			// TODO Auto-com.codea2z.xmlobject catch block
			responseData = "Through Exception.";
			e.printStackTrace();
		}

		// Create a Marshaller

		return responseData;
		//return RequestEntity.ok(responseData);
		
	}
	
	private static String marshalToXml(Object obj) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, sw);
        return sw.toString();
    }
	
	private static Object unmarshalFromXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Object.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Object) unmarshaller.unmarshal(new StringReader(xml));
    }
	
	public void generateDemoExcel() {
		try {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Form Data");

        // Create header row
        Row headerRow = sheet.createRow(0);

        // Add header cells
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Email");
        headerRow.createCell(2).setCellValue("Address");

        // Create data rows
        Row dataRow1 = sheet.createRow(1);
        dataRow1.createCell(0).setCellValue("John Doe");
        dataRow1.createCell(1).setCellValue("johndoe@example.com");
        dataRow1.createCell(2).setCellValue("123 Main Street, Anytown, CA 12345");

        Row dataRow2 = sheet.createRow(2);
        dataRow2.createCell(0).setCellValue("Jane Smith");
        dataRow2.createCell(1).setCellValue("janesmith@example.com");
        dataRow2.createCell(2).setCellValue("456 Elm Street, Anytown, CA 98765");

        // Autosize columns
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to an Excel file
        FileOutputStream fileOutputStream;
		
		fileOutputStream = new FileOutputStream("excelForm.xlsx");
			
		workbook.write(fileOutputStream);
		fileOutputStream.close();
			
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        System.out.println("Excel form generated successfully.");
    }

}
