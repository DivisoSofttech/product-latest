/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diviso.graeshoppe.product.xls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diviso.graeshoppe.product.domain.Category;
import com.diviso.graeshoppe.product.domain.Product;
import com.diviso.graeshoppe.product.service.dto.CategoryDTO;
import com.diviso.graeshoppe.product.service.dto.ProductDTO;
import com.diviso.graeshoppe.product.service.dto.ProductFile;
import com.diviso.graeshoppe.product.web.rest.*;
import com.diviso.graeshoppe.product.service.*;
import com.diviso.graeshoppe.product.service.mapper.*;

/**
 * @author maya mayabytatech, maya.k.k@lxisoft.com
 */

@RestController
@RequestMapping("/api")
public class ProductLoadController {

	@Autowired
	ProductResource ProductResource;

	@Autowired
	ProductMapper ProductMapper;

	@Autowired
	CategoryResource CategoryResource;
	@Autowired
	CategoryService CategoryService;

	@Autowired
	CategoryMapper CategoryMapper;

	@PostMapping("/byte-import-products")
	public void ExcelDatatoDB(@RequestBody ProductFile file) throws IOException, URISyntaxException {

		System.out.println("...........file.........." + file);
		List<Product> tempDriveList = new ArrayList<Product>();

		InputStream targetStream = new ByteArrayInputStream(file.getFile());

		XSSFWorkbook workbook = new XSSFWorkbook(targetStream);

		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 0; i <= getLastRowWithData(worksheet); i++) {

			System.out.println("....................i...." + i + "..................." + getLastRowWithData(worksheet));
			Product tempProduct = new Product();
			XSSFRow row = worksheet.getRow(i);
			tempProduct.setName((row.getCell(0).getStringCellValue()));
			tempProduct.setiDPcode(row.getCell(1).getStringCellValue());
			tempProduct.setSellingPrice((double) row.getCell(2).getNumericCellValue());
			tempProduct.setIsAuxilaryItem(row.getCell(3).getBooleanCellValue());
			tempProduct.setReference(row.getCell(4).getStringCellValue());

			Category tempCategory = new Category();

			tempCategory.setiDPcode(row.getCell(5).getStringCellValue());
			tempCategory.setName(row.getCell(6).getStringCellValue());

			System.out.println("...........celllog.........." + row.getLastCellNum());

			if (row.getCell(row.getLastCellNum()) != null) {
				
				tempCategory.setDescription(row.getCell(7).getStringCellValue());
			}

			ProductDTO productDTO = ProductMapper.toDto(tempProduct);

			Category alreadyexist = CategoryService.findByName(tempCategory.getName());

			System.out.println("............alreadyexist category........" + alreadyexist);

			CategoryDTO categoryDTO = null;

			if (alreadyexist == null) {

				categoryDTO = CategoryMapper.toDto(tempCategory);

				categoryDTO = CategoryResource.createCategoryViaUpload(categoryDTO).getBody();

				productDTO.setCategoryId(categoryDTO.getId());

			} else {
				productDTO.setCategoryId(alreadyexist.getId());
			}

			ProductDTO dto = ProductResource.createProductViaProduct(productDTO).getBody();

			System.out.println("..........P............" + dto + "..........C: ............" + categoryDTO + "......."
					+ alreadyexist);
		

		}
	}
/*
 * As poi doesnot contain a method getLastRowWithData we are using this code, getPhysicalNumberOfRows method available will take every rows in sheet
 * 
 */
	
	public int getLastRowWithData(XSSFSheet worksheet) {
		int rowCount = 0;
		Iterator<Row> iter = worksheet.rowIterator();

		while (iter.hasNext()) {
			Row r = iter.next();
			if (!this.isRowBlank(r)) {
				rowCount = r.getRowNum();
			}
		}

		return rowCount;
	}

	public boolean isRowBlank(Row r) {
		boolean ret = true;

		/*
		 * If a row is null, it must be blank.
		 */
		if (r != null) {
			Iterator<Cell> cellIter = r.cellIterator();
			/*
			 * Iterate through all cells in a row.
			 */
			while (cellIter.hasNext()) {
				/*
				 * If one of the cells in given row contains data, the row is considered not
				 * blank.
				 */
				if (!this.isCellBlank(cellIter.next())) {
					ret = false;
					break;
				}
			}
		}

		return ret;
	}

	public boolean isCellBlank(Cell c) {
		return (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK);
	}

	public boolean isCellEmpty(Cell c) {
		return (c == null || c.getCellType() == Cell.CELL_TYPE_BLANK
				|| (c.getCellType() == Cell.CELL_TYPE_STRING && c.getStringCellValue().isEmpty()));
	}
}
