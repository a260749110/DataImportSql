package com.check.cells;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.sql.domain.CDataBasePo;
import com.util.RandomConfig;

public class LycjssFlagData extends DataBase {
	private double lycjssFlags = 0;
	private double lycjssVma = 0;
	private double lycjssVpr = 0;
	private double macdDif = 0;
	private double macdDea = 0;
	private double macdMacd = 0;
	private double lycjdmiFlags = 0;
	private double lycjdmiFlagsums = 0;
	private double lycjdmiHightsum = 0;
	private double lycjdmiFlagsumsshow = 0;
	private double lycjdmiVdif = 0;
	private double lycjdmiVma = 0;
	private double lycjdmiVpr = 0;
	private double lykdjBuyflag = 0;
	private double lydmiBuyflag = 0;
	private double lydmiSellflag = 0;
	private double lydmiPdi = 0;
	private double lydmiAdx = 0;
	private double lydmiAdxr = 0;
	private double lydmiMdi = 0;
	private double lysarfVsum = 0;
	private double lysarfVsumdif = 0;
	private double lysarf = 0;
	private double lysarfVdif = 0;
	private double ttttC = 0;
	private double ttttSumrc = 0;
	private double ttttZ = 0;
	private double ttttR = 0;
	private double ttttAll;
	private double ttttDifrc;
	private double ttttSumtt;
	private double ttttFcd;;

	public double getLydmiPdi() {
		return lydmiPdi;
	}

	@RandomConfig(enable = false, calculateYestoday = false)
	private Date date;
	@RandomConfig(enable = false, calculateYestoday = false)
	private long id;
	private double start = 0;
	private double high = 0;
	private double low = 0;
	private double close = 0;
	private double deal = 0;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getStart() {
		return start;
	}

	public void setStart(double start) {
		this.start = start;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getDeal() {
		return deal;
	}

	public void setDeal(double deal) {
		this.deal = deal;
	}

	public double getTtttC() {
		return ttttC;
	}

	public void setTtttC(double ttttC) {
		this.ttttC = ttttC;
	}

	public double getTtttSumrc() {
		return ttttSumrc;
	}

	public void setTtttSumrc(double ttttSumrc) {
		this.ttttSumrc = ttttSumrc;
	}

	public double getTtttZ() {
		return ttttZ;
	}

	public void setTtttZ(double ttttZ) {
		this.ttttZ = ttttZ;
	}

	public double getTtttR() {
		return ttttR;
	}

	public void setTtttR(double ttttR) {
		this.ttttR = ttttR;
	}

	public double getTtttAll() {
		return ttttAll;
	}

	public void setTtttAll(double ttttAll) {
		this.ttttAll = ttttAll;
	}

	public double getTtttDifrc() {
		return ttttDifrc;
	}

	public void setTtttDifrc(double ttttDifrc) {
		this.ttttDifrc = ttttDifrc;
	}

	public double getTtttSumtt() {
		return ttttSumtt;
	}

	public void setTtttSumtt(double ttttSumtt) {
		this.ttttSumtt = ttttSumtt;
	}

	public double getTtttFcd() {
		return ttttFcd;
	}

	public void setTtttFcd(double ttttFcd) {
		this.ttttFcd = ttttFcd;
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

	public double getLysarfVsum() {
		return lysarfVsum;
	}

	public void setLysarfVsum(double lysarfVsum) {
		this.lysarfVsum = lysarfVsum;
	}

	public double getLysarfVsumdif() {
		return lysarfVsumdif;
	}

	public void setLysarfVsumdif(double lysarfVsumdif) {
		this.lysarfVsumdif = lysarfVsumdif;
	}

	public double getLysarf() {
		return lysarf;
	}

	public void setLysarf(double lysarf) {
		this.lysarf = lysarf;
	}

	public double getLysarfVdif() {
		return lysarfVdif;
	}

	public void setLysarfVdif(double lysarfVdif) {
		this.lysarfVdif = lysarfVdif;
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
