package com.check.cells;

import java.util.Date;

import com.util.RandomConfig;

public class DataBase {
	@RandomConfig(enable = false, calculateYestoday = false)
	private Date date;
	@RandomConfig(enable = false, calculateYestoday = false)
	private long id;
	@RandomConfig(enable = false, calculateYestoday = false)
	private double start = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private double high = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private double low = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private double close = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
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

}
