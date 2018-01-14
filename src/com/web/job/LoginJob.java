package com.web.job;

import com.ly.web.job.WebJobBase;

public class LoginJob extends WebJobBase {
	public LoginJob() {
		setStartDelay(10000L);

	}

	@Override
	public void doJob() {
		System.err.println("aaa");
		getWebBrowser().executeJavascript("document.getElementById('username').value='asddssss';");
		getWebBrowser().executeJavascript("alert('aaa')");

	}

}
