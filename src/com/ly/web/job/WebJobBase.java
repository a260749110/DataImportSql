package com.ly.web.job;

import com.ly.web.BasseWebBrowser;

public abstract class WebJobBase {

	private WebJobBase nextJob;
	private WebJobBase befJobJob;
	private long startDelay;
	private long finishDelay;
	private BasseWebBrowser webBrowser;

	public abstract void doJob();

	public WebJobBase getNextJob() {
		return nextJob;
	}

	public void setNextJob(WebJobBase nextJob) {
		this.nextJob = nextJob;
	}

	public WebJobBase getBefJobJob() {
		return befJobJob;
	}

	public void setBefJobJob(WebJobBase befJobJob) {
		this.befJobJob = befJobJob;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public long getFinishDelay() {
		return finishDelay;
	}

	public void setFinishDelay(long finishDelay) {
		this.finishDelay = finishDelay;
	}

	public BasseWebBrowser getWebBrowser() {
		return webBrowser;
	}

	public void setWebBrowser(BasseWebBrowser webBrowser) {
		this.webBrowser = webBrowser;
	}

}
