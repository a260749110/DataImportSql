package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the c_data_result database table.
 * 
 */
@Entity
@Table(name="c_data_result")
public class CDataResultPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CDataResultPoPK id;
	
	private double avg;
	private String other;
	private double result;
	
	private double score;
	private int size;

	public CDataResultPo() {
	}

	public CDataResultPoPK getId() {
		return this.id;
	}

	public void setId(CDataResultPoPK id) {
		this.id = id;
	}

	public double getAvg() {
		return this.avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public double getResult() {
		return this.result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}