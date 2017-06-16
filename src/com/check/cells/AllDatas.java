package com.check.cells;

public class AllDatas extends LycjssFlagData {
	private int count;
	private static final int SIZE=20;
private ScoreList avgLycjdmiFlagsumsshow=new ScoreList(SIZE);
	public void add(LycjssFlagData data) {
		setLycjdmiFlagsumsshow(getLycjdmiFlagsumsshow() + data.getLycjdmiFlagsumsshow());
		avgLycjdmiFlagsumsshow.add(data.getLycjdmiFlagsumsshow());
		count++;
	}

	public double getCurrAvgLycjdmiFlagsumsshow() {
		if (count == 0)
			return 0;
		return getLycjdmiFlagsumsshow() / count;

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
