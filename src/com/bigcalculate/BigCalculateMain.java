package com.bigcalculate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.bigcalculate.job.Caluculate;
import com.bigcalculate.job.DaySimulationJob;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;
import com.util.Helper;

public class BigCalculateMain {

	public static void main(String[] args) {
		Thread t = new Thread() {
			public void run() {
				run1();
			};
		};
		t.start();

	}

	private static List<CalculateNode> nodes = new ArrayList<>();
	private static int size = 500;

	private static void run1() {
		while (true) {
			try {
				
				BigCalculateJob bigCalculateJob = new BigCalculateJob();
				bigCalculateJob.init();

				int id = nextInt();
				CBigCalculatePo cb = AppContextUtil.instance.getCBigCalculateDao().findOne(id);
				if (cb == null) {
					cb = new CBigCalculatePo();
				}
				cb = bigCalculateJob.run(cb);
				AppContextUtil.instance.getCBigCalculateDao().save(cb);
				Helper.showMem();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private static Random random = new Random();

	private static int nextInt() {
		return random.nextInt((int) ImportConfig.getInstance().getMax_size());

	}

	public static void fun() {
		long id = 1;

		CalculateNode node = new CalculateNode();
		nodes.add(node);
		DecimalFormat df = new DecimalFormat("0.00");
		Random random = new Random();
		for (int i = 0; i < 5000000; i++) {
			if (nodes.size() >= size) {
				node = nodes.get(random.nextInt(nodes.size()));
			} else {
				System.err.println("new");
				node = new CalculateNode();
			}
			node = Helper.randomNode(node);
			nodes.add(node);
			Caluculate caluculate = new Caluculate(node, new DaySimulationJob());
			caluculate.run(id);
			node.setScore((float) caluculate.resultAll);
			if (nodes.size() > size) {
				CalculateNode nodeRemove = null;
				for (CalculateNode n : nodes) {
					if (nodeRemove == null) {
						nodeRemove = n;
					} else {
						if (nodeRemove.getScore() > n.getScore()) {
							nodeRemove = n;
						}
					}
				}
				if (nodeRemove != null) {
					nodes.remove(nodeRemove);
					nodeRemove.getTodayP().clear();
					nodeRemove.getYestodayP().clear();
				}
			}

			// 显示JVM总内存
			long totalMem = Runtime.getRuntime().totalMemory();
			System.out.println(df.format(totalMem / 1000000F) + " MB");
			// 显示JVM尝试使用的最大内存
			long maxMem = Runtime.getRuntime().maxMemory();
			System.out.println(df.format(maxMem / 1000000F) + " MB");
			// 空闲内存
			long freeMem = Runtime.getRuntime().freeMemory();
			System.out.println(df.format(freeMem / 1000000F) + " MB");
			System.err.println("size:" + nodes.size());

			if (i % 1000 == 0) {
				System.gc();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
