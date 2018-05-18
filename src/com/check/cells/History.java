package com.check.cells;

import java.text.ParseException;
import java.util.Date;

import com.util.Helper;

public class History {
	public long id;
	private Date start;
	private Date end;
	private String startStr;
	private String endStr;
	public double startMoney;
	public double endMoney;
	public int size;
	public double dif;
	public double nowWin;
	public double score = 1;
	private int allSize = 0;
	private int iSize = 0;
	public int index = 0;
	public boolean takeFlag = false;
	public LycjssFlagData now;
	public LycjssFlagData bf;
	public LycjssFlagData bbf;
	public double avgLycjdmiFlagsumsshow;
	public boolean buySuccessFlag = false;
	public double variance;
	private long startTime;
	private long endTime;
	public int buyI;
	public int usersI;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.startStr = Helper.dateFormat.format(start);
		this.startTime = start.getTime();

		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.endStr = Helper.dateFormat.format(end);
		this.endTime = end.getTime();

		this.end = end;
	}

	public double getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(double startMoney) {
		this.startMoney = startMoney;
	}

	public double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(double endMoney) {
		this.endMoney = endMoney;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getDif() {
		return dif;
	}

	public void setDif(double dif) {
		this.dif = dif;
	}

	public double getNowWin() {
		return nowWin;
	}

	public void setNowWin(double nowWin) {
		this.nowWin = nowWin;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the endStr
	 */
	public String getEndStr() {
		return endStr;
	}

	/**
	 * @param endStr
	 *            the endStr to set
	 */
	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	/**
	 * @return the startStr
	 */
	public String getStartStr() {
		return startStr;
	}

	/**
	 * @param startStr
	 *            the startStr to set
	 */
	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public int getAllSize() {
		return allSize;
	}

	public void setAllSize(int allSize) {
		this.allSize = allSize;
	}

	public int getiSize() {
		return iSize;
	}

	public void setiSize(int iSize) {
		this.iSize = iSize;
	}

}
