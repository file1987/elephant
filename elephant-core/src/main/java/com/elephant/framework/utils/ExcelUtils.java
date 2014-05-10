package com.elephant.framework.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.elephant.framework.exception.ReadDataException;
import com.elephant.framework.templates.ValuePair;

public final class ExcelUtils {

	public static ValuePair readExcel2ValuePair(String path) {
		ValuePair valuePair = new ValuePair();
		try {
			InputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			if (sheet == null)
				throw new ReadDataException();
			Row headerRow = sheet.getRow(0);

			if (headerRow == null)
				throw new ReadDataException("Excel 数据模板不正确！");

			boolean isHeader = "header".equalsIgnoreCase(headerRow.getCell(0)
					.getStringCellValue());
			int i = 1;
			while (true) {
				Row row = sheet.getRow(i++);
				if (row == null)
					break;
				int j = 0;
				Cell fisrtCell = row.getCell(j++);
				if (fisrtCell == null)
					continue;
				else if (fisrtCell.getCellType() != Cell.CELL_TYPE_STRING
						|| "end".equalsIgnoreCase(fisrtCell
								.getStringCellValue()))
					break;
				else if ("read"
						.equalsIgnoreCase(fisrtCell.getStringCellValue())) {
					if (isHeader) {
						while (true) {
							Cell headerCell = headerRow.getCell(j);
							if (headerCell == null
									|| "end".equalsIgnoreCase(headerCell
											.getStringCellValue()))
								break;
							else if (headerCell.getCellType() != Cell.CELL_TYPE_STRING
									|| "".equals(headerCell
											.getStringCellValue())) {
								j++;
								continue;
							} else {
								Cell cell = row.getCell(j++);
								if (cell == null)
									continue;

								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_BOOLEAN:
									valuePair.set(
											headerCell.getStringCellValue(),
											cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_STRING:
									valuePair.set(
											headerCell.getStringCellValue(),
											cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC:
									valuePair.set(
											headerCell.getStringCellValue(),
											cell.getNumericCellValue());
									break;
								default:
									valuePair.set(
											headerCell.getStringCellValue(),
											cell.getStringCellValue());
									break;
								}
							}
						}
					} else {
						Cell keyCell = row.getCell(j++);
						if (keyCell == null
								|| keyCell.getCellType() != Cell.CELL_TYPE_STRING)
							continue;
						Cell valueCell = row.getCell(j);

						switch (valueCell.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							valuePair.set(keyCell.getStringCellValue(),
									valueCell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							valuePair.set(keyCell.getStringCellValue(),
									valueCell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							valuePair.set(keyCell.getStringCellValue(),
									valueCell.getNumericCellValue());
							break;
						default:
							valuePair.set(keyCell.getStringCellValue(),
									valueCell.getStringCellValue());
							break;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
		return valuePair;
	}
	
	
	public static Collection<ValuePair> readExcel2ValuePairs(String path,String sheetName) {

		Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();

		try {
			InputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = sheetName==null?wb.getSheetAt(0): wb.getSheet(sheetName);;
			if (sheet == null)
				throw new ReadDataException();
			Row headerRow = sheet.getRow(0);

			if (headerRow == null)
				throw new ReadDataException("Excel 数据模板不正确！");

			boolean isHeader = "header".equalsIgnoreCase(headerRow.getCell(0).getStringCellValue());
			if (!isHeader)
				throw new ReadDataException("Excel 数据模板不正确！");

			int i = 1;
			while (true) {
				Row row = sheet.getRow(i++);
				if (row == null)
					break;
				int j = 0;
				Cell fisrtCell = row.getCell(j++);
				if (fisrtCell == null)
					continue;
				else if (fisrtCell.getCellType() != Cell.CELL_TYPE_STRING || "end".equalsIgnoreCase(fisrtCell.getStringCellValue()))
					break;
				else if ("read".equalsIgnoreCase(fisrtCell.getStringCellValue())) {
					ValuePair valuePair = new ValuePair();
					while (true) {
						Cell headerCell = headerRow.getCell(j);
						if (headerCell == null || "end".equalsIgnoreCase(headerCell.getStringCellValue()))
							break;
						else if (headerCell.getCellType() != Cell.CELL_TYPE_STRING || "".equals(headerCell.getStringCellValue())) {
							j++;
							continue;
						} else {
							Cell cell = row.getCell(j++);
							if (cell == null)
								continue;
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								valuePair.set(headerCell.getStringCellValue(),cell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								valuePair.set(headerCell.getStringCellValue(),cell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								valuePair.set(headerCell.getStringCellValue(),cell.getNumericCellValue());
								break;
							default:
								valuePair.set(headerCell.getStringCellValue(),cell.getStringCellValue());
								break;
							}
						}
					}
					valuePairs.add(valuePair);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
		return valuePairs;
	}
	

	public static Collection<ValuePair> readExcel2ValuePairs(String path) {
		return readExcel2ValuePairs(path, null);
	}
	
	public static Map<String,Collection<ValuePair>>  readExcel2ValuePairMap(String path){
		
		try {
			InputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			int sheetNum = wb.getNumberOfSheets();
			Map<String, Collection<ValuePair>>  maps = new HashMap<String, Collection<ValuePair>>(sheetNum);
			
			for(int k=0;k<sheetNum;k++){
				
				Sheet sheet = wb.getSheetAt(k);
				if (sheet == null)
					continue;
				
				String sheetName = sheet.getSheetName();
				
				Row headerRow = sheet.getRow(0);

				if (headerRow == null)
					throw new ReadDataException("Excel sheetName( " + sheetName + ") 数据模板不正确！");

				boolean isHeader = "header".equalsIgnoreCase(headerRow.getCell(0).getStringCellValue());
				if (!isHeader)
					throw new ReadDataException("Excel sheetName( " + sheetName + ") 数据模板不正确！");
				
				Collection<ValuePair> valuePairs = new ArrayList<ValuePair>();
				int i = 1;
				while (true) {
					Row row = sheet.getRow(i++);
					if (row == null)
						break;
					int j = 0;
					Cell fisrtCell = row.getCell(j++);
					if (fisrtCell == null)
						continue;
					else if (fisrtCell.getCellType() != Cell.CELL_TYPE_STRING || "end".equalsIgnoreCase(fisrtCell.getStringCellValue()))
						break;
					else if ("read".equalsIgnoreCase(fisrtCell.getStringCellValue())) {
						ValuePair valuePair = new ValuePair();
						while (true) {
							Cell headerCell = headerRow.getCell(j);
							if (headerCell == null || "end".equalsIgnoreCase(headerCell.getStringCellValue()))
								break;
							else if (headerCell.getCellType() != Cell.CELL_TYPE_STRING || "".equals(headerCell.getStringCellValue())) {
								j++;
								continue;
							} else {
								Cell cell = row.getCell(j++);
								if (cell == null)
									continue;
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_BOOLEAN:
									valuePair.set(headerCell.getStringCellValue(),cell.getBooleanCellValue());
									break;
								case Cell.CELL_TYPE_STRING:
									valuePair.set(headerCell.getStringCellValue(),cell.getStringCellValue());
									break;
								case Cell.CELL_TYPE_NUMERIC:
									valuePair.set(headerCell.getStringCellValue(),cell.getNumericCellValue());
									break;
								default:
									valuePair.set(headerCell.getStringCellValue(),cell.getStringCellValue());
									break;
								}
							}
						}
						valuePairs.add(valuePair);
					}
				}
				
				maps.put(sheetName, valuePairs);
				
			}			
			return maps;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ReadDataException(e);
		}
	}

}
