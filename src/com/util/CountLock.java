package com.util;

public class CountLock {
	private int size;

	public CountLock(int size) {
		this.size = size;
	}

	public synchronized void reduce(int i) {
		size -= i;
		if (size <= 0) {
			synchronized (this) {
				try {
					notifyAll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void hold() {
		synchronized (this) {
			try {
				wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
