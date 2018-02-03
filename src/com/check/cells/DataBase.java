package com.check.cells;

import java.util.Date;

import com.util.RandomConfig;

public class DataBase {
	@RandomConfig(enable = false, calculateYestoday = false)
	private Date date;
	@RandomConfig(enable = false, calculateYestoday = false)
	private long id;
	@RandomConfig(enable = false, calculateYestoday = false)
	private float start = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private float high = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private float low = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
	private float close = 0;
	@RandomConfig(enable = false, calculateYestoday = false)
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

}
