package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the c_big_calculate database table.
 * 
 */
@Entity
@Table(name = "c_big_calculate")
@DynamicInsert
@DynamicUpdate
public class CBigCalculate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "data_base")
	private String dataBase;
	@Column(name = "score")
	private float score;
	@Column(name = "s_score")
	private float sScore;
	public CBigCalculate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataBase() {
		return this.dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}