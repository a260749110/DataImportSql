package com.bigcalculate.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.bigcalculate.job.DaySimulationJob.YearResult;

public class CalculateNode {
	private Map<String, Float> todayP = new HashMap<>();
	private Map<String, Float> yestodayP = new HashMap<>();
	private List<Map<String, Float>> parMapList = new ArrayList<>();
	private List<Map<String, Float>> par2MapList = new ArrayList<>();
	// private List<Map<String, Float>> parPwoMapList = new ArrayList<>();
	// private List<Map<String, Float>> parPowAMapList =new ArrayList<>();
	private float pc = 0;
	private Map<String, Float> para = new HashMap<>();
	private Map<String, Float> parb = new HashMap<>();
	private Map<String, YearResult> yearInfo = new HashMap<>();
	private double score;
	private double sScore;
	private double dscore;
	private int cacheId = 0;
	@JSONField(deserialize = false, serialize = false)
	private HashSet<String> changeSet;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getsScore() {
		return sScore;
	}

	public void setsScore(double sScore) {
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
	 * @param para
	 *            the para to set
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
	 * @param parb
	 *            the parb to set
	 */
	public void setParb(Map<String, Float> parb) {
		this.parb = parb;
	}

	public double getDscore() {
		return dscore;
	}

	public void setDscore(double dscore) {
		this.dscore = dscore;
	}

	public float getPc() {
		return pc;
	}

	public void setPc(float pc) {
		this.pc = pc;
	}

	/**
	 * @return the cacheId
	 */
	public int getCacheId() {
		return cacheId;
	}

	/**
	 * @param cacheId
	 *            the cacheId to set
	 */
	public void setCacheId(int cacheId) {
		this.cacheId = cacheId;
	}


	public HashSet<String> getChangeSet() {
		return changeSet;
	}

	public void setChangeSet(HashSet<String> changeSet) {
		this.changeSet = changeSet;
	}

	// public List<Map<String, Float>> getParPwoMapList() {
	// return parPwoMapList;
	// }
	//
	// public void setParPwoMapList(List<Map<String, Float>> parPwoMapList) {
	// this.parPwoMapList = parPwoMapList;
	// }
	//
	//
	// public List<Map<String, Float>> getParPowAMapList() {
	// return parPowAMapList;
	// }
	//
	// public void setParPowAMapList(List<Map<String, Float>> parPowAMapList) {
	// this.parPowAMapList = parPowAMapList;
	// }
}
