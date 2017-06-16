package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the c_buy_sell database table.
 * 
 */
@Entity
@Table(name="c_buy_sell")
public class CBuySellPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="buy_date")
	private Date buyDate;

	@Column(name="buy_money")
	private Double buyMoney;

	@Column(name="buy_size")
	private int buySize;

	@Column(name="dif_money")
	private Double difMoney;

	@Column(name="dif_rate")
	private Double difRate;

	@Column(name="is_sell")
	private byte isSell;

	@Temporal(TemporalType.DATE)
	@Column(name="last_date")
	private Date lastDate;

	@Column(name="last_dif")
	private Double lastDif;

	@Column(name="last_money")
	private Double lastMoney;

	@Temporal(TemporalType.DATE)
	@Column(name="sell_date")
	private Date sellDate;

	@Column(name="sell_money")
	private Double sellMoney;

	public CBuySellPo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBuyDate() {
		return this.buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public double getBuyMoney() {
		return this.buyMoney;
	}

	public void setBuyMoney(Double buyMoney) {
		this.buyMoney = buyMoney;
	}

	public int getBuySize() {
		return this.buySize;
	}

	public void setBuySize(int buySize) {
		this.buySize = buySize;
	}

	public double getDifMoney() {
		return this.difMoney;
	}

	public void setDifMoney(Double difMoney) {
		this.difMoney = difMoney;
	}

	public double getDifRate() {
		return this.difRate;
	}

	public void setDifRate(Double difRate) {
		this.difRate = difRate;
	}

	public byte getIsSell() {
		return this.isSell;
	}

	public void setIsSell(byte isSell) {
		this.isSell = isSell;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public double getLastDif() {
		return this.lastDif;
	}

	public void setLastDif(Double lastDif) {
		this.lastDif = lastDif;
	}

	public double getLastMoney() {
		return this.lastMoney;
	}

	public void setLastMoney(Double lastMoney) {
		this.lastMoney = lastMoney;
	}

	public Date getSellDate() {
		return this.sellDate;
	}

	public void setSellDate(Date sellDate) {
		this.sellDate = sellDate;
	}

	public double getSellMoney() {
		return this.sellMoney;
	}

	public void setSellMoney(Double sellMoney) {
		this.sellMoney = sellMoney;
	}

}