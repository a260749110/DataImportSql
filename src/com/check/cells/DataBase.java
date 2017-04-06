package com.check.cells;

import java.util.Date;

public class DataBase {
	private Date date;
	private long id;
	private double start;
	private double high;
	private double low;
	private double close;
	private double deal;
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
