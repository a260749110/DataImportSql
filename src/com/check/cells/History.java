package com.check.cells;

public class History {
	public long id;
	public String start;
	public String end;
	public double startMoney;
	public double endMoney;
	public int size;
	public double dif;
	public double nowWin;
	public double score = 1;
	public int index = 0;
	public boolean takeFlag = false;
	public LycjssFlagData now;
	public LycjssFlagData bf;
	public LycjssFlagData bbf;
	public double avgLycjdmiFlagsumsshow;
	public boolean buySuccessFlag = false;
	public double variance;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
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

}
