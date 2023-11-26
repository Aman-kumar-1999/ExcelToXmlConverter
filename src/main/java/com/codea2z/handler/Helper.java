package com.codea2z.handler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

public class Helper {

	// check that file is of excel type or not
	public static boolean checkExcelFormat(MultipartFile file) {

		String contentType = file.getContentType();

		if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		} else {
			return false;
		}

	}

	// convert excel to list of products

	public static Map<String, Object> convertExcelToListOfProduct(InputStream is) {
		// List<Product> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		ArrayList<?> arrayList = new ArrayList<>();
		
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(is);
			
			//int sheetNo = workbook.getActiveSheetIndex();
			//System.out.println("SheetNo: ");
			//for (int i = 0; i < sheetNo; i++) {
			//	XSSFSheet sheet1 = workbook.getSheetAt(i);				
				//arrayList.addAll(i, sheet1);
			//}

			XSSFSheet sheet = workbook.getSheet("Sheet1");

			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();

			while (iterator.hasNext()) {
				Row row = iterator.next();

				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cells = row.iterator();

				int cid = 0;

				//Product p = new Product();
				Object p = new Object();
				while (cells.hasNext()) {
					Cell cell = cells.next();

					switch (cid) {
//					case 0:
//						p.setProductName(cell.getStringCellValue());
//						break;
//					case 1:
//						p.setProductDescription(cell.getStringCellValue());
//						break;
//					case 2:
//						p.setProductLongDescription(cell.getStringCellValue());
//						break;
//					case 3:
//						p.setTag(cell.getStringCellValue());
//						break;
//					case 4:
//						p.setCountryOrigin(cell.getStringCellValue());
//						break;
//					case 5:
//						p.setIndividualProductPrice((float) cell.getNumericCellValue());
//						break;
//					case 6:
//						p.setBrandName(cell.getStringCellValue());
//						break;
//					case 7:
//						p.setProductQuantity((int) cell.getNumericCellValue());
//						break;
//					case 8:
//						p.setStatus(cell.getStringCellValue());
//						break;
//					case 9:
//						p.setAction(cell.getStringCellValue());
//						break;
//					case 10:
//						p.setDiscountPercentage((float) cell.getNumericCellValue());
//						break;
//					case 11:
//						p.setCategory(cell.getStringCellValue());
//						break;
//					case 12:
//						p.setSubCategory(cell.getStringCellValue());
//						break;
//					case 13:
//						p.setBulkCode(cell.getStringCellValue());
//						break;
//					case 14:
//						p.setVariationName(cell.getStringCellValue());
//						break;
//					case 15:
//						p.setVariationId(cell.getStringCellValue());
//						break;
//					case 16:
//						p.setGst((float) cell.getNumericCellValue());
//						break;
//					case 17:
//						p.setHsn(cell.toString());
//						break;
//					case 18:
//						p.setIsVerified(cell.getStringCellValue());
//						break;
//					case 19:
//						p.setTierNo(cell.getStringCellValue());
//						break;
//					case 20:
//						p.setContainLiquid(cell.getStringCellValue());
//						break;
//					case 21:
//						p.setCompanyCode(cell.toString());
//						break;
//					case 22:
//						p.setBulkPack(cell.getStringCellValue());
//						break;
//					case 23:
//						p.setBulkPrice((float) cell.getNumericCellValue());
//						break;
//					default:
//						break;
					}
					cid++;

				}

				//list.add(p);

			}
			//System.out.println(list);
			//map.put("PRODUCTS", list);

		} catch (Exception e) {
			map.put("STATUS", "FAILED");
			map.put("MSG", "Kindly save correct data in Excel");
			e.printStackTrace();
		}
		return map;

	}

}
