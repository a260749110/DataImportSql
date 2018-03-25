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
	@Value("${thread_num}")
	private Integer thread_num;

	@Value("${gaussian_per:0.5}")
	private Float gaussianPer;

	@Value("${gaussian_mul:2}")
	private Float gaussianMul;

	@Value("${sample_size}")
	private int sampleSize = 2;
	@Value("${swap_per}")
	private float swap_per;

	@Value("${mul_per:0.3}")
	private float mulPer;
	@Value("${mul_step:0.2}")
	private float mulStep;

	@Value("${cpu_use:2}")
	private int cpuUse;
	@Value("${pow_mi:2}")
	private float powMi;

	@Value("${calculatDays}")
	private String calculatDays;

	public String getDir() {
		return dir;
	}

	private int[] samples;

	public int[] getSimples() {
		if (samples == null) {
			String[] strs = calculatDays.split(",");
			samples = new int[strs.length];
			for (int i = 0; i < strs.length; i++) {
				samples[i] = Integer.valueOf(strs[i]);
			}
		}
		return samples;
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

	public Integer getThread_num() {
		return thread_num;
	}

	public void setThread_num(Integer thread_num) {
		this.thread_num = thread_num;
	}

	public int getSampleSize() {
		sampleSize = getSimples()[samples.length - 1];
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public float getSwap_per() {
		return swap_per;
	}

	public void setSwap_per(float swap_per) {
		this.swap_per = swap_per;
	}

	public String getCalculatDays() {
		return calculatDays;
	}

	public void setCalculatDays(String calculatDays) {
		this.calculatDays = calculatDays;
	}

	public int getCpuUse() {
		return cpuUse;
	}

	public void setCpuUse(int cpuUse) {
		this.cpuUse = cpuUse;
	}

	public Float getGaussianPer() {
		return gaussianPer;
	}

	public void setGaussianPer(Float gaussianPer) {
		this.gaussianPer = gaussianPer;
	}

	public Float getGaussianMul() {
		return gaussianMul;
	}

	public void setGaussianMul(Float gaussianMul) {
		this.gaussianMul = gaussianMul;
	}

	public float getPowMi() {
		return powMi;
	}

	public void setPowMi(float powMi) {
		this.powMi = powMi;
	}

	public float getMulPer() {
		return mulPer;
	}

	public void setMulPer(float mulPer) {
		this.mulPer = mulPer;
	}

	public float getMulStep() {
		return mulStep;
	}

	public void setMulStep(float mulStep) {
		this.mulStep = mulStep;
	}

}
