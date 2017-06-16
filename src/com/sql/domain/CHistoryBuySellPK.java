package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the c_history_buy_sell database table.
 * 
 */
@Embeddable
public class CHistoryBuySellPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="buy_date")
	private java.util.Date buyDate;

	@Temporal(TemporalType.DATE)
	@Column(name="sell_date")
	private java.util.Date sellDate;

	public CHistoryBuySellPK() {
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.util.Date getBuyDate() {
		return this.buyDate;
	}
	public void setBuyDate(java.util.Date buyDate) {
		this.buyDate = buyDate;
	}
	public java.util.Date getSellDate() {
		return this.sellDate;
	}
	public void setSellDate(java.util.Date sellDate) {
		this.sellDate = sellDate;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CHistoryBuySellPK)) {
			return false;
		}
		CHistoryBuySellPK castOther = (CHistoryBuySellPK)other;
		return 
			(this.id == castOther.id)
			&& this.buyDate.equals(castOther.buyDate)
			&& this.sellDate.equals(castOther.sellDate);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id;
		hash = hash * prime + this.buyDate.hashCode();
		hash = hash * prime + this.sellDate.hashCode();
		
		return hash;
	}
}