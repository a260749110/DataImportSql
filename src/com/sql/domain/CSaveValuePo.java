package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the c_save_values database table.
 * 
 */
@Entity
@Table(name="c_save_values")
public class CSaveValuePo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "`id`")
	private int id;
	@Column(name = "`others`")
	private String others;

	public CSaveValuePo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOthers() {
		return this.others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

}