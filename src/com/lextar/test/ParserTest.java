package com.lextar.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lextar.parser.DvfCsvParser;
import com.lextar.raw.DvfData;

import java.nio.file.Paths;

public class ParserTest {

	private static final double DELTA = 1e-15;

	
	@Test
	public void dvfCsvParserTest() throws IOException {
		
		
		ArrayList<DvfData> dvfDataList = (ArrayList<DvfData>) new DvfCsvParser().Parse("D:/DVF_Temp/P5SR0221_T05TM78809-0P0W4LL01_#20170829201158#_U20P57800P00_94.44-2.53-3.03.csv");
		assertEquals("Raw data count error.",13145, dvfDataList.size());
		
		int idx = 0;
		DvfData data = dvfDataList.get(idx);
		assertEquals("Raw [" + idx + "] test field field.",1, data.getTest());
		
		assertEquals("Raw [" + idx + "] BIN_CODE field field.","U036502B", data.getBinCode());
		
		assertEquals("Raw [" + idx + "] BIN_CODE field field.","U036502B", data.getBinCode());
		
		
		assertEquals("Raw [" + idx + "] VF2 field field.",2.603, data.getVf2(), DELTA);
		
		
		ObjectMapper mapper = new ObjectMapper();
        
        String json = mapper.writeValueAsString(data);
        
        
        
        
        DvfData data2 = mapper.readValue(json, DvfData.class);
        assertEquals("json recovert to object test field field.",1, data2.getTest());
		
		assertEquals("json recovert to object BIN_CODE field field.","U036502B", data2.getBinCode());
		
		assertEquals("json recovert to object BIN_CODE field field.","U036502B", data2.getBinCode());
		
		
		assertEquals("json recovert to object VF2 field field.",2.603, data2.getVf2(), DELTA);
	}
	
	
	@Test
	public void stringToDoubleTest() throws IOException
	{
		String str = ".1";
		
		Boolean b = NumberUtils.isParsable(str);
		System.out.println("is number : " +b);
		Double d = Double.parseDouble(str);
		
		//assertEquals("parse fail.", 1, d, DELTA);
		
		Path fileToMovePath = Paths.get("D:/Pony/abc.txt");
			      
		Path targetPath = Paths.get("D:/Pony _test");
		
		Files.move(fileToMovePath, targetPath.resolve(fileToMovePath.getFileName()));
		System.out.println("done!");
	}
	

}
