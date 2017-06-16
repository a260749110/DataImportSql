package com.check.cells;

import com.alibaba.fastjson.JSON;
import com.sql.domain.CDataBasePo;

public class SaveOtherData {

	private double lycjssFlags;
	private double lycjssVma;
	private double lycjssVpr;
	private double macdDif;
	private double macdDea;
	private double macdMacd;
	private double lycjdmiFlags;
	private double lycjdmiFlagsums;
	private double lycjdmiHightsum;
	private double lycjdmiFlagsumsshow;
	private double lycjdmiVdif;
	private double lycjdmiVma;
	private double lycjdmiVpr;
	private double lykdjBuyflag;
	private double lydmiBuyflag;
	private double lydmiSellflag;
	private double lydmiPdi;
	private double lydmiAdx;
	private double lydmiAdxr;
	private double lydmiMdi;

	public double getLydmiPdi() {
		return lydmiPdi;
	}

	public void setLydmiPdi(double lydmiPdi) {
		this.lydmiPdi = lydmiPdi;
	}

	public double getLydmiAdx() {
		return lydmiAdx;
	}

	public void setLydmiAdx(double lydmiAdx) {
		this.lydmiAdx = lydmiAdx;
	}

	public double getLydmiAdxr() {
		return lydmiAdxr;
	}

	public void setLydmiAdxr(double lydmiAdxr) {
		this.lydmiAdxr = lydmiAdxr;
	}

	public double getLycjssFlags() {
		return lycjssFlags;
	}

	public void setLycjssFlags(double lycjssFlags) {
		this.lycjssFlags = lycjssFlags;
	}

	public double getLycjssVma() {
		return lycjssVma;
	}

	public static LycjssFlagData createData(CDataBasePo po) {
		LycjssFlagData data = JSON.parseObject(po.getDataBase(), LycjssFlagData.class);
		data.setDate(po.getId().getDate());
		data.setId(po.getId().getId());
		return data;
	}

	public void setLycjssVma(double lycjssVma) {
		this.lycjssVma = lycjssVma;
	}

	public double getLycjssVpr() {
		return lycjssVpr;
	}

	public void setLycjssVpr(double lycjssVpr) {
		this.lycjssVpr = lycjssVpr;
	}

	public double getMacdDif() {
		return macdDif;
	}

	public void setMacdDif(double macdDif) {
		this.macdDif = macdDif;
	}

	public double getMacdDea() {
		return macdDea;
	}

	public void setMacdDea(double macdDea) {
		this.macdDea = macdDea;
	}

	public double getMacdMacd() {
		return macdMacd;
	}

	public void setMacdMacd(double macdMacd) {
		this.macdMacd = macdMacd;
	}

	public double getLycjdmiFlags() {
		return lycjdmiFlags;
	}

	public void setLycjdmiFlags(double lycjdmiFlags) {
		this.lycjdmiFlags = lycjdmiFlags;
	}

	public double getLycjdmiFlagsums() {
		return lycjdmiFlagsums;
	}

	public void setLycjdmiFlagsums(double lycjdmiFlagsums) {
		this.lycjdmiFlagsums = lycjdmiFlagsums;
	}

	public double getLycjdmiVpr() {
		return lycjdmiVpr;
	}

	public void setLycjdmiVpr(double lycjdmiVpr) {
		this.lycjdmiVpr = lycjdmiVpr;
	}

	public double getLycjdmiHightsum() {
		return lycjdmiHightsum;
	}

	public void setLycjdmiHightsum(double lycjdmiHightsum) {
		this.lycjdmiHightsum = lycjdmiHightsum;
	}

	public double getLycjdmiVma() {
		return lycjdmiVma;
	}

	public void setLycjdmiVma(double lycjdmiVma) {
		this.lycjdmiVma = lycjdmiVma;
	}

	public double getLycjdmiVdif() {
		return lycjdmiVdif;
	}

	public void setLycjdmiVdif(double lycjdmiVdif) {
		this.lycjdmiVdif = lycjdmiVdif;
	}

	public double getLykdjBuyflag() {
		return lykdjBuyflag;
	}

	public void setLykdjBuyflag(double lykdjBuyflag) {
		this.lykdjBuyflag = lykdjBuyflag;
	}

	public double getLydmiBuyflag() {
		return lydmiBuyflag;
	}

	public void setLydmiBuyflag(double lydmiBuyflag) {
		this.lydmiBuyflag = lydmiBuyflag;
	}

	public double getLydmiMdi() {
		return lydmiMdi;
	}

	public void setLydmiMdi(double lydmiMdi) {
		this.lydmiMdi = lydmiMdi;
	}

	public double getLydmiSellflag() {
		return lydmiSellflag;
	}

	public void setLydmiSellflag(double lydmiSellflag) {
		this.lydmiSellflag = lydmiSellflag;
	}

	public double getLycjdmiFlagsumsshow() {
		return lycjdmiFlagsumsshow;
	}

	public void setLycjdmiFlagsumsshow(double lycjdmiFlagsumsshow) {
		this.lycjdmiFlagsumsshow = lycjdmiFlagsumsshow;
	}

}
