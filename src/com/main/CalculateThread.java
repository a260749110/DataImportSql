package com.main;

public class CalculateThread {
	private Thread thread;

	public CalculateThread() {
		thread=new Thread(new  Runnable() {
			public void run() {
				
			}
		});
		
	}
	private void run()
	{
		
	}
	public void start() {
		thread.start();
	}
}
