package com.dataImport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryCell {

	private Date date;
	private long id;
	private int count;
	private float money;
	private boolean isBuy;
	private static DateFormat format = new SimpleDateFormat("yyyyMMdd");

	public HistoryCell(String str) {
		String[] datas = str.split("\t");
		try {
			date = format.parse(datas[0]);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		id = Long.valueOf(datas[1]);
		count = Integer.valueOf(datas[4]);
		money = Float.valueOf(datas[5]);
		isBuy = datas[3].contains("¬Ú»Î");
	}

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public boolean isBuy() {
		return isBuy;
	}

	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
}
