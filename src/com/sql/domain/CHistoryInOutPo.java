package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_history_in_out database table.
 * 
 */
@Entity
@Table(name = "c_history_in_out")
public class CHistoryInOutPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mark_date")
	private Date markDate;

	@Column(name = "money_all")
	private double moneyAll;

	@Column(name = "money_in")
	private double moneyIn;

	@Column(name = "money_in_all")
	private double moneyInAll;
	
	@Column(name = "money_out")
	private double moneyOut;

	@Column(name = "money_out_all")
	private double moneyOutAll;
	
	public double getMoneyOutAll() {
		return moneyOutAll;
	}

	public void setMoneyOutAll(double moneyOutAll) {
		this.moneyOutAll = moneyOutAll;
	}

	@Column(name = "others")
	private String others;

	public CHistoryInOutPo() {
	}

	public Date getMarkDate() {
		return this.markDate;
	}

	public void setMarkDate(Date markDate) {
		this.markDate = markDate;
	}

	public double getMoneyAll() {
		return this.moneyAll;
	}

	public void setMoneyAll(double moneyAll) {
		this.moneyAll = moneyAll;
	}

	public double getMoneyIn() {
		return this.moneyIn;
	}

	public void setMoneyIn(double moneyIn) {
		this.moneyIn = moneyIn;
	}

	public double getMoneyOut() {
		return this.moneyOut;
	}

	public void setMoneyOut(double moneyOut) {
		this.moneyOut = moneyOut;
	}

	public String getOthers() {
		return this.others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public double getMoneyInAll() {
		return moneyInAll;
	}

	public void setMoneyInAll(double moneyInAll) {
		this.moneyInAll = moneyInAll;
	}

}