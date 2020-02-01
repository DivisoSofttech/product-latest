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
import java.util.List;

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
	CategoryMapper CategoryMapper;

	@PostMapping("/byte-import-products")
	public void ExcelDatatoDB(@RequestBody ProductFile file) throws IOException, URISyntaxException {

		System.out.println("...........file.........." + file);
		List<Product> tempDriveList = new ArrayList<Product>();

		InputStream targetStream = new ByteArrayInputStream(file.getFile());

		XSSFWorkbook workbook = new XSSFWorkbook(targetStream);

		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {

			Product tempProduct = new Product();
			XSSFRow row = worksheet.getRow(i);
			tempProduct.setName((row.getCell(0).getStringCellValue()));
			tempProduct.setiDPcode(row.getCell(1).getStringCellValue());
			tempProduct.setSellingPrice((double) row.getCell(2).getNumericCellValue());
			tempProduct.setIsActive(row.getCell(3).getBooleanCellValue());
			tempProduct.setReference(row.getCell(4).getStringCellValue());

			Category tempCategory = new Category();
		
			tempCategory.setiDPcode(row.getCell(5).getStringCellValue());
			tempCategory.setName(row.getCell(6).getStringCellValue());
			
			Log.info("_____________________________________________celllog"+row.getLastCellNum());
			
			if (row.getLastCellNum()==7)
			{
			tempCategory.setDescription(row.getCell(7).getStringCellValue());
			}

			ProductDTO productDTO = ProductMapper.toDto(tempProduct);

			CategoryDTO categoryDTO = CategoryMapper.toDto(tempCategory);

			categoryDTO = CategoryResource.createCategoryViaUpload(categoryDTO).getBody();

			productDTO.setCategoryId(categoryDTO.getId());

			ProductResource.createProductViaProduct(productDTO);

			System.out.println("..........P............" + productDTO + "..........C: ............" + categoryDTO);
			// tempDriveList.add(tempDrive);

		}
	}
}
