package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the c_data_base database table.
 * 
 */
@Entity
@Table(name="c_data_base")
@DynamicInsert
@DynamicUpdate
public class CDataBasePo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CDataBasePoPK id;

	@Column(name="data_base")
	private String dataBase;
	@Column(name="`other`")
	private String other="";

	public CDataBasePo() {
	}

	public CDataBasePoPK getId() {
		return this.id;
	}

	public void setId(CDataBasePoPK id) {
		this.id = id;
	}

	public String getDataBase() {
		return this.dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

}