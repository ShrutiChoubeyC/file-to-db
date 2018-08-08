package com.sample.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ServiceImpl {

	public void excelToCSVConvert(String xlsxPath, String csvPath) {
		try {
			Workbook wb = new XSSFWorkbook(xlsxPath);

			DataFormatter formatter = new DataFormatter();
			PrintStream out;
			out = new PrintStream(new FileOutputStream(csvPath), true, "UTF-8");
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet sheet = wb.getSheetAt(i);
				for (Row row : sheet) {
					boolean firstCell = true;
					for (Cell cell : row) {
						if (!firstCell)
							out.print(',');
						String text = formatter.formatCellValue(cell);
						out.print(text);
						firstCell = false;
					}
					out.println();
				}
			}
			out.close();

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void insertToDb(String csvPath) {
		List<String> statements = new ArrayList<>();
		statements.add(".mode csv");
		statements.add(".import " + csvPath + " TableName");
		statements.add(".quit");

		try {
			Files.write(Paths.get("cmd.list"), statements);
			String[] cmd = { "sqlite3", "test.db", ".read cmd.list" };
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
