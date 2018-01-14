package com.ly.web.job.event;

import com.ly.web.BasseWebBrowser;

public abstract class BaseCMDEven {
	private String cmd;
	private String eventJs;

	public String getEventJs() {
		return eventJs;
	}

	public void setEventJs(String eventJs) {
		this.eventJs = eventJs;
	}

	public BaseCMDEven(String cmd) {
		this.cmd = cmd;
	}

	public abstract void onCmd(BasseWebBrowser web, Object[] params);

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
