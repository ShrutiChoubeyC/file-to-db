package com.example.main;

import com.sample.service.ServiceImpl;

public class Main {

	public static void main(String[] args) {

		ServiceImpl serviceImpl = new ServiceImpl();

		String excelPath = "sampleDatax.xlsx";
		String CSVPath = "csvFile.csv";
		serviceImpl.excelToCSVConvert(excelPath, CSVPath);
		serviceImpl.insertToDb(CSVPath);

	}

}
