package com.web.event;

import com.ly.web.BasseWebBrowser;
import com.ly.web.job.event.BaseCMDEven;

public class TestEvent extends BaseCMDEven {

	public TestEvent() {
		super("test");
	}

	@Override
	public void onCmd(BasseWebBrowser web, Object[] params) {
		System.err.println("hahahahaha");
	}

}
