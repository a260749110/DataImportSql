package com.sql.dto;

public class ParameterDto {

	private String name;

	private double learning;

	private double rate;

	private double[] values;

	public ParameterDto() {
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

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double[] getValues() {
		return this.values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

}
