package com.bigcalculate.cell;

import java.util.HashMap;
import java.util.Map;

public class CalculateNode {
	private Map<String, Float> todayP = new HashMap<>();
	private Map<String, Float> yestodayP = new HashMap<>();
	private float score;
	private float sScore;
	public Map<String, Float> getTodayP() {
		return todayP;
	}

	public void setTodayP(Map<String, Float> todayP) {
		this.todayP = todayP;
	}

	public Map<String, Float> getYestodayP() {
		return yestodayP;
	}

	public void setYestodayP(Map<String, Float> yestodayP) {
		this.yestodayP = yestodayP;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getsScore() {
		return sScore;
	}

	public void setsScore(float sScore) {
		this.sScore = sScore;
	}
}
