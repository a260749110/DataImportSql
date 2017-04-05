package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the c_data_base database table.
 * 
 */
@Embeddable
public class CDataBasePoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="`date`")
	private java.util.Date date;

	public CDataBasePoPK() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public java.util.Date getDate() {
		return this.date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CDataBasePoPK)) {
			return false;
		}
		CDataBasePoPK castOther = (CDataBasePoPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.date.equals(castOther.date);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.date.hashCode();
		
		return hash;
	}
}