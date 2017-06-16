package com.check.cells;

public class ScoreColdList {
	private double[] scores;
	private double score;
	private int index = 0;
	private double cold = 0.008;
	private double useHavey = 1d/180d;

	public ScoreColdList(int size) {
		scores = new double[size];
		for (int i = 0; i < scores.length; i++) {
			scores[i] = 0;
		}
	}

	public double add(double s, int count) {
//		s *= (120+count )* useHavey;
		score = 0;
		scores[index] = s;
		float rate = 1;
		for (int i = 0; i < scores.length; i++) {
			score += scores[(index+scores.length + i) % scores.length] * rate;
			rate *= 1 - getCold();
		}
		index++;
		index = index % scores.length;
		return score;

	}

	public double getScore() {
		return Double.valueOf(score).doubleValue();
	}

	public double getCold() {
		return cold;
	}

	public void setCold(double cold) {
		this.cold = cold;
	}

}
