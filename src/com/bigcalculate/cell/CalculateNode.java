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
	private List<Map<String, Float>> par2MapList = new ArrayList<>();
	private Map<String, Float> para = new HashMap<>();
	private Map<String, Float> parb = new HashMap<>();
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

	public List<Map<String, Float>> getPar2MapList() {
		return par2MapList;
	}

	public void setPar2MapList(List<Map<String, Float>> par2MapList) {
		this.par2MapList = par2MapList;
	}

	/**
	 * @return the para
	 */
	public Map<String, Float> getPara() {
		return para;
	}

	/**
	 * @param para the para to set
	 */
	public void setPara(Map<String, Float> para) {
		this.para = para;
	}

	/**
	 * @return the parb
	 */
	public Map<String, Float> getParb() {
		return parb;
	}

	/**
	 * @param parb the parb to set
	 */
	public void setParb(Map<String, Float> parb) {
		this.parb = parb;
	}
}
