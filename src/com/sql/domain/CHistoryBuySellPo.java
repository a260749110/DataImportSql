package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the c_history_buy_sell database table.
 * 
 */
@Entity
@Table(name="c_history_buy_sell")
public class CHistoryBuySellPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CHistoryBuySellPK id;

	@Column(name="buy_money")
	private double buyMoney;

	private double dif;

	private String others;

	private double rate;

	@Column(name="sell_money")
	private double sellMoney;

	@Column(name="user_day")
	private int userDay;

	public CHistoryBuySellPo() {
	}

	public CHistoryBuySellPK getId() {
		return this.id;
	}

	public void setId(CHistoryBuySellPK id) {
		this.id = id;
	}

	public double getBuyMoney() {
		return this.buyMoney;
	}

	public void setBuyMoney(double buyMoney) {
		this.buyMoney = buyMoney;
	}

	public double getDif() {
		return this.dif;
	}

	public void setDif(double dif) {
		this.dif = dif;
	}

	public String getOthers() {
		return this.others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getSellMoney() {
		return this.sellMoney;
	}

	public void setSellMoney(double sellMoney) {
		this.sellMoney = sellMoney;
	}

	public int getUserDay() {
		return this.userDay;
	}

	public void setUserDay(int userDay) {
		this.userDay = userDay;
	}

}