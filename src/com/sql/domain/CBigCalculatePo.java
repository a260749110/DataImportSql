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
public class CBigCalculatePo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "data_base")
	private String dataBase;
	@Column(name = "score")
	private double score;
	@Column(name = "s_score")
	private double sScore;
	@Column(name = "dscore")
	private double dscore;

	public CBigCalculatePo() {
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

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getsScore() {
		return sScore;
	}

	public void setsScore(double sScore) {
		this.sScore = sScore;
	}

	public double getDscore() {
		return dscore;
	}

	public void setDscore(double dscore) {
		this.dscore = dscore;
	}

}