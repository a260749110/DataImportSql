package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * The persistent class for the parameter database table.
 * 
 */
@Entity
@Table(name = "parameter")
@DynamicInsert
@DynamicUpdate
public class ParameterPo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, length = 127)
	private String name;

	@Column(nullable = false)
	private double learning;

	@Column(nullable = false)
	private double rate;

	@Lob
	@Column(nullable = false)
	private String values;

	public ParameterPo() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLearning() {
		return this.learning;
	}

	public void setLearning(double learning) {
		this.learning = learning;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
	}

}