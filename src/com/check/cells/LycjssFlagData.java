package com.check.cells;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.sql.domain.CDataBasePo;
import com.util.RandomConfig;

public class LycjssFlagData extends DataBase {
	private float lycjssFlags = 0;
	private float lycjssVma = 0;
	private float lycjssVpr = 0;
	private float macdDif = 0;
	private float macdDea = 0;
	private float macdMacd = 0;
	private float lycjdmiFlags = 0;
	private float lycjdmiFlagsums = 0;
	private float lycjdmiHightsum = 0;
	private float lycjdmiFlagsumsshow = 0;
	private float lycjdmiVdif = 0;
	private float lycjdmiVma = 0;
	private float lycjdmiVpr = 0;
	private float lykdjBuyflag = 0;
	private float lydmiBuyflag = 0;
	private float lydmiSellflag = 0;
	private float lydmiPdi = 0;
	private float lydmiAdx = 0;
	private float lydmiAdxr = 0;
	private float lydmiMdi = 0;
	private float lysarfVsum = 0;
	private float lysarfVsumdif = 0;
	private float lysarf = 0;
	private float lysarfVdif = 0;
	private float ttttC = 0;
	private float ttttSumrc = 0;
	private float ttttZ = 0;
	private float ttttR = 0;
	private float ttttAll;
	private float ttttDifrc;
	private float ttttSumtt;
	private float ttttFcd;;
	private History buyHistory;
	public float getLydmiPdi() {
		return lydmiPdi;
	}

	@RandomConfig(enable = false, calculateYestoday = false)
	private Date date;
	@RandomConfig(enable = false, calculateYestoday = false)
	private long id;
	private float start = 0;
	private float high = 0;
	private float low = 0;
	private float close = 0;
	private float deal = 0;

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

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getHigh() {
		return high;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public float getLow() {
		return low;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public float getDeal() {
		return deal;
	}

	public void setDeal(float deal) {
		this.deal = deal;
	}

	public float getTtttC() {
		return ttttC;
	}

	public void setTtttC(float ttttC) {
		this.ttttC = ttttC;
	}

	public float getTtttSumrc() {
		return ttttSumrc;
	}

	public void setTtttSumrc(float ttttSumrc) {
		this.ttttSumrc = ttttSumrc;
	}

	public float getTtttZ() {
		return ttttZ;
	}

	public void setTtttZ(float ttttZ) {
		this.ttttZ = ttttZ;
	}

	public float getTtttR() {
		return ttttR;
	}

	public void setTtttR(float ttttR) {
		this.ttttR = ttttR;
	}

	public float getTtttAll() {
		return ttttAll;
	}

	public void setTtttAll(float ttttAll) {
		this.ttttAll = ttttAll;
	}

	public float getTtttDifrc() {
		return ttttDifrc;
	}

	public void setTtttDifrc(float ttttDifrc) {
		this.ttttDifrc = ttttDifrc;
	}

	public float getTtttSumtt() {
		return ttttSumtt;
	}

	public void setTtttSumtt(float ttttSumtt) {
		this.ttttSumtt = ttttSumtt;
	}

	public float getTtttFcd() {
		return ttttFcd;
	}

	public void setTtttFcd(float ttttFcd) {
		this.ttttFcd = ttttFcd;
	}

	public void setLydmiPdi(float lydmiPdi) {
		this.lydmiPdi = lydmiPdi;
	}

	public float getLydmiAdx() {
		return lydmiAdx;
	}

	public void setLydmiAdx(float lydmiAdx) {
		this.lydmiAdx = lydmiAdx;
	}

	public float getLydmiAdxr() {
		return lydmiAdxr;
	}

	public void setLydmiAdxr(float lydmiAdxr) {
		this.lydmiAdxr = lydmiAdxr;
	}

	public float getLysarfVsum() {
		return lysarfVsum;
	}

	public void setLysarfVsum(float lysarfVsum) {
		this.lysarfVsum = lysarfVsum;
	}

	public float getLysarfVsumdif() {
		return lysarfVsumdif;
	}

	public void setLysarfVsumdif(float lysarfVsumdif) {
		this.lysarfVsumdif = lysarfVsumdif;
	}

	public float getLysarf() {
		return lysarf;
	}

	public void setLysarf(float lysarf) {
		this.lysarf = lysarf;
	}

	public float getLysarfVdif() {
		return lysarfVdif;
	}

	public void setLysarfVdif(float lysarfVdif) {
		this.lysarfVdif = lysarfVdif;
	}

	public float getLycjssFlags() {
		return lycjssFlags;
	}

	public void setLycjssFlags(float lycjssFlags) {
		this.lycjssFlags = lycjssFlags;
	}

	public float getLycjssVma() {
		return lycjssVma;
	}

	public static LycjssFlagData createData(CDataBasePo po) {
		LycjssFlagData data = JSON.parseObject(po.getDataBase(), LycjssFlagData.class);
		data.setDate(po.getId().getDate());
		data.setId(po.getId().getId());
		return data;
	}

	public void setLycjssVma(float lycjssVma) {
		this.lycjssVma = lycjssVma;
	}

	public float getLycjssVpr() {
		return lycjssVpr;
	}

	public void setLycjssVpr(float lycjssVpr) {
		this.lycjssVpr = lycjssVpr;
	}

	public float getMacdDif() {
		return macdDif;
	}

	public void setMacdDif(float macdDif) {
		this.macdDif = macdDif;
	}

	public float getMacdDea() {
		return macdDea;
	}

	public void setMacdDea(float macdDea) {
		this.macdDea = macdDea;
	}

	public float getMacdMacd() {
		return macdMacd;
	}

	public void setMacdMacd(float macdMacd) {
		this.macdMacd = macdMacd;
	}

	public float getLycjdmiFlags() {
		return lycjdmiFlags;
	}

	public void setLycjdmiFlags(float lycjdmiFlags) {
		this.lycjdmiFlags = lycjdmiFlags;
	}

	public float getLycjdmiFlagsums() {
		return lycjdmiFlagsums;
	}

	public void setLycjdmiFlagsums(float lycjdmiFlagsums) {
		this.lycjdmiFlagsums = lycjdmiFlagsums;
	}

	public float getLycjdmiVpr() {
		return lycjdmiVpr;
	}

	public void setLycjdmiVpr(float lycjdmiVpr) {
		this.lycjdmiVpr = lycjdmiVpr;
	}

	public float getLycjdmiHightsum() {
		return lycjdmiHightsum;
	}

	public void setLycjdmiHightsum(float lycjdmiHightsum) {
		this.lycjdmiHightsum = lycjdmiHightsum;
	}

	public float getLycjdmiVma() {
		return lycjdmiVma;
	}

	public void setLycjdmiVma(float lycjdmiVma) {
		this.lycjdmiVma = lycjdmiVma;
	}

	public float getLycjdmiVdif() {
		return lycjdmiVdif;
	}

	public void setLycjdmiVdif(float lycjdmiVdif) {
		this.lycjdmiVdif = lycjdmiVdif;
	}

	public float getLykdjBuyflag() {
		return lykdjBuyflag;
	}

	public void setLykdjBuyflag(float lykdjBuyflag) {
		this.lykdjBuyflag = lykdjBuyflag;
	}

	public float getLydmiBuyflag() {
		return lydmiBuyflag;
	}

	public void setLydmiBuyflag(float lydmiBuyflag) {
		this.lydmiBuyflag = lydmiBuyflag;
	}

	public float getLydmiMdi() {
		return lydmiMdi;
	}

	public void setLydmiMdi(float lydmiMdi) {
		this.lydmiMdi = lydmiMdi;
	}

	public float getLydmiSellflag() {
		return lydmiSellflag;
	}

	public void setLydmiSellflag(float lydmiSellflag) {
		this.lydmiSellflag = lydmiSellflag;
	}

	public float getLycjdmiFlagsumsshow() {
		return lycjdmiFlagsumsshow;
	}

	public void setLycjdmiFlagsumsshow(float lycjdmiFlagsumsshow) {
		this.lycjdmiFlagsumsshow = lycjdmiFlagsumsshow;
	}

	public History getBuyHistory() {
		return buyHistory;
	}

	public void setBuyHistory(History buyHistory) {
		this.buyHistory = buyHistory;
	}

}
