package com.lextar.raw;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class DvfData {
	private int test;
	private int bin;
	private String binCode;
	private int binGrade;
	private double vf1;
	private double vf2;
	private double dval1;
	private double lop1;
	private double spec1Mw;
	private double spec1Wlp;
	private double vz1;
	private double ir1;
	private double spec1St;
	private double spec1Int;
	private double offSetX;
	private double offSetY;
	
	@JsonGetter("TEST")
	public int getTest() {
		return test;
	}
	
	@JsonSetter("TEST")
	public void setTest(int test) {
		this.test = test;
	}
	
	@JsonGetter("BIN")
	public int getBin() {
		return bin;
	}
	
	@JsonSetter("BIN")
	public void setBin(int bin) {
		this.bin = bin;
	}
	
	@JsonGetter("BIN_CODE")
	public String getBinCode() {
		return binCode;
	}
	
	@JsonSetter("BIN_CODE")
	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}
	
	@JsonGetter("BIN_GRADE")
	public int getBinGrade() {
		return binGrade;
	}
	
	@JsonSetter("BIN_GRADE")
	public void setBinGrade(int binGrade) {
		this.binGrade = binGrade;
	}
	
	@JsonGetter("VF1")
	public double getVf1() {
		return vf1;
	}
	
	@JsonSetter("VF1")
	public void setVf1(double vf1) {
		this.vf1 = vf1;
	}
	
	@JsonGetter("VF2")
	public double getVf2() {
		return vf2;
	}
	
	@JsonSetter("VF2")
	public void setVf2(double vf2) {
		this.vf2 = vf2;
	}
	
	@JsonGetter("DEVAL1")
	public double getDval1() {
		return dval1;
	}
	
	@JsonSetter("DEVAL1")
	public void setDval1(double dval1) {
		this.dval1 = dval1;
	}
	
	@JsonGetter("LOP1")
	public double getLop1() {
		return lop1;
	}
	
	@JsonSetter("LOP1")
	public void setLop1(double lop1) {
		this.lop1 = lop1;
	}
	
	@JsonGetter("SPEC1_MW")
	public double getSpec1Mw() {
		return spec1Mw;
	}
	
	@JsonSetter("SPEC1_MW")
	public void setSpec1Mw(double spec1Mw) {
		this.spec1Mw = spec1Mw;
	}
	
	@JsonGetter("SPEC1_WLP")
	public double getSpec1Wlp() {
		return spec1Wlp;
	}
	
	@JsonSetter("SPEC1_WLP")
	public void setSpec1Wlp(double spec1Wlp) {
		this.spec1Wlp = spec1Wlp;
	}
	
	@JsonGetter("VZ1")
	public double getVz1() {
		return vz1;
	}
	
	@JsonSetter("VZ1")
	public void setVz1(double vz1) {
		this.vz1 = vz1;
	}
	
	@JsonGetter("IR1")
	public double getIr1() {
		return ir1;
	}
	
	@JsonSetter("IR1")
	public void setIr1(double ir1) {
		this.ir1 = ir1;
	}
	
	@JsonGetter("SPEC1_ST")
	public double getSpec1St() {
		return spec1St;
	}
	
	@JsonSetter("SPEC1_ST")
	public void setSpec1St(double spec1St) {
		this.spec1St = spec1St;
	}
	
	@JsonGetter("SPEC1_INT")
	public double getSpec1Int() {
		return spec1Int;
	}
	
	@JsonSetter("SPEC1_INT")
	public void setSpec1Int(double spec1Int) {
		this.spec1Int = spec1Int;
	}
	
	@JsonGetter("OFFSET_X")
	public double getOffSetX() {
		return offSetX;
	}
	
	@JsonSetter("OFFSET_X")
	public void setOffSetX(double offSetX) {
		this.offSetX = offSetX;
	}
	
	@JsonGetter("OFFSET_Y")
	public double getOffSetY() {
		return offSetY;
	}
	
	@JsonSetter("OFFSET_Y")
	public void setOffSetY(double offSetY) {
		this.offSetY = offSetY;
	}
	
}
