package com.ly.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.ly.web.job.JobThread;
import com.ly.web.job.WebJobBase;
import com.ly.web.job.event.BaseCMDEven;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;

@SuppressWarnings("serial")
public class BasseWebBrowser extends JWebBrowser {

	private JobThread currenThread;
	private Map<String, BaseCMDEven> eventMap = new HashMap<>();

	public BasseWebBrowser() {
		this.addWebBrowserListener(new CallFunction(this));
	}

	public void doJob(WebJobBase webJob) {
		webJob.setWebBrowser(this);
		currenThread = new JobThread(webJob);
		currenThread.doJob();

	}

	public JobThread getCurrenThread() {
		return currenThread;
	}

	public void setCurrenThread(JobThread currenThread) {
		this.currenThread = currenThread;
	}

	public void addEvent(BaseCMDEven event) {
		if (event.getEventJs() != null) {
			this.executeJavascript(event.getEventJs());
		}
		eventMap.put(event.getCmd(), event);

	}

	public void removeEvent(BaseCMDEven event) {
		removeEvent(event.getCmd());
	}

	public void removeEvent(String key) {
		eventMap.remove(key);
	}

	public void cmd(WebBrowserCommandEvent e) {
		BaseCMDEven event = eventMap.get(e.getCommand());
		if (event != null)
			event.onCmd(this, e.getParameters());
	}

	public static class CallFunction extends WebBrowserAdapter {

		private BasseWebBrowser webBrowser;

		public CallFunction(BasseWebBrowser webBrowser) {
			this.webBrowser = webBrowser;
		}

		@Override
		public void commandReceived(WebBrowserCommandEvent e) {
			webBrowser.cmd(e);
		}
	}
}
