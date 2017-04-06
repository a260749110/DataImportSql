package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the c_data_result database table.
 * 
 */
@Embeddable
public class CDataResultPoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;

	public CDataResultPoPK() {
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CDataResultPoPK)) {
			return false;
		}
		CDataResultPoPK castOther = (CDataResultPoPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.type.equals(castOther.type);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.type.hashCode();
		
		return hash;
	}
}