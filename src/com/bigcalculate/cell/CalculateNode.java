package com.bigcalculate.cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.bigcalculate.job.DaySimulationJob.YearResult;
import com.check.cells.LycjssFlagData;

public class CalculateNode {
	private LycjssFlagData todayP =new LycjssFlagData() ;
	private LycjssFlagData yestodayP =new LycjssFlagData() ;
	private List<LycjssFlagData> parMapList = new ArrayList<>();
	private List<LycjssFlagData> par2MapList = new ArrayList<>();
	// private List<Map<String, Float>> parPwoMapList = new ArrayList<>();
	// private List<Map<String, Float>> parPowAMapList =new ArrayList<>();
	private float pc = 0;
	private LycjssFlagData para =new LycjssFlagData() ;
	private LycjssFlagData parb =new LycjssFlagData() ;
	private Map<String, YearResult> yearInfo = new HashMap<>();
	private double score;
	private double sScore;
	private double dscore;
	private int cacheId = 0;
	@JSONField(deserialize = false, serialize = false)
	private HashSet<String> changeSet;
	/**
	 * @return the todayP
	 */
	public LycjssFlagData getTodayP() {
		return todayP;
	}
	/**
	 * @param todayP the todayP to set
	 */
	public void setTodayP(LycjssFlagData todayP) {
		this.todayP = todayP;
	}
	/**
	 * @return the yestodayP
	 */
	public LycjssFlagData getYestodayP() {
		return yestodayP;
	}
	/**
	 * @param yestodayP the yestodayP to set
	 */
	public void setYestodayP(LycjssFlagData yestodayP) {
		this.yestodayP = yestodayP;
	}
	/**
	 * @return the parMapList
	 */
	public List<LycjssFlagData> getParMapList() {
		return parMapList;
	}
	/**
	 * @param parMapList the parMapList to set
	 */
	public void setParMapList(List<LycjssFlagData> parMapList) {
		this.parMapList = parMapList;
	}
	/**
	 * @return the par2MapList
	 */
	public List<LycjssFlagData> getPar2MapList() {
		return par2MapList;
	}
	/**
	 * @param par2MapList the par2MapList to set
	 */
	public void setPar2MapList(List<LycjssFlagData> par2MapList) {
		this.par2MapList = par2MapList;
	}
	/**
	 * @return the pc
	 */
	public float getPc() {
		return pc;
	}
	/**
	 * @param pc the pc to set
	 */
	public void setPc(float pc) {
		this.pc = pc;
	}
	/**
	 * @return the para
	 */
	public LycjssFlagData getPara() {
		return para;
	}
	/**
	 * @param para the para to set
	 */
	public void setPara(LycjssFlagData para) {
		this.para = para;
	}
	/**
	 * @return the parb
	 */
	public LycjssFlagData getParb() {
		return parb;
	}
	/**
	 * @param parb the parb to set
	 */
	public void setParb(LycjssFlagData parb) {
		this.parb = parb;
	}
	/**
	 * @return the yearInfo
	 */
	public Map<String, YearResult> getYearInfo() {
		return yearInfo;
	}
	/**
	 * @param yearInfo the yearInfo to set
	 */
	public void setYearInfo(Map<String, YearResult> yearInfo) {
		this.yearInfo = yearInfo;
	}
	/**
	 * @return the score
	 */
	public double getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * @return the sScore
	 */
	public double getsScore() {
		return sScore;
	}
	/**
	 * @param sScore the sScore to set
	 */
	public void setsScore(double sScore) {
		this.sScore = sScore;
	}
	/**
	 * @return the dscore
	 */
	public double getDscore() {
		return dscore;
	}
	/**
	 * @param dscore the dscore to set
	 */
	public void setDscore(double dscore) {
		this.dscore = dscore;
	}
	/**
	 * @return the cacheId
	 */
	public int getCacheId() {
		return cacheId;
	}
	/**
	 * @param cacheId the cacheId to set
	 */
	public void setCacheId(int cacheId) {
		this.cacheId = cacheId;
	}
	/**
	 * @return the changeSet
	 */
	public HashSet<String> getChangeSet() {
		return changeSet;
	}
	/**
	 * @param changeSet the changeSet to set
	 */
	public void setChangeSet(HashSet<String> changeSet) {
		this.changeSet = changeSet;
	}

}
