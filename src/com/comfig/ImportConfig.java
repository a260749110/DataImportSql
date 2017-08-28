package com.comfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.util.AppContextUtil;

@Service
public class ImportConfig {
	private static ImportConfig instance;
	@Value(value = "${data_src}")
	private String dir;
	@Value("${save_src}")
	private String save_src;
	
	@Value("${pre_random}")
	private Float pre_random;
	@Value("${def_parameter}")
	private Float def_parameter;
	@Value("${max_random}")
	private Float max_random;
	@Value("${min_random}")
	private Float min_random;
	@Value("${step_random}")
	private Float step_random;
	@Value("${max_size}")
	private Float max_size;
	@Value("${save_size}")
	private Integer save_size;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getSaveSrc() {
		return save_src;
	}

	public void setSave_src(String save_src) {
		this.save_src = save_src;
	}

	public static ImportConfig getInstance() {
		if (instance == null)
			instance = AppContextUtil.getContext().getBean(ImportConfig.class);
		return instance;
	}

	public float getMax_random() {
		return max_random;
	}

	public void setMax_random(float max_random) {
		this.max_random = max_random;
	}

	public float getMin_random() {
		return min_random;
	}

	public void setMin_random(float min_random) {
		this.min_random = min_random;
	}

	public float getStep_random() {
		return step_random;
	}

	public void setStep_random(float step_random) {
		this.step_random = step_random;
	}

	public String getSave_src() {
		return save_src;
	}

	public float getMax_size() {
		return max_size;
	}

	public void setMax_size(float max_size) {
		this.max_size = max_size;
	}

	public float getDef_parameter() {
		return def_parameter;
	}

	public void setDef_parameter(float def_parameter) {
		this.def_parameter = def_parameter;
	}

	public int getSave_size() {
		return save_size;
	}

	public void setSave_size(int save_size) {
		this.save_size = save_size;
	}

	public static void setInstance(ImportConfig instance) {
		ImportConfig.instance = instance;
	}

	public float getPre_random() {
		return pre_random;
	}

	public void setPre_random(float pre_random) {
		this.pre_random = pre_random;
	}
}
