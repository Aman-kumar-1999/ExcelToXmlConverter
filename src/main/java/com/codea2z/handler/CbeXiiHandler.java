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

import generated.CBEXII;
import generated.CBEXII.AddressOfTheConsignee;
import generated.CBEXII.AddressOfTheConsignor;
import generated.CBEXII.CRNDetails;
import generated.CBEXII.Header;
import generated.CBEXII.Header.AddressOfTheAuthorizedCourier;
import generated.CBEXII.ItemDesc;
import generated.CBEXII.ItemDesc.AddressOfManufacturer;
import generated.CBEXII.ItemDesc.Notification;
import generated.CBEXII.ItemDesc.Notification.NonTariffNotification;
import generated.CBEXII.ItemDesc.Notification.TariffNotification;

@Service
public class CbeXiiHandler {
	
	

	public String sendExcelData(InputStream inputStream) {
		String responseData = null;
		try {
			
			//List<CBEXII> listCbeii = new ArrayList<CBEXII>();
			CbeXiiRoot cbeXiiRoot = new CbeXiiRoot();
			
			CBEXII cbexii = new CBEXII();
			
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
				cbexii.setHeader(header);
				cbexii.setHAWBNumber(row.getCell(15).getStringCellValue());
				cbexii.setHAWBCRN(row.getCell(16).getStringCellValue());
					CRNDetails crnDetails = new CRNDetails();
					crnDetails.setCRNNo(row.getCell(17).getStringCellValue());					
				cbexii.setCRNDetails(crnDetails);
				cbexii.setNumberOfMPSPackages( (short) row.getCell(18).getNumericCellValue());
				cbexii.setNumberOfPackages((short) row.getCell(19).getNumericCellValue());
				cbexii.setHAWBImageAttached(row.getCell(20).getStringCellValue());
				cbexii.setNumberOfItemsInHAWB((short) row.getCell(21).getNumericCellValue());
				cbexii.setCountryOfExportation(row.getCell(22).getStringCellValue());
				cbexii.setNameOfTheConsignor(row.getCell(23).getStringCellValue());
				
					AddressOfTheConsignor addressOfTheConsignor = new AddressOfTheConsignor();
					addressOfTheConsignor.setAddress1(row.getCell(24).getStringCellValue());
					addressOfTheConsignor.setAddress2(row.getCell(25).getStringCellValue());
					addressOfTheConsignor.setCity(row.getCell(26).getStringCellValue());
					addressOfTheConsignor.setState(row.getCell(27).getStringCellValue());
					addressOfTheConsignor.setPostalCode((short) row.getCell(28).getNumericCellValue());
					addressOfTheConsignor.setCountry(row.getCell(29).getStringCellValue());
				cbexii.setAddressOfTheConsignor(addressOfTheConsignor);
				cbexii.setNameOfTheConsignee(row.getCell(30).getStringCellValue());
				
					AddressOfTheConsignee addressOfTheConsignee = new AddressOfTheConsignee();
					addressOfTheConsignee.setAddress1(row.getCell(31).getStringCellValue());
					addressOfTheConsignee.setAddress2(row.getCell(32).getStringCellValue());
					addressOfTheConsignee.setCity(row.getCell(33).getStringCellValue());
					addressOfTheConsignee.setState(row.getCell(34).getStringCellValue());
					addressOfTheConsignee.setPostalCode((short) row.getCell(35).getNumericCellValue());
					addressOfTheConsignee.setCountry(row.getCell(36).getStringCellValue());
				
				cbexii.setAddressOfTheConsignee(addressOfTheConsignee);
				cbexii.setImportExportCode((long) row.getCell(37).getNumericCellValue());
				cbexii.setIECBranchCode(row.getCell(38).getStringCellValue());
				cbexii.setGrossWeight(new BigDecimal(row.getCell(39).getNumericCellValue()));
				cbexii.setNetWeight(new BigDecimal(row.getCell(40).getNumericCellValue()));
					
					ItemDesc itemDesc = new ItemDesc();
					itemDesc.setCTSH((long) row.getCell(41).getNumericCellValue());					
					itemDesc.setNatureOfShipment(row.getCell(42).getStringCellValue());
					itemDesc.setCountryOfOrigin(row.getCell(43).getStringCellValue());
					itemDesc.setDescriptionOfGoods(row.getCell(44).getStringCellValue());
					itemDesc.setNameOfManufacturer(row.getCell(45).getStringCellValue());
						
						AddressOfManufacturer addressOfManufacturer = new AddressOfManufacturer();
						addressOfManufacturer.setAddress1(row.getCell(46).getStringCellValue());
						addressOfManufacturer.setAddress2(row.getCell(47).getStringCellValue());
						addressOfManufacturer.setCity(row.getCell(48).getStringCellValue());
						addressOfManufacturer.setState(row.getCell(49).getStringCellValue());
						addressOfManufacturer.setPostalCode((short) row.getCell(50).getNumericCellValue());
						addressOfManufacturer.setCountry(row.getCell(51).getStringCellValue());				
					
					itemDesc.setAddressOfManufacturer(addressOfManufacturer);
					itemDesc.setNumberOfPackages((short) row.getCell(52).getNumericCellValue());
					itemDesc.setMarksNumberOfPackages(row.getCell(53).getStringCellValue());
					itemDesc.setUnitOfMeasure(row.getCell(54).getStringCellValue());
					itemDesc.setQuantity(new BigDecimal (row.getCell(55).getNumericCellValue()));
					itemDesc.setInvoiceNumber(row.getCell(56).getStringCellValue());
					itemDesc.setInvoiceValue(new BigDecimal(row.getCell(57).getNumericCellValue()));
					itemDesc.setInvoiceCurrency(row.getCell(58).getStringCellValue());
					itemDesc.setRateOfExchange(new BigDecimal(row.getCell(59).getNumericCellValue()));
					itemDesc.setAssessableValue(new BigDecimal(row.getCell(60).getNumericCellValue()));
					
						Notification notification = new Notification();
						
							TariffNotification tariffNotification = new TariffNotification();
							tariffNotification.setTariffNotificationNumber(row.getCell(61).getStringCellValue());
							tariffNotification.setTariffNotificationSerialNumber(row.getCell(62).getStringCellValue());
						
						notification.setTariffNotification(tariffNotification);
							
							NonTariffNotification nonTariffNotification = new NonTariffNotification();
							nonTariffNotification.setNonTariffNotificationNumber(row.getCell(63).getStringCellValue());
							nonTariffNotification.setNonTariffNotificationSerialNumber(row.getCell(64).getStringCellValue());
						notification.setNonTariffNotification(nonTariffNotification);
					itemDesc.setNotification(notification);
						
				cbexii.setItemDesc(itemDesc);
				cbexii.setGSTINType(row.getCell(65).getStringCellValue());
				cbexii.setGSTINNumber(row.getCell(66).getStringCellValue());
				cbexii.setStateCode((short) row.getCell(67).getNumericCellValue());
				cbexii.setTransactionDateTime(row.getCell(68).getDateCellValue().toString());
				
				//listCbeii.add(cbexii);
				cbeXiiRoot.getList().add(cbexii);
					
				
			}
			
			
			
			// System.out.println("W ::::::::::::::  " + marshalToXml(cbexii));
			 responseData = marshalToXml(cbeXiiRoot);
			//responseData = marshalToXmlListObject(listCbeii);
			System.out.println("XML ::::::::::::::::::::::::::::::::::::::::::::::::::::::"+responseData);
			
			
			
		} catch (IOException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseData;
		
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
