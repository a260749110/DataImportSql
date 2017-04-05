package com.sql.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the data_base database table.
 * 
 */
@Entity
@Table(name="data_base")
@DynamicInsert
@DynamicUpdate
public class DataBasePo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DataBasePoPK id;

	private float end;

	private float start;

	private float turnover;

	@Column(name="turnover_volume")
	private float turnoverVolume;

	public DataBasePo() {
	}

	public DataBasePoPK getId() {
		return this.id;
	}

	public void setId(DataBasePoPK id) {
		this.id = id;
	}

	public float getEnd() {
		return this.end;
	}

	public void setEnd(float end) {
		this.end = end;
	}

	public float getStart() {
		return this.start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getTurnover() {
		return this.turnover;
	}

	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}

	public float getTurnoverVolume() {
		return this.turnoverVolume;
	}

	public void setTurnoverVolume(float turnoverVolume) {
		this.turnoverVolume = turnoverVolume;
	}

}