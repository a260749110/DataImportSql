package com.ly.web.job;

import java.awt.AWTEvent;
import java.awt.Event;

public class JobThread implements Runnable {

	private WebJobBase webJob;
	private Thread t;

	public JobThread(WebJobBase webJob) {
		this.webJob = webJob;

	}

	public void doJob() {

		t = new Thread(new Runnable() {
			public void run() {

				delay(webJob.getStartDelay());
				webJob.getWebBrowser().runInSequence(this);

				delay(webJob.getFinishDelay());
				if (webJob.getNextJob() != null) {
					webJob.getNextJob().setWebBrowser(webJob.getWebBrowser());
					webJob.getNextJob().getWebBrowser().doJob(webJob.getNextJob());
				}
				webJob.getWebBrowser().dispatchEvent(new AWTEvent(new Event(webJob.getWebBrowser(), 1, "")) {
				});
			}
		});
	}

	@Override
	public void run() {
		webJob.doJob();

	}

	public void delay(long delay) {
		if (delay > 0) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
