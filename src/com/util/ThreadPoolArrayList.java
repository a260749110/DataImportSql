package com.util;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@SuppressWarnings("serial")
public class ThreadPoolArrayList<E> extends ArrayList<E> {
	private int poolSize = 1;

	public ThreadPoolArrayList(int poolSize) {
		this.poolSize = poolSize;
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		if(poolSize<=1)
		{
			super.forEach(action);
			return;
		}
		CountLock lock = new CountLock(poolSize);
		for (int i = 0; i < poolSize; i++) {
			threadRun(i, poolSize, action, lock);
		}

		lock.hold();

	}
	// @Override
	// public void forEach(Consumer<? super E> action) {
	// CountLock lock = new CountLock(size());
	// ExecutorService threadPool;
	// threadPool = Executors.newFixedThreadPool(poolSize);
	// super.forEach(e -> {
	// threadPool.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// action.accept(e);
	// lock.reduce(1);
	// }
	// });
	//
	// });
	//
	// lock.hold();
	// threadPool.shutdown();
	//
	// }

	private void threadRun(int index, int size, Consumer<? super E> action, CountLock lock) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				for (int i = index; i < size(); i += size) {
					action.accept(get(i));
				}
				lock.reduce(1);
			}
		});
		t.start();

	}

	public static void main(String[] args) {
		ThreadPoolArrayList<Integer> list = new ThreadPoolArrayList<>(4);
		for (int i = 0; i < 300; i++) {
			list.add(i);
		}
		list.forEach(i -> {
			System.err.println("start:" + i);
			long a = 0;
			long b = 2;
			long c = 0;
			for (int j = 0; j < 10000; j++) {
				for (int k = 0; k < 1000; k++) {

					a++;
					b--;
					a = a * 2 + b * 3;
					b = b * 5 + a;
					a = a * b;
					b = a * a;
					c = c + a;
					String s = c + "";
				}
			}
			System.err.println("end:" + i);

		});
		System.err.println("end锛侊紒锛侊紒锛侊紒锛�");
	}
}
