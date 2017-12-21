package com.lextar.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lextar.dao.mongodb.DvfRawDataDao;
import com.lextar.interfaces.ICsvParser;
import com.lextar.proxy.DataProxy;
import com.lextar.raw.DvfData;

public class DvfCsvParser implements ICsvParser<List<DvfData>> {
	private static final Logger logger = LoggerFactory.getLogger(DvfCsvParser.class);
	
	//raw data header
	private static final String TEST = "TEST";
	private static final String BIN = "BIN";
	private static final String BIN_CODE = "BIN_CODE";
	private static final String BIN_GRADE = "BIN_GRADE";
	private static final String VF2 = "VF2";
	private static final String DVAL1 = "DVAL1";
	private static final String VF1 = "VF1";
	private static final String LOP1 = "LOP1";
	private static final String SPEC1_MW = "SPEC1_MW";
	private static final String SPEC1_WLP = "SPEC1_WLP";
	private static final String VZ1 = "VZ1";
	private static final String IR1 = "IR1";
	private static final String SPEC1_ST = "SPEC1_ST";
	private static final String SPEC1_INT = "SPEC1_INT";
	private static final String OFF_SETX = "OFF_SETX";
	private static final String OFF_SETY = "OFF_SETY";
	
	enum ParseStep{
		Init,
		Header,
		Summary,
		Raw
	}
	
	private Document dvfDoc = new Document();
	
	@Override
	public List<DvfData> Parse(String path) throws IOException {
		String[] headers = null;
		boolean startParse = false;
		
		List<DvfData> dvfList = new ArrayList<DvfData>(); 
		int lineNo = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
            	lineNo++;
            	
                if(line.startsWith("TEST,Bin,BIN_CODE"))
                {
                	headers = getColumnHeader(line);
                	startParse = true;
                	continue;
                }
                
                if(line.startsWith("<<<") || line.trim() == "")
                	continue;
                
                if(startParse)
                {
                	DvfData dvfData = convertToObject(headers, line);
                	if(dvfData != null)
                		dvfList.add(dvfData);
                	
                }
            }
        } catch (Exception e) {
        	logger.error("Parse line no {} error", lineNo);
            throw e;
        }
		
		return dvfList;
	}
	
	
	
	
	public List<Document> ParseToDocument(String path) throws Exception {
		ParseStep currStep = ParseStep.Init;
		
		String[] headers = null;
		String[] strs = path.split("#");
		double testTime = Double.parseDouble(strs[1]);
		List<Document> dvfList = new ArrayList<Document>(); 
		int lineNo = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
            	lineNo++;
            	
            	if(line.startsWith("FileID,")){
            		currStep = ParseStep.Header;
            	}
            	
                if(line.startsWith("TEST,Bin,BIN_CODE"))
                {
                	headers = getColumnHeader(line);
                	currStep = ParseStep.Raw;
                	continue;
                }
                
                //SUMITEM,Bin
                if(line.startsWith("SUMITEM,Bin")){
                	currStep = ParseStep.Summary;
                	continue;
                }
                
                if(line.startsWith("<<<") || line.trim() == "")
                	continue;
                
                if(currStep == ParseStep.Header){
                	parseHeaderInfo(line);
                }
                
                if(currStep == ParseStep.Raw)
                {
                	Document doc = convertToDocument(headers, line);
                	
                	
                	if(doc != null){
                		doc.append("TEST_TIME", testTime);
                		String moNo = doc.getString("WO");
                		String prod = DataProxy.getProductID(moNo);
                		doc.append("PRODUCT_NO", prod);
                		dvfList.add(doc);
                	}
                		
                		
                	
                }
            }
            
            
        } catch (Exception e) {
        	logger.error("Parse line no {} error", lineNo);
            throw e;
        }
		
		return dvfList;
	}
	
	private void parseHeaderInfo(String line)
	{
		String[] strs = line.split(",",-1);
		String val = strs[2].replace("\"", "").trim();
		if(NumberUtils.isParsable(val))
		{
			dvfDoc.append(strs[0], Double.parseDouble(val));
		}else{
			dvfDoc.append(strs[0], val);
		}
	}
	
	private String[] getColumnHeader(String line)
	{
		return line.toUpperCase().split(",");
	}
	
	private Document convertToDocument(String[] headers, String line)
	{
		
		String[] datas = line.split(",");
		if(headers.length != datas.length)
			return null;
		
		Document doc = new Document();
		doc.putAll(dvfDoc);
		
		String val;
		for(int idx = 0; idx < headers.length ; idx++)
		{
			val = datas[idx].replace("\"", "").trim();
			
			if(NumberUtils.isParsable(val))
			{
				doc.append(headers[idx], Double.parseDouble(val));
			}else{
				doc.append(headers[idx], val);
			}
			
			
		}
		
		
		return doc;
	}
	
	private DvfData convertToObject(String[] headers, String line)
	{
		String[] datas = line.split(",");
		
		int hIdx = 0; // header index
		if(headers.length != datas.length)
			return null;
		
		
		DvfData dvfData = new DvfData();
		
		hIdx = ArrayUtils.indexOf(headers, TEST);
		dvfData.setTest(Integer.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, BIN);
		dvfData.setBin(Integer.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, BIN_CODE);
		dvfData.setBinCode(datas[hIdx]);
		
		hIdx = ArrayUtils.indexOf(headers, BIN_GRADE);
		dvfData.setBinGrade(Integer.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, VF2);
		dvfData.setVf2(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, DVAL1);
		dvfData.setDval1(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, VF1);
		dvfData.setVf1(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, LOP1);
		dvfData.setLop1(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, SPEC1_MW);
		dvfData.setSpec1Mw(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, SPEC1_WLP);
		dvfData.setSpec1Wlp(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, VZ1);
		dvfData.setVz1(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, IR1);
		dvfData.setIr1(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, SPEC1_ST);
		dvfData.setSpec1St(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, SPEC1_INT);
		dvfData.setSpec1Int(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, OFF_SETX);
		dvfData.setOffSetX(Double.valueOf(datas[hIdx]));
		
		hIdx = ArrayUtils.indexOf(headers, OFF_SETY);
		dvfData.setOffSetY(Double.valueOf(datas[hIdx]));
		
		return dvfData;
	}
	
	
//	public List<Document> ParseToDocument(String path) throws IOException {
//		String[] headers = null;
//		boolean startParse = false;
//		
//		List<Document> dvfList = new ArrayList<Document>(); 
//		int lineNo = 0;
//		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//            	lineNo++;
//            	
//                if(line.startsWith("TEST,Bin,BIN_CODE"))
//                {
//                	headers = getHeader(line);
//                	startParse = true;
//                	continue;
//                }
//                
//                if(line.startsWith("<<<") || line.trim() == "")
//                	continue;
//                
//                if(startParse)
//                {
//                	Document doc = convertToDocument(headers, line);
//                	if(doc != null)
//                		dvfList.add(doc);
//                	
//                }
//            }
//        } catch (Exception e) {
//        	logger.error("Parse line no {} error", lineNo);
//            throw e;
//        }
//		
//		return dvfList;
//	}
}
