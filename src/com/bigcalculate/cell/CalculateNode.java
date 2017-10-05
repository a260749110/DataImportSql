package com.bigcalculate.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bigcalculate.job.DaySimulationJob.YearResult;

public class CalculateNode {
	private Map<String, Float> todayP = new HashMap<>();
	private Map<String, Float> yestodayP = new HashMap<>();
	private List<Map<String, Float>> parMapList = new ArrayList<>();
	private Map<String, YearResult> yearInfo = new HashMap<>();
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

	public List<Map<String, Float>> getParMapList() {
		return parMapList;
	}

	public void setParMapList(List<Map<String, Float>> parMapList) {
		this.parMapList = parMapList;
	}

	public Map<String, YearResult> getYearInfo() {
		return yearInfo;
	}

	public void setYearInfo(Map<String, YearResult> yearInfo) {
		this.yearInfo = yearInfo;
	}
}
