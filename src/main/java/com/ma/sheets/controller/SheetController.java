package com.ma.sheets.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ma.sheets.domain.DummyObject;

@Controller
public class SheetController {

	@PostMapping("/import")
	public ResponseEntity<List<DummyObject>> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {

		List<DummyObject> dummyList = new ArrayList<DummyObject>();
		XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			DummyObject tempDummy= new DummyObject();

			XSSFRow row = worksheet.getRow(i);

			tempDummy.setId(Integer.valueOf(row.getCell(0).getRawValue()));
			tempDummy.setContent(row.getCell(1).getStringCellValue());
			dummyList.add(tempDummy);
		}
		workbook.close();
		
		return new ResponseEntity<List<DummyObject>>(dummyList, HttpStatus.OK);
	}
	

}
