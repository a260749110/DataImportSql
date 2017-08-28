package com.autorun;

import java.awt.Robot;

import com.comfig.ImportConfig;

public class AutoImport {

	private String src;
	private String toSrc;
	private Robot robot;
	public AutoImport() {
		src = ImportConfig.getInstance().getDir();
		toSrc = ImportConfig.getInstance().getSaveSrc();
	}

}
