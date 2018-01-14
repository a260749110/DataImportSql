package com.web;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ly.web.BasseWebBrowser;
import com.web.event.TestEvent;
import com.web.job.LoginJob;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;

public class WebMain {
	protected static final String LS = System.getProperty("line.separator");
	private BasseWebBrowser webBrowser = new BasseWebBrowser();

	public JComponent createContent() {
		JPanel contentPane = new JPanel(new BorderLayout(5, 5));
		JPanel commandPanel = new JPanel(new BorderLayout());
		commandPanel.add(new JLabel("Received command: "), BorderLayout.WEST);
		commandPanel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
		final JTextField receivedCommandTextField = new JTextField();
		commandPanel.add(receivedCommandTextField, BorderLayout.CENTER);
		contentPane.add(commandPanel, BorderLayout.SOUTH);
		JPanel webBrowserPanel = new JPanel(new BorderLayout());
		webBrowserPanel.setBorder(BorderFactory.createTitledBorder("Native Web Browser component"));
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(true);

		webBrowser
				.navigate("http://upass.10jqka.com.cn/login?redir=HTTP_REFERER&sign=8fd3cc7c13f0c48fa2ff383a74a7b5b4");
		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
		contentPane.add(webBrowserPanel, BorderLayout.CENTER);
		return contentPane;
	}

	/* Standard main method to try that test as a standalone application. */
	public static void main(String[] args) {
		NativeInterface.open();
		UIUtils.setPreferredLookAndFeel();
		WebMain webMain = new WebMain();
		webMain.webBrowser.addEvent(new TestEvent());

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("DJ Native Swing Test");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(webMain.createContent(), BorderLayout.CENTER);
				frame.setSize(800, 600);
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
				webMain.webBrowser.doJob(new LoginJob());

			}
		});
		NativeInterface.runEventPump();
	}
}
