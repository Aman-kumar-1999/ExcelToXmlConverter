package com.codea2z.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.codea2z.config.CbeXiiRoot;
import com.codea2z.config.CbeXiiiRoot;

import generated.CBEXIII;
import generated.CBEXIII.AddressOfTheConsignee;
import generated.CBEXIII.AddressOfTheConsignor;
import generated.CBEXIII.CRNDetails;
import generated.CBEXIII.Header;
import generated.CBEXIII.Header.AddressOfTheAuthorizedCourier;
import generated.CBEXIII.ItemDesc;
import generated.CBEXIII.ItemDesc.AddressOfManufacturer;
import generated.CBEXIII.ItemDesc.Charges;
import generated.CBEXIII.ItemDesc.Notification;
import generated.CBEXIII.ItemDesc.Notification.IGSTNotification;
import generated.CBEXIII.ItemDesc.Notification.NonTariffNotification;
import generated.CBEXIII.ItemDesc.Notification.TariffNotification;

@Service
public class CbeXiiiHandler {
	
	

	public String sendExcelData(InputStream inputStream) {
		String responseData = null;
		try {
			
			List<CBEXIII> listCbeiii = new ArrayList<CBEXIII>();
			CbeXiiiRoot cbeXiiiRoot = new CbeXiiiRoot();
			
			CBEXIII cbexiii = new CBEXIII();
			
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			
			XSSFSheet sheet = workbook.getSheet("Sheet1");		
			
			Iterator<Row> rowIterator = sheet.iterator();
			
			rowIterator.next();
			
			while(rowIterator.hasNext()) {
				
				Row row = rowIterator.next();
					Header header = new Header();
					header.setCourierRegistrationNumber(row.getCell(0).getStringCellValue());
					header.setNameOfTheAuthorizedCourier(row.getCell(1).getStringCellValue());
						AddressOfTheAuthorizedCourier addressOfTheAuthorizedCourier = new AddressOfTheAuthorizedCourier();
						addressOfTheAuthorizedCourier.setAddress1(row.getCell(2).getStringCellValue());
						addressOfTheAuthorizedCourier.setAddress2(row.getCell(3).getStringCellValue());
						addressOfTheAuthorizedCourier.setCity(row.getCell(4).getStringCellValue());
						addressOfTheAuthorizedCourier.setState(row.getCell(5).getStringCellValue());
						addressOfTheAuthorizedCourier.setPostalCode((long) row.getCell(6).getNumericCellValue());
						addressOfTheAuthorizedCourier.setCountry(row.getCell(7).getStringCellValue());
					header.setAddressOfTheAuthorizedCourier(addressOfTheAuthorizedCourier);				
					header.setAirlinesName(row.getCell(8).getStringCellValue());
					header.setFlightNumber(row.getCell(9).getStringCellValue());
					header.setAirportOfArrival(row.getCell(10).getStringCellValue());
					header.setFirstPortOfArrival(row.getCell(11).getStringCellValue());
					header.setDateOfArrival(row.getCell(12).getDateCellValue().toString());
					header.setTimeOfArrival(row.getCell(13).getStringCellValue());
					header.setAirportOfShippment(row.getCell(14).getStringCellValue());
				cbexiii.setHeader(header);
				cbexiii.setHAWBNumber(row.getCell(15).getStringCellValue());
				cbexiii.setHAWBCRN(row.getCell(16).getStringCellValue());
					CRNDetails crnDetails = new CRNDetails();
					crnDetails.setCRNNo(row.getCell(17).getStringCellValue());					
				cbexiii.setCRNDetails(crnDetails);
				cbexiii.setNumberOfMPSPackages( (short) row.getCell(18).getNumericCellValue());
				//cbexiii.setNumberOfPackages((short) row.getCell(19).getNumericCellValue());					// remove
				//cbexiii.setHAWBImageAttached(row.getCell(20).getStringCellValue());           // remove
				cbexiii.setNumberOfItemsInHAWB((short) row.getCell(19).getNumericCellValue());
				cbexiii.setCountryOfExportation(row.getCell(20).getStringCellValue());
				cbexiii.setUniqueConsignmentReferenceNo(row.getCell(21).getStringCellValue());					// changes
				cbexiii.setNameOfTheConsignor(row.getCell(22).getStringCellValue());
				
					AddressOfTheConsignor addressOfTheConsignor = new AddressOfTheConsignor();
					addressOfTheConsignor.setAddress1(row.getCell(23).getStringCellValue());
					addressOfTheConsignor.setAddress2(row.getCell(24).getStringCellValue());
					addressOfTheConsignor.setCity(row.getCell(25).getStringCellValue());
					addressOfTheConsignor.setState(row.getCell(26).getStringCellValue());
					addressOfTheConsignor.setPostalCode((short) row.getCell(27).getNumericCellValue());
					addressOfTheConsignor.setCountry(row.getCell(28).getStringCellValue());
				cbexiii.setAddressOfTheConsignor(addressOfTheConsignor);
				cbexiii.setNameOfTheConsignee(row.getCell(29).getStringCellValue());
				
					AddressOfTheConsignee addressOfTheConsignee = new AddressOfTheConsignee();
					addressOfTheConsignee.setAddress1(row.getCell(30).getStringCellValue());
					addressOfTheConsignee.setAddress2(row.getCell(31).getStringCellValue());
					addressOfTheConsignee.setCity(row.getCell(32).getStringCellValue());
					addressOfTheConsignee.setState(row.getCell(33).getStringCellValue());
					addressOfTheConsignee.setPostalCode((short) row.getCell(34).getNumericCellValue());
					addressOfTheConsignee.setCountry(row.getCell(35).getStringCellValue());
				
				cbexiii.setAddressOfTheConsignee(addressOfTheConsignee);
				cbexiii.setImportExportCode((long) row.getCell(36).getNumericCellValue());
				cbexiii.setIECBranchCode((short) row.getCell(37).getNumericCellValue());       // changes
				//Special_Requests
				cbexiii.setSpecialRequests(row.getCell(38).getStringCellValue());
				cbexiii.setGrossWeight(new BigDecimal(row.getCell(39).getNumericCellValue()));
				cbexiii.setNetWeight(new BigDecimal(row.getCell(40).getNumericCellValue()));
				//GSTIN_Type
				cbexiii.setGSTINType(row.getCell(41).getStringCellValue());
				//GSTIN_Number
				cbexiii.setGSTINNumber(row.getCell(42).getStringCellValue());
				//State_code
				cbexiii.setStateCode((short) row.getCell(43).getNumericCellValue());
				//AD_Code
				cbexiii.setADCode((long) row.getCell(44).getNumericCellValue());
				//GOV_NONGOV_TYPE
				cbexiii.setGOVNONGOVTYPE(row.getCell(45).getStringCellValue());
					
					ItemDesc itemDesc = new ItemDesc();
					itemDesc.setLicenseType(row.getCell(46).getStringCellValue());									// add
					itemDesc.setLicenseNumber((long) row.getCell(47).getNumericCellValue());									// add
					itemDesc.setCTSH((long) row.getCell(48).getNumericCellValue());					
					//itemDesc.setNatureOfShipment(row.getCell(42).getStringCellValue());
					itemDesc.setCETSH((long) row.getCell(49).getNumericCellValue());     		// add
					itemDesc.setCountryOfOrigin(row.getCell(50).getStringCellValue());
					itemDesc.setDescriptionOfGoods(row.getCell(51).getStringCellValue());
					itemDesc.setNameOfManufacturer(row.getCell(52).getStringCellValue());
						
						AddressOfManufacturer addressOfManufacturer = new AddressOfManufacturer();
						addressOfManufacturer.setAddress1(row.getCell(53).getStringCellValue());
						addressOfManufacturer.setAddress2(row.getCell(54).getStringCellValue());
						addressOfManufacturer.setCity(row.getCell(55).getStringCellValue());
						addressOfManufacturer.setState(row.getCell(56).getStringCellValue());
						addressOfManufacturer.setPostalCode((short) row.getCell(57).getNumericCellValue());
						addressOfManufacturer.setCountry(row.getCell(58).getStringCellValue());				
					
					itemDesc.setAddressOfManufacturer(addressOfManufacturer);
					itemDesc.setNumberOfPackages((short) row.getCell(59).getNumericCellValue());
					itemDesc.setMarksNumberOfPackages((long) row.getCell(60).getNumericCellValue());
					itemDesc.setUnitOfMeasure(row.getCell(61).getStringCellValue());
					itemDesc.setQuantity(new BigDecimal (row.getCell(62).getNumericCellValue()));
					itemDesc.setInvoiceNumber((long) row.getCell(63).getNumericCellValue());
					itemDesc.setInvoiceValue(new BigDecimal(row.getCell(64).getNumericCellValue()));
					itemDesc.setInvoiceCurrency(row.getCell(65).getStringCellValue());
					itemDesc.setRateOfExchange(new BigDecimal(row.getCell(66).getNumericCellValue()));
					//added
					itemDesc.setRSP(row.getCell(67).toString());
					itemDesc.setIncoterms(row.getCell(68).getStringCellValue());
					itemDesc.setDiscountAmount((long) row.getCell(69).getNumericCellValue());
					itemDesc.setDiscountCurrency(row.getCell(70).getStringCellValue());//added
					
					itemDesc.setAssessableValue(new BigDecimal(row.getCell(71).getNumericCellValue()));
					//added
					itemDesc.setFreightCharges(new BigDecimal(row.getCell(72).getNumericCellValue()));
					itemDesc.setLandingCharges(new BigDecimal(row.getCell(73).getNumericCellValue()));
					itemDesc.setInsurance(new BigDecimal(row.getCell(74).getNumericCellValue()));
					itemDesc.setRSPFlag(row.getCell(75).getStringCellValue());//added
						//added
						Charges charges = new Charges();
						charges.setChargeType(row.getCell(76).getStringCellValue());
						charges.setChargeAmount((long) row.getCell(77).getNumericCellValue());
					itemDesc.setCharges(charges); //added
					
						Notification notification = new Notification();
						
							TariffNotification tariffNotification = new TariffNotification();
							tariffNotification.setTariffNotificationNumber(row.getCell(78).getStringCellValue());
							tariffNotification.setTariffNotificationSerialNumber((short) row.getCell(79).getNumericCellValue()); // changes
							// added
							tariffNotification.setListNumber((long) row.getCell(80).getNumericCellValue());
							tariffNotification.setListSerialNumber((long) row.getCell(81).getNumericCellValue());
							tariffNotification.setQuantityUnit(new BigDecimal(row.getCell(82).getNumericCellValue()));
							tariffNotification.setQuantityMeasure(row.getCell(83).getStringCellValue());
							tariffNotification.setNotificationType(row.getCell(84).getStringCellValue());
							tariffNotification.setNotificationSubType(row.getCell(85).getStringCellValue()); //added
							
						notification.setTariffNotification(tariffNotification);
							
							NonTariffNotification nonTariffNotification = new NonTariffNotification();
							nonTariffNotification.setNonTariffNotificationNumber(row.getCell(86).getStringCellValue());
							nonTariffNotification.setNonTariffNotificationSerialNumber(row.getCell(87).getStringCellValue()); //change
							// added
							nonTariffNotification.setListNumber((long) row.getCell(88).getNumericCellValue());
							nonTariffNotification.setListSerialNumber( (long) row.getCell(89).getNumericCellValue());
						notification.setNonTariffNotification(nonTariffNotification); //added
							
							// added
							IGSTNotification igstNotification = new IGSTNotification();
							igstNotification.setIGSTNotificationNumber(row.getCell(90).getStringCellValue());
							igstNotification.setIGSTSerialNumber(row.getCell(91).getStringCellValue());
							igstNotification.setIGSTType(row.getCell(92).getStringCellValue());
							igstNotification.setIGSTSubType(row.getCell(93).getStringCellValue());
							igstNotification.setIGSTRate(new BigDecimal(row.getCell(94).getNumericCellValue()));
							igstNotification.setIGSTCondition(row.getCell(95).getStringCellValue());
						notification.getIGSTNotification().add(igstNotification);
						
					itemDesc.setNotification(notification);
					itemDesc.setABATERATE(row.getCell(96).getStringCellValue());
						
				cbexiii.setItemDesc(itemDesc);
				//cbexiii.setGSTINType(row.getCell(97).getStringCellValue());
				//cbexiii.setGSTINNumber(row.getCell(98).getStringCellValue());
				//cbexiii.setStateCode((short) row.getCell(99).getNumericCellValue());
				cbexiii.setTransactionDateTime(row.getCell(97).getDateCellValue().toString());
				
				//listCbeiii.add(cbexiii);
				cbeXiiiRoot.getList().add(cbexiii);
				
					
				
			}
			
			//cbeXiiiRoot.setObjects(listCbeiii);
			
			// System.out.println("W ::::::::::::::  " + marshalToXml(cbexiii));
			responseData = marshalToXml(cbeXiiiRoot);
			//responseData = marshalToXmlListObject(cbeXiiiRoot);
			System.out.println("XML ::::::::::::::::::::::::::::::::::::::::::::::::::::::"+responseData);
			
			
			
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseData;
		
	}
	
	private String marshalToXmlListObject(CbeXiiiRoot cbeXiiiRoot) throws JAXBException {
		StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(cbeXiiiRoot.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(cbeXiiiRoot, sw);
        return sw.toString();
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

}
