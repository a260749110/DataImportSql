package com.check.cells;

public class SuccessScore {
	private int count = 0;
	private double score = 0;

	public void add(double score) {
		this.score += score;
		this.count++;
	}

	public double getAvg() {
		return count == 0 ? 0 : getScore() / count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}



}
