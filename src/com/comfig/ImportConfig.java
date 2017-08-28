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
}
